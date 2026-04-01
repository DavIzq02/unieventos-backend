
package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.models.Rol;
import com.example.unieventos.repositories.RolRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rol")
public class RolController {
    
    private final RolRepository rolRepository;
    
    public RolController(RolRepository rolRepository){
        this.rolRepository = rolRepository;
    }
    
    @GetMapping("listAll")
    public ApiResponse<Rol> listar() {
        List<Rol> listaComunidades =  rolRepository.findAll();
        if(listaComunidades.isEmpty()){
            return new ApiResponse<>(400, "Sin Resultados", null); 
        }else{
           return new ApiResponse<>(200, "Consulta realizada exitosamente", listaComunidades);  
        }
            
    }
    
    @PostMapping("/create")
    public ApiResponse<Rol> create(@RequestBody Rol nuevoRol) {
        try {
            Rol guardado = rolRepository.save(nuevoRol);
            return new ApiResponse<>(200, "OK", guardado);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error", null);
        }
    }
}
