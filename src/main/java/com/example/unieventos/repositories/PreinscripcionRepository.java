package com.example.unieventos.repositories;

import com.example.unieventos.models.Evento;
import com.example.unieventos.models.Preinscripcion;
import com.example.unieventos.models.PreinscripcionJornada;
import com.example.unieventos.models.Usuario;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PreinscripcionRepository extends JpaRepository<Preinscripcion, Integer>  {

    @Query("""
        SELECT new com.example.unieventos.models.Preinscripcion(p.id)
        FROM Preinscripcion p
        WHERE p.evento = :evento AND p.usuario = :usuario
    """)
    Preinscripcion findPreinscripcionJornada(
            @Param("evento") Evento evento,
            @Param("usuario") Usuario usuario
    );
    }
