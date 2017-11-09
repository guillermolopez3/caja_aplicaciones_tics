package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.model.NewPost;
import com.gru.cajaaplicacionestics.model.Post;
import com.gru.cajaaplicacionestics.view.DetallaRecursoActivity;
import com.gru.cajaaplicacionestics.view.DetalleRecursoAudio;
import com.gru.cajaaplicacionestics.view.YoutubeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by guill on 07/10/2017.
 */

public class AdapterNewPost extends RecyclerView.Adapter<AdapterNewPost.RecursosHolder>
{
    private List<NewPost> array;
    private Activity activity;

    public AdapterNewPost(Activity ac, List<NewPost> item)
    {
        array=item;
        activity= ac;
    }
    @Override
    public RecursosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.card_recursos,parent,false);
        return new RecursosHolder(view);
    }

    @Override
    public void onBindViewHolder(RecursosHolder holder, int position) {
        final NewPost modelRecursos= array.get(position);
        holder.nombreRecurso.setText(modelRecursos.getNombre());

        holder.descripcionCorta.setText(modelRecursos.getDescripcion_corta());
        holder.hastag.setText(modelRecursos.getTag());

        String img = modelRecursos.getUrl_img();
        if((img!=null) && (img!="") && (!img.equals("null")))
        {
            Picasso.with(activity).load(img).into(holder.imagen);
        }
        else {
            Picasso.with(activity).load(R.drawable.fondo_card).into(holder.imagen);
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDetalleCorrespondiente(modelRecursos.getCategoria(),modelRecursos);
            }
        });

    }

    private void abrirDetalleCorrespondiente(String tipo_rec,NewPost post) //dependiendo si es audio, video o web muestra distintos activity detalles
    {
        Log.e("tipo_recurso", tipo_rec);
        if(tipo_rec.equals("Video"))
        {
            Intent i = new Intent(activity, YoutubeActivity.class);
            i.putExtra("data",post);
            activity.startActivity(i);
        }
        else if(tipo_rec.equals("Audio"))
        {
            Intent i = new Intent(activity, DetalleRecursoAudio.class);
            i.putExtra("data",post);
            activity.startActivity(i);
        }
        else {
            Intent i = new Intent(activity, DetallaRecursoActivity.class);
            i.putExtra("data",post);
            activity.startActivity(i);
        }
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class RecursosHolder extends RecyclerView.ViewHolder
    {
        private TextView nombreRecurso;
        private TextView descripcionCorta;
        private ImageView imagen;
        private Button hastag;
        private CardView cardView;
        public RecursosHolder(View itemView) {
            super(itemView);

            nombreRecurso     = (TextView)itemView.findViewById(R.id.nombreCardRecursos);
            descripcionCorta  = (TextView)itemView.findViewById(R.id.descripcionCardRecursos);
            hastag            = (Button)itemView.findViewById(R.id.buttonCardRecursos);
            cardView          = (CardView)itemView.findViewById(R.id.cardRecursos);
            imagen            = (ImageView)itemView.findViewById(R.id.imagenCardRecursos);
        }
    }
}