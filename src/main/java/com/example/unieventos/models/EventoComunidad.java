package com.example.unieventos.models;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "evento_comunidad")
public class EventoComunidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "id_comunidad", nullable = false)
    private Comunidad comunidad;

    public EventoComunidad() {
    }

    public EventoComunidad(Integer id, Evento evento, Comunidad comunidad) {
        this.id = id;
        this.evento = evento;
        this.comunidad = comunidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }
}
