package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.models.Permiso;
import com.example.unieventos.repositories.PermisoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/permiso")
public class PermisoController {

    private final PermisoRepository permisoRepository;

    public PermisoController(PermisoRepository permisoRepository){
        this.permisoRepository = permisoRepository;
    }

    @GetMapping("listAll")
    public ApiResponse<Permiso> listar() {
        List<Permiso> listaPermisos =  permisoRepository.findAll();
        if(listaPermisos.isEmpty()){
            return new ApiResponse<>(400, "Sin Resultados", null);
        }else{
            return new ApiResponse<>(200, "Consulta realizada exitosamente", listaPermisos);
        }

    }

}
