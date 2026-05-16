package com.example.unieventos.repositories;

import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AsistenciaEventoRepository extends JpaRepository<AsistenciaEvento,Integer> {

    @Query("""
        SELECT new com.example.unieventos.models.Usuario(a.usuario.nombre,
        a.usuario.apellido,
        a.usuario.correo,
        a.usuario.codigo,
        a.usuario.urlFoto)
        FROM AsistenciaEvento a
        WHERE a.evento = :evento AND a.jornada = :jornada
    """)
    List<Usuario> findAsistenciaByEventoJornada(@Param("evento") Evento evento, @Param("jornada") Jornada jornada);

    @Query("""
        SELECT new com.example.unieventos.models.AsistenciaEvento(a.id)
        FROM AsistenciaEvento a
        WHERE a.usuario = :usuario AND a.jornada = :jornada
    """)
    Optional<AsistenciaEvento> findAsistenciaByUsuarioyJornada(@Param("usuario") Usuario usuario, @Param("jornada") Jornada jornada);

    @Query("""
        SELECT COUNT(ec) > 0
        FROM EventoComunidad ec
        JOIN ec.comunidad c
        JOIN Usuario u on u.comunidad = c
        WHERE ec.evento = :evento
        AND u = :usuario
    """)
    boolean usuarioPerteneceAComunidadEvento(
            @Param("evento") Evento evento,
            @Param("usuario") Usuario usuario);


    @Query("""
    SELECT new com.example.unieventos.models.Usuario(
        u.nombre,
        u.apellido,
        u.correo,
        u.codigo,
        u.urlFoto,
        c.nombre)
    FROM AsistenciaEvento ae
    JOIN ae.usuario u 
    JOIN u.comunidad c
    WHERE ae.jornada = :jornada
    """)
    List<Usuario> findAsistenciaByJornada(@Param("jornada") Jornada jornada);


    @Query("""
    SELECT new com.example.unieventos.dto.EventoDTO(
            e.id,
            e.nombre,
            e.descripcion,
            e.fechaDeApertura,
            e.fechaDeFinalizacion,
            e.urlImagenPortada,
            te.nombre,
            e.codigo,
            e.requiereInscripcion,
            e.codigoDinamico,
            e.requiereCodigo,
            e.abierto,
            e.revisarPreinscritos,
            ae.id
        )
    FROM AsistenciaEvento ae 
    JOIN ae.evento e
    JOIN e.tipoDeEvento te
    WHERE ae.usuario = :usuario AND (:fechaActual > e.fechaDeFinalizacion OR e.abierto = false)
    """)
    List<EventoDTO> findAsistenciaByUsuario(@Param("usuario") Usuario usuario, @Param("fechaActual") LocalDateTime fechaActual);



}
