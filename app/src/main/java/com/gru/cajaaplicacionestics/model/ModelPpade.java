package com.gru.cajaaplicacionestics.model;

public class ModelPpade
{
    private String title;
    private String url_img;
    private int id_activity;
    private String url_link;
    private String sub_seccion;

    public ModelPpade(){}


    public ModelPpade(String title, String url_img, int id_activity, String url_link, String sub_seccion) {
        this.title = title;
        this.url_img = url_img;
        this.id_activity = id_activity;
        this.url_link = url_link;
        this.sub_seccion = sub_seccion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public int getId_activity() {
        return id_activity;
    }

    public void setId_activity(int id_activity) {
        this.id_activity = id_activity;
    }

    public String getUrl_link() {
        return url_link;
    }

    public void setUrl_link(String url_link) {
        this.url_link = url_link;
    }

    public String getSub_seccion() {
        return sub_seccion;
    }

    public void setSub_seccion(String sub_seccion) {
        this.sub_seccion = sub_seccion;
    }
}
