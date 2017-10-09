package com.gru.cajaaplicacionestics.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.view.fragment.FragmentSalaA;
import com.gru.cajaaplicacionestics.view.fragment.FragmentSalaB;
import com.gru.cajaaplicacionestics.view.fragment.FragmentSalaC;
import com.gru.cajaaplicacionestics.view.fragment.FragmentSalaD;
import com.gru.cajaaplicacionestics.view.fragment.FragmentSalaE;

public class GrillaSemanaTicActivity extends AppCompatActivity {

    FragmentSalaA salaA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grilla_semana_tic);

        showToolbar("Grilla Semana Tic",true);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        salaA = FragmentSalaA.getInstancia();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,salaA)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_sala_a:
                    salaA = FragmentSalaA.getInstancia();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,salaA)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                    return true;
                case R.id.navigation_sala_b:
                    FragmentSalaB guiaFragment = new FragmentSalaB();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,guiaFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                    return true;
                case R.id.navigation_sala_c:
                    FragmentSalaC oportunidadFragment = new FragmentSalaC();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,oportunidadFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                    return true;
                case R.id.navigation_sala_d:
                    FragmentSalaD sala_d = new FragmentSalaD();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,sala_d)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                    return true;
                case R.id.navigation_sala_e:
                    FragmentSalaE sala_e = new FragmentSalaE();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,sala_e)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                    return true;
            }
            return false;
        }

    };

    public void showToolbar(String tittle, boolean upButton)
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}
