
package com.example.unieventos.repositories;

import com.example.unieventos.models.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByCodigo(String codigo);

    List<Usuario> findByActivo(Boolean activo);

    List<Usuario> findByRol_Id(Integer idRol);

    List<Usuario> findByComunidad_Id(Integer idComunidad);

    /*Cuando el nombre del metodo se vuelve muy largo
    y se necesita una lógica más compleja
    @Query("SELECT u FROM Usuario u WHERE u.activo = true")
    List<Usuario> listarActivos();*/

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.activo = :estado WHERE u.id = :idUsuario")
    int updateEstado(@Param("idUsuario") Integer idUsuario,
                     @Param("estado") Boolean estado);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.urlFoto = :url WHERE u.id = :idUsuario")
    int updateUrlFoto(@Param("idUsuario") Integer idUsuario,
                      @Param("url") String url);
}
