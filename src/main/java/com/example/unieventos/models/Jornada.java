package com.example.unieventos.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "jornada")
public class Jornada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "hora_de_inicio", nullable = false)
    private LocalTime horaDeInicio;

    @Column(name = "hora_de_finalizacion", nullable = false)
    private LocalTime horaDeFinalizacion;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    public Jornada() {
    }

    public Jornada(Integer id, LocalTime horaDeInicio, LocalTime horaDeFinalizacion, Evento evento) {
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
}
