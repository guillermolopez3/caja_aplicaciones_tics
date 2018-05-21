package com.gru.cajaaplicacionestics.backend;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gru.cajaaplicacionestics.adapter.AdapterNPost;
import com.gru.cajaaplicacionestics.model.ModelNE;
import com.gru.cajaaplicacionestics.model.ModelPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Entity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RecursosNE
{
    private static ArrayList<ModelPost> lista;

    public static ArrayList<ModelPost> obtenerRecursos(String url, Activity act, String nombre_array, AdapterNPost adapter)
    {
        //url= donde me conecto, nombre_array es el nombre de los [] del json
        lista = new ArrayList<>();
        cargarLista(act,url,nombre_array,adapter);
        return lista;
    }

    private static void cargarLista(Activity activity, String url_recurso, final String nombre_array, final AdapterNPost adapt)
    {
        StringRequest request;
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(request=new StringRequest(Request.Method.GET,
                        url_recurso,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    //de esta manera me toma los acentos
                                    response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray array= jsonObject.getJSONArray(nombre_array);

                                    for(int i=0; i< array.length();i++)
                                    {
                                        JSONObject o = array.getJSONObject(i);
                                        ModelPost post = new ModelPost(
                                                o.getInt("id"),
                                                o.getString("title"),
                                                o.getString("copete"),
                                                o.getString("image"),
                                                o.getInt("id_tipo_activity"),
                                                o.getString("descripcion"),
                                                o.getString("link")
                                        );
                                        adapt.add(post);
                                    }

                                } catch (JSONException e) {
                                    Log.e("error",e.getMessage());
                                    e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));
    }
}
