package com.gru.cajaaplicacionestics.view.semana_tic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.view.fragment.NEFragment;

public class MenuST extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_info:
                    cargarFragmentInfo();
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.nav_event:
                    cargarFragmentAgenda();
                   // mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.nav_place:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_st);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void cargarFragmentInfo()
    {
        Fragment fragment = new NEFragment();
        Bundle bundle = new Bundle();
        bundle.putString("seccion","noticia");
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).commit();
    }

    private void cargarFragmentAgenda()
    {
        Fragment fragment = new FragmentAgendaST();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).commit();
    }

}
