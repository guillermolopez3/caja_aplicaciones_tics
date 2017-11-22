package com.gru.cajaaplicacionestics.backend;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterPost;
import com.gru.cajaaplicacionestics.model.NewPost;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by guill on 13/11/2017.
 */

public class Paginacion
{
    Activity activity;
    RecyclerView recyclerView;
    private PullToLoadView pullToLoadView;
    private AdapterPost adapter;
    private boolean isLoading=false;
    private boolean hasLoadedAll=false;

    private int seccion;

    String URL_BASE="",URL_JSON="",JSON_BUSCAR="", palabra_buscar="",JSON_FILTRAR="";


    int LIMIT=0,CANTIDAD_POST=15;

    public Paginacion(Activity activity, PullToLoadView pullToLoadView,int seccion) {
        this.activity = activity;
        this.pullToLoadView = pullToLoadView;
        this.seccion=seccion;
        recyclerView = pullToLoadView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        adapter= new AdapterPost(activity,new ArrayList<NewPost>());
        recyclerView.setAdapter(adapter);

    }

    public void iniciarPaginacion()
    {
        pullToLoadView.isLoadMoreEnabled(true);

        URL_BASE=activity.getResources().getString(R.string.URL_BASE);
        URL_JSON=activity.getResources().getString(R.string.JSON_SECCION);
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

    public void iniciarPaginacionBusqueda(String palabra)
    {
        LIMIT=0;
        adapter.clear(); //limpio el adapter
        palabra_buscar=palabra;
        pullToLoadView.isLoadMoreEnabled(true);

        URL_BASE=activity.getResources().getString(R.string.URL_BASE);
        JSON_BUSCAR=activity.getResources().getString(R.string.JSON_BUSQUDA_SECCION);
        CANTIDAD_POST= Integer.parseInt(activity.getResources().getString(R.string.CANTIDAD_POST));

        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                Log.e("on load","entra");
                LIMIT=LIMIT+CANTIDAD_POST;
                cargarListaXBusqueda();

            }

            //evento ni bien ingresa y cuando haces swipe para arriba
            @Override
            public void onRefresh() {
                Log.e("on refresh","entra");
                adapter.clear(); //limpio el adapter
                hasLoadedAll=false;
                LIMIT=0; //reinicio el contador
                cargarListaXBusqueda();

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

    public void iniciarPaginacionFiltrada(final String web, final String video, final String audio, final String pdf)
    {
        LIMIT=0;
        adapter.clear(); //limpio el adapter

        pullToLoadView.isLoadMoreEnabled(true);

        URL_BASE=activity.getResources().getString(R.string.URL_BASE);
        JSON_FILTRAR=activity.getResources().getString(R.string.JSON_FILTRO_POST);
        CANTIDAD_POST= Integer.parseInt(activity.getResources().getString(R.string.CANTIDAD_POST));

        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                Log.e("on load","entra");
                LIMIT=LIMIT+CANTIDAD_POST;
                cargarListaFiltrada(web,video,audio,pdf);

            }

            //evento ni bien ingresa y cuando haces swipe para arriba
            @Override
            public void onRefresh() {
                Log.e("on refresh","entra");
                adapter.clear(); //limpio el adapter
                hasLoadedAll=false;
                LIMIT=0; //reinicio el contador
                cargarListaFiltrada(web,video,audio,pdf);

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

    public void iniciarPaginacionRecursosxNivel(final String nivel)
    {
        pullToLoadView.isLoadMoreEnabled(true);

        URL_BASE=activity.getResources().getString(R.string.URL_BASE);
        URL_JSON=activity.getResources().getString(R.string.JSON_POST_X_NIVEL);
        CANTIDAD_POST= Integer.parseInt(activity.getResources().getString(R.string.CANTIDAD_POST));

        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                Log.e("on load","entra");
                LIMIT=LIMIT+CANTIDAD_POST;
                cargarListaRecursosXNivel(nivel);

            }

            //evento ni bien ingresa y cuando haces swipe para arriba
            @Override
            public void onRefresh() {
                Log.e("on refresh","entra");
                adapter.clear(); //limpio el adapter
                hasLoadedAll=false;
                LIMIT=0; //reinicio el contador
                cargarListaRecursosXNivel(nivel);

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
                        URL_BASE + URL_JSON + "limite="+ LIMIT + "&seccion=" + seccion,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray array= jsonObject.getJSONArray("post");

                                    for(int i=0; i< array.length();i++)
                                    {
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
                                                o.getString("activity")
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

    private void cargarListaXBusqueda()
    {
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(new StringRequest(Request.Method.GET,
                        URL_BASE + JSON_BUSCAR + "limite="+ LIMIT + "&seccion="+seccion+" &busqueda=" + palabra_buscar,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray array= jsonObject.getJSONArray("post");

                                    for(int i=0; i< array.length();i++)
                                    {
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
                                                o.getString("activity")
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

    private void cargarListaFiltrada(String web,String video,String audio,String pdf)
    {
        Log.e("url",URL_BASE + JSON_FILTRAR + "limite="+ LIMIT + "&seccion="+seccion+
                "&web="+web+"&video="+video+"&audio="+audio+"&pdf="+pdf+"");
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(new StringRequest(Request.Method.GET,
                        URL_BASE + JSON_FILTRAR + "limite="+ LIMIT + "&seccion="+seccion+
                                "&web="+web+"&video="+video+"&audio="+audio+"&pdf="+pdf+"",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray array= jsonObject.getJSONArray("post");

                                    for(int i=0; i< array.length();i++)
                                    {
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
                                                o.getString("activity")
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


    private void cargarListaRecursosXNivel(String nivel)
    {
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(new StringRequest(Request.Method.GET,
                        URL_BASE + URL_JSON + "limite="+ LIMIT + "&nivel=" + nivel,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray array= jsonObject.getJSONArray("post");

                                    for(int i=0; i< array.length();i++)
                                    {
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
                                                o.getString("activity")
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
