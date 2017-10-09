package com.gru.cajaaplicacionestics.model;

/**
 * Created by guill on 08/10/2017.
 */

public class ModelNovedades
{
    private int id;
    private String url_imagen;
    private String url_pdf;
    private String detalle;

    public ModelNovedades(int id, String url_imagen, String url_pdf) {
        this.id = id;
        this.url_imagen = url_imagen;
        this.url_pdf = url_pdf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getUrl_pdf() {
        return url_pdf;
    }

    public void setUrl_pdf(String url_pdf) {
        this.url_pdf = url_pdf;
    }
}
