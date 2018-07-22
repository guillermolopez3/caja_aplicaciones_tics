package com.gru.cajaaplicacionestics.backend;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterNPost;
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.model.ModelPost;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by guill on 24/02/2018.
 */

public class PaginacionPost
{
    Activity activity;
    RecyclerView recyclerView;
    private PullToLoadView pullToLoadView;
    private AdapterNPost adapter;
    private boolean isLoading=false;
    private boolean hasLoadedAll=false;
    private int seccion;
    //private String consulta="";

    private PaginationErrorCallBack mCallback;

    private String URL_BASE="",URL_GET_ALL="",URL_SEARCH="";
    private int PAGINA_ACTUAL;

    private int TOTAL_PAGES=1;


    public PaginacionPost(Activity activity, PullToLoadView pullToLoadView,int seccion)
    {
        this.activity = activity;
        this.pullToLoadView = pullToLoadView;
        this.seccion=seccion;
        recyclerView = pullToLoadView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        adapter= new AdapterNPost(activity);
        recyclerView.setAdapter(adapter);


        mCallback=(PaginationErrorCallBack)activity;
    }


    public void iniciarPaginacion()
    {
        Log.e("estoy en","paginacion");
        pullToLoadView.isLoadMoreEnabled(true);

        URL_BASE    =activity.getResources().getString(R.string.URL_BASE);
        URL_GET_ALL =activity.getResources().getString(R.string.URL_GET_ALL_POST);


        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                PAGINA_ACTUAL++;
                cargarLista();
            }

