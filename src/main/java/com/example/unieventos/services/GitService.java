package com.example.unieventos.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class GitService {
    @Value("${github.token}")
    private String token;

    @Value("${github.repo}")
    private String repo;

    public String upLoadFile(String carpeta, String nombreArchivo, MultipartFile file,Boolean update,String nombreArchivoAnterior) throws IOException {
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
        if(update){
            try {
                String sha = obtenerShaArchivo(carpeta, nombreArchivoAnterior);
                body.put("sha", sha); // si existe, se actualiza
            } catch (Exception e) {
                // si no existe, no pasa nada, se crea
            }
        }

        // 4. Enviar petición PUT a GitHub
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenConfig);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        try {
            restTemplate.exchange(urlApiGithub, HttpMethod.PUT, entity, String.class);

            if(update) {
                //Si esta actualizando la imagen hacemos la purga del cache del cdn
                String urlPurge = "https://purge.jsdelivr.net/gh/"
                        + repoConfig + "@main/"
                        + carpeta + "/" + nombreArchivo;

                try {
                    restTemplate.getForObject(urlPurge, String.class);
                } catch (Exception e) {
                    System.out.println("No se pudo hacer purge, pero no afecta: " + e.getMessage());
                }
            }
            // 5. Construir URL de jsDelivr para la BD
            urlCdn = "https://cdn.jsdelivr.net/gh/" + repoConfig + "@main/" + carpeta + "/" + nombreArchivo;
            return urlCdn;

        } catch (Exception e) {
            throw new RuntimeException("Error al subir la imagen a GitHub: " + e.getMessage());
        }
    }

    public String obtenerShaArchivo(String carpeta, String nombreArchivo) {
        String tokenConfig = this.token;
        String repoConfig = this.repo;

        String url = "https://api.github.com/repos/"
                + repoConfig + "/contents/"
                + carpeta + "/" + nombreArchivo;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenConfig);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            Map body = response.getBody();

            if (body != null && body.containsKey("sha")) {
                return body.get("sha").toString();
            } else {
                throw new RuntimeException("No se encontró el SHA del archivo");
            }

        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("El archivo no existe en el repositorio");
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el SHA: " + e.getMessage());
        }
    }
}
