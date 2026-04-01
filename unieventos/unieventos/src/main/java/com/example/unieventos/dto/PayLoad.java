package com.example.unieventos.dto;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public class PayLoad<T>  {
    private T data;
    private List<T> listaRespuesta;
    private MultipartFile imagen;

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
}
