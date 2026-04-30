
package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.models.Comunidad;
import com.example.unieventos.models.EventoComunidad;
import com.example.unieventos.models.EventoInteresUsuario;
import com.example.unieventos.services.EventoComunidadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/evento-comunidad/")
@CrossOrigin(origins = "*", allowedHeaders = "*") //permitir conexion de diferentes puertos
public class EventoComunidadController {

    private final EventoComunidadService service;

    public EventoComunidadController(EventoComunidadService service){
        this.service = service;
    }

    @PostMapping("listarByComunidad")
    public ApiResponse<EventoDTO> listarByComunidad(
            @RequestBody Comunidad comunidad
    ) {
        List<EventoDTO> listaEventos =  service.listarEventosByComunidad(comunidad);
        if(listaEventos.isEmpty()){
            return ApiResponse.empty();
        }else{
            return ApiResponse.successList(listaEventos);
        }

    }
    
    @PostMapping("/create/{id}")
    public ApiResponse<EventoComunidad> create(
            @RequestBody List<Comunidad> listaComunidades,
            @PathVariable Integer id) {
        try {
            List<EventoComunidad> listaCreada = service.create(id,listaComunidades);
            if(listaCreada.isEmpty() || listaCreada == null){
                return ApiResponse.empty();
            }
            return ApiResponse.createdLista(listaCreada);
        } catch (Exception e) {
            return ApiResponse.error(e.toString());
        }
    }
}
