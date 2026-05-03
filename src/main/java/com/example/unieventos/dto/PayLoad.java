package com.example.unieventos.dto;
import com.example.unieventos.models.Evento;
import com.example.unieventos.models.Jornada;
import com.example.unieventos.models.Preinscripcion;
import com.example.unieventos.models.Usuario;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public class PayLoad<T>  {
    private T data;
    private List<T> listaRespuesta;
    private MultipartFile imagen;
    private Preinscripcion preinscripcion;
    private Usuario usuario;
    private Evento evento;
    private Jornada jornada;
    public PayLoad() {
    }

    public MultipartFile getImagen() {
        return imagen;
    }

    public void setImagen(MultipartFile imagen) {
        this.imagen = imagen;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getListaRespuesta() {
        return listaRespuesta;
    }

    public void setListaRespuesta(List<T> listaRespuesta) {
        this.listaRespuesta = listaRespuesta;
    }

    public Preinscripcion getPreinscripcion() {
        return preinscripcion;
    }

    public void setPreinscripcion(Preinscripcion preinscripcion) {
        this.preinscripcion = preinscripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }
}
