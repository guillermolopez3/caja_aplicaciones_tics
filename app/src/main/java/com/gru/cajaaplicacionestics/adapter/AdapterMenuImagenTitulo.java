package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.map.MapsActivity;
import com.gru.cajaaplicacionestics.map.NewMapActivity;
import com.gru.cajaaplicacionestics.model.ImagenTituloModel;
import com.gru.cajaaplicacionestics.view.ne.NEFragmentGralActivity;
import com.gru.cajaaplicacionestics.view.semana_tic.Agenda_ST;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterMenuImagenTitulo extends RecyclerView.Adapter<AdapterMenuImagenTitulo.viewHolder>
{
    ArrayList<ImagenTituloModel> lista;
    Activity activity;
    boolean es_menu = false; //bandera para saber si cargo el menu de ST o es la agenda u noticia

    public AdapterMenuImagenTitulo(ArrayList<ImagenTituloModel> models,Activity activity,boolean es_menu)
    {
        lista=models;
        this.activity=activity;
        this.es_menu = es_menu;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.card_img_titulo,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        ImagenTituloModel model = lista.get(position);

        if(es_menu)
        {
            cargarMenuST(holder,position,model);
        }
        else {

        }

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    private void cargarMenuST(viewHolder holder, final int position, ImagenTituloModel model )
    {
       holder.titulo.setText(model.getTitulo());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position)
                {
                    case 0:
                        //activity.startActivity(new Intent(activity, MapsActivity.class));
                        activity.startActivity(new Intent(activity, NewMapActivity.class));
                        break;
                    case 1:
                        /*Intent i = new Intent(activity, NEFragmentGralActivity.class);
                        i.putExtra("seccion","agenda");
                        i.putExtra("titulo","Agenda");
                        activity.startActivity(i);*/
                        activity.startActivity(new Intent(activity, Agenda_ST.class));
                        break;
                    case 2:
                        Intent in = new Intent(activity, NEFragmentGralActivity.class);
                        in.putExtra("seccion","noticia");
                        in.putExtra("titulo","Noticias");
                        activity.startActivity(in);
                        break;

                }
            }
        });
    }


    class viewHolder extends RecyclerView.ViewHolder
    {
        ImageView imagen;
        TextView titulo;
        CardView card;
        public viewHolder(View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.cardImagen);
            titulo = itemView.findViewById(R.id.txtTitulo);
            card   = itemView.findViewById(R.id.card);
        }
    }
}
