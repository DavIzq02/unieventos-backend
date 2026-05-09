package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.models.AsistenciaEvento;
import com.example.unieventos.services.AsistenciaEventoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/asistencia")
public class AsistenciaEventoController {

    private final AsistenciaEventoService service;

    public AsistenciaEventoController(AsistenciaEventoService service){
        this.service = service;
    }

    @PostMapping("create")
    public ApiResponse<AsistenciaEvento> create(@RequestBody AsistenciaEvento datosAsistencia) {
        return service.createAsistenciaEvento(datosAsistencia);
    }


//    @PostMapping("listar")
//    public ApiResponse<Preinscripcion> listar(@RequestBody Preinscripcion datosPreinscripcion) {
//        Preinscripcion inscripcion =  service.findPreinscripcionJornada(datosPreinscripcion);
//        if(inscripcion == null){
//            return ApiResponse.empty();
//        }else{
//            return ApiResponse.success(inscripcion);
//        }
//
//    }

}
