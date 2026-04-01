
package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.models.Comunidad;
import com.example.unieventos.repositories.ComunidadRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Convierte a Json
@RequestMapping("/api/comunidad") //url base del rest
public class ComunidadController {
    
    private final ComunidadRepository comunidadRepository;

    public ComunidadController(ComunidadRepository comunidadRepository) {
        this.comunidadRepository = comunidadRepository;
    }

    @GetMapping("listAll")
    public ApiResponse<Comunidad> listar() {
        List<Comunidad> listaComunidades =  comunidadRepository.findAll();
        if(listaComunidades.isEmpty()){
            return new ApiResponse<>(400, "Sin Resultados", null); 
        }else{
           return new ApiResponse<>(200, "Consulta realizada exitosamente", listaComunidades);  
        }
            
    }
    
    @PostMapping("/create")
    public ApiResponse<Comunidad> create(@RequestBody Comunidad nuevaComunidad) {
        try {
            Comunidad guardado = comunidadRepository.save(nuevaComunidad);
            return new ApiResponse<>(200, "OK", guardado);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error", null);
        }
    }
    
}
