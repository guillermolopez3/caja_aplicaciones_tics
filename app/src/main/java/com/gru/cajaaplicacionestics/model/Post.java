package com.gru.cajaaplicacionestics.model;

import java.io.Serializable;

/**
 * Created by guill on 14/06/2017.
 */

public class Post implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String id;
    private String urlImagen;
    private int imagenLocal;
    private int audio_local;
    private String url_recurso;
    private String nombreRecurso;
    private String descripcionCorta;
    private String descripcion;
    private String hastag;
    private String tipo_recurso; //lo uso para saber si es un video, audio, etc
    private String escuela= "ESCUELA: ";
    private String docente= "DOCENTE: ";
    private String video_id;
    private String fecha;

    public Post(){}

    public Post(String nombre, String desc, String img)
    {
        nombreRecurso=nombre;
        descripcionCorta=desc;
        urlImagen=img;
    }



    public Post(String video)
    {
        video_id=video;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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
        this.escuela = escuela;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl_recurso() {
        return url_recurso;
    }

    public void setUrl_recurso(String url_recurso) {
        this.url_recurso = url_recurso;
    }

    public int getAudio_local() {
        return audio_local;
    }

    public void setAudio_local(int audio_local) {
        this.audio_local = audio_local;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
}
