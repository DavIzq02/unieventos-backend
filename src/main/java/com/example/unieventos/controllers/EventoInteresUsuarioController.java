
package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.models.EventoInteresUsuario;
import com.example.unieventos.models.Rol;
import com.example.unieventos.models.Usuario;
import com.example.unieventos.repositories.EventoInteresUsuarioRepository;
import com.example.unieventos.repositories.RolRepository;
import com.example.unieventos.services.EventoInteresUsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/interes-usuario/")
@CrossOrigin(origins = "*", allowedHeaders = "*") //permitir conexion de diferentes puertos
public class EventoInteresUsuarioController {

    private final EventoInteresUsuarioService service;

    public EventoInteresUsuarioController(EventoInteresUsuarioService service){
        this.service = service;
    }

    @PostMapping("listarByInteres")
    public ApiResponse<EventoDTO> listarByInteres(
            @RequestBody Usuario usuario
    ) {
        List<EventoDTO> listaEventos =  service.listarEventosByInteres(usuario);
        if(listaEventos.isEmpty()){
            return ApiResponse.empty();
        }else{
            return ApiResponse.successList(listaEventos);
        }

    }

}
