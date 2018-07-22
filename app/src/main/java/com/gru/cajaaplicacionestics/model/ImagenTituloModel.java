package com.gru.cajaaplicacionestics.model;

public class ImagenTituloModel
{
    private String urlImg;
    private String titulo;

    public ImagenTituloModel(String urlImg, String titulo) {
        this.urlImg = urlImg;
        this.titulo = titulo;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
