
package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.models.Rol;
import com.example.unieventos.repositories.RolRepository;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rol")
@CrossOrigin(origins = "*", allowedHeaders = "*") //permitir conexion de diferentes puertos
public class RolController {
    
    private final RolRepository rolRepository;
    
    public RolController(RolRepository rolRepository){
        this.rolRepository = rolRepository;
    }
    
    @GetMapping("listAll")
    public ApiResponse<Rol> listar() {
        List<Rol> listaRoles =  rolRepository.findAll();
        if(listaRoles.isEmpty()){
            return ApiResponse.empty();
        }else{
           return ApiResponse.successList(listaRoles);
        }
            
    }
    
    @PostMapping("/create")
    public ApiResponse<Rol> create(@RequestBody Rol nuevoRol) {
        try {
            Rol guardado = rolRepository.save(nuevoRol);
            return ApiResponse.created(guardado);
        } catch (Exception e) {
            return ApiResponse.error(e.toString());
        }
    }
}
