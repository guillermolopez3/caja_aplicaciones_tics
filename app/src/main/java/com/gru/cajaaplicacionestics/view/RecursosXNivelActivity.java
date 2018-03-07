package com.gru.cajaaplicacionestics.view;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.AnalitycsAplication;
import com.gru.cajaaplicacionestics.auxiliares.BottomNavigationViewHelper;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.view.fragment.FragmentRecursosNIvel;

public class RecursosXNivelActivity extends AppCompatActivity {
    private Tracker mTracker;
    SearchView searchView=null;
    Fragment recursos;

    String str_web="";
    String str_video="";
    String str_audio="";
    String str_pdf="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recursos_nivel);

        AnalitycsAplication aplication = (AnalitycsAplication) getApplication();
        mTracker = aplication.getDefaultTracker();

        MetodosComunes.showToolbar("Espacio Didáctico", true, this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);//le quito el efecto de aumento al cliquear los iconos
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        recursos = new FragmentRecursosNIvel();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, recursos)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        enviarNivel(2);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_inicial:
                            recursos = new FragmentRecursosNIvel();
                            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, recursos)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            enviarNivel(2);
                            return true;
                        case R.id.navigation_primaria:
                            recursos = new FragmentRecursosNIvel();
                            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, recursos)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            enviarNivel(3);
                            return true;
                        case R.id.navigation_secundaria:
                            recursos = new FragmentRecursosNIvel();
                            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, recursos)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            enviarNivel(4);
                            return true;
                        case R.id.navigation_superior:
                            recursos = new FragmentRecursosNIvel();
                            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, recursos)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            enviarNivel(5);
                            return true;
                    }
                    return false;
                }
            };

    private void enviarNivel(int nivel)
    {
        //2 inicial, 3 primario, 4 secundario
        Bundle data = new Bundle();
        data.putInt("nivel", nivel);
        recursos.setArguments(data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Recursos por Nivel");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
