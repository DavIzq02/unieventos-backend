package com.example.unieventos.dto;

import com.example.unieventos.models.ResenaMultimedia;

import java.time.LocalDateTime;
import java.util.List;

public class ResenaDTO {

    private Integer id;
    private Integer idAsistencia;
    private Integer idEvento;
    private Integer idUsuario;
    private String titulo;
    private String descripcion;
    private Integer calificacion;
    private LocalDateTime fechaDeCreacion;
    private LocalDateTime fechaDeModificacion;
    private Boolean activo;
    private List<ResenaMultimedia> multimedia;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String urlFoto;

    public ResenaDTO() {}

    // Constructor para consultas por evento (con info del usuario y evento)
    public ResenaDTO(Integer id, Integer idAsistencia, Integer idEvento, Integer idUsuario,
                     String titulo, String descripcion, Integer calificacion,
                     LocalDateTime fechaDeCreacion, LocalDateTime fechaDeModificacion,
                     Boolean activo) {
        this.id = id;
        this.idAsistencia = idAsistencia;
        this.idEvento = idEvento;
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.calificacion = calificacion;
        this.fechaDeCreacion = fechaDeCreacion;
        this.fechaDeModificacion = fechaDeModificacion;
        this.activo = activo;
    }

    public ResenaDTO(Integer id, Integer idAsistencia, Integer idEvento, Integer idUsuario,String nombreUsuario,String apellidoUsuario,String urlFoto
                     ,String titulo, String descripcion, Integer calificacion,
                     LocalDateTime fechaDeCreacion, LocalDateTime fechaDeModificacion,
                     Boolean activo) {
        this.id = id;
        this.idAsistencia = idAsistencia;
        this.idEvento = idEvento;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.urlFoto = urlFoto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.calificacion = calificacion;
        this.fechaDeCreacion = fechaDeCreacion;
        this.fechaDeModificacion = fechaDeModificacion;
        this.activo = activo;
    }



    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdAsistencia() { return idAsistencia; }
    public void setIdAsistencia(Integer idAsistencia) { this.idAsistencia = idAsistencia; }

    public Integer getIdEvento() { return idEvento; }
    public void setIdEvento(Integer idEvento) { this.idEvento = idEvento; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getCalificacion() { return calificacion; }
    public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }

    public LocalDateTime getFechaDeCreacion() { return fechaDeCreacion; }
    public void setFechaDeCreacion(LocalDateTime fechaDeCreacion) { this.fechaDeCreacion = fechaDeCreacion; }

    public LocalDateTime getFechaDeModificacion() { return fechaDeModificacion; }
    public void setFechaDeModificacion(LocalDateTime fechaDeModificacion) { this.fechaDeModificacion = fechaDeModificacion; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public List<ResenaMultimedia> getMultimedia() { return multimedia; }
    public void setMultimedia(List<ResenaMultimedia> multimedia) { this.multimedia = multimedia; }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
