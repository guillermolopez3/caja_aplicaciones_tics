package com.gru.cajaaplicacionestics.view.semana_tic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterNPost;
import com.gru.cajaaplicacionestics.backend.VolleySingleton;
import com.gru.cajaaplicacionestics.model.ItemSTModel;
import com.gru.cajaaplicacionestics.model.ModelPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class FragmentInfoST extends Fragment
{
    RecyclerView recyclerView;
    AdapterNPost adapter;
    private ProgressBar progressBar;

    public FragmentInfoST(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_st, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.progress);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapter = new AdapterNPost(getActivity());
        cargarLista();
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void cargarLista()
    {
        StringRequest request;
        VolleySingleton.getInstancia(getActivity()).
                addToRequestQueue(request= new StringRequest(Request.Method.GET,
                        "http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/semana_tic.json" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                                    JSONObject jsonObject = new JSONObject(response); //convierte toda la respuesta en json
                                    JSONArray array= jsonObject.getJSONArray("agenda"); //lo que est en corchetes lo convierto en array

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
                                        adapter.add(post);
                                    }
                                    adapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);

                                } catch (JSONException e) {
                                    Log.e("error",e.getMessage());
                                    //manejoError(true);
                                }
                                catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                        FirebaseCrash.report(new Exception("VolleyError" + error.toString()));
                        // manejoError(true);
                    }
                }));
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                //FirebaseCrash.report(new Exception("Timeout" + URL_BASE + URL_GET_ALL + "?page=" + PAGINA_ACTUAL + "&seccion=" +seccion  ));
                return 8000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 8000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

}
