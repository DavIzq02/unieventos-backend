package com.example.unieventos.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "resena")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_asistencia", nullable = false)
    private AsistenciaEvento asistencia;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private Integer calificacion;

    @Column(name = "fecha_de_creacion", nullable = false)
    private LocalDateTime fechaDeCreacion;

    @Column(name = "fecha_de_modificacion")
    private LocalDateTime fechaDeModificacion;

    @Column(nullable = false)
    private Boolean activo;

    public Resena() {}

    public Resena(Integer id) {
        this.id = id;
    }

    public Resena(Integer id, AsistenciaEvento asistencia, String titulo,
                  String descripcion, Integer calificacion,
                  LocalDateTime fechaDeCreacion, LocalDateTime fechaDeModificacion,
                  Boolean activo) {
        this.id = id;
        this.asistencia = asistencia;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.calificacion = calificacion;
        this.fechaDeCreacion = fechaDeCreacion;
        this.fechaDeModificacion = fechaDeModificacion;
        this.activo = activo;
    }

    // Constructor para proyecciones JPQL
    public Resena(Integer id, String titulo, String descripcion,
                  Integer calificacion, LocalDateTime fechaDeCreacion,
                  LocalDateTime fechaDeModificacion, Boolean activo) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.calificacion = calificacion;
        this.fechaDeCreacion = fechaDeCreacion;
        this.fechaDeModificacion = fechaDeModificacion;
        this.activo = activo;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public AsistenciaEvento getAsistencia() { return asistencia; }
    public void setAsistencia(AsistenciaEvento asistencia) { this.asistencia = asistencia; }

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
}
