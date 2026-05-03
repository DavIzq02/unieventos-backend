package com.example.unieventos.services;

import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.models.Comunidad;
import com.example.unieventos.models.Evento;
import com.example.unieventos.models.EventoComunidad;
import com.example.unieventos.models.Usuario;
import com.example.unieventos.repositories.EventoComunidadRepository;
import com.example.unieventos.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventoComunidadService {

    private final EventoComunidadRepository repository;


    public EventoComunidadService(EventoComunidadRepository repository) {
        this.repository = repository;
    }


    public List<EventoDTO> listarEventosByComunidad(Comunidad comunidad){
        return repository.findEventosByComunidad(comunidad);
    }

    public List<EventoComunidad> create(Integer id,List<Comunidad> listaComunidades){
        List<EventoComunidad> listaCreada = new ArrayList<EventoComunidad>();
        for(Comunidad c : listaComunidades){
            Evento e = new Evento(id);
            EventoComunidad ec = new EventoComunidad(e,c);
            repository.save(ec);
            listaCreada.add(ec);
        }
        return listaCreada;
    }

    public List<EventoComunidad> update(Integer id,List<Comunidad> listaComunidades){
        Evento e = new Evento(id);
        List<EventoComunidad> listaActualizada = new ArrayList<EventoComunidad>();
        //Eliminamos todas las comunidades a las que estaba asignado el evento
        repository.deleteEventoComunidadByEvento(e);
        //Volvemos a crear la relacion
        for(Comunidad c : listaComunidades){
            EventoComunidad ec = new EventoComunidad(e,c);
            repository.save(ec);
            listaActualizada.add(ec);
        }
        return listaActualizada;
    }


}