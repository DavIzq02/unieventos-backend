
package com.example.unieventos.repositories;

import com.example.unieventos.models.Jornada;
import com.example.unieventos.models.Preinscripcion;
import com.example.unieventos.models.PreinscripcionJornada;
import com.example.unieventos.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreinscripcionJornadaRepository extends JpaRepository<PreinscripcionJornada, Integer>{

    @Query("""
    SELECT new com.example.unieventos.models.Usuario(
        u.nombre,
        u.apellido,
        u.correo,
        u.codigo,
        u.urlFoto)
    FROM PreinscripcionJornada pj
    JOIN pj.preinscripcion p
    JOIN p.usuario u 
    WHERE pj.jornada = :jornada
    """)
    List<Usuario> findUsuariosByJornada(@Param("jornada") Jornada jornada);

    List<PreinscripcionJornada> findByPreinscripcion(Preinscripcion preinscripcion);
}
