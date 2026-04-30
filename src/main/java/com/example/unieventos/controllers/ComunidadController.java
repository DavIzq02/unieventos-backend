
package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.models.Comunidad;
import com.example.unieventos.repositories.ComunidadRepository;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController //Convierte a Json
@RequestMapping("/api/comunidad") //url base del rest
@CrossOrigin(origins = "*", allowedHeaders = "*") //permitir conexion de diferentes puertos
public class ComunidadController {
    
    private final ComunidadRepository comunidadRepository;

    public ComunidadController(ComunidadRepository comunidadRepository) {
        this.comunidadRepository = comunidadRepository;
    }

    @GetMapping("listAll")
    public ApiResponse<Comunidad> listar() {
        List<Comunidad> listaComunidades =  comunidadRepository.findAll();
        if(listaComunidades.isEmpty()){
            return ApiResponse.empty();
        }else{
           return ApiResponse.successList(listaComunidades);
        }
            
    }
    
    @PostMapping("/create")
    public ApiResponse<Comunidad> create(@RequestBody Comunidad nuevaComunidad) {
        try {
            Comunidad guardado = comunidadRepository.save(nuevaComunidad);
            return ApiResponse.created(guardado);
        } catch (Exception e) {
            return ApiResponse.error(e.toString());
        }
    }
    
}
