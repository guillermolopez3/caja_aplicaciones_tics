package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.gru.cajaaplicacionestics.R;

public class SeleccionActivity extends AppCompatActivity {

    Button entrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion);
        showToolbar(getResources().getString(R.string.nombre_app),false);

        entrar= (Button)findViewById(R.id.btnEntrarActivitySeleccion);


    }

    public void ActivitySeleccionEntrar(View view)
    {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void showToolbar(String tittle, boolean upButton)
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
