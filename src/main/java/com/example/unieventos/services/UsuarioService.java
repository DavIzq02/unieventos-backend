package com.example.unieventos.services;

import com.example.unieventos.dto.PayLoad;
import com.example.unieventos.models.Rol;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.example.unieventos.models.Usuario;
import com.example.unieventos.repositories.UsuarioRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario crearUsuario(Usuario nuevoUsuario) {

        if (nuevoUsuario.getNombre() == null || nuevoUsuario.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio");
        }

        if (nuevoUsuario.getCorreo() == null || nuevoUsuario.getCorreo().isEmpty()) {
            throw new RuntimeException("El correo es obligatorio");
        }

        nuevoUsuario.setFechaDeCreacion(LocalDateTime.now());

        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario login(String correo, String contrasena) {

        Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow(() ->
                new RuntimeException("Usuario no existe")
        );

        if (!usuario.getContrasena().equals(contrasena)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        return usuario;
    }

    public Usuario crearUsuarioPostLogin(Usuario nuevoUsuario , MultipartFile cargaUtil) throws IOException {

        Optional<Usuario> usuarioExistente = usuarioRepository.findByCodigo(nuevoUsuario.getCodigo());

        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("El código ya está registrado");
        }

        // guardar imagen
        if(cargaUtil != null){
            String nombreArchivo = UUID.randomUUID() + "_" +cargaUtil.getOriginalFilename();

            Path ruta = Paths.get("C:/app/uploads/" + nombreArchivo);
            Files.write(ruta, cargaUtil.getBytes());

            String url = "/uploads/" + nombreArchivo;
            nuevoUsuario.setUrlFoto(url);
        }else{
            nuevoUsuario.setUrlFoto("not defined");
        }


        nuevoUsuario.setFechaDeCreacion(LocalDateTime.now());
        nuevoUsuario.setActivo(true);
        if (nuevoUsuario.getComunidad().getId() != 5 ){
            nuevoUsuario.setRol(new Rol(4)); //Si es un Invitado la comunidad será Inivitado
        }else{
            nuevoUsuario.setRol(new Rol(3)); //Por defecto se crea con Rol Usuario
        }

        return usuarioRepository.save(nuevoUsuario);
    }
}