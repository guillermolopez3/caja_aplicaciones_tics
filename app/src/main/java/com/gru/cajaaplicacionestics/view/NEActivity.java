package com.gru.cajaaplicacionestics.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.AnalitycsAplication;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.backend.PaginacionNE;
import com.srx.widget.PullToLoadView;

public class NEActivity extends AppCompatActivity {
    private Tracker mTracker;
    PullToLoadView pullToLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuestra_escuela_menu);

        /*AnalitycsAplication aplication = (AnalitycsAplication) getApplication();
        mTracker = aplication.getDefaultTracker();

        MetodosComunes.showToolbar("Doc. de Acompañamiento",true,this);

        pullToLoadView=(PullToLoadView)findViewById(R.id.recyclerNe);

        String json = getResources().getString(R.string.JSON_NE_DA);
        new PaginacionNE(this,pullToLoadView,false,json).iniciarPaginacion();*/


    }

    @Override
    protected void onResume() {
        super.onResume();
       /* mTracker.setScreenName("NE");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/
    }
}
