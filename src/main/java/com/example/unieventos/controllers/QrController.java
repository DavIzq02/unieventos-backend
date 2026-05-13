package com.example.unieventos.controllers;

import com.example.unieventos.services.QrService;
import com.example.unieventos.services.SeguridadService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evento/qr")
public class QrController {

    private final QrService qrService;
    private final SeguridadService seguridadService;

    public QrController(QrService qrService, SeguridadService seguridadService) {
        this.qrService = qrService;
        this.seguridadService = seguridadService;
    }

    @GetMapping("/{eventoId}/{jornadaId}/{codigo}")
    public ResponseEntity<byte[]> generarQR(
            @PathVariable int eventoId,
            @PathVariable int jornadaId,
            @PathVariable String codigo) throws Exception {

        long ts = System.currentTimeMillis();
        String data = eventoId + "|" + jornadaId + "|" + ts +"|" + codigo;
        String token = seguridadService.generarToken(data);
        String serverProd = "https://davizq02.github.io/unieventos-frontend";
        String serverLocal = "http://localhost:4200";
        String url = serverProd+"/#/asistencia/?e="+ eventoId+ "&j="+jornadaId+ "&ts=" + ts+ "&tk=" + token + "&c="+codigo;

        byte[] qr = qrService.generarQR(url);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .body(qr);
    }
}