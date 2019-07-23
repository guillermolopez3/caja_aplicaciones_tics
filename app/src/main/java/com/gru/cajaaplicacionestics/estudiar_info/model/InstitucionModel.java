package com.gru.cajaaplicacionestics.estudiar_info.model;

public class InstitucionModel
{
    private int id;
    private String nombre;
    private String url_logo;

    public InstitucionModel(int id, String nombre, String url_logo) {
        this.id = id;
        this.nombre = nombre;
        this.url_logo = url_logo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl_logo() {
        return url_logo;
    }

    public void setUrl_logo(String url_logo) {
        this.url_logo = url_logo;
    }
}
