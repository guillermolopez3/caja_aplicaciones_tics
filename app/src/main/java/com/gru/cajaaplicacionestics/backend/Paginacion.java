package com.gru.cajaaplicacionestics.backend;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterNPost;
import com.gru.cajaaplicacionestics.adapter.AdapterPost;
import com.gru.cajaaplicacionestics.model.ModelPost;
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
    private AdapterNPost m_adapter;
    private boolean isLoading=false;
    private boolean hasLoadedAll=false;

    private int seccion;

    String URL_BASE="",URL_JSON="",JSON_BUSCAR="", palabra_buscar="",JSON_FILTRAR="",URL_GET_ALL="";


    int LIMIT=0,CANTIDAD_POST=15;
    int PAGINA_ACTUAL=1;


    /*public Paginacion(Activity activity, PullToLoadView pullToLoadView,int seccion) {
        this.activity = activity;
        this.pullToLoadView = pullToLoadView;
        this.seccion=seccion;
        recyclerView = pullToLoadView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        adapter= new AdapterPost(activity,new ArrayList<NewPost>());
        recyclerView.setAdapter(adapter);

    }*/


    public Paginacion(Activity activity, PullToLoadView pullToLoadView) {
        this.activity = activity;
        this.pullToLoadView = pullToLoadView;
        recyclerView = pullToLoadView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        m_adapter= new AdapterNPost(activity,true);
        recyclerView.setAdapter(m_adapter);

    }

   /* public void iniciarPaginacion()
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
    }*/

    /*public void iniciarPaginacionBusqueda(String palabra)
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
    }*/

   /* public void iniciarPaginacionFiltrada(final String web, final String video, final String audio, final String pdf)
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
    }*/

    public void iniciarPaginacionRecursosxNivel(final int nivel)
    {
        pullToLoadView.isLoadMoreEnabled(true);

        URL_BASE=activity.getResources().getString(R.string.URL_BASE);
        URL_GET_ALL=activity.getResources().getString(R.string.URL_GET_ALL_POST_LEVEL);
        CANTIDAD_POST= Integer.parseInt(activity.getResources().getString(R.string.CANTIDAD_POST));

        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                Log.e("on load","entra");
                LIMIT=LIMIT+CANTIDAD_POST;
                PAGINA_ACTUAL++;
                cargarListaRecursosXNivel(nivel);

            }

            //evento ni bien ingresa y cuando haces swipe para arriba
            @Override
            public void onRefresh() {
                Log.e("on refresh","entra");
                m_adapter.clear(); //limpio el adapter
                hasLoadedAll=false;
                LIMIT=0; //reinicio el contador
                PAGINA_ACTUAL=1;
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
        Log.e("rta","entro a cargar la lista");
        StringRequest request;
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(request = new StringRequest(Request.Method.GET,
                        URL_BASE + URL_JSON + "limite="+ LIMIT + "&seccion=" + seccion,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray array= jsonObject.getJSONArray("post");
                                    Log.e("rta",response);
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
                                     Log.e("error",e.toString());
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                        FirebaseCrash.report(new Exception("Error en la peticion" + error.toString() ));
                    }
                }));
                //estblezco nuevos tiempos x si el servidor tarda mucho en contestar
                request.setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        FirebaseCrash.report(new Exception("Timeout" + URL_BASE + URL_JSON + "limite="+ LIMIT + "&seccion=" + seccion ));
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

    private void cargarListaXBusqueda()
    {
        StringRequest request;
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(request= new StringRequest(Request.Method.GET,
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
                                    Log.e("error",e.toString());
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        FirebaseCrash.report(new Exception("Error en la peticion" + error.getMessage()));
                    }
                }));
                request.setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        FirebaseCrash.report(new Exception("Timeout" + URL_BASE + JSON_BUSCAR + "limite="+ LIMIT + "&seccion="+seccion+" &busqueda=" + palabra_buscar ));
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

    private void cargarListaFiltrada(String web,String video,String audio,String pdf)
    {
        StringRequest request;
        Log.e("url",URL_BASE + JSON_FILTRAR + "limite="+ LIMIT + "&seccion="+seccion+
                "&web="+web+"&video="+video+"&audio="+audio+"&pdf="+pdf+"");
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(request = new StringRequest(Request.Method.GET,
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
                request.setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        return 50000;
                    }

                    @Override
                    public int getCurrentRetryCount() {
                        return 50000;
                    }

                    @Override
                    public void retry(VolleyError error) throws VolleyError {

                    }
                });
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
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


    private void cargarListaRecursosXNivel(int nivel)
    {
        String URL = FavoritosBackend.getUrlEspacioDidactico(PAGINA_ACTUAL,nivel);
        Log.e("url",URL );
        StringRequest request;
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(request = new StringRequest(Request.Method.GET,URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray array= jsonObject.getJSONArray("data");

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
                                        m_adapter.add(post);
                                    }
                                    pullToLoadView.setComplete();
                                    isLoading=false;
                                } catch (JSONException e) {
                                    //Log.e("error",e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        FirebaseCrash.report(new Exception("VolleyError" + error.toString() ));
                        //Log.e("error",error.getMessage());
                    }
                }));
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                FirebaseCrash.report(new Exception("Timeout" + URL_BASE + JSON_BUSCAR + "limite="+ LIMIT + "&seccion="+seccion+" &busqueda=" + palabra_buscar ));
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
