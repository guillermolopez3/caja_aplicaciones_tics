package com.gru.cajaaplicacionestics.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.BottomNavigationViewHelper;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.view.fragment.FragmentRecursosNIvel;

public class RecursosXNivelActivity extends AppCompatActivity {
    Fragment recursos;

    FirebaseAnalytics analytics;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recursos_nivel);

        analytics=FirebaseAnalytics.getInstance(this);
        bundle=new Bundle();

        MetodosComunes.showToolbar("Espacio Didáctico", true, this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);//le quito el efecto de aumento al cliquear los iconos
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        recursos = new FragmentRecursosNIvel();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, recursos)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        bundle.putString("nivel_espacio","inicial");
        analytics.logEvent("nivel",bundle);
        enviarNivel(2);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_inicial:
                            recursos = new FragmentRecursosNIvel();
                            bundle.putString("nivel_espacio","inicial");
                            analytics.logEvent("nivel",bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, recursos)
                                    .commit();
                            enviarNivel(2);
                            return true;
                        case R.id.navigation_primaria:
                            recursos = new FragmentRecursosNIvel();
                            bundle.putString("nivel_espacio","primario");
                            analytics.logEvent("nivel",bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, recursos)
                                    .commit();
                            enviarNivel(3);
                            return true;
                        case R.id.navigation_secundaria:
                            recursos = new FragmentRecursosNIvel();
                            bundle.putString("nivel_espacio","secundario");
                            analytics.logEvent("nivel",bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, recursos)
                                    .commit();
                            enviarNivel(4);
                            return true;
                        /*case R.id.navigation_superior:
                            recursos = new FragmentRecursosNIvel();
                            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, recursos)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            enviarNivel(5);
                            return true;*/
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


}
