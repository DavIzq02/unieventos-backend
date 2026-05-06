package com.example.unieventos.services;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;


@Service
public class SeguridadService {

    private final String SECRET = "MI_CLAVE_SUPER_SECRETA";

    public String generarToken(String data) {
        return DigestUtils.sha256Hex(data + SECRET);
    }
}