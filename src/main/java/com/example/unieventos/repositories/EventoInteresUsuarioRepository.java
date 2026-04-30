
package com.example.unieventos.repositories;

import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.models.EventoInteresUsuario;
import com.example.unieventos.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoInteresUsuarioRepository extends JpaRepository<EventoInteresUsuario, Integer>{

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
    FROM Evento e
    JOIN e.tipoDeEvento te
    JOIN EventoInteresUsuario eia ON eia.tipoDeEvento = te
    WHERE eia.usuario = :usuario AND e.activo = true
    """)
    List<EventoDTO> findEventosByInteresUsuario(@Param("usuario") Usuario usuario);

}
