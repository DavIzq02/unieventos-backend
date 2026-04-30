package com.example.unieventos.services;

import com.example.unieventos.dto.JornadaDTO;
import com.example.unieventos.models.Evento;
import com.example.unieventos.models.Jornada;
import com.example.unieventos.repositories.JornadaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JornadaService {

    private final JornadaRepository repository;


    public JornadaService(JornadaRepository repository) {
        this.repository = repository;
    }


    public List<Jornada> createJornadas(List<Jornada> listaJornadas){
        return repository.saveAll(listaJornadas);
    }

    public List<JornadaDTO> findByEvento(Evento evento){
        return repository.findJornadasByEvento(evento);
    }
}