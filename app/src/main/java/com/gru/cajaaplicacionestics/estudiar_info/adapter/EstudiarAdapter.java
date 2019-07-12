package com.gru.cajaaplicacionestics.estudiar_info.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.estudiar_info.model.CarrerasModel;

import java.util.ArrayList;

public class EstudiarAdapter extends RecyclerView.Adapter<EstudiarAdapter.EstudiarHolder>
{
    private ArrayList<CarrerasModel> list;
    private Activity activity;

    public EstudiarAdapter(ArrayList<CarrerasModel> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public EstudiarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_estudiar_info,parent,false);
        return new EstudiarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstudiarHolder holder, int position) {
        CarrerasModel model = list.get(position);

        holder.carrera.setText(model.getNombreCarrera());
        holder.institucion.setText(model.getInstitucion());
        holder.duracion.setText(model.getDuracion() + " años");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class EstudiarHolder extends RecyclerView.ViewHolder
    {
        TextView carrera, institucion, duracion;
        public EstudiarHolder(@NonNull View itemView)
        {
            super(itemView);
            carrera = itemView.findViewById(R.id.nombreCarrera);
            institucion = itemView.findViewById(R.id.nombreInstitucion);
            duracion = itemView.findViewById(R.id.duracion);
        }
    }
}
