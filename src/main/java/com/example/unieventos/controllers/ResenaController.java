package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.dto.ResenaDTO;
import com.example.unieventos.models.Evento;
import com.example.unieventos.models.Resena;
import com.example.unieventos.models.Usuario;
import com.example.unieventos.services.ResenaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/resena")
public class ResenaController {

    private final ResenaService service;

    public ResenaController(ResenaService service) {
        this.service = service;
    }

    // ─── CRUD básico ─────────────────────────────────────────────────────────────

    @PostMapping("create")
    public ApiResponse<Resena> crear(@RequestBody Resena resena) {
        return service.crearResena(resena);
    }

    @PutMapping("update")
    public ApiResponse<Resena> actualizar(@RequestBody Resena resena) {
        return service.actualizarResena(resena);
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<Resena> eliminar(@PathVariable Integer id) {
        return service.eliminarResena(id);
    }

    @GetMapping("get/{id}")
    public ApiResponse<Resena> obtener(@PathVariable Integer id) {
        return service.obtenerResena(id);
    }

    /**
     * Lista las reseñas de un evento junto con su multimedia.
     * Body: { "id": <idEvento> }
     */
    @PostMapping("listarByEvento")
    public ApiResponse<ResenaDTO> listarByEvento(@RequestBody Evento evento) {
        List<ResenaDTO> resenas = service.listarResenasPorEvento(evento);
        if (resenas.isEmpty()) {
            return ApiResponse.empty();
        }
        return ApiResponse.successList(resenas);
    }

    /**
     * Lista todas las reseñas hechas por un usuario junto con su multimedia.
     * Body: { "id": <idUsuario> }
     */
    @PostMapping("listarPorUsuario")
    public ApiResponse<ResenaDTO> listarPorUsuario(@RequestBody Usuario usuario) {
        List<ResenaDTO> resenas = service.listarResenasPorUsuario(usuario);
        if (resenas.isEmpty()) {
            return ApiResponse.empty();
        }
        return ApiResponse.successList(resenas);
    }

    /**
     * Retorna la calificación promedio otorgada por un usuario en sus reseñas.
     * Body: { "id": <idUsuario> }
     */
    @PostMapping("calificacionPromedio")
    public ApiResponse<Double> calificacionPromedio(@RequestBody Usuario usuario) {
        Double promedio = service.calcularCalificacionPromedioUsuario(usuario);
        return ApiResponse.success(promedio);
    }
}
