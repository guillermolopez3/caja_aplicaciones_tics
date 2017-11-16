package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.model.ModelNovedades;
import com.gru.cajaaplicacionestics.view.PdfActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by guill on 08/10/2017.
 */

public class AdapterNovedades extends RecyclerView.Adapter<AdapterNovedades.NovedadesHolder>
{
    private List<ModelNovedades> array;
    private Activity activity;

    public AdapterNovedades(Activity ac, List<ModelNovedades> item)
    {
        array=item;
        activity= ac;
    }

    @Override
    public NovedadesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(activity).inflate(R.layout.card_novedades,parent,false);
        return new NovedadesHolder(view);
    }

    @Override
    public void onBindViewHolder(NovedadesHolder holder, int position) {
        ModelNovedades novedades = array.get(position);

        String img = novedades.getUrl_imagen();
        if(!img.equals("") && !img.equals("null"))
        {
            Picasso.with(activity).load(img).into(holder.imagen);
        }else {
            Picasso.with(activity).load(R.drawable.fondo_card).into(holder.imagen);
        }
        final String url_pdf= novedades.getUrl_pdf();

        holder.novedades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, PdfActivity.class);
                i.putExtra("link",url_pdf);
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class NovedadesHolder extends RecyclerView.ViewHolder
    {
        ImageView imagen;
        Button novedades, link;
        public NovedadesHolder(View itemView) {
            super(itemView);
            imagen      =(ImageView)itemView.findViewById(R.id.imagenCardNovedades);
            novedades   =(Button)itemView.findViewById(R.id.btnNovedades);
            link        =(Button)itemView.findViewById(R.id.btnNovedadesLink);
        }
    }
}
