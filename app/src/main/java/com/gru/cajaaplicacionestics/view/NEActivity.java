package com.gru.cajaaplicacionestics.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.backend.PaginacionNE;
import com.srx.widget.PullToLoadView;

public class NEActivity extends AppCompatActivity {
    PullToLoadView pullToLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ne);

        MetodosComunes.showToolbar("Doc. de Acompañamiento",true,this);

        pullToLoadView=(PullToLoadView)findViewById(R.id.recyclerNe);
        new PaginacionNE(this,pullToLoadView).iniciarPaginacion();


    }
}
