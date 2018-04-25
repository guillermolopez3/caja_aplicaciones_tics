package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.model.ModelNE;
import com.gru.cajaaplicacionestics.model.NewPost;
import com.gru.cajaaplicacionestics.view.PdfActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by guill on 20/11/2017.
 */

public class AdapterNE extends RecyclerView.Adapter<AdapterNE.NEHolder>
{
    Activity activity;
    private List<ModelNE> array;
    private boolean escalo=false; //si hay que mostrar las card vs x fila es scale type es center inside

    public AdapterNE(Activity activity, List<ModelNE> array) {
        this.activity = activity;
        this.array = array;
    }

    public AdapterNE(Activity activity, List<ModelNE> array, boolean escalo) {
        this.activity = activity;
        this.array = array;
        this.escalo=escalo;
    }

    @Override
    public NEHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.card_recursos,parent,false);
        return new AdapterNE.NEHolder(view);
    }

    @Override
    public void onBindViewHolder(NEHolder holder, int position) {
        final ModelNE modelRecursos= array.get(position);
        holder.nombreRecurso.setVisibility(View.GONE);

        holder.descripcionCorta.setText(modelRecursos.getDescripcion());
        holder.hastag.setVisibility(View.GONE);

        String img = modelRecursos.getUrl_img();
        if((img!=null) && (img!="") && (!img.equals("null")))
        {
            Picasso.with(activity).load(img).into(holder.imagen);
        }
        else {
            Picasso.with(activity).load(R.drawable.fondo_card).into(holder.imagen);
        }
        if(escalo)
        {
            holder.imagen.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, PdfActivity.class);
                i.putExtra("link",modelRecursos.getUrl_pdf());
                activity.startActivity(i);
                //abrirDetalleCorrespondiente(modelRecursos.getCategoria(),modelRecursos);
                MetodosComunes.enviarPostSeleccionado(String.valueOf(modelRecursos.getId()),activity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public void add(ModelNE pos)
    {
        array.add(pos);
        notifyDataSetChanged();
    }

    public void clear()
    {
        array.clear();
        notifyDataSetChanged();
    }

    public class NEHolder extends RecyclerView.ViewHolder
    {
        private TextView nombreRecurso;
        private TextView descripcionCorta;
        private ImageView imagen;
        private Button hastag;
        private CardView cardView;

        public NEHolder(View itemView) {
            super(itemView);
            nombreRecurso     = (TextView)itemView.findViewById(R.id.nombreCardRecursos);
            descripcionCorta  = (TextView)itemView.findViewById(R.id.descripcionCardRecursos);
            cardView          = (CardView)itemView.findViewById(R.id.cardRecursos);
            hastag            = (Button)itemView.findViewById(R.id.buttonCardRecursos);
            imagen            = (ImageView)itemView.findViewById(R.id.imagenCardRecursos);
        }
    }
}
