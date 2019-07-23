package com.gru.cajaaplicacionestics.estudiar_info.model;

public class CarrerasModel
{
    private int id;
    private String nombreCarrera;
    private String institucion;
    private String duracion;

    private String direccion;
    private int caracter_id;
    private String telefono;
    private String web;
    private String url_logo;

    public CarrerasModel(Integer id, String nombreCarrera, String institucion, String duracion) {
        this.id = id;
        this.nombreCarrera = nombreCarrera;
        this.institucion = institucion;
        this.duracion = duracion;
    }


    public CarrerasModel(Integer id, String nombreCarrera, String institucion, String direccion, int caracter_id, String telefono, String web, String url_logo) {
        this.id = id;
        this.nombreCarrera = nombreCarrera;
        this.institucion = institucion;
        this.direccion = direccion;
        this.caracter_id = caracter_id;
        this.telefono = telefono;
        this.web = web;
        this.url_logo = url_logo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCaracter_id() {
        return caracter_id;
    }

    public void setCaracter_id(int caracter_id) {
        this.caracter_id = caracter_id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getUrl_logo() {
        return url_logo;
    }

    public void setUrl_logo(String url_logo) {
        this.url_logo = url_logo;
    }
}
