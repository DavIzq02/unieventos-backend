package com.example.unieventos.models;

import jakarta.persistence.*;

@Entity
@Table(name = "preinscripcion")
public class Preinscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    public Preinscripcion(Integer id) {
        this.id = id;
    }

    public Preinscripcion() {
    }

    public Preinscripcion(Usuario usuario, Integer id, Evento evento) {
        this.usuario = usuario;
        this.id = id;
        this.evento = evento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
