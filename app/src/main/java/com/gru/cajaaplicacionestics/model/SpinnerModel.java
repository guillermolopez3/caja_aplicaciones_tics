package com.gru.cajaaplicacionestics.model;

/**
 * Created by guill on 21/09/2017.
 */

public class SpinnerModel
{
    private int id;
    private String nombre;

    public SpinnerModel(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}
