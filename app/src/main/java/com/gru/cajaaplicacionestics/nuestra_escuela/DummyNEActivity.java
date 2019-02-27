package com.gru.cajaaplicacionestics.nuestra_escuela;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.nuestra_escuela.fragment.FragmentJornadas;
import com.gru.cajaaplicacionestics.view.fragment.TabsFragment;

// Clase que contiene un FrameLayout para cargar fragments
public class DummyNEActivity extends AppCompatActivity
{
    private String vista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_ne);

        String titulo="";

        if (getIntent().getExtras() != null){
            titulo = getIntent().getStringExtra("titulo");
            vista = getIntent().getStringExtra("vista");
        }
        MetodosComunes.showToolbar(titulo,false,this);
        cargarFragment();
    }

    private void cargarFragment()
    {
        if(vista.equals("recursos"))
        {
            //Carga el fragment con recursos digitales
            TabsFragment mFragment = new TabsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,mFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }
        else if(vista.equals("jornadas")){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentJornadas())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }
    }
}
