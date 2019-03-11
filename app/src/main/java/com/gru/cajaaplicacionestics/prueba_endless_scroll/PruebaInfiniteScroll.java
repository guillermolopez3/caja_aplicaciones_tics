package com.gru.cajaaplicacionestics.prueba_endless_scroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.backend.FavoritosBackend;
import com.gru.cajaaplicacionestics.backend.VolleySingleton;
import com.gru.cajaaplicacionestics.model.ModelPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PruebaInfiniteScroll extends AppCompatActivity
{
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private ImageView imgNodata;

    private  int pagina_actual = 1; //representa la pag que tiene q cargar
    private int ultima_pagina = 0;

    private ArrayList<Item> lista;
    private AdapterPruebaInfinite adapterPruebaInfinite;
    private DataManager dataManager;

    private boolean hasError = false;
    private boolean cargandoDatos = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_infinite_scroll);

        dataManager = new DataManager(this);

        progressBar     = findViewById(R.id.progress);
        recyclerView    = findViewById(R.id.recycler);
        relativeLayout  = findViewById(R.id.relative);
        imgNodata       = findViewById(R.id.imgNoData);

        imgNodata.setVisibility(View.GONE);

        lista = new ArrayList<>();
        adapterPruebaInfinite = new AdapterPruebaInfinite(this,lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterPruebaInfinite);

        progressBar.setVisibility(View.VISIBLE);
        fetchData();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if( (pagina_actual <= ultima_pagina) && !(hasFooter()) && !(cargandoDatos) && !(hasError) ){
                    if(linearLayoutManager.findLastCompletelyVisibleItemPosition() >= linearLayoutManager.getItemCount() -2)
                    {
                        Log.e("entro","scroll");
                        addFooter();
                        fetchData();
                    }
                }
            }
        });

    }

    private void fetchData(){
        cargandoDatos = true;
        dataManager.fetchData(pagina_actual,new IDataValue() {
            @Override
            public void getArrayValue(ArrayList<Item> lis) {
                removeFooter();
                cargandoDatos = false;
                lista.addAll(lis);
                noData();
                pagina_actual ++;
                ultima_pagina = dataManager.getUltima_pagina();
                adapterPruebaInfinite.notifyDataSetChanged();
                Log.e("paginas", "actual:" + pagina_actual + "- ultima:" + ultima_pagina);
            }

            @Override
            public void showError(String error) {
                hasError = true;
                cargandoDatos = false;
                removeFooter();
                noData();
                Snackbar.make(relativeLayout,"No hay conexión",Snackbar.LENGTH_INDEFINITE).setAction("Reintentar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hasError = false;
                        Toast.makeText(PruebaInfiniteScroll.this, "Volviendo a conectar", Toast.LENGTH_SHORT).show();
                        addFooter();
                        recyclerView.getLayoutManager().scrollToPosition(lista.size()-1);
                        fetchData();
                    }
                }).show();
            }
        });
    }

    //Agrego a la lista una última fila con el footer
    private void addFooter(){
        lista.add(new Footer());
        adapterPruebaInfinite.notifyDataSetChanged();
    }

    //Elimino el footer cuando traigo datos
    private void removeFooter()
    {
        if(!lista.isEmpty() && lista.get(lista.size()-1) instanceof Footer){
            lista.remove(lista.size()-1);
            adapterPruebaInfinite.notifyDataSetChanged();
        }
    }

    //si la lista esta vacia, muestro el no data
    private void noData()
    {
        progressBar.setVisibility(View.GONE);
        if(lista.isEmpty()){
            imgNodata.setVisibility(View.VISIBLE);
        }else {
            imgNodata.setVisibility(View.GONE);
        }
    }

    //veo si la última fila tiene un elemento footer
    public boolean hasFooter(){
        return lista.get(lista.size() -1) instanceof Footer;
    }


}
