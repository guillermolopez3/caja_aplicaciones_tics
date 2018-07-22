package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.model.ItemSTModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterListST extends RecyclerView.Adapter<AdapterListST.HolderST>
{
    ArrayList<ItemSTModel> lista;
    Activity activity;

    public AdapterListST(ArrayList<ItemSTModel> lista, Activity activity)
    {
        this.lista      = lista;
        this.activity   = activity;
    }

    @NonNull
    @Override
    public HolderST onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_agenda,viewGroup,false);
        return new HolderST(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderST holderST, int posicion) {
        ItemSTModel model = lista.get(posicion);

        holderST.hora.setText(model.getHora());
        holderST.mananaTarde.setText(model.getMananaTarde());
        holderST.titulo.setText(model.getTitulo());
        holderST.disertante.setText(model.getDisertante());
        holderST.salon.setText(model.getSalon());

        try{
            if(model.getCiudad().equals("cba"))
            {
                holderST.filtro.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_circulo_naranja));
            }else if(model.getCiudad().equals("rio")){
                holderST.filtro.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_circulo_verde));
            }else if(model.getCiudad().equals("maria")){
                holderST.filtro.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_circulo_rojo));
            }else if(model.getCiudad().equals("san")){
                holderST.filtro.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_circulo_azul));
            }
        }catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    class HolderST extends RecyclerView.ViewHolder
    {
        TextView hora,mananaTarde,titulo,disertante,salon;
        ImageView filtro;

        public HolderST(@NonNull View itemView) {
            super(itemView);
            hora        = itemView.findViewById(R.id.txtHora);
            mananaTarde = itemView.findViewById(R.id.txtMananaTarde);
            titulo      = itemView.findViewById(R.id.tituloEvento);
            disertante  = itemView.findViewById(R.id.txtDisertante);
            salon       = itemView.findViewById(R.id.txtSalon);
            filtro      = itemView.findViewById(R.id.circuloIndicador);
        }
    }
}
