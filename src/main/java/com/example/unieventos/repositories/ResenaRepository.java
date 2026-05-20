package com.example.unieventos.repositories;

import com.example.unieventos.dto.ResenaDTO;
import com.example.unieventos.models.AsistenciaEvento;
import com.example.unieventos.models.Evento;
import com.example.unieventos.models.Resena;
import com.example.unieventos.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Integer> {

    // Verificar si ya existe reseña para esa asistencia
    @Query("""
        SELECT COUNT(r) > 0
        FROM Resena r
        WHERE r.asistencia = :asistencia AND r.activo = true
    """)
    boolean existeResenaParaAsistencia(@Param("asistencia") AsistenciaEvento asistencia);

    // Listar reseñas activas por evento (con datos del usuario)
    @Query("""
        SELECT new com.example.unieventos.dto.ResenaDTO(
            r.id,
            r.asistencia.id,
            r.asistencia.evento.id,
            r.asistencia.usuario.id,
            r.asistencia.usuario.nombre,
            r.asistencia.usuario.apellido,
            r.asistencia.usuario.urlFoto,
            r.titulo,
            r.descripcion,
            r.calificacion,
            r.fechaDeCreacion,
            r.fechaDeModificacion,
            r.activo
        )
        FROM Resena r
        JOIN r.asistencia a
        JOIN a.evento e
        WHERE e = :evento AND r.activo = true
        ORDER BY r.fechaDeCreacion DESC
    """)
    List<ResenaDTO> findResenasByEvento(@Param("evento") Evento evento);

    // Listar reseñas activas por usuario
    @Query("""
        SELECT new com.example.unieventos.dto.ResenaDTO(
            r.id,
            r.asistencia.id,
            r.asistencia.evento.id,
            r.asistencia.usuario.id,
            r.asistencia.usuario.nombre,
            r.asistencia.usuario.apellido,
            r.asistencia.usuario.urlFoto,
            r.titulo,
            r.descripcion,
            r.calificacion,
            r.fechaDeCreacion,
            r.fechaDeModificacion,
            r.activo
        )
        FROM Resena r
        JOIN r.asistencia a
        JOIN a.usuario u
        WHERE u = :usuario AND r.activo = true
        ORDER BY r.fechaDeCreacion DESC
    """)
    List<ResenaDTO> findResenasByUsuario(@Param("usuario") Usuario usuario);

    // Calificacion promedio de los eventos asistidos por un usuario
    @Query("""
        SELECT AVG(r.calificacion)
        FROM Resena r
        JOIN r.asistencia a
        JOIN a.usuario u
        WHERE u = :usuario AND r.activo = true
    """)
    Double calcularCalificacionPromedioUsuario(@Param("usuario") Usuario usuario);

    // Buscar reseña por id de asistencia
    @Query("""
        SELECT r FROM Resena r
        WHERE r.asistencia = :asistencia AND r.activo = true
    """)
    Optional<Resena> findResenaByAsistencia(@Param("asistencia") AsistenciaEvento asistencia);
}
