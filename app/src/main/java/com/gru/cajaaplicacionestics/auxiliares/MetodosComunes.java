package com.gru.cajaaplicacionestics.auxiliares;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.view.CapacitacionActivity;
import com.gru.cajaaplicacionestics.view.EnviarRecursosActivity;
import com.gru.cajaaplicacionestics.view.MainActivity;
import com.gru.cajaaplicacionestics.view.ServicioTecnicoActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by guill on 14/11/2017.
 */

public class MetodosComunes
{

    public static void showToolbar(String tittle, boolean upButton, AppCompatActivity activity)
    {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(tittle);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    //abre el fab con las opciones de pedido de capacitacion/st o enviar recursos
    public static void abrirActivityFab(AppCompatActivity act)
    {
        final AppCompatActivity activity= act;
        final FloatingActionsMenu fab;
        com.getbase.floatingactionbutton.FloatingActionButton service,capac,recurso;
        fab = (FloatingActionsMenu) activity.findViewById(R.id.menu_fab);
        recurso = (com.getbase.floatingactionbutton.FloatingActionButton)activity.findViewById(R.id.accionNuevoRecurso);
        service = (com.getbase.floatingactionbutton.FloatingActionButton)activity.findViewById(R.id.accionServicioTecnico);
        capac   =  (com.getbase.floatingactionbutton.FloatingActionButton)activity.findViewById(R.id.accionCapacitacion);

        recurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.collapse();
                Intent i = new Intent(activity,EnviarRecursosActivity.class);
                activity.startActivity(i);
            }
        });

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.collapse();
                Intent i = new Intent(activity,ServicioTecnicoActivity.class);
                activity.startActivity(i);
            }
        });

        capac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.collapse();
                Intent i = new Intent(activity,CapacitacionActivity.class);
                activity.startActivity(i);
            }
        });
    }

    //metodo para compartir texto ya sea whatsapp, mail
    public static void compartirRecursos(String mens,AppCompatActivity activity)
    {
        String mensaje="Recurso enviado desde CajaTic \n";
        mensaje+= mens;

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Compartir pdf");
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        emailIntent.setType("text/plain");
        activity.startActivity(Intent.createChooser(emailIntent, "Enviar Recurso "));
    }

}
