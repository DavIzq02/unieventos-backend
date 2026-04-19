package com.example.unieventos.repositories;


import com.example.unieventos.models.TipoDeEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDeEventoRepository extends JpaRepository<TipoDeEvento,Integer> {

}
