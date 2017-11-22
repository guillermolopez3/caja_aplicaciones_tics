package com.gru.cajaaplicacionestics.backend;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterNE;
import com.gru.cajaaplicacionestics.model.ModelNE;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by guill on 20/11/2017.
 */

public class PaginacionNE
{
    Activity activity;
    RecyclerView recyclerView;
    private PullToLoadView pullToLoadView;
    private AdapterNE adapter;
    private boolean isLoading=false;
    private boolean hasLoadedAll=false;


    String URL_BASE="",URL_JSON="",JSON_BUSCAR="", palabra_buscar="",JSON_FILTRAR="";


    int LIMIT=0,CANTIDAD_POST=15;

    public PaginacionNE(Activity activity, PullToLoadView pullToLoadView) {
        this.activity = activity;
        this.pullToLoadView = pullToLoadView;

        recyclerView = pullToLoadView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        adapter= new AdapterNE(activity,new ArrayList<ModelNE>());
        recyclerView.setAdapter(adapter);

    }

    public void iniciarPaginacion()
    {
        pullToLoadView.isLoadMoreEnabled(true);

        URL_BASE=activity.getResources().getString(R.string.URL_BASE);
        URL_JSON=activity.getResources().getString(R.string.JSON_NE_DA);
        CANTIDAD_POST= Integer.parseInt(activity.getResources().getString(R.string.CANTIDAD_POST));

        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                Log.e("on load","entra");
                LIMIT=LIMIT+CANTIDAD_POST;
                cargarLista();

            }

            //evento ni bien ingresa y cuando haces swipe para arriba
            @Override
            public void onRefresh() {
                Log.e("on refresh","entra");
                adapter.clear(); //limpio el adapter
                hasLoadedAll=false;
                LIMIT=0; //reinicio el contador
                cargarLista();

            }

            @Override
            public boolean isLoading() {
                Log.e("on loading","entra");
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                Log.e("on hasLoaded","entra");
                return hasLoadedAll;
            }
        });
        pullToLoadView.initLoad();
    }

    private void cargarLista()
    {
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(new StringRequest(Request.Method.GET,
                        URL_BASE + URL_JSON + "limite="+ LIMIT,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray array= jsonObject.getJSONArray("post");

                                    for(int i=0; i< array.length();i++)
                                    {
                                        JSONObject o = array.getJSONObject(i);
                                        ModelNE post = new ModelNE(
                                                o.getInt("id"),
                                                o.getString("url_imagen"),
                                                o.getString("descripcion"),
                                                o.getString("url_pdf"),
                                                o.getString("seccion"),
                                                o.getString("nivel"),
                                                o.getString("otra_descripcion")
                                        );
                                        adapter.add(post);
                                    }
                                    pullToLoadView.setComplete();
                                    isLoading=false;
                                } catch (JSONException e) {
                                    Log.e("error",e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));
    }

}
