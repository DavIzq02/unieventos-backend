package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.dto.PayLoad;
import com.example.unieventos.models.Usuario;
import com.example.unieventos.repositories.UsuarioRepository;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.example.unieventos.services.UsuarioService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("api/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*") //permitir conexion de diferentes puertos
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioRepository usuarioRepository,UsuarioService usuarioService)
    {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    //Enlista todos los usuarios servicio consumible solo por los administradores
    @GetMapping("listAll")
    public ApiResponse<Usuario> listar() {
        List<Usuario> listaComunidades =  usuarioRepository.findAll();
        if(listaComunidades.isEmpty()){
            return new ApiResponse<>(400, "Sin Resultados", null); 
        }else{
           return new ApiResponse<>(200, "Consulta realizada exitosamente", listaComunidades);  
        }
            
    }
    
    @PostMapping("/create") //Opcion crear desde el administrador
    public ApiResponse<Usuario> create(@RequestBody Usuario nuevoUsuario) {
        try {
            Usuario guardado = usuarioService.crearUsuario(nuevoUsuario);
            return new ApiResponse<>(200, "OK", guardado);
        } catch (Exception e) {
            return new ApiResponse<>(500, e.getMessage(), null);
        }
    }
    
    @GetMapping("/{id}")
    public Optional<Usuario> byId(@PathVariable Integer id) {
        Optional<Usuario> userResult =  usuarioRepository.findById(id);
        return userResult;
            
    }

    @PostMapping("/login")
    public ApiResponse<Usuario> login(@RequestBody Usuario payLoad) {

        try {
            Usuario usuario = usuarioService.login(
                    payLoad.getCorreo(),
                    payLoad.getContrasena()
            );

            return new ApiResponse<>(200, "Login exitoso", usuario);
        }catch (Exception e){
            return new ApiResponse<>(500, e.getMessage(), null);
        }

    }

    @PostMapping("/createPostLogin")
    public ApiResponse<Usuario> createPostLogin(
            @RequestPart("data") Usuario nuevoUsuario,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) throws IOException {
        try {
            Usuario guardado = usuarioService.crearUsuarioPostLogin(nuevoUsuario, imagen);
            return new ApiResponse<>(200, "OK", guardado);
        } catch (Exception e) {
            return new ApiResponse<>(500, e.getMessage(), null);
        }
    }

    @PutMapping("/update")
    public ApiResponse<Usuario> updateUsuario(
            @RequestBody Usuario usuarioModificado
    ) throws IOException {
        try {
            usuarioService.updateUsuario(usuarioModificado);
            return new ApiResponse<>(200, "OK", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, e.getMessage(), null);
        }
    }

    @PutMapping("/cambiar-estado")
    public ApiResponse<Usuario> cambiarEstado(
            @RequestBody Usuario usuarioModificado
    ) throws IOException {
        try {
            Integer result = usuarioService.cambiarEstadoUsuario(usuarioModificado.getId(),usuarioModificado.getActivo());
            return new ApiResponse<>(result, "OK", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, e.getMessage(), null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Usuario> delete(
            @PathVariable("id") Integer idUsuario
    ) throws IOException {
        try {
            Integer result = usuarioService.deleteUsuario(idUsuario);
            return new ApiResponse<>(result, "OK", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, e.getMessage(), null);
        }
    }

    @PostMapping("/updateFotoPerfil")
    public ApiResponse<Usuario> updateFotoPerfil(
            @RequestPart("data") Usuario usuario,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) throws IOException {
        try {
            Integer result = usuarioService.updateFotoPerfil(usuario, imagen);
            return new ApiResponse<>(result, "OK", null);
        } catch (Exception e) {
            return new ApiResponse<>(500, e.getMessage(), null);
        }
    }
}
