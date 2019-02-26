package com.gru.cajaaplicacionestics.nuestra_escuela.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterNE;
import com.gru.cajaaplicacionestics.adapter.AdapterNPost;
import com.gru.cajaaplicacionestics.nuestra_escuela.model.SectionRecyclerNEModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SectionRecyclerViewAdapter extends RecyclerView.Adapter<SectionRecyclerViewAdapter.SectionViewHolder>
{
    private Activity activity;
    private ArrayList<SectionRecyclerNEModel> lista;

    public SectionRecyclerViewAdapter(Activity activity, ArrayList<SectionRecyclerNEModel> lista){
        this.activity = activity;
        this.lista = lista;
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_row_recycler,parent,false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        SectionRecyclerNEModel datos = lista.get(position);

        holder.seccion.setText(datos.getSection());

        holder.item.setHasFixedSize(true);
        holder.item.setNestedScrollingEnabled(false);
        holder.item.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        PostAdapter adapter = new PostAdapter(activity,datos.getItems());
        holder.item.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    class SectionViewHolder extends RecyclerView.ViewHolder
    {
        TextView seccion;
        RecyclerView item;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            seccion = itemView.findViewById(R.id.txtSection);
            item = itemView.findViewById(R.id.recyclerItem);
        }
    }
}
