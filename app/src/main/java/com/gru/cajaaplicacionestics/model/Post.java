package com.gru.cajaaplicacionestics.model;

/**
 * Created by guill on 14/06/2017.
 */

public class Post
{
    private String id;
    private String urlImagen;
    private int imagenLocal;
    private String nombreRecurso;
    private String descripcionCorta;
    private String hastag;

    public Post(){}

    public Post(String nombre, String desc, String img)
    {
        nombreRecurso=nombre;
        descripcionCorta=desc;
        urlImagen=img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public int getImagenLocal() {
        return imagenLocal;
    }

    public void setImagenLocal(int imagenLocal) {
        this.imagenLocal = imagenLocal;
    }

    public String getNombreRecurso() {
        return nombreRecurso;
    }

    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getHastag() {
        return hastag;
    }

    public void setHastag(String hastag) {
        this.hastag = hastag;
    }
}