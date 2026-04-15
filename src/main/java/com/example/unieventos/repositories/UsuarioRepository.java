
package com.example.unieventos.repositories;

import com.example.unieventos.models.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
    
}
