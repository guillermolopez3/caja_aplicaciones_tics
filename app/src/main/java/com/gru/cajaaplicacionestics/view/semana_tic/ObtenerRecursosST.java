package com.gru.cajaaplicacionestics.view.semana_tic;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gru.cajaaplicacionestics.adapter.AdapterNPost;
import com.gru.cajaaplicacionestics.backend.VolleySingleton;
import com.gru.cajaaplicacionestics.model.ModelPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

public class ObtenerRecursosST
{
    private static ArrayList<ModelPost> lista;
    private static ArrayList<PopUpModel> model_popup;

    private static boolean TERMINO_BUSQUEDA_URL = false;

    public static ArrayList<ModelPost> obtenerRecursos(String url, Activity act, String nombre_array, AdapterNPost adapter)
    {
        //url= donde me conecto, nombre_array es el nombre de los [] del json
        lista = new ArrayList<>();
        cargarLista(act,url,nombre_array,adapter);
        return lista;
    }

    public static void obtenetPopUp(Activity activity)
    {
        model_popup = new ArrayList<>();
        cargarListaPopUp(activity);
    }

    public static ArrayList<PopUpModel> getModel_popup() {
        return model_popup;
    }

    public static void setModel_popup(ArrayList<PopUpModel> model_popup) {
        ObtenerRecursosST.model_popup = model_popup;
    }

    public static boolean isTerminoBusquedaUrl() {
        return TERMINO_BUSQUEDA_URL;
    }

    public static void setTerminoBusquedaUrl(boolean terminoBusquedaUrl) {
        TERMINO_BUSQUEDA_URL = terminoBusquedaUrl;
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

    private static void cargarListaPopUp(Activity activity)
    {
        StringRequest request;
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(request=new StringRequest(Request.Method.GET,
                        "http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/popup.json",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    //de esta manera me toma los acentos
                                    response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray array= jsonObject.getJSONArray("popup");

                                    for(int i=0; i< array.length();i++)
                                    {
                                        JSONObject o = array.getJSONObject(i);
                                        Log.e("json", o.toString());
                                        PopUpModel pu = new PopUpModel(
                                                o.getString("fecha_desde"),
                                                o.getString("fecha_hasta"),
                                                o.getString("url_img")
                                        );
                                        model_popup.add(pu);
                                        TERMINO_BUSQUEDA_URL = true;
                                    }

                                } catch (JSONException e) {
                                    Log.e("error",e.getMessage());
                                    e.printStackTrace();
                                    TERMINO_BUSQUEDA_URL = true;
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error volley",error.getMessage());
                        TERMINO_BUSQUEDA_URL = true;
                    }
                }));
    }


    public static class PopUpModel
    {
        String fecha_desde;
        String fecha_hasta;
        String url;

        public PopUpModel(String fecha_desde, String fecha_hasta, String url) {
            this.fecha_desde = fecha_desde;
            this.fecha_hasta = fecha_hasta;
            this.url = url;
        }

        public String getFecha_desde() {
            return fecha_desde;
        }

        public void setFecha_desde(String fecha_desde) {
            this.fecha_desde = fecha_desde;
        }

        public String getFecha_hasta() {
            return fecha_hasta;
        }

        public void setFecha_hasta(String fecha_hasta) {
            this.fecha_hasta = fecha_hasta;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }


}
