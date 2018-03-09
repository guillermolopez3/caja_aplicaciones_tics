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

import com.gru.cajaaplicacionestics.R;;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.auxiliares.WebViewActivity;
import com.gru.cajaaplicacionestics.model.ModelPost;
import com.gru.cajaaplicacionestics.view.DetalleRecursoGeneralActivity;
import com.gru.cajaaplicacionestics.view.DetalleRecursoAudio;
import com.gru.cajaaplicacionestics.view.PdfActivity;
import com.gru.cajaaplicacionestics.view.YoutubeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guill on 23/02/2018.
 */

public class AdapterNPost extends RecyclerView.Adapter<AdapterNPost.RecursosHolder>
{
    private Activity activity;
    private List<ModelPost> array;
    private boolean isLoadingAdded=false; //bandera para saber si es el ultimo

    public AdapterNPost(Activity a)
    {
        Log.e("estoy en","constructor");
        activity=a;
        array=new ArrayList<>();
    }
    @Override
    public AdapterNPost.RecursosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.card_recursos,parent,false);
        return new AdapterNPost.RecursosHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterNPost.RecursosHolder holder, int position) {
        final ModelPost modelRecursos= array.get(position);
        holder.nombreRecurso.setText(modelRecursos.getTitle());

        if((modelRecursos.getCopete()!=null) && (modelRecursos.getCopete()!="") && (!modelRecursos.getCopete().equals("null"))) {
            holder.descripcionCorta.setText(modelRecursos.getCopete());
        }
        else {
            holder.descripcionCorta.setVisibility(View.GONE);
        }



        String img = modelRecursos.getImage();
        if((img!=null) && (img!="") && (!img.equals("null")))
        {

            Picasso.with(activity).load(MetodosComunes.verificarUrl(img)).into(holder.imagen);
        }
        else {
            Picasso.with(activity).load(R.drawable.fondo_card).into(holder.imagen);
        }

        if((modelRecursos.getTags()!=null) && (modelRecursos.getTags()!="") && (!modelRecursos.getTags().equals("null")))
        {
            holder.hastag.setText(modelRecursos.getTags());
        }else {
            holder.hastag.setVisibility(View.INVISIBLE);
        }



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDetalleCorrespondiente(modelRecursos.getId_tipo_activity(),modelRecursos);
            }
        });
    }

    private void abrirDetalleCorrespondiente(int tipoActivity,ModelPost post) //dependiendo si es audio, video o web muestra distintos activity detalles
    {
        //Log.e("tipo_recurso", tipo_rec);
        Intent i;

        switch (tipoActivity){
            case 1:
                //general
                i = new Intent(activity, DetalleRecursoGeneralActivity.class);
                i.putExtra("data",post);
                activity.startActivity(i);
                break;
            case 2:
                //video
                i = new Intent(activity, YoutubeActivity.class);
                i.putExtra("data",post);
                activity.startActivity(i);
                break;
            case 3:
                //audio
                i = new Intent(activity, DetalleRecursoAudio.class);
                i.putExtra("data",post);
                activity.startActivity(i);
                break;
            case 4:
                //pfd
                i = new Intent(activity, WebViewActivity.class);
                i.putExtra("link",MetodosComunes.verificarUrl(post.getLink()));
                activity.startActivity(i);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public void add(ModelPost pos)
    {
        array.add(pos);
        notifyDataSetChanged();
    }

    public void clear()
    {
        array.clear();
        notifyDataSetChanged();
    }

    public int getArrayCount() {
        return array.size(); //cantidad de items del array
    }

    //VH

    protected class RecursosHolder extends RecyclerView.ViewHolder
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

    protected class LoadingVH extends RecyclerView.ViewHolder
    {
        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
