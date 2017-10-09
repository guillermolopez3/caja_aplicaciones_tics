package com.gru.cajaaplicacionestics.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.map.MapsActivity;
import com.gru.cajaaplicacionestics.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EspecialSemanaTicActivity extends AppCompatActivity
{
    Button btn_vivo, btn_acreditacion,btn_agenda,btn_mapa;

    String URL_BASE="";
    String URL_VIDEO="";

    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especial_semana_tic);

        showToolbar("Semana Tic",false);

        URL_BASE=getResources().getString(R.string.URL_BASE);
        URL_VIDEO= getResources().getString(R.string.URL_VIDEO);

        btn_vivo = (Button) findViewById(R.id.cardBtnVivo);
        btn_acreditacion = (Button) findViewById(R.id.cardBtnAcreditacion);
        btn_agenda = (Button) findViewById(R.id.cardBtnGrilla);
        btn_mapa = (Button) findViewById(R.id.cardBtnMapa);

        btn_vivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerUrlVideoVivo();
            }
        });

        btn_acreditacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EspecialSemanaTicActivity.this,SemanaTicActivity.class);
                startActivity(i);
            }
        });

        btn_agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EspecialSemanaTicActivity.this,GrillaSemanaTicActivity.class);
                startActivity(i);
            }
        });

        btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EspecialSemanaTicActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

    }

    public void showToolbar(String tittle, boolean upButton)
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    private void obtenerUrlVideoVivo() {
        final ProgressDialog dialog=new ProgressDialog(EspecialSemanaTicActivity.this);
        dialog.setMessage("Cargando datos...");
        dialog.show();
        Log.e("entrando", "obtener");
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_BASE + URL_VIDEO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("post");

                            JSONObject o = array.getJSONObject(0);
                            Post post = new Post();
                            post.setNombreRecurso("Semana Tic");
                            post.setHastag("#semana tic, #VIVO");
                            post.setDescripcion("Todo sobre la semana tic");

                            url = o.getString("url_video");
                            post.setVideo_id(url);
                            post.setDescripcion(o.getString("descripcion"));
                            post.setFecha(o.getString("fecha"));
                            Intent i = new Intent(EspecialSemanaTicActivity.this,YoutubeActivity.class);
                            i.putExtra("data",post);
                            startActivity(i);
                            Log.e("url video ob", url);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(EspecialSemanaTicActivity.this,SeleccionActivity.class);
        startActivity(i);
    }
}
