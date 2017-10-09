package com.gru.cajaaplicacionestics.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterNewPost;
import com.gru.cajaaplicacionestics.adapter.AdapterRecursos;
import com.gru.cajaaplicacionestics.model.ModelAudio;
import com.gru.cajaaplicacionestics.model.ModelRecursos;
import com.gru.cajaaplicacionestics.model.NewPost;
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
    AdapterNewPost adapter;
    ArrayList<NewPost> lista= new ArrayList<>(); //este lo voy a usar cuando traiga datos de internet
    String categoria=""; //con esto se en que categoría estoy
    String consulta="";
    LinearLayout sin_internet;

    private int inicial=-1;
    private int primaria=-1;
    private int secundaria=-1;

    private int limite=0;

    private ArrayList<Post> arrayTodos;//array que carga todos los recursos

    //private String URL_BASE = "http://faltaequipo.xyz";
    //private static final String URL_JSON = "/obtener_post.php";

    private String URL_BASE= "http://www.igualdadycalidadcba.gov.ar/CajaTIC/consultas_app/";


    String url="";

    public FragmentPrincipal(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(getArguments()!= null)
        {
            categoria    = getArguments().getString("categoria");
            consulta     = getArguments().getString("consulta");
            inicial      = getArguments().getInt("inicial");
            primaria     = getArguments().getInt("primaria");
            secundaria   = getArguments().getInt("secundaria");
        }

        View view = inflater.inflate(R.layout.fragment_principal,container,false);

        sin_internet= (LinearLayout) view.findViewById(R.id.sin_internet);
        sin_internet.setVisibility(View.GONE);

        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerPrincipal);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter= new AdapterNewPost(getActivity(),lista);
        recyclerView.setAdapter(adapter);

        Log.e("categoria",categoria);

        if(!consulta.equals(""))
        {
            buscarPost();
        }
        else if(categoria.equals("Todos")) {
            obtenerTodosLosPost();
        }
        else {
            obtenerPostXCategoria();
        }

        //list= new ArrayList<>();
       /* arrayTodos= new ArrayList<>();


        elegirAdapter();

        if(consulta!="")
        {
            buscar(view);
        }*/

        return view;

    }


    private void obtenerTodosLosPost() {
        final ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Cargando datos...");
        dialog.show();
        Log.e("entrando", "obtener");
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_BASE + "GetPost.php?limite="+limite + "&inicial="+inicial+"&primario="+primaria+"&secundario=" + secundaria,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("post");
                            Log.e("niveles", "ini" + inicial + " pri" + primaria + "sec" + secundaria);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                NewPost post = new NewPost(
                                        o.getInt("id"),
                                        o.getString("titulo"),
                                        o.getString("descripcion_corta"),
                                        o.getString("fecha"),
                                        o.getString("url_img_ppal"),
                                        o.getString("tags"),
                                        o.getString("detalle"),
                                        o.getString("url_mas"),
                                        o.getString("categoria")
                                );
                                lista.add(post);
                            }
                            Log.e("tamaño array","" + lista.size());
                            adapter.notifyDataSetChanged();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void obtenerPostXCategoria() {
        final ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Cargando datos...");
        dialog.show();
        Log.e("entrando", "obtener");
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_BASE + "GetPostCategoria.php?limite="+limite + "&inicial="+inicial+"&primario="+primaria+"&secundario=" + secundaria +
                        "&categoria=" + categoria,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("post");
                            Log.e("niveles", "ini" + inicial + " pri" + primaria + "sec" + secundaria);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                NewPost post = new NewPost(
                                        o.getInt("id"),
                                        o.getString("titulo"),
                                        o.getString("descripcion_corta"),
                                        o.getString("fecha"),
                                        o.getString("url_img_ppal"),
                                        o.getString("tags"),
                                        o.getString("detalle"),
                                        o.getString("url_mas"),
                                        o.getString("categoria")
                                );
                                lista.add(post);
                            }
                            Log.e("tamaño array","" + lista.size());
                            adapter.notifyDataSetChanged();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void buscarPost() {
        final ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Cargando datos...");
        dialog.show();
        Log.e("entrando", "obtener");
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_BASE + "BuscarPost.php?limite="+limite + "&inicial="+inicial+"&primario="+primaria+"&secundario=" + secundaria +
                        "&busqueda=" + consulta,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("post");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                NewPost post = new NewPost(
                                        o.getInt("id"),
                                        o.getString("titulo"),
                                        o.getString("descripcion_corta"),
                                        o.getString("fecha"),
                                        o.getString("url_img_ppal"),
                                        o.getString("tags"),
                                        o.getString("detalle"),
                                        o.getString("url_mas"),
                                        o.getString("categoria")
                                );
                                lista.add(post);
                            }
                            Log.e("tamaño array","" + lista.size());
                            adapter.notifyDataSetChanged();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


   /* private void buscar(View view) //metodo que solo sirve para los datos harcodeados
    {
        ArrayList<Post> resultado=new ArrayList<>();
        for(Post p : arrayTodos){
            String titulo=p.getNombreRecurso();
            if(titulo.matches("(?i)(" +  consulta + ").*"))
            {
                resultado.add(p);
            }
        }

        if(resultado.isEmpty()) //si no encuentro resultados en la busqueda, muestro el mensaje
        {
            Log.e("sin datos","array");
            sin_internet.setVisibility(View.VISIBLE);
            ImageView imagen = (ImageView)view.findViewById(R.id.imgSinInternet);
            TextView texto = (TextView)view.findViewById(R.id.txtSinInternet);
            imagen.setVisibility(View.GONE);
            texto.setText("No se encontraron resultados");

        }

        adapter = new AdapterRecursos(getActivity(),resultado);
        recyclerView.setAdapter(adapter);
    }

    private void elegirAdapter()
    {
        if(new String(categoria).equals("video"))
        {
            adapter = new AdapterRecursos(getActivity(),buildVideo(url));
            recyclerView.setAdapter(adapter);
        }
        else if(new String(categoria).equals("audio")){
            adapter = new AdapterRecursos(getActivity(),buildAudio());
            recyclerView.setAdapter(adapter);
        }
        else if(new String(categoria).equals("pd")){
            adapter = new AdapterRecursos(getActivity(),buildPD());
            recyclerView.setAdapter(adapter);
        }
        else if(new String(categoria).equals("web")){
            adapter = new AdapterRecursos(getActivity(),buildWeb());
            recyclerView.setAdapter(adapter);
        }
        else if(new String(categoria).equals("pdf")){
            adapter = new AdapterRecursos(getActivity(),buildPdf());
            recyclerView.setAdapter(adapter);
        }
        else if(new String(categoria).equals("ci")){
            adapter = new AdapterRecursos(getActivity(),buildCi());
            recyclerView.setAdapter(adapter);
        }
        else if(new String(categoria).equals("ci")){
            adapter = new AdapterRecursos(getActivity(),buildCi());
            recyclerView.setAdapter(adapter);
        }
        else if(new String(categoria).equals("todos")){
            builTodos();
            adapter = new AdapterRecursos(getActivity(),arrayTodos);
            recyclerView.setAdapter(adapter);
        }
        else {
            // cargarLista();
        }

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
                            if(lista.size()==0) //si la lista esta vacia, le digo q no hay internet Jaja
                            {
                                error_no_internet();
                            }
                            else
                            {
                                sin_internet.setVisibility(View.GONE);
                                adapter= new AdapterRecursos(getActivity(),lista);
                                recyclerView.setAdapter(adapter);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                error_no_internet();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void builTodos()
    {
        arrayTodos.addAll(buildAudio());
        arrayTodos.addAll(buildWeb());
        arrayTodos.addAll(buildPdf());
        arrayTodos.addAll(buildPD());
        arrayTodos.addAll(buildCi());
    }
    private void error_no_internet() //metodo por si hay algun error al traer los datos desde internet
    {
        sin_internet.setVisibility(View.VISIBLE);
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

*/


}
