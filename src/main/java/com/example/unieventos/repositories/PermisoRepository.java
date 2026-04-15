package com.example.unieventos.repositories;

import com.example.unieventos.models.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso,Integer> {

}
