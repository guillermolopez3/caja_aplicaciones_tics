package com.gru.cajaaplicacionestics.model;

/**
 * Created by guill on 25/10/2017.
 */

public class ModelPopUp
{
    String url_imagen;
    String descripcion;
    String tipo_recurso;
    int id_post;

    public ModelPopUp(){}

    public ModelPopUp(String url_imagen, String descripcion, String tipo_recurso, int id_post) {
        this.url_imagen = url_imagen;
        this.descripcion = descripcion;
        this.tipo_recurso = tipo_recurso;
        this.id_post = id_post;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo_recurso() {
        return tipo_recurso;
    }

    public void setTipo_recurso(String tipo_recurso) {
        this.tipo_recurso = tipo_recurso;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }
}
