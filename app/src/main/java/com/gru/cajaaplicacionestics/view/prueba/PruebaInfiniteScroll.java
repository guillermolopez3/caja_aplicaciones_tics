package com.gru.cajaaplicacionestics.view.prueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterPruebaInfinite;
import com.gru.cajaaplicacionestics.backend.FavoritosBackend;
import com.gru.cajaaplicacionestics.backend.VolleySingleton;
import com.gru.cajaaplicacionestics.model.ModelPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PruebaInfiniteScroll extends AppCompatActivity
{
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private  int pagina_actual = 1; //representa la pag que tiene q cargar
    private int ultima_pagina = 0;
    private ArrayList<ModelPost> lista;
    private AdapterPruebaInfinite adapterPruebaInfinite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_infinite_scroll);

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.recycler);

        lista = new ArrayList<>();
        adapterPruebaInfinite = new AdapterPruebaInfinite(this,lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterPruebaInfinite);

        fetchData();

        //detecto el dezplazamiento
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //detecto cuando llego al final de la lista
                if(dy >0){
                    if(!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN))
                    {
                        if(pagina_actual <= ultima_pagina){
                            fetchData();
                            Log.e("deveria","cargar mas");
                        }
                    }
                }
            }
        });

    }


    public void fetchData()
    {
        String URL = FavoritosBackend.getUrlNovedades(pagina_actual,"2018");
        Request request;
        progressBar.setVisibility(View.VISIBLE);
        VolleySingleton.getInstancia(this).
                addToRequestQueue(request = new StringRequest(Request.Method.GET,URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                try {
                                    JSONObject jsonObject = new JSONObject(response); //convierte toda la respuesta en json
                                    JSONArray array= jsonObject.getJSONArray("data"); //lo que est en corchetes lo convierto en array
                                    ultima_pagina = jsonObject.getInt("last_page");
                                    pagina_actual = pagina_actual +1;
                                    Log.e("pag","ultima:"+ ultima_pagina + "- actual:" + pagina_actual);

                                    for(int i=0; i< array.length();i++)
                                    {
                                        JSONObject o = array.getJSONObject(i);
                                        ModelPost post = new ModelPost(
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
                                        adapterPruebaInfinite.notifyDataSetChanged();
                                    }
                                } catch (JSONException e) {
                                    Log.e("error",e.getMessage());
                                    // manejoError(true);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(PruebaInfiniteScroll.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}
