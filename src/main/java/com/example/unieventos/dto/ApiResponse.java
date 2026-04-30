
package com.example.unieventos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private Integer codigo;
    private String mensaje;
    private T data;
    private List<T> listaRespuesta;
    
    public ApiResponse(){}

    //Se usa cuando se quiere responder el detalle de la operacion solamente con el codigo de transaccion
    public ApiResponse(Integer codigo) {
        this.codigo = codigo;
        configMensaje();
    }

    private void configMensaje(){
        switch (this.codigo) {
            case 200: this.mensaje = "Consulta realizada exitosamente"; break;
            case 201: this.mensaje = "Recurso creado exitosamente"; break;
            case 404: this.mensaje = "Consulta sin resultados"; break;
            case 500: this.mensaje = "Error interno del servidor"; break;
            default: this.mensaje = "Operación procesada"; break;
        }
    }

    public ApiResponse(Integer codigo, T data) {
        this.codigo = codigo;
        configMensaje();
        this.data = data;
    }

    public ApiResponse(Integer codigo, List<T> listaRespuesta) {
        this.codigo = codigo;
        configMensaje();
        this.listaRespuesta = listaRespuesta;
    }
    public ApiResponse(Integer codigo, String mensaje, List<T> listaRespuesta) {
        this.codigo = codigo;
        configMensaje();
        this.mensaje +=" "+mensaje;
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


    public static <T> ApiResponse<T> empty() {
        return new ApiResponse<>(404);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, data);
    }

    public static <T> ApiResponse<T> successList(List<T> datos) {
        return new ApiResponse<>(200, datos);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(201, data);
    }

    public static <T> ApiResponse<T> createdLista(List<T> datos) {
        return new ApiResponse<>(201, datos);
    }


    public static <T> ApiResponse<T> error(String mensaje) {
        return new ApiResponse<>(500, mensaje, null);
    }
}
