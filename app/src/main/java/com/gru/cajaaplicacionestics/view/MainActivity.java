package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.view.fragment.FragmentPrincipal;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingSearchView mSearchView;
    private String categoria; //variable para saber en que fragment estoy
    private String consulta="";
    FragmentPrincipal fragmentPrincipal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        categoria= "todos";

        mSearchView = (FloatingSearchView)findViewById(R.id.floating_search_view);

        mSearchView.setOnLeftMenuClickListener(new FloatingSearchView.OnLeftMenuClickListener() {
            @Override
            public void onMenuOpened() {

            }

            @Override
            public void onMenuClosed() {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mSearchView.attachNavigationDrawerToMenuButton(drawer);

        drawer.openDrawer(GravityCompat.START);

        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_settings) {
                    Intent i = new Intent(MainActivity.this,AcercadeActivity.class);
                    startActivity(i);
                }
            }
        });

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                consulta=newQuery;
                cambiarFragment();
            }

        });



       /* ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemTextAppearance(R.style.texto_slide);

       cambiarFragment();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_back)
        {
           Intent i = new Intent(this,SeleccionActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_video)
        {
            categoria="video";
            cambiarFragment();
        }
        else if (id == R.id.nav_office)
        {
            categoria="office";
            cambiarFragment();
        }

       DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cambiarFragment() //segun la categoría seleccionada, es el fragment mostrado
    {
        fragmentPrincipal=new FragmentPrincipal();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentPrincipal)
                .commit();
        enviarCategoria();
    }

    private void enviarCategoria()
    {
        Bundle data = new Bundle();
        data.putString("categoria", categoria);
        data.putString("consulta", categoria);
        fragmentPrincipal.setArguments(data);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}
