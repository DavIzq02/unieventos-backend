package com.example.unieventos.services;

import com.example.unieventos.dto.PayLoad;
import com.example.unieventos.models.Rol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

import com.example.unieventos.models.Usuario;
import com.example.unieventos.repositories.UsuarioRepository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    @Value("${github.token}")
    private String token;

    @Value("${github.repo}")
    private String repo;

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

    public Usuario crearUsuarioPostLogin(Usuario nuevoUsuario, MultipartFile cargaUtil) throws IOException {

        Optional<Usuario> usuarioExistente = usuarioRepository.findByCodigo(nuevoUsuario.getCodigo());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("El código ya está registrado");
        }

        if (cargaUtil != null && !cargaUtil.isEmpty()) {
            // 1. Configuración de GitHub (Lo ideal es que esto venga de un archivo .properties o .env)
            String tokenConfig = this.token;
            String repoConfig = this.repo;
            String carpeta = "usuarios";
            String nombreArchivo = UUID.randomUUID() + "_" + cargaUtil.getOriginalFilename();
            String urlApiGithub = "https://api.github.com/repos/" + repoConfig + "/contents/" + carpeta + "/" + nombreArchivo;

            // 2. Convertir imagen a Base64
            byte[] bytes = cargaUtil.getBytes();
            String contentBase64 = Base64.getEncoder().encodeToString(bytes);

            // 3. Preparar el JSON para GitHub
            Map<String, String> body = new HashMap<>();
            body.put("message", "Upload user profile picture: " + nombreArchivo);
            body.put("content", contentBase64);

            // 4. Enviar petición PUT a GitHub
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(tokenConfig);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

            try {
                restTemplate.exchange(urlApiGithub, HttpMethod.PUT, entity, String.class);

                // 5. Construir URL de jsDelivr para la BD
                String urlCdn = "https://cdn.jsdelivr.net/gh/" + repoConfig + "@main/" + carpeta + "/" + nombreArchivo;
                nuevoUsuario.setUrlFoto(urlCdn);

            } catch (Exception e) {
                throw new RuntimeException("Error al subir la imagen a GitHub: " + e.getMessage());
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