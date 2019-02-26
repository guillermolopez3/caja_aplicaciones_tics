package com.gru.cajaaplicacionestics.nuestra_escuela.backend;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gru.cajaaplicacionestics.backend.VolleySingleton;
import com.gru.cajaaplicacionestics.model.ModelPost;
import com.gru.cajaaplicacionestics.nuestra_escuela.auxiliares.IReturnPeticionGet;
import com.gru.cajaaplicacionestics.nuestra_escuela.model.SectionRecyclerNEModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ObtenerRecursosJornadasNE
{
    private final String URL = "http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/orientacion.json";
    private IReturnPeticionGet retornoArrayDatos;

    public void setRetornoArrayDatos(IReturnPeticionGet retornoArrayDatos) {
        this.retornoArrayDatos = retornoArrayDatos;
    }

    public void obtenerJson(Activity activity)
    {
        StringRequest request;
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(request=new StringRequest(Request.Method.GET,
                        URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                manejarDatosJson(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));
    }

    private void manejarDatosJson(String response)
    {
        ArrayList<SectionRecyclerNEModel> lista = new ArrayList<>();
        try{
            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
            JSONObject jsonObject = new JSONObject(response);
            JSONArray proyectando = jsonObject.getJSONArray("proyectando_2019");
            JSONArray aspectos = jsonObject.getJSONArray("formacion_aspectos");
            JSONArray recomendacion = jsonObject.getJSONArray("formacion_recomendacion");
            JSONArray cronograma = jsonObject.getJSONArray("cronograma_formacion");

            lista.add(new SectionRecyclerNEModel("Cronograma",separarDatos(cronograma)));
            lista.add(new SectionRecyclerNEModel("Aspectos Priorizados",separarDatos(aspectos)));
            lista.add(new SectionRecyclerNEModel("Recomendaciones",separarDatos(recomendacion)));
            lista.add(new SectionRecyclerNEModel("Proyectando el 2019",separarDatos(proyectando)));

        }catch (JSONException e){ lista = null;}
        catch (UnsupportedEncodingException e) {
            lista = null;
        }
        retornoArrayDatos.returnArray(lista);
    }

    private ArrayList<ModelPost> separarDatos(JSONArray array)
    {
        ArrayList<ModelPost> lista = new ArrayList<>();
        try{
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
                lista.add(post);
            }
        }
        catch (JSONException e){ lista = null;}
        return lista;
    }
}
