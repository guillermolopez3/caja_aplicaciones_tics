package com.gru.cajaaplicacionestics.estudiar_info.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.estudiar_info.model.CarrerasModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EstudiarAdapter extends RecyclerView.Adapter<EstudiarAdapter.EstudiarHolder>
{
    private ArrayList<CarrerasModel> list;
    private Activity activity;

    public EstudiarAdapter(Activity activity, boolean fav) {
        this.activity = activity;
        list = new ArrayList<>();
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

        String img = model.getUrl_logo();

        holder.carrera.setText(model.getNombreCarrera());
        holder.institucion.setText(model.getInstitucion());
        //holder.duracion.setText(model.getDuracion() + " años");
        try{
            if((img!=null) && (!img.equals("")) && (!img.equals("null")))
            {
                //Picasso.with(activity).load(MetodosComunes.verificarUrl(img)).into(holder.imagen);
                Glide.with(activity).load(img).apply(RequestOptions.bitmapTransform(new RoundedCorners(14))).into(holder.imagen);
            }
            else {
                Picasso.with(activity).load(R.drawable.fondo_card).into(holder.imagen);
            }
        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(CarrerasModel pos)
    {
        list.add(pos);
        notifyDataSetChanged();
    }

    public void clear()
    {
        list.clear();
        notifyDataSetChanged();
    }

    public int getArrayCount() {
        return list.size(); //cantidad de items del array
    }

    class EstudiarHolder extends RecyclerView.ViewHolder
    {
        ImageView imagen;
        TextView carrera, institucion, duracion;
        public EstudiarHolder(@NonNull View itemView)
        {
            super(itemView);
            imagen = itemView.findViewById(R.id.imgCarrera);
            carrera = itemView.findViewById(R.id.nombreCarrera);
            institucion = itemView.findViewById(R.id.nombreInstitucion);
            //duracion = itemView.findViewById(R.id.duracion);
        }
    }
}