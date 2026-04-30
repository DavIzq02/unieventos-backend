package com.example.unieventos.services;

import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.models.Comunidad;
import com.example.unieventos.models.Evento;
import com.example.unieventos.models.EventoComunidad;
import com.example.unieventos.models.Usuario;
import com.example.unieventos.repositories.EventoComunidadRepository;
import com.example.unieventos.repositories.EventoInteresUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoInteresUsuarioService {

    private final EventoInteresUsuarioRepository repository;


    public EventoInteresUsuarioService(EventoInteresUsuarioRepository repository) {
        this.repository = repository;
    }

    public List<EventoDTO> listarEventosByInteres(Usuario usuario){
        return repository.findEventosByInteresUsuario(usuario);
    }
}