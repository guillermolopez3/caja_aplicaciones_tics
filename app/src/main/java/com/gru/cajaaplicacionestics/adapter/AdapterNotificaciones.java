package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.model.ModelNotificaciones;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterNotificaciones extends RecyclerView.Adapter<AdapterNotificaciones.NotificacionesHolder>
{
    ArrayList<ModelNotificaciones> lista;
    Activity activity;


    public AdapterNotificaciones(ArrayList<ModelNotificaciones> lista, Activity activity){
        this.lista = lista;
        this.activity = activity;
    }

    @NonNull
    @Override
    public NotificacionesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificaciones,parent,false);
        return new NotificacionesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificacionesHolder holder, int position) {
        final ModelNotificaciones model = lista.get(position);

        holder.titulo.setText(model.getTitulo());
        holder.fecha.setText(model.getFecha());
        holder.nivel.setText(model.getNivel());
        holder.destinatario.setText(model.getDestinatario());
        holder.modalidad.setText(model.getModalidad());
        holder.hs_cer.setText(model.getHs_cert());

        if(model.isEsNuevo()){
            holder.borde.setVisibility(View.VISIBLE);
        }else {
            holder.borde.setVisibility(View.GONE);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(model.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class NotificacionesHolder extends RecyclerView.ViewHolder{
        TextView titulo,fecha,nivel,destinatario,modalidad,hs_cer;
        FrameLayout borde;
        CardView card;

        public NotificacionesHolder(@NonNull View item) {
            super(item);
            titulo       = item.findViewById(R.id.Titulo);
            fecha        = item.findViewById(R.id.Fecha);
            nivel        = item.findViewById(R.id.Nivel);
            destinatario = item.findViewById(R.id.Destinatarios);
            modalidad    = item.findViewById(R.id.Modalidad);
            hs_cer       = item.findViewById(R.id.HorasCert);
            borde        = item.findViewById(R.id.bordeColor);
            card         = item.findViewById(R.id.card);
        }
    }
}
