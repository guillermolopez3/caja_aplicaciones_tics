package com.gru.cajaaplicacionestics.model;

public class ItemSTModel
{
    private String dia;
    private String hora;
    private String mananaTarde;
    private String titulo;
    private String disertante;
    private String ciudad;
    private String salon;

    public ItemSTModel(){}

    public ItemSTModel(String dia, String hora, String mananaTarde, String titulo, String disertante, String ciudad, String salon) {
        this.dia = dia;
        this.hora = hora;
        this.mananaTarde = mananaTarde;
        this.titulo = titulo;
        this.disertante = disertante;
        this.ciudad = ciudad;
        this.salon = salon;
    }


    public String getMananaTarde() {
        return mananaTarde;
    }

    public void setMananaTarde(String mananaTarde) {
        this.mananaTarde = mananaTarde;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDisertante() {
        return disertante;
    }

    public void setDisertante(String disertante) {
        this.disertante = disertante;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }
}
