package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.view.NovedadActivity;
import com.gru.cajaaplicacionestics.model.ModelMenu;
import com.gru.cajaaplicacionestics.view.NuestraEscuelaMenuActivity;
import com.gru.cajaaplicacionestics.view.PostActivity;
import com.gru.cajaaplicacionestics.view.RecursosXNivelActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by guill on 23/02/2018.
 */

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.MenuHolder>
{
    Activity activity;
    ArrayList<ModelMenu> array;
    private FirebaseAnalytics analytics;

    public AdapterMenu(Activity a, ArrayList<ModelMenu> modelMenus,FirebaseAnalytics analytics)
    {
        activity=a;
        array= modelMenus;
        this.analytics=analytics;
    }
    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.card_menu,parent,false);
        return new AdapterMenu.MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, final int position) {
        ModelMenu menu = array.get(position);

        Picasso.with(activity).load(menu.getSrcImagen()).into(holder.imagen);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuSeleccionado(position);
            }
        });
    }

    private void menuSeleccionado(int position)
    {
        Intent i;
        Bundle params = new Bundle();
        switch (position){
            case 2:
                i = new Intent(activity,PostActivity.class);
                i.putExtra("seleccion","pd");
                i.putExtra("titulo","Primaria Digital");
                params.putString("nombre_pantalla","PD");
                activity.startActivity(i);
                break;
            case 3:
                i = new Intent(activity,PostActivity.class);
                i.putExtra("seleccion","ci");
                i.putExtra("titulo","Conectar Igualdad");
                params.putString("nombre_pantalla","CI");
                activity.startActivity(i);
                break;
            case 4:
                i = new Intent(activity,NuestraEscuelaMenuActivity.class);
               // i.putExtra("seleccion","ci");
                //i.putExtra("titulo","Conectar Igualdad");
                params.putString("nombre_pantalla","NE");
                activity.startActivity(i);
                break;
            case 1:
                i= new Intent(activity, RecursosXNivelActivity.class);
                params.putString("nombre_pantalla","RD");
                activity.startActivity(i);
                break;
            case 0:
                i = new Intent(activity,NovedadActivity.class);
                params.putString("nombre_pantalla","Novedades");
                activity.startActivity(i);
                break;
            case 5:
                i = new Intent(activity,PostActivity.class);
                i.putExtra("seleccion","canal");
                i.putExtra("titulo","Audiovisuales");
                params.putString("nombre_pantalla","Canal");
                activity.startActivity(i);
                break;
        }
        analytics.logEvent("pantalla",params);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MenuHolder extends RecyclerView.ViewHolder
    {
        ImageView imagen;
        CardView card;
        public MenuHolder(View itemView) {
            super(itemView);
            imagen  = itemView.findViewById(R.id.itemMenuSrc);
            card    = itemView.findViewById(R.id.cardMenu);
        }
    }
}
