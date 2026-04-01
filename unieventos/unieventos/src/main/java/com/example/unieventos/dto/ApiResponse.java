
package com.example.unieventos.dto;

import java.util.List;

public class ApiResponse<T> {
    private Integer codigo;
    private String mensaje;
    private T data;
    private List<T> listaRespuesta;
    
    public ApiResponse(){}

    public ApiResponse(Integer codigo, String mensaje, List<T> listaRespuesta) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.listaRespuesta = listaRespuesta;
    }

    
    public ApiResponse(Integer codigo, String mensaje, T data) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.data = data;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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
