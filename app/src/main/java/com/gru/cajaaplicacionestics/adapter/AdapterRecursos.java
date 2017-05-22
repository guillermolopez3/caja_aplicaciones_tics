package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.model.ModelRecursos;

import java.util.ArrayList;

/**
 * Created by guill on 22/05/2017.
 */

public class AdapterRecursos extends RecyclerView.Adapter<AdapterRecursos.RecursosHolder>
{
    private ArrayList<ModelRecursos> array;
    private Activity activity;

    public AdapterRecursos(Activity ac,ArrayList<ModelRecursos> m)
    {
        array=m;
        activity= ac;
    }



    @Override
    public RecursosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recursos,parent,false);
        return new RecursosHolder(view);
    }

    @Override
    public void onBindViewHolder(RecursosHolder holder, int position) {
        ModelRecursos modelRecursos= array.get(position);
        holder.nombreRecurso.setText(modelRecursos.getNombreRecurso());
        holder.descripcionCorta.setText(modelRecursos.getDescripcionCorta());
        holder.hastag.setText(modelRecursos.getHastag());

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class RecursosHolder extends RecyclerView.ViewHolder
   {
       private TextView nombreRecurso;
       private TextView descripcionCorta;
       private Button hastag;
       public RecursosHolder(View itemView) {
           super(itemView);

           nombreRecurso = (TextView)itemView.findViewById(R.id.nombreCardRecursos);
           descripcionCorta = (TextView)itemView.findViewById(R.id.descripcionCardRecursos);
           hastag = (Button)itemView.findViewById(R.id.buttonCardRecursos);
       }
   }
}