            //evento ni bien ingresa y cuando haces swipe para arriba
            @Override
            public void onRefresh() {
                Log.e("estoy en","onLoadMore");
                adapter.clear(); //limpio el adapter
                hasLoadedAll=false;
                PAGINA_ACTUAL=1; //reinicio el contador
                cargarLista();

            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return hasLoadedAll;
            }
        });
        pullToLoadView.initLoad();
    }


    public void iniciarPaginacionNe(final int seccion)
    {
        pullToLoadView.isLoadMoreEnabled(true);

        URL_BASE=activity.getResources().getString(R.string.URL_BASE);
        URL_GET_ALL=activity.getResources().getString(R.string.URL_GET_ALL_NE);
        //CANTIDAD_POST= Integer.parseInt(activity.getResources().getString(R.string.CANTIDAD_POST));

        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                Log.e("on load","entra");
                //LIMIT=LIMIT+CANTIDAD_POST;
                PAGINA_ACTUAL++;
                cargarListaNe(seccion);

            }

            //evento ni bien ingresa y cuando haces swipe para arriba
            @Override
            public void onRefresh() {
                Log.e("on refresh","entra");
                adapter.clear(); //limpio el adapter
                hasLoadedAll=false;
                PAGINA_ACTUAL=1; //reinicio el contador
                cargarListaNe(seccion);

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

    //paginacion para aprender conectados
    public void iniciarPaginacionAprenderConectados()
    {
        pullToLoadView.isLoadMoreEnabled(true);

        URL_BASE    =activity.getResources().getString(R.string.URL_BASE);
        URL_GET_ALL =activity.getResources().getString(R.string.URL_GET_ALL_POST_APRENDER_CONECTADPS);


        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                PAGINA_ACTUAL++;
                cargarListaAprenderConectados();
            }

            //evento ni bien ingresa y cuando haces swipe para arriba
            @Override
            public void onRefresh() {
                Log.e("estoy en","onLoadMore");
                adapter.clear(); //limpio el adapter
                hasLoadedAll=false;
                PAGINA_ACTUAL=1; //reinicio el contador
                cargarListaAprenderConectados();

            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return hasLoadedAll;
            }
        });
        pullToLoadView.initLoad();
    }

    public void iniciarPaginacionSearch(final String consulta)
    {
        Log.e("estoy en","paginacion search");
        pullToLoadView.isLoadMoreEnabled(true);
        //adapter.clear(); //limpio el adapter

        URL_BASE    =activity.getResources().getString(R.string.URL_BASE);

        URL_SEARCH  =activity.getResources().getString(R.string.URL_POST_SEARCH);
        //Log.e("url search",URL_BASE+ URL_SEARCH);

        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                PAGINA_ACTUAL++;
                cargarListaSearch(consulta);

            }

            //evento ni bien ingresa y cuando haces swipe para arriba
            @Override
            public void onRefresh() {
                Log.e("estoy en","onLoadMore ");
                adapter.clear(); //limpio el adapter
                hasLoadedAll=false;
                PAGINA_ACTUAL=1; //reinicio el contador
                cargarListaSearch(consulta);

            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return hasLoadedAll;
            }
        });
        pullToLoadView.initLoad();
    }

    public void cargarLista()
    {
        StringRequest request;
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(request= new StringRequest(Request.Method.GET,
                        URL_BASE + URL_GET_ALL + "?page=" + PAGINA_ACTUAL + "&seccion=" +seccion ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response); //convierte toda la respuesta en json
                                    JSONArray array= jsonObject.getJSONArray("data"); //lo que est en corchetes lo convierto en array
                                    Log.e("rta",URL_BASE + URL_GET_ALL + "?page=" + PAGINA_ACTUAL + "&seccion=" +seccion);
                                    TOTAL_PAGES= Integer.parseInt(jsonObject.getString("last_page")); //obtengo del JsonObject el valor de la última pagina

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
                                        adapter.add(post);
                                    }
                                    if(adapter.getArrayCount()==0)manejoError(false); //si no hay datos en el array, muestro el error
                                    pullToLoadView.setComplete();
                                    isLoading=false;
                                } catch (JSONException e) {
                                    Log.e("error",e.getMessage());
                                    manejoError(true);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                        FirebaseCrash.report(new Exception("VolleyError" + error.toString()));
                        manejoError(true);
                    }
                }));
            request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                FirebaseCrash.report(new Exception("Timeout" + URL_BASE + URL_GET_ALL + "?page=" + PAGINA_ACTUAL + "&seccion=" +seccion  ));
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

    public void cargarListaAprenderConectados()
    {
        StringRequest request;
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(request= new StringRequest(Request.Method.GET,
                        URL_BASE + URL_GET_ALL + "?page=" + PAGINA_ACTUAL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response); //convierte toda la respuesta en json
                                    JSONArray array= jsonObject.getJSONArray("data"); //lo que est en corchetes lo convierto en array
                                    Log.e("rta",URL_BASE + URL_GET_ALL + "?page=" + PAGINA_ACTUAL + "&seccion=" +seccion);
                                    TOTAL_PAGES= Integer.parseInt(jsonObject.getString("last_page")); //obtengo del JsonObject el valor de la última pagina

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
                                        adapter.add(post);
                                    }
                                    if(adapter.getArrayCount()==0)manejoError(false); //si no hay datos en el array, muestro el error
                                    pullToLoadView.setComplete();
                                    isLoading=false;
                                } catch (JSONException e) {
                                    Log.e("error",e.getMessage());
                                    manejoError(true);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                        FirebaseCrash.report(new Exception("VolleyError" + error.toString()));
                        manejoError(true);
                    }
                }));
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                FirebaseCrash.report(new Exception("Timeout" + URL_BASE + URL_GET_ALL + "?page=" + PAGINA_ACTUAL + "&seccion=" +seccion  ));
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

    public void cargarListaSearch(final String consulta)
    {
        StringRequest request;
        Log.e("url search",URL_BASE + URL_SEARCH + "?page=" + PAGINA_ACTUAL + "&consulta=" +consulta);
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(request= new StringRequest(Request.Method.GET,
                        URL_BASE + URL_SEARCH + "?page=" + PAGINA_ACTUAL + "&consulta=" +consulta ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response); //convierte toda la respuesta en json
                                    JSONArray array= jsonObject.getJSONArray("data"); //lo que est en corchetes lo convierto en array
                                    TOTAL_PAGES= Integer.parseInt(jsonObject.getString("last_page")); //obtengo del JsonObject el valor de la última pagina

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
                                        adapter.add(post);
                                    }
                                    if(adapter.getArrayCount()==0)manejoError(false); //si no hay datos en el array, muestro el error
                                    pullToLoadView.setComplete();
                                    isLoading=false;
                                } catch (JSONException e) {
                                    Log.e("error",e.getMessage());
                                    manejoError(true);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                        FirebaseCrash.report(new Exception("VolleyError" + error.toString()));
                        manejoError(true);
                    }
                }));
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                FirebaseCrash.report(new Exception("Timeout" + URL_BASE + URL_SEARCH + "?page=" + PAGINA_ACTUAL + "&consulta=" +consulta));
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

    private void cargarListaNe(int seccionNe)
    {
        StringRequest request;
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(request = new StringRequest(Request.Method.GET,
                        URL_BASE + URL_GET_ALL + "?page=" + PAGINA_ACTUAL + "&seccionNe=" + seccionNe,
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
                                        adapter.add(post);
                                    }
                                    pullToLoadView.setComplete();
                                    isLoading=false;
                                } catch (JSONException e) {
                                    Log.e("error",e.getMessage());
                                    FirebaseCrash.report(new Exception("VolleyError " + e.toString() ));
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
                FirebaseCrash.report(new Exception("Timeout " + URL_BASE + URL_GET_ALL ));
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

    private void manejoError(Boolean hayError)//si no hay datos para mostrar, muestro eso en la pantalla
    {
        pullToLoadView.setComplete();
        if(PAGINA_ACTUAL<=TOTAL_PAGES)
        {
            mCallback.reintentar(adapter.getArrayCount(),hayError);
        }

    }

}
