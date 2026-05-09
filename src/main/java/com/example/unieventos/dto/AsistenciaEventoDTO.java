package com.example.unieventos.dto;

import com.example.unieventos.models.Evento;
import com.example.unieventos.models.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsistenciaEventoDTO {

    private String  nombreUsuario;
    private String  apellidoUsuario;
    private String  correoUsuario;
    private String  codigoUsuario;
    private String  urlFotoUsuario;
    private Usuario usuario;

    public AsistenciaEventoDTO() {
    }

    public AsistenciaEventoDTO(Usuario usuario) {
        this.usuario = usuario;
    }

}
