package com.gru.cajaaplicacionestics.backend;

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
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.model.ModelPost;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guill on 02/03/2018.
 */

public class PaginacionNovedades
{
    private Activity activity;
    private RecyclerView recyclerView;
    private PullToLoadView pullToLoadView;
    private AdapterNPost adapter;
    private boolean isLoading=false;
    private boolean hasLoadedAll=false;

    private PaginationErrorCallBack mCallback;

    private String URL_BASE="",URL_GET_ALL="";
    private int PAGINA_ACTUAL;

    private String año;
    private int TOTAL_PAGES=1;

    public PaginacionNovedades(Activity activity, PullToLoadView pullToLoadView)
    {
        this.activity = activity;
        this.pullToLoadView = pullToLoadView;
        recyclerView = pullToLoadView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        adapter= new AdapterNPost(activity,true);
        recyclerView.setAdapter(adapter);


        mCallback=(PaginationErrorCallBack)activity;
    }

    public void iniciarPaginacion(String año)
    {
        Log.e("estoy en","paginacion");
        pullToLoadView.isLoadMoreEnabled(true);

        URL_BASE    = activity.getResources().getString(R.string.URL_BASE);
        URL_GET_ALL = activity.getResources().getString(R.string.URL_GET_ALL_NOVEDADES);
        this.año=año;


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


    public void cargarLista()
    {
        String URL = FavoritosBackend.getUrlNovedades(PAGINA_ACTUAL,año);
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
