package com.example.unieventos.models;

import jakarta.persistence.*;

@Entity
@Table(name = "preinscripcion_jornada")
public class PreinscripcionJornada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_jornada", nullable = false)
    private Jornada jornada;

    @ManyToOne
    @JoinColumn(name = "id_preinscripcion", nullable = false)
    private Preinscripcion preinscripcion;

    public PreinscripcionJornada() {
    }

    public PreinscripcionJornada(Integer id, Jornada jornada, Preinscripcion preinscripcion) {
        this.id = id;
        this.jornada = jornada;
        this.preinscripcion = preinscripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }

    public Preinscripcion getPreinscripcion() {
        return preinscripcion;
    }

    public void setPreinscripcion(Preinscripcion preinscripcion) {
        this.preinscripcion = preinscripcion;
    }
}
