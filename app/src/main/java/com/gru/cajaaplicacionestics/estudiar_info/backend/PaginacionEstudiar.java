package com.gru.cajaaplicacionestics.estudiar_info.backend;

import android.app.Activity;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterNPost;
import com.gru.cajaaplicacionestics.auxiliares.Constantes;
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.backend.FavoritosBackend;
import com.gru.cajaaplicacionestics.backend.VolleySingleton;
import com.gru.cajaaplicacionestics.estudiar_info.adapter.EstudiarAdapter;
import com.gru.cajaaplicacionestics.estudiar_info.model.CarrerasModel;
import com.gru.cajaaplicacionestics.model.ModelPost;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaginacionEstudiar
{
    private Activity activity;
    private RecyclerView recyclerView;
    private PullToLoadView pullToLoadView;
    private EstudiarAdapter adapter;
    private boolean isLoading=false;
    private boolean hasLoadedAll=false;

    private PaginationErrorCallBack mCallback;

    private String URL_BASE="",URL_GET_ALL="";
    private int PAGINA_ACTUAL;

    private String año;
    private int TOTAL_PAGES=1;

    public PaginacionEstudiar(Activity activity, PullToLoadView pullToLoadView)
    {
        this.activity = activity;
        this.pullToLoadView = pullToLoadView;
        recyclerView = pullToLoadView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        adapter= new EstudiarAdapter(activity,false);
        recyclerView.setAdapter(adapter);


        mCallback=(PaginationErrorCallBack)activity;
    }

    public void iniciarPaginacion()
    {
        Log.e("estoy en","paginacion");
        pullToLoadView.isLoadMoreEnabled(true);

        URL_BASE    = Constantes.URL_BASE_OVO;
        URL_GET_ALL = Constantes.URL_ALL_CARRERAS;
        //this.año=año;


        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                PAGINA_ACTUAL++;
                cargarCarreras();
            }

            //evento ni bien ingresa y cuando haces swipe para arriba
            @Override
            public void onRefresh() {
                Log.e("estoy en","onLoadMore");
                adapter.clear(); //limpio el adapter
                hasLoadedAll=false;
                PAGINA_ACTUAL=1; //reinicio el contador
                cargarCarreras();

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


    public void cargarCarreras()
    {
        //String URL = FavoritosBackend.getUrlNovedades(PAGINA_ACTUAL,año);
        String URL = URL_BASE + URL_GET_ALL;
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
                                        CarrerasModel post = new CarrerasModel(
                                                o.getInt("id"),
                                                o.getString("nombre_carrera"),
                                                o.getString("nombre"),
                                                o.getString("direccion"),
                                                o.getInt("caracter_id"),
                                                o.getString("telefono"),
                                                o.getString("web"),
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

