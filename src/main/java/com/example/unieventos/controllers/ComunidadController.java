
package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.models.Comunidad;
import com.example.unieventos.repositories.ComunidadRepository;
import java.util.List;

import com.example.unieventos.services.ComunidadService;
import org.springframework.web.bind.annotation.*;

@RestController //Convierte a Json
@RequestMapping("/api/comunidad") //url base del rest
@CrossOrigin(origins = "*", allowedHeaders = "*") //permitir conexion de diferentes puertos
public class ComunidadController {
    
    private final ComunidadService service;

    public ComunidadController(ComunidadService service) {
        this.service = service;
    }

    @GetMapping("listAll")
    public ApiResponse<Comunidad> listar() {
        List<Comunidad> listaComunidades =  service.listarComunidades();
        if(listaComunidades.isEmpty()){
            return ApiResponse.empty();
        }else{
           return ApiResponse.successList(listaComunidades);
        }
            
    }

    @GetMapping("listarbyEvento/{id}")
    public ApiResponse<Comunidad> listarbyEvento(
            @PathVariable("id") Integer idEvento
    ) {
        List<Comunidad> listaComunidades =  service.listarbyEvento(idEvento);
        if(listaComunidades.isEmpty()){
            return ApiResponse.empty();
        }else{
            return ApiResponse.successList(listaComunidades);
        }

    }

    @PostMapping("/create")
    public ApiResponse<Comunidad> create(@RequestBody Comunidad nuevaComunidad) {
        try {
            Comunidad guardado = service.guardarComunidad(nuevaComunidad);
            return ApiResponse.created(guardado);
        } catch (Exception e) {
            return ApiResponse.error(e.toString());
        }
    }
    
}
