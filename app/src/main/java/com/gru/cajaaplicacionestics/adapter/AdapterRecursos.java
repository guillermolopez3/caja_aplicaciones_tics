package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.content.Context;
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


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.model.ModelRecursos;
import com.gru.cajaaplicacionestics.model.Post;
import com.gru.cajaaplicacionestics.view.DetallaRecursoActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guill on 22/05/2017.
 */

public class AdapterRecursos extends RecyclerView.Adapter<AdapterRecursos.RecursosHolder>
{
    private List<Post> array;
    private Activity activity;

    public AdapterRecursos(Activity ac, List<Post> item)
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
        Post modelRecursos= array.get(position);
        holder.nombreRecurso.setText(modelRecursos.getNombreRecurso());
        Log.d("nombre",modelRecursos.getNombreRecurso());
        holder.descripcionCorta.setText(modelRecursos.getDescripcionCorta());
        holder.hastag.setText(modelRecursos.getHastag());

        Picasso.with(activity).load(R.drawable.fondo_web);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, DetallaRecursoActivity.class);
                activity.startActivity(i);
            }
        });

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
