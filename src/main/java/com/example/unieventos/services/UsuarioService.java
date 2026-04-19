package com.example.unieventos.services;

import com.example.unieventos.config.SecurityConfig;
import com.example.unieventos.models.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import com.example.unieventos.models.Usuario;
import com.example.unieventos.repositories.UsuarioRepository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GitService gitService;

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

        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        return usuario;
    }

    public Usuario crearUsuarioPostLogin(Usuario nuevoUsuario, MultipartFile cargaUtil) throws IOException {

        Optional<Usuario> usuarioExistente = usuarioRepository.findByCodigo(nuevoUsuario.getCodigo());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("El código ya está registrado");
        }

        String passwordHash = passwordEncoder.encode(nuevoUsuario.getContrasena());
        nuevoUsuario.setContrasena(passwordHash);

        if (cargaUtil != null && !cargaUtil.isEmpty()) {
            try {
                String carpeta = "usuarios";
                String nombreArhivo = "foto_perfil_de_"+nuevoUsuario.getCodigo();
                String urlCdn = gitService.upLoadFile(carpeta,nombreArhivo,cargaUtil);
                nuevoUsuario.setUrlFoto(urlCdn);
            } catch (Exception e) {
                nuevoUsuario.setUrlFoto("not defined");
                throw new RuntimeException(e);

            }
        } else {
            nuevoUsuario.setUrlFoto("not defined");
        }

        nuevoUsuario.setFechaDeCreacion(LocalDateTime.now());
        nuevoUsuario.setActivo(true);

        int rolId = (nuevoUsuario.getComunidad().getId() != 5) ? 4 : 3;
        nuevoUsuario.setRol(new Rol(rolId));

        return usuarioRepository.save(nuevoUsuario);
    }
}