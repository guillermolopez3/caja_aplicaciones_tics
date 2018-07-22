package com.gru.cajaaplicacionestics.view.semana_tic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterMenuImagenTitulo;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.model.ImagenTituloModel;

import java.util.ArrayList;

public class MenuSemanaTicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_semana_tic);

        MetodosComunes.showToolbar("Semana Tic",false,this);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayout.VERTICAL,false));
        AdapterMenuImagenTitulo adapter = new AdapterMenuImagenTitulo(lista(),this,true);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<ImagenTituloModel> lista()
    {
        ArrayList<ImagenTituloModel> list = new ArrayList<>();

        list.add(new ImagenTituloModel("","- Mapa del evento -"));
        list.add(new ImagenTituloModel("","- Agenda -"));
        //list.add(new ImagenTituloModel("","Disertantes"));
        list.add(new ImagenTituloModel("","- Noticias -"));

        return list;
    }



}
