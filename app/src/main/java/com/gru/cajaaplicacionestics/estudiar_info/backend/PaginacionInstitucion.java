package com.gru.cajaaplicacionestics.estudiar_info.backend;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.gru.cajaaplicacionestics.auxiliares.Constantes;
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.backend.VolleySingleton;
import com.gru.cajaaplicacionestics.estudiar_info.adapter.EstudiarAdapter;
import com.gru.cajaaplicacionestics.estudiar_info.adapter.InstitucionesAdapter;
import com.gru.cajaaplicacionestics.estudiar_info.model.CarrerasModel;
import com.gru.cajaaplicacionestics.estudiar_info.model.InstitucionModel;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaginacionInstitucion
{
    private Activity activity;
    private RecyclerView recyclerView;
    private PullToLoadView pullToLoadView;
    private InstitucionesAdapter adapter;
    private boolean isLoading=false;
    private boolean hasLoadedAll=false;

    private PaginationErrorCallBack mCallback;

    private String URL_BASE="",URL_GET_ALL="";
    private int PAGINA_ACTUAL;

    private String año;
    private int TOTAL_PAGES=1;

    public PaginacionInstitucion(Activity activity, PullToLoadView pullToLoadView)
    {
        this.activity = activity;
        this.pullToLoadView = pullToLoadView;
        recyclerView = pullToLoadView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        adapter= new InstitucionesAdapter(activity,false);
        recyclerView.setAdapter(adapter);


        mCallback=(PaginationErrorCallBack)activity;
    }

    public void iniciarPaginacion()
    {
        Log.e("estoy en","paginacion");
        pullToLoadView.isLoadMoreEnabled(true);

        URL_BASE    = Constantes.URL_BASE_OVO;
        URL_GET_ALL = Constantes.URL_ALL_INSTITUCIONES;
        //this.año=año;


        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                PAGINA_ACTUAL++;
                cargarInstituciones();
            }

            //evento ni bien ingresa y cuando haces swipe para arriba
            @Override
            public void onRefresh() {
                Log.e("estoy en","onLoadMore");
                adapter.clear(); //limpio el adapter
                hasLoadedAll=false;
                PAGINA_ACTUAL=1; //reinicio el contador
                cargarInstituciones();

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

    public void iniciarPaginacionSearch(final String query)
    {
        Log.e("estoy en","paginacion query");
        pullToLoadView.isLoadMoreEnabled(true);

        URL_BASE    = Constantes.URL_BASE_OVO;
        URL_GET_ALL = Constantes.URL_ALL_INSTITUCIONES_SEARCH;
        //this.año=año;


        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                PAGINA_ACTUAL++;
                cargarInstitucionesSearch(query);
            }

            //evento ni bien ingresa y cuando haces swipe para arriba
            @Override
            public void onRefresh() {
                Log.e("estoy en","onLoadMore");
                adapter.clear(); //limpio el adapter
                hasLoadedAll=false;
                PAGINA_ACTUAL=1; //reinicio el contador
                cargarInstitucionesSearch(query);

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


    public void cargarInstituciones()
    {
        //String URL = FavoritosBackend.getUrlNovedades(PAGINA_ACTUAL,año);
        String URL = URL_BASE + URL_GET_ALL + "?page=" + PAGINA_ACTUAL;
        Log.e("url", URL);
        Request request;
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(request = new StringRequest(Request.Method.GET,URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response); //convierte toda la respuesta en json
                                    JSONArray array= jsonObject.getJSONArray("data"); //lo que est en corchetes lo convierto en array

                                    for(int i=0; i< array.length();i++)
                                    {
                                        JSONObject o = array.getJSONObject(i);
                                        InstitucionModel post = new InstitucionModel(
                                                o.getInt("id"),
                                                o.getString("nombre"),
                                                o.getString("url_logo")
                                        );
                                        /*if(o.has("fav"))
                                        {
                                            if(o.getString("fav").equals("null")){
                                                post.setFav(false);
                                            }else {
                                                post.setFav(true);
                                            }

                                        }*/

                                        adapter.add(post);
                                    }
                                    pullToLoadView.setComplete();
                                    isLoading=false;
                                } catch (JSONException e) {
                                    Log.e("error",e.getMessage());
                                    // manejoError(true);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                        FirebaseCrash.report(new Exception("VooleyError " + error.toString() ));
                        // manejoError(true);
                    }
                }));
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                FirebaseCrash.report(new Exception("Timeout" + URL_BASE + URL_GET_ALL + "?page=" + PAGINA_ACTUAL + "&ano=" + año  ));
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

    public void cargarInstitucionesSearch(String query)
    {
        //String URL = FavoritosBackend.getUrlNovedades(PAGINA_ACTUAL,año);
        String URL = URL_BASE + URL_GET_ALL + "?localidad="+ query + "&page=" + PAGINA_ACTUAL;
        Log.e("url", URL);
        Request request;
        VolleySingleton.getInstancia(activity).
                addToRequestQueue(request = new StringRequest(Request.Method.GET, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response); //convierte toda la respuesta en json
                                    JSONArray array= jsonObject.getJSONArray("data"); //lo que est en corchetes lo convierto en array

                                    for(int i=0; i< array.length();i++)
                                    {
                                        JSONObject o = array.getJSONObject(i);
                                        InstitucionModel post = new InstitucionModel(
                                                o.getInt("id"),
                                                o.getString("nombre"),
                                                o.getString("url_logo")
                                        );
                                        /*if(o.has("fav"))
                                        {
                                            if(o.getString("fav").equals("null")){
                                                post.setFav(false);
                                            }else {
                                                post.setFav(true);
                                            }

                                        }*/

                                        adapter.add(post);
                                    }
                                    pullToLoadView.setComplete();
                                    isLoading=false;
                                } catch (JSONException e) {
                                    Log.e("error",e.getMessage());
                                    // manejoError(true);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                        FirebaseCrash.report(new Exception("VooleyError " + error.toString() ));
                        // manejoError(true);
                    }
                }));
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                FirebaseCrash.report(new Exception("Timeout" + URL_BASE + URL_GET_ALL + "?page=" + PAGINA_ACTUAL + "&ano=" + año  ));
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
