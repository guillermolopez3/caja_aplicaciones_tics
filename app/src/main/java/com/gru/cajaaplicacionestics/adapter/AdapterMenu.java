package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.WebViewActivity;
import com.gru.cajaaplicacionestics.view.NovedadActivity;
import com.gru.cajaaplicacionestics.model.ModelMenu;
import com.gru.cajaaplicacionestics.view.NEActivity;
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
        switch (position){
            case 0:
                i = new Intent(activity,PostActivity.class);
                i.putExtra("seleccion","pd");
                i.putExtra("titulo","Primaria Digital");
                activity.startActivity(i);
                break;
            case 1:
                i = new Intent(activity,PostActivity.class);
                i.putExtra("seleccion","ci");
                i.putExtra("titulo","Conectar Igualdad");
                activity.startActivity(i);
                break;
            case 2:
                i = new Intent(activity,NEActivity.class);
               // i.putExtra("seleccion","ci");
                //i.putExtra("titulo","Conectar Igualdad");
                activity.startActivity(i);
                break;
            case 3:
                i= new Intent(activity, RecursosXNivelActivity.class);
                activity.startActivity(i);
                break;
            case 4:
                i = new Intent(activity,NovedadActivity.class);
                activity.startActivity(i);
                break;
            case 5:
                i = new Intent(activity,PostActivity.class);
                i.putExtra("seleccion","canal");
                i.putExtra("titulo","Audiovisuales");
                activity.startActivity(i);
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
            imagen  = (ImageView) itemView.findViewById(R.id.itemMenuSrc);
            card    = (CardView) itemView.findViewById(R.id.cardMenu);
        }
    }
}
