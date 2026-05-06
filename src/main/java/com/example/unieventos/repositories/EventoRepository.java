
package com.example.unieventos.repositories;

import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.models.Evento;
import com.example.unieventos.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer>{

    Evento findEventoById(Integer id);

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
        e.revisarPreinscritos
    )
    FROM Evento e
    JOIN e.tipoDeEvento te
    WHERE e.fechaDeApertura > :fechaActual AND e.activo = true
    GROUP BY e.id,
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
        e.revisarPreinscritos
    """)
    List<EventoDTO> findEventosProximos(@Param("fechaActual") LocalDateTime fechaActual);

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
        e.revisarPreinscritos
    )
    FROM Evento e
    JOIN e.tipoDeEvento te
    WHERE :fechaActual BETWEEN e.fechaDeApertura AND e.fechaDeFinalizacion AND e.activo = true
    GROUP BY e.id,
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
        e.revisarPreinscritos
    """)
    List<EventoDTO> findEventosActivos(@Param("fechaActual") LocalDateTime fechaActual);

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
        e.revisarPreinscritos
    )
    FROM Evento e
    JOIN e.tipoDeEvento te
    WHERE e.idUsuarioCreador = :usuario AND e.activo = true
    """)
    List<EventoDTO> findEventosByUsuario(@Param("usuario") Usuario usuario);


    @Modifying
    @Transactional
    @Query("""
        UPDATE Evento e 
        SET e.urlImagenPortada = :url 
        WHERE e.id = :id
    """)
    void upLoadPortadaEvento(@Param("id") Integer id,
                            @Param("url") String url);

    @Modifying
    @Transactional
    @Query("""
        UPDATE Evento e 
        SET e.activo = false 
        WHERE e = :ev
    """)
    void inactivarEvento(Evento ev);

}
