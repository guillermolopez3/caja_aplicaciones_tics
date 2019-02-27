package com.gru.cajaaplicacionestics.prueba_endless_scroll;

public class PruebaModelPost extends Item {
    private int id;
    private String created_at;
    private String title;
    private String copete;
    private String image;
    private String tags;
    private int id_tipo_activity;
    private String description;
    private String link;
    private Boolean fav;

    public PruebaModelPost(){}

    public PruebaModelPost(int id, String created_at, String title, String copete, String image, String tags, int id_tipo_activity, String description, String link) {
        this.id = id;
        this.created_at = created_at;
        this.title = title;
        this.copete = copete;
        this.image = image;
        this.tags = tags;
        this.id_tipo_activity = id_tipo_activity;
        this.description = description;
        this.link = link;
    }

    public PruebaModelPost(int id, String title, String copete, String image, int id_tipo_activity, String description, String link) {
        this.id = id;
        this.title = title;
        this.copete = copete;
        this.image = image;
        this.id_tipo_activity = id_tipo_activity;
        this.description = description;
        this.link = link;
    }

    public PruebaModelPost(int id, String title, String copete, String image, int id_tipo_activity, String description, String link, boolean fav) {
        this.id = id;
        this.title = title;
        this.copete = copete;
        this.image = image;
        this.id_tipo_activity = id_tipo_activity;
        this.description = description;
        this.link = link;
        this.fav = fav;
    }

    public PruebaModelPost(int id, String created_at, String title, String copete, String image, String tags) {
        this.id = id;
        this.created_at = created_at;
        this.title = title;
        this.copete = copete;
        this.image = image;
        this.tags=tags;
    }

    public PruebaModelPost(int id, String image, String link) {
        this.id = id;
        this.image = image;
        this.link = link;
    }

    public Boolean getFav() {
        return fav;
    }

    public void setFav(Boolean fav) {
        this.fav = fav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCopete() {
        return copete;
    }

    public void setCopete(String copete) {
        this.copete = copete;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getId_tipo_activity() {
        return id_tipo_activity;
    }

    public void setId_tipo_activity(int id_tipo_activity) {
        this.id_tipo_activity = id_tipo_activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
