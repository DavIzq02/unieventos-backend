package com.example.unieventos.services;

import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.models.Jornada;
import com.example.unieventos.models.Preinscripcion;
import com.example.unieventos.models.PreinscripcionJornada;
import com.example.unieventos.models.Usuario;
import com.example.unieventos.repositories.EventoInteresUsuarioRepository;
import com.example.unieventos.repositories.PreinscripcionJornadaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreinscripcionJornadaService {

    private final PreinscripcionJornadaRepository repository;


    public PreinscripcionJornadaService(PreinscripcionJornadaRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listarUsuariosByJornada(Jornada jornada){
        return repository.findUsuariosByJornada(jornada);
    }

    public PreinscripcionJornada createPreinscripcionJornada(PreinscripcionJornada datosPreinscripcion){
        return repository.save(datosPreinscripcion);
    }

    public List<PreinscripcionJornada> listarByPreinscripcion(Preinscripcion pre){
        return repository.findByPreinscripcion(pre);
    }
}