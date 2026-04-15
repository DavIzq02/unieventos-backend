package com.example.unieventos.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class GitService {
    @Value("${github.token}")
    private String token;

    @Value("${github.repo}")
    private String repo;

    public String upLoadFile(String carpeta, String nombreArchivo, MultipartFile file) throws IOException {
        String urlCdn;

        // 1. Configuración de GitHub (Lo ideal es que esto venga de un archivo .properties o .env)
        String tokenConfig = this.token;
        String repoConfig = this.repo;
        String urlApiGithub = "https://api.github.com/repos/" + repoConfig + "/contents/" + carpeta + "/" + nombreArchivo;

        // 2. Convertir imagen a Base64
        byte[] bytes = file.getBytes();
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
            urlCdn = "https://cdn.jsdelivr.net/gh/" + repoConfig + "@main/" + carpeta + "/" + nombreArchivo;
            return urlCdn;

        } catch (Exception e) {
            throw new RuntimeException("Error al subir la imagen a GitHub: " + e.getMessage());
        }
    }
}
