package com.gru.cajaaplicacionestics.view;

import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.AnalitycsAplication;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.backend.PaginacionNE;
import com.gru.cajaaplicacionestics.backend.PaginacionPost;
import com.srx.widget.PullToLoadView;

public class NEActivity extends AppCompatActivity implements PaginationErrorCallBack {
    private Tracker mTracker;
    private String      seccion="";
    private int     id_seccionNE=1;

    SearchView searchView=null;
    PullToLoadView      pullToLoadView; //se encarga de ir llenando el recycler
    LinearLayout errorLayout;
    CoordinatorLayout coordinatorLayout;
    PaginacionPost paginacionPost;

   Button btnReintentar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //seccion             = getIntent().getExtras().getString("seleccion");
        String titulo       = getIntent().getExtras().getString("titulo");
        id_seccionNE        = getIntent().getExtras().getInt("ne");

        pullToLoadView      =(PullToLoadView)findViewById(R.id.recyclerPd);
        coordinatorLayout   =(CoordinatorLayout)findViewById(R.id.postCoordinator);

        errorLayout         =(LinearLayout)findViewById(R.id.error_layout);
        btnReintentar=(Button) findViewById(R.id.error_btn_retry);

        MetodosComunes.showToolbar(titulo,true,this);

        iniciarPaginacion();


        MetodosComunes.abrirActivityFab(this);


    }

    private void iniciarPaginacion()
    {
        if(pullToLoadView.getVisibility()== View.GONE)
        {
            pullToLoadView.setVisibility(View.VISIBLE);
        }
        new PaginacionPost(this,pullToLoadView,2).iniciarPaginacionNe(id_seccionNE);

    }

    @Override
    protected void onResume() {
        super.onResume();
       /* mTracker.setScreenName("NE");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/
    }

    @Override
    public void reintentar(int arrayCount, @Nullable Boolean hayError) {
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

                btnReintentar=(Button)findViewById(R.id.error_btn_retry);
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
           /*Snackbar.make(coordinatorLayout, "Problemas de conexión", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Reintentar", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    errorLayout.setVisibility(View.GONE);
                                    iniciarPaginacion();
                                }
                            })
                            .show();*/
            Snackbar.make(coordinatorLayout, "Problemas de conexión", Snackbar.LENGTH_INDEFINITE)
                    .show();
        }

    }
}
