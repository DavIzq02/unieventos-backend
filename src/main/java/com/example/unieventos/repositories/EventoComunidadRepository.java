
package com.example.unieventos.repositories;

import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.models.Comunidad;
import com.example.unieventos.models.Evento;
import com.example.unieventos.models.EventoComunidad;
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
public interface EventoComunidadRepository extends JpaRepository<EventoComunidad, Integer>{

    @Query("""
        SELECT new com.example.unieventos.dto.EventoDTO(
            e.id,
            e.nombre,
            e.descripcion,
            e.fechaDeApertura,
            e.fechaDeFinalizacion,
            e.urlImagenPortada,
            te.nombre
        )
        FROM EventoComunidad ec
        JOIN ec.evento e
        JOIN e.tipoDeEvento te
        WHERE ec.comunidad = :comunidad AND e.activo = true 
            GROUP BY e.id,
            e.nombre,
            e.descripcion,
            e.fechaDeApertura,
            e.fechaDeFinalizacion,
            e.urlImagenPortada,
            te.nombre
    """)
    List<EventoDTO> findEventosByComunidad(@Param("comunidad") Comunidad comunidad);


    @Modifying
    @Transactional
    @Query("DELETE FROM  EventoComunidad ec WHERE ec.evento = :evento")
    void deleteEventoComunidadByEvento(@Param("evento") Evento evento);

}
