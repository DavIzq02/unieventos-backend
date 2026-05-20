package com.example.unieventos.repositories;

import com.example.unieventos.models.Resena;
import com.example.unieventos.models.ResenaMultimedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaMultimediaRepository extends JpaRepository<ResenaMultimedia, Integer> {

    // Listar multimedia activa de una reseña
    @Query("""
        SELECT new com.example.unieventos.models.ResenaMultimedia(
            rm.id, rm.urlMultimedia, rm.activo
        )
        FROM ResenaMultimedia rm
        WHERE rm.resena = :resena AND rm.activo = true
    """)
    List<ResenaMultimedia> findMultimediaByResena(@Param("resena") Resena resena);

    // Listar multimedia activa por id de reseña (util para carga masiva)
    @Query("""
        SELECT new com.example.unieventos.models.ResenaMultimedia(
            rm.id, rm.urlMultimedia, rm.activo
        )
        FROM ResenaMultimedia rm
        WHERE rm.resena.id = :idResena AND rm.activo = true
    """)
    List<ResenaMultimedia> findMultimediaByResenaId(@Param("idResena") Integer idResena);
}
