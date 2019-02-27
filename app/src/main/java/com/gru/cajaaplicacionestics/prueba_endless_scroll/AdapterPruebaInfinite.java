package com.gru.cajaaplicacionestics.prueba_endless_scroll;

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

public class AdapterPruebaInfinite extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Activity activity;
    private List<Item> array;

    private static final int TYPE_POST=0;
    private static final int TYPE_FOOTER = 1;

    public AdapterPruebaInfinite(Activity activity,List<Item> array){
        this.activity = activity;
        this.array = array;
    }

    @Override
    public int getItemViewType(int position) {
        if (array.get(position) instanceof PruebaModelPost){
            return TYPE_POST;
        }else if (array.get(position) instanceof Footer){
            return TYPE_FOOTER;
        }else{
            throw new RuntimeException("item viewtype unknown");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_POST){
            View view = LayoutInflater.from(activity).inflate(R.layout.card_recursos,parent,false);
            return new PostViewHolder(view);
        }else {
            View view = LayoutInflater.from(activity).inflate(R.layout.progress_footer,parent,false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PostViewHolder){

            final PruebaModelPost modelRecursos=(PruebaModelPost) array.get(position);
            PostViewHolder postViewHolder = (PostViewHolder) holder;

            postViewHolder.nombreRecurso.setText(modelRecursos.getTitle());
            String img = modelRecursos.getImage();

           try{
                if((img!=null) && (!img.equals("")) && (!img.equals("null")))
                {
                    Picasso.with(activity).load(MetodosComunes.verificarUrl(img)).into(postViewHolder.imagen);
                }
                else {
                    Picasso.with(activity).load(R.drawable.fondo_card).into(postViewHolder.imagen);
                }
            }catch (Exception e){}


        }

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    protected class PostViewHolder extends RecyclerView.ViewHolder
    {
        private TextView nombreRecurso;
        private TextView descripcionCorta;
        private ImageView imagen;
        private ImageView icono;
        private Button hastag;
        private CardView cardView;
        private Button favBtn;
        public PostViewHolder(View itemView) {
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

    protected class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
