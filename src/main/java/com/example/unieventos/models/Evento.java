
package com.example.unieventos.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_tipo", nullable = false)
    private TipoDeEvento tipoDeEvento;

    @Column(name = "fecha_de_apertura", nullable = false)
    private LocalDateTime fechaDeApertura;

    @Column(name = "fecha_de_finalizacion")
    private LocalDateTime fechaDeFinalizacion;

    @Column(name = "url_imagen_de_portada")
    private String urlImagenPortada;

    @Column(name = "fecha_de_creacion", nullable = false)
    private LocalDateTime fechaDeCreacion;

    @Column(name = "fecha_de_modificacion", nullable = true)
    private LocalDateTime fechaDeModificacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario_creador", nullable = false)
    private Usuario idUsuarioCreador;

    @Column(nullable = false)
    private Boolean activo;

    public Evento(Integer id) {
        this.id = id;
    }

    public Evento(Integer id, String nombre, String descripcion, TipoDeEvento tipoDeEvento, LocalDateTime fechaDeFinalizacion, LocalDateTime fechaDeApertura, String urlImagenPortada, LocalDateTime fechaDeCreacion, LocalDateTime fechaDeModificacion, Usuario idUsuarioCreador, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoDeEvento = tipoDeEvento;
        this.fechaDeFinalizacion = fechaDeFinalizacion;
        this.fechaDeApertura = fechaDeApertura;
        this.urlImagenPortada = urlImagenPortada;
        this.fechaDeCreacion = fechaDeCreacion;
        this.fechaDeModificacion = fechaDeModificacion;
        this.idUsuarioCreador = idUsuarioCreador;
        this.activo = activo;
    }

    public Evento() {}

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    //public Usuario getIdUsuarioCreador() {
    //    return idUsuarioCreador;
    //}

    public void setIdUsuarioCreador(Usuario idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public LocalDateTime getFechaDeModificacion() {
        return fechaDeModificacion;
    }

    public void setFechaDeModificacion(LocalDateTime fechaDeModificacion) {
        this.fechaDeModificacion = fechaDeModificacion;
    }

    public LocalDateTime getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(LocalDateTime fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public String getUrlImagenPortada() {
        return urlImagenPortada;
    }

    public void setUrlImagenPortada(String urlImagenPortada) {
        this.urlImagenPortada = urlImagenPortada;
    }

    public LocalDateTime getFechaDeFinalizacion() {
        return fechaDeFinalizacion;
    }

    public void setFechaDeFinalizacion(LocalDateTime fechaDeFinalizacion) {
        this.fechaDeFinalizacion = fechaDeFinalizacion;
    }

    public LocalDateTime getFechaDeApertura() {
        return fechaDeApertura;
    }

    public void setFechaDeApertura(LocalDateTime fechaDeApertura) {
        this.fechaDeApertura = fechaDeApertura;
    }

    public TipoDeEvento getTipoDeEvento() {
        return tipoDeEvento;
    }

    public void setTipoDeEvento(TipoDeEvento tipoDeEvento) {
        this.tipoDeEvento = tipoDeEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}