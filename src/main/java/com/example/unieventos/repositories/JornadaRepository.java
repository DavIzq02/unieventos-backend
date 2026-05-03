
package com.example.unieventos.repositories;



import com.example.unieventos.dto.JornadaDTO;
import com.example.unieventos.models.Evento;
import com.example.unieventos.models.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Integer>{

    @Query("""
    SELECT new com.example.unieventos.dto.JornadaDTO(
        j.id,
        j.horaDeInicio,
        j.horaDeFinalizacion,
        e.id
    )
    FROM Jornada j
    JOIN j.evento e
    WHERE e = :evento
    """)
    List<JornadaDTO> findJornadasByEvento(Evento evento);


    @Modifying
    @Transactional
    @Query("DELETE FROM  Jornada j WHERE j.evento = :evento")
    void deleteByEvento(Evento evento);
}
