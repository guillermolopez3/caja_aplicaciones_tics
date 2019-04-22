package com.gru.cajaaplicacionestics.model;

public class ModelNotificaciones
{
    private String titulo;
    private String fecha;
    private String nivel;
    private String destinatario;
    private String modalidad;
    private String hs_cert;
    private boolean esNuevo;
    private String link;

    public ModelNotificaciones(String titulo, String fecha, String nivel, String destinatario, String modalidad, String hs_cert, boolean esNuevo, String link) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.nivel = nivel;
        this.destinatario = destinatario;
        this.modalidad = modalidad;
        this.hs_cert = hs_cert;
        this.esNuevo = esNuevo;
        this.link = link;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getHs_cert() {
        return hs_cert;
    }

    public void setHs_cert(String hs_cert) {
        this.hs_cert = hs_cert;
    }

    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
