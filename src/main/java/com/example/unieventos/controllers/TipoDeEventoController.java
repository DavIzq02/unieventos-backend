package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.models.Rol;
import com.example.unieventos.models.TipoDeEvento;
import com.example.unieventos.repositories.TipoDeEventoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tipos-eventos")
public class TipoDeEventoController {

    private final TipoDeEventoRepository tEventoRepository;

    public TipoDeEventoController(TipoDeEventoRepository tEventoRepository){
        this.tEventoRepository = tEventoRepository;
    }

    @GetMapping("listAll")
    public ApiResponse<TipoDeEvento> listar() {
        List<TipoDeEvento> listaTipos =  tEventoRepository.findAll();
        if(listaTipos.isEmpty()){
            return new ApiResponse<>(400, "Sin Resultados", null);
        }else{
            return new ApiResponse<>(200, "Consulta realizada exitosamente", listaTipos);
        }

    }
    @PostMapping("/create")
    public ApiResponse<TipoDeEvento> create(@RequestBody TipoDeEvento nuevoTipoEvento) {
        try {
            TipoDeEvento guardado = tEventoRepository.save(nuevoTipoEvento);
            return new ApiResponse<>(200, "OK", guardado);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error", null);
        }
    }

}
