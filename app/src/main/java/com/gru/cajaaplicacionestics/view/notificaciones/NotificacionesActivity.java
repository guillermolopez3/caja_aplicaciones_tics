package com.gru.cajaaplicacionestics.view.notificaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterNPost;
import com.gru.cajaaplicacionestics.adapter.AdapterNotificaciones;
import com.gru.cajaaplicacionestics.auxiliares.Constantes;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.backend.VolleySingleton;
import com.gru.cajaaplicacionestics.model.ModelNotificaciones;
import com.gru.cajaaplicacionestics.model.ModelPost;
import com.gru.cajaaplicacionestics.model.NewPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class NotificacionesActivity extends AppCompatActivity {
    RecyclerView recycler;
    ProgressBar progressBar;
    AdapterNotificaciones adapter;
    ArrayList<ModelNotificaciones> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        recycler = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progress);

        MetodosComunes.showToolbar("Notificaciones",true,this);

        lista = new ArrayList<>();
        adapter = new AdapterNotificaciones(lista, this);

        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
        cargarLista();
    }

    private void cargarLista()
    {
        VolleySingleton.getInstancia(this).
                addToRequestQueue(new StringRequest(Request.Method.GET,
                        Constantes.URL_NOTIFICACIONES,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    //de esta manera me toma los acentos
                                    response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray array= jsonObject.getJSONArray("notificaciones");

                                    for(int i=0; i< array.length();i++)
                                    {
                                        JSONObject o = array.getJSONObject(i);
                                        ModelNotificaciones post = new ModelNotificaciones(
                                                o.getString("titulo"),
                                                o.getString("fecha"),
                                                o.getString("nivel"),
                                                o.getString("destinatario"),
                                                o.getString("modalidad"),
                                                o.getString("hs_cert"),
                                                o.getBoolean("esNuevo"),
                                                o.getString("link")
                                        );
                                        lista.add(post);
                                    }
                                    progressBar.setVisibility(View.GONE);
                                    adapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    Log.e("error",e.getMessage());
                                   // e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    //e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));
    }
}
