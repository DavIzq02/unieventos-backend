package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.models.Comunidad;
import com.example.unieventos.models.Evento;
import com.example.unieventos.models.Permiso;
import com.example.unieventos.models.Usuario;
import com.example.unieventos.repositories.EventoRepository;
import com.example.unieventos.repositories.PermisoRepository;
import com.example.unieventos.services.EventoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("api/evento")
public class EventoController {

    private final EventoService service;

    public EventoController(EventoService service){
        this.service = service;
    }

    @GetMapping("findById/{id}")
    public ApiResponse<Evento> findEventoById(@PathVariable("id") Integer id) {
        Evento evento =  service.findEventoById(id);
        if(evento == null){
            return ApiResponse.empty();
        }else{
            return ApiResponse.success(evento);
        }
    }

    @GetMapping("listarEventosActivos")
    public ApiResponse<EventoDTO> listarEventosActivos() {
        List<EventoDTO> listaEventos =  service.listarEventosActivos();
        if(listaEventos.isEmpty()){
            return ApiResponse.empty();
        }else{
            return ApiResponse.successList(listaEventos);
        }

    }

    @GetMapping("listarEventosProximos")
    public ApiResponse<EventoDTO> listarEventosProximos() {
        List<EventoDTO> listaEventos =  service.listarEventosProximos();
        if(listaEventos.isEmpty()){
            return ApiResponse.empty();
        }else{
            return ApiResponse.successList(listaEventos);
        }

    }

    @PostMapping("listarByUsuario")
    public ApiResponse<EventoDTO> listarByUsuario(
            @RequestBody Usuario usuario
    ) {
        List<EventoDTO> listaEventos =  service.listarEventosByUsuario(usuario);
        if(listaEventos.isEmpty()){
            return ApiResponse.empty();
        }else{
            return ApiResponse.successList(listaEventos);
        }

    }

    @PostMapping("create")
    public ApiResponse<Evento> crearEvento(
            @RequestBody Evento evento
    ) {
        try {
            Evento nuevoEvento =  service.crearEvento(evento);
            return ApiResponse.created(nuevoEvento);

        }catch (Exception e){
            return  ApiResponse.error(e.toString());
        }
    }

    @PostMapping("/upLoadPortadaEvento/{id}")
    public ApiResponse<Evento> upLoadPortadaEvento(
            @PathVariable("id") Integer id,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) throws IOException {
        try {
            service.upLoadPortadaEvento(id, imagen);
            return ApiResponse.success(new Evento(id));
        } catch (Exception e) {
            return ApiResponse.error(e.toString());
        }
    }

    @PutMapping("/inactivar/{id}")
    public ApiResponse<Evento> inactivar(
            @PathVariable("id") Integer id
    ) throws IOException {
        try {
            String respuesta = service.inactivarEvento(id);
            if(respuesta.equals("OK")){
                return ApiResponse.successDelete();
            }else{
                return ApiResponse.error(respuesta);
            }

        } catch (Exception e) {
            return ApiResponse.error(e.toString());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Evento> delete(
            @PathVariable("id") Integer id
    ) throws IOException {
        try {
            String respuesta = service.deleteEvento(id);
            if(respuesta.equals("OK")){
                return ApiResponse.successDelete();
            }else{
                return ApiResponse.error(respuesta);
            }

        } catch (Exception e) {
            return ApiResponse.error(e.toString());
        }
    }

    @PutMapping("update")
    public ApiResponse<Evento> updateEvento(
            @RequestBody Evento eventoModificado
    ) {
        try {
            Evento nuevoEvento =  service.modificarEvento(eventoModificado);
            return ApiResponse.created(nuevoEvento);

        }catch (Exception e){
            return  ApiResponse.error(e.toString());
        }
    }

    @PutMapping("/updatePortadaEvento/{id}")
    public ApiResponse<Evento> updatePortadaEvento(
            @PathVariable("id") Integer id,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) throws IOException {
        try {
            service.updatePortadaEvento(id, imagen);
            return ApiResponse.success(new Evento(id));
        } catch (Exception e) {
            return ApiResponse.error(e.toString());
        }
    }

}
