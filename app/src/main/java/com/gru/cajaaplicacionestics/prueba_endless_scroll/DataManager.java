package com.gru.cajaaplicacionestics.prueba_endless_scroll;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gru.cajaaplicacionestics.backend.FavoritosBackend;
import com.gru.cajaaplicacionestics.backend.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataManager
{
    private Context context;
    private int ultima_pagina = 0;

    public DataManager(Context context){
        this.context = context;
    }


    public int getUltima_pagina() {
        return ultima_pagina;
    }


    public void fetchData(final int pagina_actual,final IDataValue iDataValue)
    {
        final String URL = FavoritosBackend.getUrlNovedades(pagina_actual,"2018");
        final ArrayList<Item> lista = new ArrayList<>();
        VolleySingleton.getInstancia(context).
                addToRequestQueue(new StringRequest(Request.Method.GET,URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response); //convierte toda la respuesta en json
                                    JSONArray array= jsonObject.getJSONArray("data"); //lo que est en corchetes lo convierto en array
                                    ultima_pagina = jsonObject.getInt("last_page");
                                    Log.e("pag","ultima:"+ ultima_pagina + "- actual:" + pagina_actual);

                                    for(int i=0; i< array.length();i++)
                                    {
                                        JSONObject o = array.getJSONObject(i);
                                        PruebaModelPost post = new PruebaModelPost(
                                                o.getInt("id"),
                                                o.getString("created_at"),
                                                o.getString("title"),
                                                o.getString("copete"),
                                                o.getString("image"),
                                                o.getString("tags"),
                                                o.getInt("id_tipo_activity"),
                                                o.getString("description"),
                                                o.getString("link")
                                        );
                                        if(o.has("fav"))
                                        {
                                            if(o.getString("fav").equals("null")){
                                                post.setFav(false);
                                            }else {
                                                post.setFav(true);
                                            }

                                        }

                                        lista.add(post);
                                    }
                                    Log.e("termino httpget","fin");
                                    iDataValue.getArrayValue(lista);
                                } catch (JSONException e) {
                                    Log.e("error",e.getMessage());
                                    iDataValue.showError("error");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                        iDataValue.showError("error");
                    }
                }));
    }
}
