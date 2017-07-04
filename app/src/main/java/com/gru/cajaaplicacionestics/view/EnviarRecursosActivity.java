package com.gru.cajaaplicacionestics.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.gru.cajaaplicacionestics.R;

public class EnviarRecursosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_recursos);
        showToolbar("Compartir Recurso",true);
    }

    public void showToolbar(String tittle, boolean upButton)
    {
        //uso appcompatacty... xq la actividad que maneja esto tiene soporte y es de este tipo
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }
}
