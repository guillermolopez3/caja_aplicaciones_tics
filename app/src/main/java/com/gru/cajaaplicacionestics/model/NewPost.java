package com.gru.cajaaplicacionestics.model;

import java.io.Serializable;

/**
 * Usado
 */

public class NewPost implements Serializable
{
    private int id;
    private String nombre;
    private String descripcion_corta;
    private String fecha;
    private String url_img;
    private String tag;
    private String detalle;
    private String ulr_mas;
    private String categoria;

    public NewPost(){}

    public NewPost(int id, String nombre, String descripcion_corta, String fecha, String url_img, String tag, String detalle, String ulr_mas, String cat) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion_corta = descripcion_corta;
        this.fecha = fecha;
        this.url_img = url_img;
        this.tag = tag;
        this.detalle = detalle;
        this.ulr_mas = ulr_mas;
        categoria=cat;
    }

    public NewPost(int id, String nombre, String descripcion_corta, String fecha, String url_img, String tag, String detalle, String ulr_mas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion_corta = descripcion_corta;
        this.fecha = fecha;
        this.url_img = url_img;
        this.tag = tag;
        this.detalle = detalle;
        this.ulr_mas = ulr_mas;

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

    public String getDescripcion_corta() {
        return descripcion_corta;
    }

    public void setDescripcion_corta(String descripcion_corta) {
        this.descripcion_corta = descripcion_corta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getUlr_mas() {
        return ulr_mas;
    }

    public void setUlr_mas(String ulr_mas) {
        this.ulr_mas = ulr_mas;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
