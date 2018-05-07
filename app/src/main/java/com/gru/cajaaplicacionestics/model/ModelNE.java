package com.gru.cajaaplicacionestics.model;

import java.io.Serializable;

/**
 * Created by guill on 20/11/2017.
 */

public class ModelNE implements Serializable
{
    private int id;
    private String url_img;
    private String descripcion;
    private String url_pdf;
    private String seccion;
    private String nivel;
    private String otra_descripcion;
    private String ano;

    public ModelNE(int id, String url_img, String descripcion, String url_pdf, String seccion, String nivel, String otra_descripcion) {
        this.id = id;
        this.url_img = url_img;
        this.descripcion = descripcion;
        this.url_pdf = url_pdf;
        this.seccion = seccion;
        this.nivel = nivel;
        this.otra_descripcion = otra_descripcion;
    }

    public ModelNE(int id, String url_img, String descripcion, String url_pdf, String seccion, String nivel, String otra_descripcion, String ano) {
        this.id = id;
        this.url_img = url_img;
        this.descripcion = descripcion;
        this.url_pdf = url_pdf;
        this.seccion = seccion;
        this.nivel = nivel;
        this.otra_descripcion = otra_descripcion;
        this.ano = ano;
    }



    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl_pdf() {
        return url_pdf;
    }

    public void setUrl_pdf(String url_pdf) {
        this.url_pdf = url_pdf;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getOtra_descripcion() {
        return otra_descripcion;
    }

    public void setOtra_descripcion(String otra_descripcion) {
        this.otra_descripcion = otra_descripcion;
    }
}
