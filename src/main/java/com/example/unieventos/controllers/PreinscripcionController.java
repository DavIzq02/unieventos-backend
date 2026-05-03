package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.models.Jornada;
import com.example.unieventos.models.Preinscripcion;
import com.example.unieventos.models.PreinscripcionJornada;
import com.example.unieventos.models.Usuario;
import com.example.unieventos.services.PreinscripcionJornadaService;
import com.example.unieventos.services.PreinscripcionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/preinscripcion")
public class PreinscripcionController {

    private final PreinscripcionService service;

    public PreinscripcionController(PreinscripcionService service){
        this.service = service;
    }



    @PostMapping("create")
    public ApiResponse<Preinscripcion> create(@RequestBody Preinscripcion datosPreinscripcion) {
        //Primero consultamos si ya existe una preinscripcion
        Preinscripcion inscripcion =  service.findPreinscripcionJornada(datosPreinscripcion);
        if(inscripcion == null){
             inscripcion =  service.createPreinscripcion(datosPreinscripcion);
        }

        if(inscripcion == null){
            return ApiResponse.empty();
        }else{
            return ApiResponse.success(inscripcion);
        }

    }


    @PostMapping("listar")
    public ApiResponse<Preinscripcion> listar(@RequestBody Preinscripcion datosPreinscripcion) {
        Preinscripcion inscripcion =  service.findPreinscripcionJornada(datosPreinscripcion);
        if(inscripcion == null){
            return ApiResponse.empty();
        }else{
            return ApiResponse.success(inscripcion);
        }

    }

}
