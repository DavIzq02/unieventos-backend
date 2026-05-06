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

    @GetMapping("/{eventoId}/{jornadaId}")
    public ResponseEntity<byte[]> generarQR(
            @PathVariable int eventoId,
            @PathVariable int jornadaId) throws Exception {

        long ts = System.currentTimeMillis();
        String data = eventoId + "|" + jornadaId + "|" + ts;
        String token = seguridadService.generarToken(data);
        String serverProd = "https://davizq02.github.io/unieventos-frontend";
        String serverLocal = "http://localhost:4200/unieventos-frontend";
        String url = serverProd+"/#/dashboard/?e="+ eventoId+ "&j="+jornadaId+ "&ts=" + ts+ "&tk=" + token;

        byte[] qr = qrService.generarQR(url);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .body(qr);
    }
}