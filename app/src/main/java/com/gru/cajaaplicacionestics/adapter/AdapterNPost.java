package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.backend.FavoritosBackend;
import com.gru.cajaaplicacionestics.view.WebViewActivity;
import com.gru.cajaaplicacionestics.model.ModelPost;
import com.gru.cajaaplicacionestics.view.DetalleRecursoGeneralActivity;
import com.gru.cajaaplicacionestics.view.DetalleRecursoAudio;
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
    private boolean muestro_fav = false;

    public AdapterNPost(Activity a,boolean muestro_fav)
    {
        activity=a;
        array=new ArrayList<>();
        this.muestro_fav = muestro_fav;
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

        definirIcono(modelRecursos.getId_tipo_activity(),holder.icono);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDetalleCorrespondiente(modelRecursos.getId_tipo_activity(),modelRecursos);
                MetodosComunes.enviarPostSeleccionado(String.valueOf(modelRecursos.getId()),activity);
            }
        });

        if(muestro_fav){
            if(MetodosComunes.estaLogueado())
            {
                holder.favBtn.setVisibility(View.VISIBLE);
                manejoDeFavoritos(holder.favBtn,modelRecursos);
            }else {
                holder.favBtn.setVisibility(View.GONE);
            }
        }else {
            holder.favBtn.setVisibility(View.GONE);
        }
    }

    //manejo lo relacionado al btn fav, si viene q es fav desde BD lo pinto, si hago click lo agrego o no a fav
    private void manejoDeFavoritos(final Button bnFav, final ModelPost model)
    {
        final Drawable star_empty = activity.getResources().getDrawable(R.drawable.star_empty);
        final Drawable star_full = activity.getResources().getDrawable(R.drawable.star);

        if(model.getFav() == null || !model.getFav())
        {
            bnFav.setCompoundDrawablesRelativeWithIntrinsicBounds(star_empty,null,null,null);
        }else {

            bnFav.setCompoundDrawablesRelativeWithIntrinsicBounds(star_full,null,null,null);
        }

        bnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable x = bnFav.getCompoundDrawables()[0];

                if(x.getConstantState().equals(star_full.getConstantState()))
                {
                    bnFav.setCompoundDrawablesRelativeWithIntrinsicBounds(star_empty,null,null,null);
                    model.setFav(false);
                    FavoritosBackend.quitarFav(model.getId(),activity);
                    Log.e("fav","quito");
                }else {
                    bnFav.setCompoundDrawablesRelativeWithIntrinsicBounds(star_full,null,null,null);
                    model.setFav(true);
                    FavoritosBackend.insertarFav(model.getId(),activity);
                    Log.e("fav","agrego");
                }

            }
        });
    }

    private void definirIcono(int tipoActivity,ImageView imagen)
    {
        RelativeLayout.LayoutParams relativeLayout = (RelativeLayout.LayoutParams)imagen.getLayoutParams();
        if(tipoActivity==2){
            //ICONO DEL VIDEO EN EL CENTRO
            Log.e("posic icono","centro");
            relativeLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,0);
            relativeLayout.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
            imagen.setLayoutParams(relativeLayout);
        }
        else {
            //EL RESTO APARECE A LA DERECHA
            Log.e("posic icono","derecha");
            relativeLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
            relativeLayout.addRule(RelativeLayout.CENTER_IN_PARENT,0);
            imagen.setLayoutParams(relativeLayout);
        }
        imagen.setVisibility(View.VISIBLE);
        switch (tipoActivity){
            case 1:
                imagen.setVisibility(View.INVISIBLE);
                //general
                break;
            case 2:
                //video
                Picasso.with(activity).load(R.drawable.play).into(imagen);
               break;
            case 3:
                //audio
                Picasso.with(activity).load(R.drawable.audio).into(imagen);
                break;
            case 4:
                //pfd
                Picasso.with(activity).load(R.drawable.acrobat).into(imagen);
                break;

        }

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
