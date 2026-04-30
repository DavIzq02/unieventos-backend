package com.example.unieventos.services;

import com.example.unieventos.dto.EventoDTO;
import com.example.unieventos.models.Comunidad;
import com.example.unieventos.models.Evento;
import com.example.unieventos.models.Rol;
import com.example.unieventos.models.Usuario;
import com.example.unieventos.repositories.EventoRepository;
import com.example.unieventos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    private final EventoRepository repository;
    @Autowired
    private GitService gitService;

    public EventoService(EventoRepository repository) {
        this.repository = repository;
    }

    public List<EventoDTO> listarEventosActivos(){
        return repository.findEventosActivos(LocalDateTime.now());
    }

    public List<EventoDTO> listarEventosProximos(){
        return repository.findEventosProximos(LocalDateTime.now());
    }

    public List<EventoDTO> listarEventosByUsuario(Usuario usuario){
        return repository.findEventosByUsuario(usuario);
    }

    public Evento crearEvento(Evento nuevoEvento) {

        if (nuevoEvento.getNombre() == null || nuevoEvento.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio");
        }
        if (nuevoEvento.getDescripcion() == null || nuevoEvento.getDescripcion().isEmpty()) {
            throw new RuntimeException("La descripción del evento es obligatorio");
        }


        nuevoEvento.setActivo(true);
        nuevoEvento.setFechaDeCreacion(LocalDateTime.now());

        return repository.save(nuevoEvento);
    }

    public Integer upLoadPortadaEvento(Integer id, MultipartFile cargaUtil) throws IOException {

        Evento evento = repository.findEventoById(id);
        if (cargaUtil != null && !cargaUtil.isEmpty()) {
            try {
                String carpeta = "eventos";

                String extension = "";
                String nombreOriginal = cargaUtil.getOriginalFilename();

                if (nombreOriginal != null && nombreOriginal.contains(".")) {
                    extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
                }

                String nombreArhivo = "portada_de_evento_"+id+extension;
                //String nombreArchivoAnterior = extraerNombreArchivoDesdeUrl(evento.getUrlImagenPortada());
                String urlCdn = gitService.upLoadFile(carpeta,nombreArhivo,cargaUtil,false,"");
                evento.setUrlImagenPortada(urlCdn);
                repository.upLoadPortadaEvento(evento.getId(),evento.getUrlImagenPortada());
            } catch (Exception e) {
                throw new RuntimeException("Ocurrió un error actualizando la foto "+e.toString());
            }
        } else {
            throw new RuntimeException("Imagen no leida, no se actualizó la foto");
        }

        return 200;
    }


    public Integer deleteEvento(Integer idEvento){
        Evento evento = new Evento(idEvento);
        try {
            repository.delete(evento);
            return 200;
        } catch (Exception e) {
            return 500;
        }
    }

    public String extraerNombreArchivoDesdeUrl(String url) {
        if (url == null || url.isEmpty()) return null;

        String[] partes = url.split("/");
        return partes[partes.length - 1];
    }


}