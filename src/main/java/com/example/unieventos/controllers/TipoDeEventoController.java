package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.models.Rol;
import com.example.unieventos.models.TipoDeEvento;
import com.example.unieventos.repositories.TipoDeEventoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tipos-eventos")
@CrossOrigin(origins = "*", allowedHeaders = "*") //permitir conexion de diferentes puertos
public class TipoDeEventoController {

    private final TipoDeEventoRepository tEventoRepository;

    public TipoDeEventoController(TipoDeEventoRepository tEventoRepository){
        this.tEventoRepository = tEventoRepository;
    }

    @GetMapping("listAll")
    public ApiResponse<TipoDeEvento> listar() {
        List<TipoDeEvento> listaTipos =  tEventoRepository.findAll();
        if(listaTipos.isEmpty()){
            return ApiResponse.empty();
        }else{
            return ApiResponse.successList(listaTipos);
        }

    }
    @PostMapping("/create")
    public ApiResponse<TipoDeEvento> create(@RequestBody TipoDeEvento nuevoTipoEvento) {
        try {
            TipoDeEvento guardado = tEventoRepository.save(nuevoTipoEvento);
            return  ApiResponse.created(guardado);
        } catch (Exception e) {
            return  ApiResponse.error(e.toString());
        }
    }

}
