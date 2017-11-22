package com.gru.cajaaplicacionestics.view;


import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.gru.cajaaplicacionestics.R;

import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.backend.Paginacion;

import com.srx.widget.PullToLoadView;



public class PDActivity extends AppCompatActivity
{
    SearchView searchView=null;
    PullToLoadView pullToLoadView; //se encarga de ir llenando el recycler
    //static final int PRIMARIA_DIGITAL=1; //el id de la BD de PD es 1
    int SECCION_SELECCIONADA=1; //aca guardo la seccion (PD,CI,NE,Rec) segun el id de la tabla

    String titulo,seccion; //segun la seccion seleccionada va el titulo

    String str_web="";
    String str_video="";
    String str_audio="";
    String str_pdf="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pd);

        seccion = getIntent().getExtras().getString("seleccion");
        seccionSeleccionada();

        MetodosComunes.showToolbar(titulo,true,this);

        //lleno el recycler
        pullToLoadView=(PullToLoadView)findViewById(R.id.recyclerPd);
        new Paginacion(this,pullToLoadView,SECCION_SELECCIONADA).iniciarPaginacion();

        MetodosComunes.abrirActivityFab(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nuevo_menu, menu);

        final MenuItem menuItem=menu.findItem(R.id.action_search);
        searchView = (SearchView)menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                new Paginacion(PDActivity.this,pullToLoadView,SECCION_SELECCIONADA).iniciarPaginacionBusqueda(query);
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
                new Paginacion(PDActivity.this,pullToLoadView,SECCION_SELECCIONADA).iniciarPaginacion();
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_filtrar){
           abrirDialogFiltro();
            Log.e("dentro del menu","Menu");
        }
        return super.onOptionsItemSelected(item);
    }

    private void abrirDialogFiltro()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_filtro_seccion,null);


        builder.setView(view);

        Button no               = (Button) view.findViewById(R.id.dialogNo);
        Button si               = (Button) view.findViewById(R.id.dialogSi);
        final CheckBox web      = (CheckBox)view.findViewById(R.id.dialogRecursosWeb);
        final CheckBox video    = (CheckBox)view.findViewById(R.id.dialogVideos);
        final CheckBox audio    = (CheckBox)view.findViewById(R.id.dialogAudio);
        final CheckBox pdf      = (CheckBox)view.findViewById(R.id.dialogPdf);
        final CheckBox sin_filtro= (CheckBox)view.findViewById(R.id.dialogSinFiltro);

        inicializarVariablesChk(); //inicializo las variables con los valores de los chk

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sin_filtro.isChecked())
                {
                    inicializarVariablesChk();
                }
                else {
                    if(!web.isChecked())
                    {
                        str_web="null";
                    }
                    if(!video.isChecked())
                    {
                        str_video="null";
                    }
                    if(!audio.isChecked())
                    {
                        str_audio="null";
                    }
                    if(!pdf.isChecked())
                    {
                        str_pdf="null";
                    }
                }

                new Paginacion(PDActivity.this,pullToLoadView,SECCION_SELECCIONADA).
                        iniciarPaginacionFiltrada(str_web,str_video,str_audio,str_pdf);
                alertDialog.dismiss();
            }
        });

    }

    //segun la seleccion del usuario, son los valores de las variables
    private void seccionSeleccionada()
    {
        if(seccion.equals("pd"))
        {
            SECCION_SELECCIONADA=1;//pd en la BD
            titulo= "Primaria Digital";
        }
        else if(seccion.equals("ci")){
            SECCION_SELECCIONADA=2;//pd en la BD
            titulo= "Conectar Igualdad";
        }


    }

    private void inicializarVariablesChk()
    {
        str_web=getResources().getString(R.string.FILTRO_GENERAL);
        str_video=getResources().getString(R.string.FILTRO_Video);
        str_audio=getResources().getString(R.string.FILTRO_Audio);
        str_pdf=getResources().getString(R.string.FILTRO_Pdf);
    }

}
