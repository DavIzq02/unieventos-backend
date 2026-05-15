package com.example.unieventos.services;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.models.*;
import com.example.unieventos.repositories.AsistenciaEventoRepository;
import com.example.unieventos.repositories.EventoRepository;
import com.example.unieventos.repositories.PreinscripcionJornadaRepository;
import com.example.unieventos.repositories.PreinscripcionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaEventoService {

    private final AsistenciaEventoRepository repository;
    private final EventoRepository repositoryEvento;
    private final PreinscripcionJornadaRepository repositoryJornada;

    public AsistenciaEventoService(AsistenciaEventoRepository repository,EventoRepository repositoryEvento,PreinscripcionJornadaRepository repositoryJornada) {
        this.repository = repository;
        this.repositoryEvento = repositoryEvento;
        this.repositoryJornada = repositoryJornada;
    }

    public ApiResponse<AsistenciaEvento> createAsistenciaEvento(AsistenciaEvento asistencia){
        try {
            //Validar que el usuario mo haya registrado asistencia
            AsistenciaEvento nuevaAsistencia;
            Optional<AsistenciaEvento> usuarioAsiste = repository.findAsistenciaByUsuarioyJornada(asistencia.getUsuario(),asistencia.getJornada());
            if (usuarioAsiste.isPresent()) {
                return ApiResponse.noDataValue(500,"El usuario ya registró asistencia a esta jornada del evento");
            }

            if(!repository.usuarioPerteneceAComunidadEvento(asistencia.getEvento(),asistencia.getUsuario())){
                return ApiResponse.noDataValue(500,"El evento no esta dirigido a la comunidad del usuario");
            }

            Evento infoEvento = repositoryEvento.findConfigEvento(asistencia.getEvento());
            if(!infoEvento.getActivo()){
                return ApiResponse.noDataValue(500,"Este evento ya no existe");
            }

            if(!infoEvento.getAbierto()){
                return ApiResponse.noDataValue(500,"El administrador no ha dado inicio al evento");
            }
            LocalDateTime ahora = LocalDateTime.now();

            boolean activa =
                    (ahora.isEqual(infoEvento.getFechaDeApertura()) || ahora.isAfter(infoEvento.getFechaDeApertura()))
                            &&
                            (ahora.isEqual(infoEvento.getFechaDeFinalizacion()) || ahora.isBefore(infoEvento.getFechaDeFinalizacion()));

            if(!activa){
                return ApiResponse.noDataValue(500,"El evento no se encuentra activo");
            }

            if(infoEvento.getRequiereInscripcion()){
                //Buscar la preinscripcion del usuario al evento
                if(!repositoryJornada.findPreinscripcionByUsuario(asistencia.getJornada(),asistencia.getUsuario())){
                    return ApiResponse.noDataValue(500,"El usuario no se encuentra en la lista de preinscritos");
                }
            }

            if(infoEvento.getRequiereCodigo()){
                if(!(infoEvento.getCodigo().equals(asistencia.getEvento().getCodigo()))){
                    return ApiResponse.noDataValue(500,"El código del evento no coincide ");
                }
            }
            nuevaAsistencia = repository.save(asistencia);

            //Aqui si el evento tiene codigo dinamico lo cambiamos

            //retornamos existoso
            return ApiResponse.success(nuevaAsistencia);
        }catch (Exception e){
            return ApiResponse.noDataValue(500,e.toString());
        }


    }

    public List<Usuario> listarUsuariosByJornada(Jornada jornada){
        return repository.findAsistenciaByJornada(jornada);
    }

    public List<Usuario> findAsistenciaJornada(AsistenciaEvento asistencia){
        return repository.findAsistenciaByEventoJornada(asistencia.getEvento(),asistencia.getJornada());
    }

    public List<EventoDTO> findAsistenciaByUsuario(Usuario usuario){
        return repository.findAsistenciaByUsuario(usuario,LocalDateTime.now());
    }
}