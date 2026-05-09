package com.example.unieventos.models;
import jakarta.persistence.*;

@Entity
@Table(name = "asistencia_evento")
public class AsistenciaEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_jornada", nullable = false)
    private Jornada jornada;

    public AsistenciaEvento(Integer id) {
        this.id = id;
    }

    public AsistenciaEvento() {
    }

    public AsistenciaEvento(Integer id, Evento evento, Usuario usuario, Jornada jornada) {
        this.id = id;
        this.evento = evento;
        this.usuario = usuario;
        this.jornada = jornada;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }
}
