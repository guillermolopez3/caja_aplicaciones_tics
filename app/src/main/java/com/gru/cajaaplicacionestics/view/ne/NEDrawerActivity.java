package com.gru.cajaaplicacionestics.view.ne;

import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.view.fragment.NEFragment;
import com.gru.cajaaplicacionestics.view.fragment.TabsFragment;

public class NEDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NEFragment fragment;
    private String menuMostrar=""; //defino que menú muestro dependiendo de donde vengo
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nedrawer);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menuMostrar = getIntent().getStringExtra("menu_mostrar");

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        View vi = navigationView.getHeaderView(0); //obtengo el header para el titulo
        TextView txtNav = vi.findViewById(R.id.navHeaderTitulo); //txt del header del nav


        navigationView.getMenu().clear();

        if(menuMostrar.equals("jornada"))
        {
            navigationView.inflateMenu(R.menu.activity_nedrawer_drawer); //seteo el menu
            txtNav.setText("Jornadas Institucionales");
        }
        else if(menuMostrar.equals("ateneos"))
        {
            navigationView.inflateMenu(R.menu.menu_ateneos);
            txtNav.setText("Ateneos didácticos");
        }
        else if(menuMostrar.equals("formacion")){
            navigationView.inflateMenu(R.menu.menu_formacion);
            txtNav.setText("Formación Situada 2019");
        }

        navigationView.setNavigationItemSelectedListener(this);

        cargarFragmentAlIniciar();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish(); //cierro el activity si el drawer esta cerrado y se presiona atras
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        bundle = new Bundle();

        if(menuMostrar.equals("jornada"))
        {
            cargarMenuJornada(id);
        }
        else if(menuMostrar.equals("ateneos"))
        {
            cargarMenuAteneo(id);
        }
        else if(menuMostrar.equals("formacion")){
            cargarMenuFormacion(id);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cargarFragmentAlIniciar()
    {
        fragment = new NEFragment();
        bundle = new Bundle();

        if(menuMostrar.equals("jornada"))
        {
            bundle.putString("seccion","cronograma");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Cronograma 2018");
        }else if(menuMostrar.equals("ateneos"))
        {
            bundle.putString("seccion","ateneo_modelo_aval");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Ateneo Modelo Aval");
        }else if(menuMostrar.equals("formacion"))
        {
            bundle.putString("seccion","proyectando_2019");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Proyectando el año 2019");
        }
    }

    private void cargarMenuJornada(int id)
    {
        //Poner formacion situada

       /* if (id == R.id.nav_cronograma) {
            fragment = new NEFragment();
            bundle.putString("seccion","cronograma");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Cronograma 2018");
        } else  if (id == R.id.nav_jornadas) {
            fragment = new NEFragment();
            bundle.putString("seccion","jornada");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Organizador de jornadas institucionales");

        } else if (id == R.id.nav_orientacion) {
            fragment = new NEFragment();
            bundle.putString("seccion","orientacion");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Orientaciones administrativas");

        } else if (id == R.id.nav_recomendacion) {
            fragment = new NEFragment();
            bundle.putString("seccion","recomendaciones");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Recomendaciones para el 2018");
        } else */if (id == R.id.mat_apoyo) {
            TabsFragment mFragment = new TabsFragment();
            /*bundle.putString("seccion","recomendaciones");
            fragment.setArguments(bundle);*/
            getSupportFragmentManager().beginTransaction().replace(R.id.container,mFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Materiales de apoyo");

        }
    }

    private void cargarMenuAteneo(int id)
    {
        if (id == R.id.nav_ateneo_aval) {
            fragment = new NEFragment();
            bundle.putString("seccion","ateneo_modelo_aval");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Ateneo Modelo Aval");
        } else if (id == R.id.nav_ateneo_activ) {
            fragment = new NEFragment();
            bundle.putString("seccion","ateneo_actividad");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Actividades Obligatorias");

        } else if (id == R.id.nav_ateneo_memo) {
            fragment = new NEFragment();
            bundle.putString("seccion", "ateneo_memo");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Memo 15-18 Ateneo Didáctico");
        }

    }

    private void cargarMenuFormacion(int id)
    {
        if (id == R.id.nav_formacion_intro) {
            fragment = new NEFragment();
            bundle.putString("seccion","proyectando_2019");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Proyectando el año 2019");
        } else if (id == R.id.nav_formacion_aspectos) {
            fragment = new NEFragment();
            bundle.putString("seccion","formacion_aspectos");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Aspectos Priorizados");

        } else if (id == R.id.nav_formacion_recomendacion) {
            fragment = new NEFragment();
            bundle.putString("seccion", "formacion_recomendacion");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Recomendaciones");
        }
        else if (id == R.id.nav_formacion_cronograma) {
            fragment = new NEFragment();
            bundle.putString("seccion", "cronograma_formacion");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Cronograma de Jornadas Institucionales");
        }

    }
}
