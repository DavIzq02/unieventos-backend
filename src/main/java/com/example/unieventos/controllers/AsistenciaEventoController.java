package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.models.AsistenciaEvento;
import com.example.unieventos.models.Jornada;
import com.example.unieventos.models.Usuario;
import com.example.unieventos.services.AsistenciaEventoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("findAsistenciaByUsuario")
    public ApiResponse<EventoDTO> findAsistenciaByUsuario(
            @RequestBody Usuario usuario
    ) {
        List<EventoDTO> listaEventos =  service.findAsistenciaByUsuario(usuario);
        if(listaEventos.isEmpty()){
            return ApiResponse.empty();
        }else{
            return ApiResponse.successList(listaEventos);
        }
    }


}
