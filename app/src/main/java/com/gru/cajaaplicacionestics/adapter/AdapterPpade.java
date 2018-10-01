package com.gru.cajaaplicacionestics.adapter;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.model.ModelPpade;
import com.gru.cajaaplicacionestics.view.WebClieActivity;
import com.gru.cajaaplicacionestics.view.WebViewActivity;

import java.util.ArrayList;

public class AdapterPpade extends RecyclerView.Adapter<AdapterPpade.PPadeHolder>
{
    Activity activity;
    ArrayList<ModelPpade> list;
    FragmentManager fragmentManager;

    public AdapterPpade(Activity activity, ArrayList<ModelPpade> list,FragmentManager manager)
    {
        this.activity = activity;
        this.list = list;
        this.fragmentManager = manager;
    }

    @NonNull
    @Override
    public PPadeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_ppade_list,viewGroup,false);
        return new PPadeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PPadeHolder pPadeHolder, int i) {
        final ModelPpade model = list.get(i);

        pPadeHolder.text.setText(model.getTitle());

        if(model.getId_activity()==4)
        {
            pPadeHolder.imagen.setImageResource(R.drawable.pdf_icon);
        }
        else if(model.getId_activity()==5)
        {
            pPadeHolder.imagen.setImageResource(R.drawable.ic_web);
        }

        pPadeHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.getId_activity()==4){
                    Intent i = new Intent(activity, WebViewActivity.class);
                    i.putExtra("link",model.getUrl_link());
                    activity.startActivity(i);

                }else if(model.getId_activity()==5)
                {
                    Intent i = new Intent(activity, WebClieActivity.class);
                    i.putExtra("link",model.getUrl_link());
                    i.putExtra("titulo", model.getTitle());
                    activity.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PPadeHolder extends RecyclerView.ViewHolder
    {
        LinearLayout linearLayout;
        ImageView imagen;
        TextView text;
        public PPadeHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearItemPpade);
            imagen = itemView.findViewById(R.id.imgItemPpade);
            text = itemView.findViewById(R.id.txtTituloPpade);
        }
    }
}
