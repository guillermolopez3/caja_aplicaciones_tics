package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.view.AprenderConectadosActivity;
import com.gru.cajaaplicacionestics.view.NovedadActivity;
import com.gru.cajaaplicacionestics.model.ModelMenu;
import com.gru.cajaaplicacionestics.view.PpadeNewActivity;
import com.gru.cajaaplicacionestics.view.ne.NuestraEscuelaMenuActivity;
import com.gru.cajaaplicacionestics.view.PostActivity;
import com.gru.cajaaplicacionestics.view.RecursosXNivelActivity;
import com.gru.cajaaplicacionestics.view.prueba.Prueba;
import com.gru.cajaaplicacionestics.view.qr.QrReadActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by guill on 23/02/2018.
 */

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.MenuHolder>
{
    Activity activity;
    ArrayList<ModelMenu> array;

    public AdapterMenu(Activity a, ArrayList<ModelMenu> modelMenus)
    {
        activity=a;
        array= modelMenus;
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
            case 0:
                i = new Intent(activity,NovedadActivity.class);
                params.putString("nombre_pantalla","Novedades");
                activity.startActivity(i);
                break;
            case 1:
                i= new Intent(activity, RecursosXNivelActivity.class);
                params.putString("nombre_pantalla","RD");
                activity.startActivity(i);
                break;
            case 2:
                i = new Intent(activity,AprenderConectadosActivity.class);
                i.putExtra("seleccion","ac");
                i.putExtra("titulo","Aprender Conectados");
                params.putString("nombre_pantalla","PD");
                activity.startActivity(i);
                break;
            case 3:
                i = new Intent(activity,NuestraEscuelaMenuActivity.class);
                params.putString("nombre_pantalla","NE");
                activity.startActivity(i);
                break;
            case 4:
                i = new Intent(activity,PostActivity.class);
                i.putExtra("seleccion","canal");
                i.putExtra("titulo","Audiovisuales");
                params.putString("nombre_pantalla","Canal");
                activity.startActivity(i);
                break;
            case 5:
                activity.startActivity(new Intent(activity, PpadeNewActivity.class));
                break;
            case 6:
                i = new Intent(activity,PostActivity.class);
                i.putExtra("seleccion","fav");
                i.putExtra("titulo","Mis Favoritos");
                params.putString("nombre_pantalla","Favoritos");
                activity.startActivity(i);
                break;
            case 7:
               // activity.startActivity(new Intent(activity,QrReadActivity.class));
                activity.startActivity(new Intent(activity, Prueba.class));
                break;

        }
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
