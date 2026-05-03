package com.example.unieventos.services;

import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.models.Comunidad;
import com.example.unieventos.models.Evento;
import com.example.unieventos.models.EventoComunidad;
import com.example.unieventos.repositories.ComunidadRepository;
import com.example.unieventos.repositories.EventoComunidadRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComunidadService {

    private final ComunidadRepository repository;


    public ComunidadService(ComunidadRepository repository) {
        this.repository = repository;
    }


    public Comunidad guardarComunidad(Comunidad comunidad){
        return repository.save(comunidad);
    }

    public List<Comunidad> listarComunidades(){
        return repository.findAll();
    }

    public List<Comunidad> listarbyEvento(Integer idEvento){
        return repository.findComunidadesByEvento(new Evento(idEvento));
    }

}