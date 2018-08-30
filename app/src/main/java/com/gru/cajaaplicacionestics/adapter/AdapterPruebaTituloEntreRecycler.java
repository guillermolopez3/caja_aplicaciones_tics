package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.model.ItemSTModel;

import java.util.ArrayList;

public class AdapterPruebaTituloEntreRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<ItemSTModel> lista;
    private Activity activity;

    private String old_city=""; //variable para saber que ciudad muestro
    private String new_city="";

    private final int TYPE_CABECERA =0;
    private final int TYPE_CUERPO =1;

    private final String CBA="cba1",SAN="sanf",MARIA="vmaria",RIO="rioc",JM="jamaria";

    private boolean es_pos_cero = false; //es para mostrar solo una vez el titulo al principio

    public AdapterPruebaTituloEntreRecycler(ArrayList<ItemSTModel> lista, Activity activity)
    {
        this.lista      = lista;
        this.activity   = activity;
    }

    @Override
    public int getItemViewType(int position) {

        ItemSTModel model = lista.get(position);
        new_city = model.getCiudad();
        String ciu = model.getCiudad();
        Log.e("old: " + old_city,"new:" + new_city);

       /* if(!old_city.equals("cba"))
        {
            Log.e("ciudades","iguale");
        }

        if(position==0)
        {
            es_pos_cero = true;
            return TYPE_CABECERA;
        }
        if(!old_city.equals(new_city) && !es_pos_cero)
        {
            old_city = new_city;
            return TYPE_CABECERA;
        }else {
            old_city = new_city;
            return TYPE_CUERPO;
        }*/
       if(ciu.equals(CBA) || ciu.equals(SAN) || ciu.equals(MARIA) || ciu.equals(RIO) || ciu.equals(JM) )
       {
           return TYPE_CABECERA;
       }
       else {
           return TYPE_CUERPO;
       }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int view_type) {
        if(view_type == TYPE_CABECERA)
        {
            return new HolderTitulo(LayoutInflater.from(activity).inflate(R.layout.item_texto_recycler,viewGroup,false));
        }else if (view_type == TYPE_CUERPO) {
            return new HolderCuerpo(LayoutInflater.from(activity).inflate(R.layout.item_agenda,viewGroup,false));
        }

        throw new RuntimeException("error al cargar la lista");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int posicion) {

        if(holder instanceof HolderTitulo)
        {
            itemTitulo(holder,posicion);
        }
        else if(holder instanceof HolderCuerpo){
            itemCuerpo(holder,posicion);
        }
    }

    private void itemCuerpo(final RecyclerView.ViewHolder holder, final int position)
    {
        ItemSTModel model = lista.get(position);

        HolderCuerpo holderST = (HolderCuerpo) holder;

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
            }else if(model.getCiudad().equals("jmaria")){
                holderST.filtro.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_circulo_rosa));
            }
        }catch (Exception e){}
    }

    private void itemTitulo(final RecyclerView.ViewHolder holder,final int position)
    {
        HolderTitulo titulo = (HolderTitulo)holder;
        String texto="";
        if(new_city.equals(RIO))
        {
            texto = "Río Cuarto";
        }else if(new_city.equals(MARIA)){
            texto = "Villa María";
        }else if(new_city.equals(CBA)){
            texto = "Córdoba";
        }else if(new_city.equals(SAN)){
            texto = "San Francisco";
        }
        else if(new_city.equals(JM)){
            texto = "Jesús María";
        }
        titulo.ciudad.setText(texto);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    class HolderCuerpo extends RecyclerView.ViewHolder
    {
        TextView hora,mananaTarde,titulo,disertante,salon;
        ImageView filtro;

        public HolderCuerpo(@NonNull View itemView) {
            super(itemView);
            hora        = itemView.findViewById(R.id.txtHora);
            mananaTarde = itemView.findViewById(R.id.txtMananaTarde);
            titulo      = itemView.findViewById(R.id.tituloEvento);
            disertante  = itemView.findViewById(R.id.txtDisertante);
            salon       = itemView.findViewById(R.id.txtSalon);
            filtro      = itemView.findViewById(R.id.circuloIndicador);
        }
    }

    class HolderTitulo extends RecyclerView.ViewHolder
    {
        TextView ciudad;

        public HolderTitulo(@NonNull View itemView) {
            super(itemView);
            ciudad = itemView.findViewById(R.id.txtCiudadTitulo);
        }
    }

}
