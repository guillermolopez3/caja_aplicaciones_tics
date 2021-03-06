package com.gru.cajaaplicacionestics.view.ne;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.backend.PaginacionPost;
import com.srx.widget.PullToLoadView;

public class NEActivity extends AppCompatActivity implements PaginationErrorCallBack {
    private String      seccion="";
    private int         id_seccionNE=1;

    PullToLoadView      pullToLoadView; //se encarga de ir llenando el recycler
    LinearLayout errorLayout;
    CoordinatorLayout coordinatorLayout;

   Button btnReintentar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        String titulo       = getIntent().getExtras().getString("titulo");
        id_seccionNE        = getIntent().getExtras().getInt("ne");

        pullToLoadView      = findViewById(R.id.recyclerPd);
        coordinatorLayout   = findViewById(R.id.postCoordinator);

        errorLayout         = findViewById(R.id.error_layout);
        btnReintentar       = findViewById(R.id.error_btn_retry);
        FloatingActionsMenu fab = findViewById(R.id.menu_fab);
        fab.setVisibility(View.GONE);

        MetodosComunes.showToolbar(titulo,false,this);

        iniciarPaginacion();


        MetodosComunes.abrirActivityFab(this);


    }

    private void iniciarPaginacion()
    {
        if(pullToLoadView.getVisibility()== View.GONE)
        {
            pullToLoadView.setVisibility(View.VISIBLE);
        }
        new PaginacionPost(this,pullToLoadView,2,false).iniciarPaginacionNe(id_seccionNE);

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

                btnReintentar = findViewById(R.id.error_btn_retry);
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
