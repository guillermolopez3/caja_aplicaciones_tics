package com.gru.cajaaplicacionestics.model;

/**
 * Created by guill on 30/06/2017.
 */

public class ModelAudio
{
    private String id;
    private String urlImagen;
    private int imagenLocal;
    private String nombreRecurso;
    private String descripcionCorta;
    private String hastag;
    private String tipo_recurso;
    private String escuela= "ESCUELA: ";
    private String docente= "DOCENTE: ";

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

    public String getTipo_recurso() {
        return tipo_recurso;
    }

    public void setTipo_recurso(String tipo_recurso) {
        this.tipo_recurso = tipo_recurso;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = this.escuela +  escuela;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = this.docente +  docente;
    }
}
