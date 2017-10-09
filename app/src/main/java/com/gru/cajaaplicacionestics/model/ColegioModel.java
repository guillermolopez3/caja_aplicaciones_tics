package com.gru.cajaaplicacionestics.model;

/**
 * Created by guill on 05/09/2017.
 */

public class ColegioModel
{
    private int id;
    private String cue;
    private String nombre_cole;
    private String localidad;
    private String inspeccion;
    private String modalidad;
    private String ar;
    private String fp;

    public ColegioModel(){}

    public ColegioModel(int id, String cue, String nombre_cole, String localidad, String inspeccion, String modalidad, String ar, String fp) {
        this.id = id;
        this.cue = cue;
        this.nombre_cole = nombre_cole;
        this.localidad = localidad;
        this.inspeccion = inspeccion;
        this.modalidad = modalidad;
        this.ar = ar;
        this.fp = fp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCue() {
        return cue;
    }

    public void setCue(String cue) {
        this.cue = cue;
    }

    public String getNombre_cole() {
        return nombre_cole;
    }

    public void setNombre_cole(String nombre_cole) {
        this.nombre_cole = nombre_cole;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getInspeccion() {
        return inspeccion;
    }

    public void setInspeccion(String inspeccion) {
        this.inspeccion = inspeccion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getFp() {
        return fp;
    }

    public void setFp(String fp) {
        this.fp = fp;
    }
}
