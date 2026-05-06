
package com.example.unieventos.dto;

import com.example.unieventos.models.Evento;
import com.example.unieventos.models.TipoDeEvento;
import com.example.unieventos.models.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventoDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private TipoDeEvento tipoDeEvento;
    private LocalDateTime fechaDeApertura;
    private LocalDateTime fechaDeFinalizacion;
    private String urlImagenPortada;
    private LocalDateTime fechaDeCreacion;
    private LocalDateTime fechaDeModificacion;
    private Usuario idUsuarioCreador;
    private Boolean activo;
    private String nombreCreador;
    private String nombreTipoEvento;
    private String codigo;
    private Boolean requiereInscripcion;
    private Boolean codigoDinamico;
    private Boolean requiereCodigo;
    private Boolean abierto;
    private Boolean revisarPreinscritos;

    public EventoDTO() {
    }

    //Este es el constructor que esta usando el repositorio
public EventoDTO( Integer id ,
                  String nombre ,
                  String descripcion ,
                  LocalDateTime fechaDeApertura ,
                  LocalDateTime fechaDeFinalizacion,
                  String urlImagenPortada,
                  String nombreTipoEvento,
                  String codigo,
                  Boolean requiereInscripcion,
                  Boolean codigoDinamico,
                  Boolean requiereCodigo,
                  Boolean abierto,
                  Boolean revisarPreinscritos){

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaDeApertura = fechaDeApertura;
        this.fechaDeFinalizacion = fechaDeFinalizacion;
        this.urlImagenPortada = urlImagenPortada;
        this.nombreTipoEvento = nombreTipoEvento;
        this.codigo = codigo;
        this.requiereInscripcion = requiereInscripcion;
        this.codigoDinamico = codigoDinamico;
        this.requiereCodigo = requiereCodigo;
        this.abierto = abierto;
        this.revisarPreinscritos = revisarPreinscritos;
}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoDeEvento getTipoDeEvento() {
        return tipoDeEvento;
    }

    public void setTipoDeEvento(TipoDeEvento tipoDeEvento) {
        this.tipoDeEvento = tipoDeEvento;
    }

    public LocalDateTime getFechaDeApertura() {
        return fechaDeApertura;
    }

    public void setFechaDeApertura(LocalDateTime fechaDeApertura) {
        this.fechaDeApertura = fechaDeApertura;
    }

    public LocalDateTime getFechaDeFinalizacion() {
        return fechaDeFinalizacion;
    }

    public void setFechaDeFinalizacion(LocalDateTime fechaDeFinalizacion) {
        this.fechaDeFinalizacion = fechaDeFinalizacion;
    }

    public String getUrlImagenPortada() {
        return urlImagenPortada;
    }

    public void setUrlImagenPortada(String urlImagenPortada) {
        this.urlImagenPortada = urlImagenPortada;
    }

    public String getNombreCreador() {
        return nombreCreador;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }

    public String getNombreTipoEvento() {
        return nombreTipoEvento;
    }

    public void setNombreTipoEvento(String nombreTipoEvento) {
        this.nombreTipoEvento = nombreTipoEvento;
    }

    public LocalDateTime getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(LocalDateTime fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public LocalDateTime getFechaDeModificacion() {
        return fechaDeModificacion;
    }

    public void setFechaDeModificacion(LocalDateTime fechaDeModificacion) {
        this.fechaDeModificacion = fechaDeModificacion;
    }

    public Usuario getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(Usuario idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getRequiereInscripcion() {
        return requiereInscripcion;
    }

    public void setRequiereInscripcion(Boolean requiereInscripcion) {
        this.requiereInscripcion = requiereInscripcion;
    }

    public Boolean getCodigoDinamico() {
        return codigoDinamico;
    }

    public void setCodigoDinamico(Boolean codigoDinamico) {
        this.codigoDinamico = codigoDinamico;
    }

    public Boolean getRequiereCodigo() {
        return requiereCodigo;
    }

    public void setRequiereCodigo(Boolean requiereCodigo) {
        this.requiereCodigo = requiereCodigo;
    }

    public Boolean getAbierto() {
        return abierto;
    }

    public void setAbierto(Boolean abierto) {
        this.abierto = abierto;
    }

    public Boolean getRevisarPreinscritos() {
        return revisarPreinscritos;
    }

    public void setRevisarPreinscritos(Boolean revisarPreinscritos) {
        this.revisarPreinscritos = revisarPreinscritos;
    }
}
