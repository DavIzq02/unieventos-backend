package com.example.unieventos.dto;

import com.example.unieventos.models.Evento;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JornadaDTO {

    private Integer id;
    private LocalTime horaDeInicio;
    private LocalTime horaDeFinalizacion;
    private Evento evento;
    private Integer idEvento;
    public JornadaDTO() {

    }

    public JornadaDTO(Integer id, LocalTime horaDeInicio, LocalTime horaDeFinalizacion, Integer idEvento) {
        this.id = id;
        this.horaDeInicio = horaDeInicio;
        this.horaDeFinalizacion = horaDeFinalizacion;
        this.idEvento = idEvento;
    }

    public JornadaDTO(Integer id, LocalTime horaDeInicio, LocalTime horaDeFinalizacion, Evento evento) {
        this.id = id;
        this.horaDeInicio = horaDeInicio;
        this.horaDeFinalizacion = horaDeFinalizacion;
        this.evento = evento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getHoraDeInicio() {
        return horaDeInicio;
    }

    public void setHoraDeInicio(LocalTime horaDeInicio) {
        this.horaDeInicio = horaDeInicio;
    }

    public LocalTime getHoraDeFinalizacion() {
        return horaDeFinalizacion;
    }

    public void setHoraDeFinalizacion(LocalTime horaDeFinalizacion) {
        this.horaDeFinalizacion = horaDeFinalizacion;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }
}
