package com.example.unieventos.services;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.dto.ResenaDTO;
import com.example.unieventos.models.*;
import com.example.unieventos.repositories.ResenaMultimediaRepository;
import com.example.unieventos.repositories.ResenaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {

    private final ResenaRepository repository;
    private final ResenaMultimediaRepository multimediaRepository;

    public ResenaService(ResenaRepository repository, ResenaMultimediaRepository multimediaRepository) {
        this.repository = repository;
        this.multimediaRepository = multimediaRepository;
    }

    // ─── CRUD básico ─────────────────────────────────────────────────────────────

    public ApiResponse<Resena> crearResena(Resena resena) {
        try {
            // Validar calificacion entre 1 y 5
            if (resena.getCalificacion() < 1 || resena.getCalificacion() > 5) {
                return ApiResponse.noDataValue(500, "La calificación debe estar entre 1 y 5");
            }

            // Un usuario solo puede tener una reseña por asistencia
            if (repository.existeResenaParaAsistencia(resena.getAsistencia())) {
                return ApiResponse.noDataValue(500, "Ya existe una reseña para esta asistencia");
            }

            resena.setFechaDeCreacion(LocalDateTime.now());
            resena.setActivo(true);

            Resena nueva = repository.save(resena);
            return ApiResponse.created(nueva);
        } catch (Exception e) {
            return ApiResponse.noDataValue(500, e.toString());
        }
    }

    public ApiResponse<Resena> actualizarResena(Resena resena) {
        try {
            Optional<Resena> existente = repository.findById(resena.getId());
            if (existente.isEmpty()) {
                return ApiResponse.noDataValue(404, "Reseña no encontrada");
            }

            Resena r = existente.get();

            if (resena.getCalificacion() < 1 || resena.getCalificacion() > 5) {
                return ApiResponse.noDataValue(500, "La calificación debe estar entre 1 y 5");
            }

            r.setTitulo(resena.getTitulo());
            r.setDescripcion(resena.getDescripcion());
            r.setCalificacion(resena.getCalificacion());
            r.setFechaDeModificacion(LocalDateTime.now());

            return ApiResponse.success(repository.save(r));
        } catch (Exception e) {
            return ApiResponse.noDataValue(500, e.toString());
        }
    }

    public ApiResponse<Resena> eliminarResena(Integer id) {
        try {
            Optional<Resena> existente = repository.findById(id);
            if (existente.isEmpty()) {
                return ApiResponse.noDataValue(404, "Reseña no encontrada");
            }
            Resena r = existente.get();
            r.setActivo(false);
            r.setFechaDeModificacion(LocalDateTime.now());
            repository.save(r);
            return ApiResponse.successDelete();
        } catch (Exception e) {
            return ApiResponse.noDataValue(500, e.toString());
        }
    }

    public ApiResponse<Resena> obtenerResena(Integer id) {
        try {
            Optional<Resena> resena = repository.findById(id);
            return resena.map(ApiResponse::success)
                    .orElseGet(() -> ApiResponse.noDataValue(404, "Reseña no encontrada"));
        } catch (Exception e) {
            return ApiResponse.noDataValue(500, e.toString());
        }
    }

    // ─── Servicios específicos ────────────────────────────────────────────────────

    /**
     * Lista las reseñas activas de un evento, enriquecidas con su multimedia.
     */
    public List<ResenaDTO> listarResenasPorEvento(Evento evento) {
        List<ResenaDTO> resenas = repository.findResenasByEvento(evento);
        resenas.forEach(dto -> {
            List<ResenaMultimedia> multimedia = multimediaRepository.findMultimediaByResenaId(dto.getId());
            dto.setMultimedia(multimedia);
        });
        return resenas;
    }

    /**
     * Lista todas las reseñas activas realizadas por un usuario, con multimedia.
     */
    public List<ResenaDTO> listarResenasPorUsuario(Usuario usuario) {
        List<ResenaDTO> resenas = repository.findResenasByUsuario(usuario);
        resenas.forEach(dto -> {
            List<ResenaMultimedia> multimedia = multimediaRepository.findMultimediaByResenaId(dto.getId());
            dto.setMultimedia(multimedia);
        });
        return resenas;
    }

    /**
     * Extrae la calificación promedio que un usuario ha otorgado en sus reseñas.
     * Refleja el puntaje general del usuario como evaluador de eventos.
     */
    public Double calcularCalificacionPromedioUsuario(Usuario usuario) {
        Double promedio = repository.calcularCalificacionPromedioUsuario(usuario);
        return promedio != null ? promedio : 0.0;
    }
}
