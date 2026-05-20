package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.models.Resena;
import com.example.unieventos.models.ResenaMultimedia;
import com.example.unieventos.services.ResenaMultimediaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/resena-multimedia")
public class ResenaMultimediaController {

    private final ResenaMultimediaService service;

    public ResenaMultimediaController(ResenaMultimediaService service) {
        this.service = service;
    }

    @PostMapping("create/{id}")
    public ApiResponse<ResenaMultimedia> crear(@PathVariable("id") Integer id,
                                               @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        return service.crearMultimedia(id,imagen);
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<ResenaMultimedia> eliminar(@PathVariable Integer id) {
        return service.eliminarMultimedia(id);
    }

    @GetMapping("get/{id}")
    public ApiResponse<ResenaMultimedia> obtener(@PathVariable Integer id) {
        return service.obtenerMultimedia(id);
    }

    /**
     * Lista la multimedia activa de una reseña específica.
     * Body: { "id": <idResena> }
     */
    @PostMapping("listarPorResena")
    public ApiResponse<ResenaMultimedia> listarPorResena(@RequestBody Resena resena) {
        List<ResenaMultimedia> lista = service.listarMultimediaPorResena(resena);
        if (lista.isEmpty()) {
            return ApiResponse.empty();
        }
        return ApiResponse.successList(lista);
    }
}
