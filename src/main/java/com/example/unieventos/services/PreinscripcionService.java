package com.example.unieventos.services;

import com.example.unieventos.models.Jornada;
import com.example.unieventos.models.Preinscripcion;
import com.example.unieventos.models.PreinscripcionJornada;
import com.example.unieventos.models.Usuario;
import com.example.unieventos.repositories.PreinscripcionJornadaRepository;
import com.example.unieventos.repositories.PreinscripcionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreinscripcionService {

    private final PreinscripcionRepository repository;


    public PreinscripcionService(PreinscripcionRepository repository) {
        this.repository = repository;
    }

    public Preinscripcion createPreinscripcion(Preinscripcion datosPreinscripcion){
        return repository.save(datosPreinscripcion);
    }

    public Preinscripcion findPreinscripcionJornada(Preinscripcion datosPreinscripcion){

        return repository.findPreinscripcionJornada(datosPreinscripcion.getEvento(),datosPreinscripcion.getUsuario());
    }
}