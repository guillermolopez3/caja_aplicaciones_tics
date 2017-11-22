package com.gru.cajaaplicacionestics.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterNovedades;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.model.ModelNovedades;
import com.gru.cajaaplicacionestics.model.NewPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NovedadesActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    AdapterNovedades adapterNovedades;
    ArrayList<ModelNovedades> list = new ArrayList<>();

    private int limite=0;

    private String URL_BASE="http://www.igualdadycalidadcba.gov.ar/CajaTIC/consultas_app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novedades);

        MetodosComunes.showToolbar("Novedades",true,this);


        recyclerView=(RecyclerView) findViewById(R.id.recyclerNovedades);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapterNovedades= new AdapterNovedades(this,list);
        recyclerView.setAdapter(adapterNovedades);
        cargarNovedades();

    }

    private void cargarNovedades() {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Cargando datos...");
        dialog.show();
        Log.e("entrando", "obtener");
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_BASE + "GetNovedades.php?limite=" + limite,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("post");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                ModelNovedades post = new ModelNovedades(
                                        o.getInt("id"),
                                        o.getString("url_imagen"),
                                        o.getString("url_pdf")
                                );
                                list.add(post);
                            }

                            adapterNovedades.notifyDataSetChanged();
                            //adapter.setLoaded();
                            limite= limite + 10;
                        } catch (JSONException e) {
                            Log.e("error", e.toString());
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

}
