package com.gru.cajaaplicacionestics.estudiar_info.model;

public class CarrerasModel
{
    private Integer id;
    private String nombreCarrera;
    private String institucion;
    private String duracion;

    public CarrerasModel(Integer id, String nombreCarrera, String institucion, String duracion) {
        this.id = id;
        this.nombreCarrera = nombreCarrera;
        this.institucion = institucion;
        this.duracion = duracion;
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
}
