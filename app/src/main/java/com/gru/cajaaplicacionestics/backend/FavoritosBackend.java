package com.gru.cajaaplicacionestics.backend;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.gru.cajaaplicacionestics.auxiliares.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FavoritosBackend
{
    public static void insertarFav(int id_post,Activity a)
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null)
        {
            insertFavBD(auth.getCurrentUser().getEmail(),auth.getCurrentUser().getUid(),id_post,a);
        }
    }

    private static void insertFavBD(final String mail, final String id_social, final int id_post, final Activity a) //trae los datos de la BD, los parsea con volley y lo carga a la lista
    {
        String URL= Constantes.URL_BASE + Constantes.URL_INSERTAR_FAV;

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String cod_rta= jsonObject.getString("estado");

                            if(cod_rta.equals("error"))
                            {
                                Toast.makeText(a,"No se pudo agregar",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(a, "Agregado a favoritos", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(a,"No se pudo agregar",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(a,"No se pudo agregar",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("mail",mail);
                hashMap.put("id_social",id_social);
                hashMap.put("post_id",String.valueOf( id_post));
                return hashMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(a);
        requestQueue.add(stringRequest);
    }

    public static void quitarFav(int id_post,Activity a)
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null)
        {
            quitFavBD(auth.getCurrentUser().getEmail(),auth.getCurrentUser().getUid(),id_post,a);
        }
    }

    private static void quitFavBD(final String mail, final String id_social, final int id_post, final Activity a) //trae los datos de la BD, los parsea con volley y lo carga a la lista
    {
        String URL= Constantes.URL_BASE + Constantes.URL_QUITAR_FAV;

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String cod_rta= jsonObject.getString("estado");

                            if(cod_rta.equals("error"))
                            {
                                Toast.makeText(a,"No se pudo eliminar de favoritos",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(a, "Post quitado de favoritos", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(a,"No se pudo eliminar de favoritos",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(a,"No se pudo agregar",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("mail",mail);
                hashMap.put("id_social",id_social);
                hashMap.put("post_id",String.valueOf( id_post));
                return hashMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(a);
        requestQueue.add(stringRequest);
    }



    public static String getUrlNovedades(int pag,String ano)
    {
        String mail = "";
        String id="";
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null)
        {
            mail = auth.getCurrentUser().getEmail();
            id= auth.getCurrentUser().getUid();
        }
        String URL = Constantes.URL_BASE + Constantes.URL_GET_NOVEDADES_FAV + "?page=" + pag + "&ano=" + ano + "&mail="
                + mail + "&id_social=" + id;
        return URL;
    }

    public static String getUrlEspacioDidactico(int pag,int level)
    {
        String mail = "";
        String id="";
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null)
        {
            mail = auth.getCurrentUser().getEmail();
            id= auth.getCurrentUser().getUid();
        }
        String URL = Constantes.URL_BASE + Constantes.URL_GET_RECURSOS_DIDAC_FAV + "?page=" + pag + "&level=" + level + "&mail="
                + mail + "&id_social=" + id;
        return URL;
    }

    public static String getUrlAprenderConectados(int pag,int seccion)
    {
        String mail = "";
        String id="";
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null)
        {
            mail = auth.getCurrentUser().getEmail();
            id= auth.getCurrentUser().getUid();
        }
        String URL = Constantes.URL_BASE + Constantes.URL_GET_APRENDER_CONECTADOS_FAV + "?page=" + pag + "&seccion=" +seccion +  "&mail="
                + mail + "&id_social=" + id;
        return URL;
    }

    public static String getUrlsearch(int pag,String consulta)
    {
        String mail = "";
        String id="";
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null)
        {
            mail = auth.getCurrentUser().getEmail();
            id= auth.getCurrentUser().getUid();
        }
        String URL = Constantes.URL_BASE + Constantes.URL_SEARCH_FAV + "?page=" + pag  + "&consulta=" +consulta + "&mail="
                + mail + "&id_social=" + id;
        return URL;
    }

    public static String getUrlFav(int pag)
    {
        String mail = "";
        String id="";
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null)
        {
            mail = auth.getCurrentUser().getEmail();
            id= auth.getCurrentUser().getUid();
        }
        String URL = Constantes.URL_BASE + Constantes.URL_GET_ALL_FAV + "?page=" + pag  + "&mail="
                + mail + "&id_social=" + id;
        Log.e("url fav",URL);
        return URL;
    }
}
