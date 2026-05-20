package com.example.unieventos.models;

import jakarta.persistence.*;

@Entity
@Table(name = "resena_multimedia")
public class ResenaMultimedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_resena", nullable = false)
    private Resena resena;

    @Column(name = "url_multimedia", nullable = false, columnDefinition = "TEXT")
    private String urlMultimedia;

    @Column(nullable = false)
    private Boolean activo;

    public ResenaMultimedia() {}

    public ResenaMultimedia(Integer id) {
        this.id = id;
    }

    public ResenaMultimedia(Integer id, Resena resena, String urlMultimedia, Boolean activo) {
        this.id = id;
        this.resena = resena;
        this.urlMultimedia = urlMultimedia;
        this.activo = activo;
    }

    // Constructor para proyecciones JPQL
    public ResenaMultimedia(Integer id, String urlMultimedia, Boolean activo) {
        this.id = id;
        this.urlMultimedia = urlMultimedia;
        this.activo = activo;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Resena getResena() { return resena; }
    public void setResena(Resena resena) { this.resena = resena; }

    public String getUrlMultimedia() { return urlMultimedia; }
    public void setUrlMultimedia(String urlMultimedia) { this.urlMultimedia = urlMultimedia; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
