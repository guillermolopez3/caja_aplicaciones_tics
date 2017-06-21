package com.gru.cajaaplicacionestics.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterRecursos;
import com.gru.cajaaplicacionestics.model.ModelRecursos;
import com.gru.cajaaplicacionestics.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guill on 10/05/2017.
 */

public class FragmentPrincipal extends Fragment
{
    RecyclerView recyclerView;
    AdapterRecursos adapter;
    ArrayList<Post> lista;
    String categoria=""; //con esto se en que categoría estoy
    String consulta="";

    private String URL_BASE = "http://faltaequipo.xyz";
    private static final String URL_JSON = "/obtener_post.php";


    public FragmentPrincipal(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(getArguments()!= null)
        {
            categoria = getArguments().getString("categoria");
            consulta= getArguments().getString("consulta");
        }

        View view = inflater.inflate(R.layout.fragment_principal,container,false);

        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerPrincipal);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        lista= new ArrayList<>();

        if(new String(categoria).equals("video"))
        {
            adapter = new AdapterRecursos(getActivity(),BuildDummy());
            recyclerView.setAdapter(adapter);
        }
        else {
            cargarLista();
        }


        return view;

    }

    private void cargarLista()
    {
        final ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Cargando datos...");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_BASE + URL_JSON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array= jsonObject.getJSONArray("post");

                            for(int i=0; i< array.length();i++)
                            {
                                JSONObject o = array.getJSONObject(i);
                                Post post = new Post(
                                        o.getString("titulo"),
                                        o.getString("descripcion_corta"),
                                        o.getString("url_img_post")
                                );
                                lista.add(post);
                            }
                            adapter= new AdapterRecursos(getActivity(),lista);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


    private ArrayList<Post> BuildDummy()
    {
        ArrayList<Post> model = new ArrayList<>();
        Post rec = new Post();
        rec.setNombreRecurso("EXPERIENCIA PRIMARIA DIGITAL");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        model.add(rec);

        rec= new Post();
        rec.setNombreRecurso("EXPERIENCIA PRIMARIA DIGITAL");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        model.add(rec);

        rec= new Post();
        rec.setNombreRecurso("EXPERIENCIA PRIMARIA DIGITAL");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        model.add(rec);

        rec= new Post();
        rec.setNombreRecurso("EXPERIENCIA PRIMARIA DIGITAL");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        model.add(rec);

        rec= new Post();
        rec.setNombreRecurso("EXPERIENCIA PRIMARIA DIGITAL");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        model.add(rec);


        return model;
    }
}
