package com.gru.cajaaplicacionestics.auxiliares;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.view.CapacitacionActivity;
import com.gru.cajaaplicacionestics.view.EnviarRecursosActivity;
import com.gru.cajaaplicacionestics.view.ServicioTecnicoActivity;

/**
 * Created by guill on 14/11/2017.
 */

public class MetodosComunes
{

    public static void showToolbar(String tittle, boolean upButton, AppCompatActivity activity)
    {
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
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
        fab     = activity.findViewById(R.id.menu_fab);
        recurso = activity.findViewById(R.id.accionNuevoRecurso);
        service = activity.findViewById(R.id.accionServicioTecnico);
        capac   = activity.findViewById(R.id.accionCapacitacion);

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

    public static void manejarActivityError(AppCompatActivity activity,String accion)
    {
        Button btnReintentar        = activity.findViewById(R.id.error_btn_retry);
        ImageView imagenError       = activity.findViewById(R.id.errorImage);
        TextView titleError         = activity.findViewById(R.id.errorTxtTitle);

        if(accion.equals("buscar"))
        {
            imagenError.setVisibility(View.GONE);
            titleError.setText("Ingrese una palabra para buscar recursos");
            btnReintentar.setVisibility(View.GONE);
        }
        else if(accion.equals("buscarError")) {
            imagenError.setVisibility(View.VISIBLE);
            imagenError.setImageResource(R.drawable.sin_conexion);
            titleError.setText("Lo sentimos! Hubo un problema. Verifique la conexión a internet");
            btnReintentar.setVisibility(View.GONE);

        }
        else if(accion.equals("buscarSinRecursos")){
            imagenError.setVisibility(View.VISIBLE);
            imagenError.setImageResource(R.drawable.search_error);
            titleError.setText("Lo sentimos! No encontramos ningun recurso");
            btnReintentar.setVisibility(View.GONE);
        }
        else if(accion.equals("sinConexion")){
            imagenError.setVisibility(View.VISIBLE);
            imagenError.setImageResource(R.drawable.sin_conexion);
            titleError.setText("Lo sentimos! Hubo un problema. Verifique la conexión a internet");
            btnReintentar.setVisibility(View.VISIBLE);
        }

    }

    public static String verificarUrl(String cadena) //verifico si la cadena de la img o de link viene de internet o de mi server
    {
        try{
            if(cadena.startsWith("img") || cadena.startsWith("files"))
            {
                String x = "http://www.igualdadycalidadcba.gov.ar/CajaTIC/storage/public/" + cadena;
                return x;
            }
            else {
                return cadena;
            }
        }
        catch (Exception e){
            return "";
        }

    }

}
