
package com.example.unieventos.repositories;

import com.example.unieventos.models.Comunidad;
import com.example.unieventos.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComunidadRepository extends JpaRepository<Comunidad, Integer>{

    @Query("""
        SELECT new com.example.unieventos.models.Comunidad(
            c.nombre,
            c.id
        )
        FROM EventoComunidad ec 
        JOIN ec.comunidad c
        WHERE ec.evento = :evento
    """)
    List<Comunidad> findComunidadesByEvento(@Param("evento") Evento evento);

}
