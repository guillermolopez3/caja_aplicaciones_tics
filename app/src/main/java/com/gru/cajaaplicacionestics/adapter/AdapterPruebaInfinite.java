package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.model.ModelPost;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterPruebaInfinite extends RecyclerView.Adapter<AdapterPruebaInfinite.RecursosHolder>
{
    private Activity activity;
    private List<ModelPost> array;

    public AdapterPruebaInfinite(Activity activity,List<ModelPost> array){
        this.activity = activity;
        this.array = array;
    }

    @NonNull
    @Override
    public AdapterPruebaInfinite.RecursosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.card_recursos,parent,false);
        return new AdapterPruebaInfinite.RecursosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPruebaInfinite.RecursosHolder holder, int position) {
        final ModelPost modelRecursos= array.get(position);
        holder.nombreRecurso.setText(modelRecursos.getTitle());
        String img = modelRecursos.getImage();

        if((modelRecursos.getCopete()!=null) && (!modelRecursos.getCopete().equals("")) && (!modelRecursos.getCopete().equals("null"))) {
            holder.descripcionCorta.setText(modelRecursos.getCopete());
        }
        else {
            holder.descripcionCorta.setVisibility(View.GONE);
        }

        try{
            if((img!=null) && (!img.equals("")) && (!img.equals("null")))
            {
                Picasso.with(activity).load(MetodosComunes.verificarUrl(img)).into(holder.imagen);
            }
            else {
                Picasso.with(activity).load(R.drawable.fondo_card).into(holder.imagen);
            }
        }catch (Exception e){}


        if((modelRecursos.getTags()!=null) && (!modelRecursos.getTags().equals("")) && (!modelRecursos.getTags().equals("null")))
        {
            holder.hastag.setText(modelRecursos.getTags());
        }else {
            holder.hastag.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    protected class RecursosHolder extends RecyclerView.ViewHolder
    {
        private TextView nombreRecurso;
        private TextView descripcionCorta;
        private ImageView imagen;
        private ImageView icono;
        private Button hastag;
        private CardView cardView;
        private Button favBtn;
        public RecursosHolder(View itemView) {
            super(itemView);
            nombreRecurso     = itemView.findViewById(R.id.nombreCardRecursos);
            descripcionCorta  = itemView.findViewById(R.id.descripcionCardRecursos);
            hastag            = itemView.findViewById(R.id.buttonCardRecursos);
            cardView          = itemView.findViewById(R.id.cardRecursos);
            imagen            = itemView.findViewById(R.id.imagenCardRecursos);
            icono             = itemView.findViewById(R.id.iconoCateg);
            favBtn            = itemView.findViewById(R.id.btnFav);
            //imgFav            = itemView.findViewById(R.id.btnFav);
        }
    }
}
