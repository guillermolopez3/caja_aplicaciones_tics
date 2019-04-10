package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.model.ModelNotificaciones;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterNotificaciones extends RecyclerView.Adapter<AdapterNotificaciones.NotificacionesHolder>
{
    ArrayList<ModelNotificaciones> lista;


    public AdapterNotificaciones(ArrayList<ModelNotificaciones> lista){
        this.lista = lista;
    }

    @NonNull
    @Override
    public NotificacionesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificaciones,parent,false);
        return new NotificacionesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificacionesHolder holder, int position) {
        ModelNotificaciones model = lista.get(position);

        holder.titulo.setText(model.getTitulo());
        holder.fecha.setText(model.getFecha());
        holder.descrip.setText(model.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class NotificacionesHolder extends RecyclerView.ViewHolder{
        TextView titulo,fecha,descrip;

        public NotificacionesHolder(@NonNull View item) {
            super(item);
            titulo  = item.findViewById(R.id.Titulo);
            fecha   = item.findViewById(R.id.Fecha);
            descrip = item.findViewById(R.id.Detalle);
        }
    }
}
