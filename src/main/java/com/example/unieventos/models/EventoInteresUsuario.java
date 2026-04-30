package com.example.unieventos.models;

import jakarta.persistence.*;

@Entity
@Table(name = "evento_interes_usuario")
public class EventoInteresUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_tipo_evento", nullable = false)
    private TipoDeEvento tipoDeEvento;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public EventoInteresUsuario(Integer id, TipoDeEvento tipoDeEvento, Usuario usuario) {
        this.id = id;
        this.tipoDeEvento = tipoDeEvento;
        this.usuario = usuario;
    }

    public EventoInteresUsuario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoDeEvento getTipoDeEvento() {
        return tipoDeEvento;
    }

    public void setTipoDeEvento(TipoDeEvento tipoDeEvento) {
        this.tipoDeEvento = tipoDeEvento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
