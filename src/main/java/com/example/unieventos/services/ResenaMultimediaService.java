package com.example.unieventos.services;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.models.Resena;
import com.example.unieventos.models.ResenaMultimedia;
import com.example.unieventos.repositories.ResenaMultimediaRepository;
import com.example.unieventos.repositories.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ResenaMultimediaService {

    private final ResenaMultimediaRepository repository;
    private final ResenaRepository resenaRepository;
    @Autowired
    private GitService gitService;

    public ResenaMultimediaService(ResenaMultimediaRepository repository, ResenaRepository resenaRepository) {
        this.repository = repository;
        this.resenaRepository = resenaRepository;
    }

    // ─── CRUD básico ─────────────────────────────────────────────────────────────

    public ApiResponse<ResenaMultimedia> crearMultimedia(Integer id, MultipartFile cargaUtil) {
        String nombreOriginal = "";
        try {
            ResenaMultimedia resenaMultimedia = new ResenaMultimedia();
            // Validar que la reseña exista y esté activa
            Optional<Resena> resena = resenaRepository.findById(id);
            if (resena.isEmpty() || !resena.get().getActivo()) {
                return ApiResponse.noDataValue(404, "La reseña no existe o no está activa");
            }
            resenaMultimedia.setResena(new Resena(id));
            resenaMultimedia.setActivo(Boolean.TRUE);
            if (cargaUtil != null && !cargaUtil.isEmpty()) {
                try {
                    String carpeta = "resenas";

                    String extension = "";
                    nombreOriginal = cargaUtil.getOriginalFilename();

                    if (nombreOriginal != null && nombreOriginal.contains(".")) {
                        extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
                    }
                    long ts = System.currentTimeMillis();
                    String nombreArhivo = "evento_"+id+ts+extension;
                    String urlCdn = gitService.upLoadFile(carpeta,nombreArhivo,cargaUtil,false,"");
                    resenaMultimedia.setUrlMultimedia(urlCdn);
                    ResenaMultimedia multimediaCreada = repository.save(resenaMultimedia);
                    return ApiResponse.created(multimediaCreada);
                } catch (Exception e) {
                    return ApiResponse.error("Ocurrió un error subiendo la foto '"+nombreOriginal+"' a la reseña");
                }
            } else {
                return ApiResponse.error("Imagen no leida, no se actualizó la foto");
            }

        } catch (Exception e) {
            return ApiResponse.error(e.toString());
        }
    }

    public ApiResponse<ResenaMultimedia> eliminarMultimedia(Integer id) {
        try {
            Optional<ResenaMultimedia> existente = repository.findById(id);
            if (existente.isEmpty()) {
                return ApiResponse.noDataValue(404, "Multimedia no encontrada");
            }
            ResenaMultimedia rm = existente.get();
            rm.setActivo(false);
            repository.save(rm);
            return ApiResponse.successDelete();
        } catch (Exception e) {
            return ApiResponse.noDataValue(500, e.toString());
        }
    }

    public ApiResponse<ResenaMultimedia> obtenerMultimedia(Integer id) {
        try {
            Optional<ResenaMultimedia> multimedia = repository.findById(id);
            return multimedia.map(ApiResponse::success)
                    .orElseGet(() -> ApiResponse.noDataValue(404, "Multimedia no encontrada"));
        } catch (Exception e) {
            return ApiResponse.noDataValue(500, e.toString());
        }
    }

    // ─── Consultas específicas ────────────────────────────────────────────────────

    public List<ResenaMultimedia> listarMultimediaPorResena(Resena resena) {
        return repository.findMultimediaByResena(resena);
    }
}
