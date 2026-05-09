package com.example.unieventos.repositories;

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
        JOIN Usuario u ON u.comunidad.id = ec.comunidad.id
        WHERE ec.evento = :evento
        AND u = :usuario
    """)
    boolean usuarioPerteneceAComunidadEvento(
            @Param("evento") Evento evento,
            @Param("usuario") Usuario usuario);

}
