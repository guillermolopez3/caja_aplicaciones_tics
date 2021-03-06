package com.gru.cajaaplicacionestics.view.semana_tic;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.gru.cajaaplicacionestics.R;

public class MenuST extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_info:
                    cambiarFragment("info");
                    return true;
                case R.id.nav_event:
                   cambiarFragment("agenda");
                    return true;
                case R.id.nav_place:
                   cambiarFragment("map");
                    return true;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_st);

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.nav_event);
    }

    private void cambiarFragment(String tab)
    {
        Fragment fragment = null;
        Log.e("MenuST","Cambiar fragment");
        if(tab.equals("info"))
        {
            fragment = new FragmentInfoST();
        }else if(tab.equals("agenda"))
        {
            fragment = new FragmentAgendaST();
        } else if (tab.equals("map")) {
            fragment = new FragmentMapaST();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).commit();
    }

}
