package com.gru.cajaaplicacionestics.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.gru.cajaaplicacionestics.adapter.AdapterPruebaTituloEntreRecycler;
import com.gru.cajaaplicacionestics.backend.VolleySingleton;
import com.gru.cajaaplicacionestics.model.ItemSTModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FragmentST extends Fragment
{
    private String dia;
    private ArrayList<ItemSTModel> lista;
    RecyclerView recyclerView;
    //AdapterListST adapter;
    AdapterPruebaTituloEntreRecycler adapter;
    private ProgressBar progressBar;

    public FragmentST(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler,container,false);

        if(getArguments() != null)
        {
            dia = getArguments().getString("dia");
        }

        lista = new ArrayList<>();
        cargarLista();
        progressBar = view.findViewById(R.id.progress);
        recyclerView = view.findViewById(R.id.recycler);
        //adapter = new AdapterListST(lista,getActivity());
        adapter = new AdapterPruebaTituloEntreRecycler(lista,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        return view;

    }



    public void cargarLista()
    {
        StringRequest request;
        VolleySingleton.getInstancia(getActivity()).
                addToRequestQueue(request= new StringRequest(Request.Method.GET,
                        "http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/agenda2_st1.json" ,
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
                                        ItemSTModel post = new ItemSTModel(
                                                o.getString("dia"),
                                                o.getString("hora"),
                                                o.getString("mananaTarde"),
                                                o.getString("titulo"),
                                                o.getString("disertante"),
                                                o.getString("ciudad"),
                                                o.getString("salon")
                                        );
                                        if(post.getDia().equals(dia))
                                        {
                                            lista.add(post);
                                        }

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
