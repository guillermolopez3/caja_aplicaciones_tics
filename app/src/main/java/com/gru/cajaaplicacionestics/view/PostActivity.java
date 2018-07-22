package com.gru.cajaaplicacionestics.view;


import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.backend.PaginacionPost;
import com.srx.widget.PullToLoadView;


public class PostActivity extends AppCompatActivity implements PaginationErrorCallBack
{
    private String      seccion="";

    private boolean     isNuestraEscuela=false;

    SearchView          searchView=null;
    PullToLoadView      pullToLoadView; //se encarga de ir llenando el recycler
    LinearLayout        errorLayout;
    CoordinatorLayout   coordinatorLayout;
    Button              btnReintentar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        seccion             = getIntent().getExtras().getString("seleccion");
        String titulo       = getIntent().getExtras().getString("titulo");

        isNuestraEscuela    = getIntent().getBooleanExtra("titulo",false);
        Log.e("ne", "valor" + isNuestraEscuela);

        pullToLoadView      = findViewById(R.id.recyclerPd);
        coordinatorLayout   = findViewById(R.id.postCoordinator);

        errorLayout         = findViewById(R.id.error_layout);

        MetodosComunes.showToolbar(titulo,true,this);

        if(seccion.equals("search")){
            iniciarPaginacionSearch();
        }
        else {
            iniciarPaginacion();
        }

        MetodosComunes.abrirActivityFab(this);

    }

    private void iniciarPaginacion()
    {
        if(pullToLoadView.getVisibility()==View.GONE)
        {
            pullToLoadView.setVisibility(View.VISIBLE);
        }
        if(seccion.equals("ac")) //ac= aprender conectados
        {
            new PaginacionPost(this,pullToLoadView,idSeccionSeleccionada()).iniciarPaginacionAprenderConectados();
        }
        else {
            new PaginacionPost(this,pullToLoadView,idSeccionSeleccionada()).iniciarPaginacion();
        }


    }

    private void iniciarPaginacionSearch()
    {
        showRecyclerOrInformation(true);
        new PaginacionPost(PostActivity.this,pullToLoadView,idSeccionSeleccionada());

    }


    private int idSeccionSeleccionada()
    {
        int id=1;
        if(seccion.equals("pd"))
        {
           id=1;
        }
        else if(seccion.equals("ci")){
            id=2;
        }
        else if(seccion.equals("search")){
            id=0;
        }
        else if(seccion.equals("canal")){
            id=6; //6 es el id en la BD del canañ de youtube
        }
        return id;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_post, menu);

        final MenuItem menuItem=menu.findItem(R.id.action_search);
        searchView = (SearchView)menuItem.getActionView();

        if(seccion.equals("search")) {
            menuItem.expandActionView(); //inicia expandido el search
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showRecyclerOrInformation(false);
                new PaginacionPost(PostActivity.this,pullToLoadView,idSeccionSeleccionada()).iniciarPaginacionSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if(seccion.equals("search")) //vuelvo atras solo si estoy buscando
                {
                    finish();
                }
                return true;
            }
        });

        return true;
    }

    private void showRecyclerOrInformation(boolean estoyBuscando) //dependiendo si aprete buscar o no muestro una cosa u la otra
    {
        if(estoyBuscando)
        {
            errorLayout.setVisibility(View.VISIBLE);
            pullToLoadView.setVisibility(View.GONE);
            MetodosComunes.manejarActivityError(this,"buscar");
        }
        else{
            errorLayout.setVisibility(View.GONE);
            pullToLoadView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void reintentar(int arrayCount,Boolean hayError)
    {
        if(arrayCount==0) //no se cargo nada en la lista
        {
            if (errorLayout.getVisibility() == View.GONE) {
                errorLayout.setVisibility(View.VISIBLE);
                pullToLoadView.setVisibility(View.GONE);
            }

            if(seccion.equals("search")) //search es cuando vengo desde la pantalla del menu
            {
                if(hayError){
                    MetodosComunes.manejarActivityError(this,"buscarError"); //busco y dio un error
                }
                else
                {
                    MetodosComunes.manejarActivityError(this,"buscarSinRecursos"); //busco y no encontre nada
               }
            }
            else
            {
                MetodosComunes.manejarActivityError(this,"sinConexion");

                btnReintentar= findViewById(R.id.error_btn_retry);
                btnReintentar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        errorLayout.setVisibility(View.GONE);
                        iniciarPaginacion();
                    }
                });

            }
        }
        else
        {
           Snackbar.make(coordinatorLayout, "Problemas de conexión", Snackbar.LENGTH_INDEFINITE)
                    .show();
        }

    }
}
