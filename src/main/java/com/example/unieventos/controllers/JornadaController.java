package com.example.unieventos.controllers;

import com.example.unieventos.dto.ApiResponse;
import com.example.unieventos.dto.JornadaDTO;
import com.example.unieventos.models.Evento;
import com.example.unieventos.models.Jornada;
import com.example.unieventos.models.TipoDeEvento;
import com.example.unieventos.repositories.JornadaRepository;
import com.example.unieventos.repositories.TipoDeEventoRepository;
import com.example.unieventos.services.JornadaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/jornada-evento")
@CrossOrigin(origins = "*", allowedHeaders = "*") //permitir conexion de diferentes puertos
public class JornadaController {

    private final JornadaService service;

    public JornadaController(JornadaService service){
        this.service = service;
    }

    @PostMapping("listByEvento")
    public ApiResponse<JornadaDTO> listar(@RequestBody Evento evento) {
        List<JornadaDTO> listaJornadas =  service.findByEvento(evento);
        if(listaJornadas.isEmpty()){
            return ApiResponse.empty();
        }else{
            return ApiResponse.successList(listaJornadas);
        }
    }

    @PostMapping("/createJornadas")
    public ApiResponse<Jornada> createJornadas(@RequestBody List <Jornada> listaJornadas) {
        try {
            List <Jornada> listaJornadasCreadas = service.createJornadas(listaJornadas);
            return  ApiResponse.createdLista(listaJornadasCreadas);
        } catch (Exception e) {
            return  ApiResponse.error(e.toString());
        }
    }

}
