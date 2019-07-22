package com.gru.cajaaplicacionestics.auxiliares;

public class Constantes
{
    public static final int ID_RECLAMO_SERVICIO_TECNICO = 1;
    public static final int ID_RECLAMO_CAPACITACION = 2;

    public static final String URL_BASE = "https://www.igualdadycalidadcba.gov.ar/CajaTIC/api/";
    public static final String URL_INSERTAR_RECLAMO = "insertarReclamo";
    public static final String URL_TIPO_RECLAMO = "getProblemasReclamos?id=";
    public static final String URL_COLE = "getColegios";
    public static final String URL_NUEVO_RECURSO = "nuevoRecurso";

    public static final String URL_GET_NOVEDADES_FAV = "getAllNovedadesConFav";
    public static final String URL_GET_RECURSOS_DIDAC_FAV = "getPostEspacioDidaConFav";
    public static final String URL_GET_APRENDER_CONECTADOS_FAV = "getAprenderConectadosConFav";
    public static final String URL_SEARCH_FAV = "getSearchFav";
    public static final String URL_GET_ALL_FAV = "getAllFav";

    //novedades
    public static final String URL_GET_ALL_NOVEDADES = "posts/getNovedades";

    //url ppade
    public static final String URL_PPADE_PRESENTACION = "https://www.igualdadycalidadcba.gov.ar/CajaTIC/view/ppade.html";
    public static final String URL_PPADE_CONTACTO = "https://www.igualdadycalidadcba.gov.ar/CajaTIC/view/ppade_contacto.html";
    public static final String URL_PPADE_COHORTE = "https://www.igualdadycalidadcba.gov.ar/CajaTIC/js/ppade.json";

    public static final String URL_INSERTAR_USUARIO = "insertarUsuario";
    public static final String URL_UPDATE_TOKEN = "updateToken";

    //fav
    public static final String URL_INSERTAR_FAV = "insertarFav";
    public static final String URL_QUITAR_FAV = "quitarFav";

    //notificaciones
    public static final String URL_NOTIFICACIONES = "https://www.igualdadycalidadcba.gov.ar/CajaTIC/js/alertas.json";

    //donde estudiar
    public static final String URL_BASE_OVO = "https://www.igualdadycalidadcba.gov.ar/OVO/api/";
    public static final String URL_ALL_CARRERAS = "listCarreras";

}
