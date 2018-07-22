package com.gru.cajaaplicacionestics.view.ne;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.view.fragment.NEFragment;

public class NEFragmentGralActivity extends AppCompatActivity
{
    private String seccion,titulo="";
    private Fragment fragment;
    private Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ne_fragment_gral);

        if(getIntent().getExtras() != null)
        {
            seccion = getIntent().getStringExtra("seccion");
            titulo  = getIntent().getStringExtra("titulo");
            MetodosComunes.showToolbar(titulo,false,this);

            cargarFragment();
        }
    }

    private void cargarFragment()
    {
        if(seccion.equals("circulo"))
        {
            fragment = new NEFragment();
            bundle = new Bundle();
            bundle.putString("seccion","circulo");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).commit();
        }
        else {
            Log.e("seccion","entro al else");
            fragment = new NEFragment();
            bundle = new Bundle();
            bundle.putString("seccion",seccion);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).commit();
        }
    }
}
