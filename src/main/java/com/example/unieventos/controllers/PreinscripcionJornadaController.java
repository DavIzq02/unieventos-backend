package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.dto.PayLoad;
import com.example.unieventos.models.Jornada;
import com.example.unieventos.models.Preinscripcion;
import com.example.unieventos.models.PreinscripcionJornada;
import com.example.unieventos.models.Usuario;
import com.example.unieventos.services.EventoService;
import com.example.unieventos.services.PreinscripcionJornadaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/preinscripcion-jornada")
public class PreinscripcionJornadaController {

    private final PreinscripcionJornadaService service;

    public PreinscripcionJornadaController(PreinscripcionJornadaService service){
        this.service = service;
    }

    @PostMapping("listarByJornada")
    public ApiResponse<Usuario> listarUsuariosByJornada(
            @RequestBody Jornada jornada
    ) {
        List<Usuario> listaUsuarios =  service.listarUsuariosByJornada(jornada);
        if(listaUsuarios.isEmpty()){
            return ApiResponse.empty();
        }else{
            return ApiResponse.successList(listaUsuarios);
        }

    }

    @PostMapping("create")
    public ApiResponse<PreinscripcionJornada> create(@RequestBody PreinscripcionJornada datosPreinscripcion) {
        PreinscripcionJornada inscripcionCreada =  service.createPreinscripcionJornada(datosPreinscripcion);
        if(inscripcionCreada == null){
            return ApiResponse.empty();
        }else{
            return ApiResponse.success(inscripcionCreada);
        }

    }

    @PostMapping("listar")
    public ApiResponse<PreinscripcionJornada> listarByPreinscripcion(
            @RequestBody Preinscripcion preinscripcion
    ) {
        List<PreinscripcionJornada> listaPreinscripcion =  service.listarByPreinscripcion(preinscripcion);
        if(listaPreinscripcion.isEmpty()){
            return ApiResponse.empty();
        }else{
            return ApiResponse.successList(listaPreinscripcion);
        }

    }

}
