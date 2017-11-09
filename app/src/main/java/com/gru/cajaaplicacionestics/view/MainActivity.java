package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.view.fragment.FragmentPrincipal;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String categoria; //variable para saber en que fragment estoy
    private String consulta="";
    FragmentPrincipal fragmentPrincipal;

    private int inicial=-1;
    private int primaria=-1;
    private int secundaria=-1;

    FloatingActionsMenu fab;
    com.getbase.floatingactionbutton.FloatingActionButton service,capac,recurso;
    SearchView searchView=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categoria= "Todos";

        inicial     =(int) getIntent().getExtras().get("inicial");
        primaria    =(int) getIntent().getExtras().get("primaria");
        secundaria  =(int) getIntent().getExtras().get("secundaria");

        fab = (FloatingActionsMenu)  findViewById(R.id.menu_fab);
        recurso = (com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.accionNuevoRecurso);
        service = (com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.accionServicioTecnico);
        capac   =  (com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.accionCapacitacion);

        recurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.collapse();
                Intent i = new Intent(MainActivity.this,EnviarRecursosActivity.class);
                startActivity(i);
            }
        });

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.collapse();
                Intent i = new Intent(MainActivity.this,ServicioTecnicoActivity.class);
                startActivity(i);
            }
        });

        capac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.collapse();
                Intent i = new Intent(MainActivity.this,CapacitacionActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.openDrawer(GravityCompat.START);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

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
        }
        /*else if (id == R.id.nav_semana_tic)
        {
            Intent i = new Intent(this,SemanaTicActivity.class);
            startActivity(i);
        }*/
        else if (id == R.id.nav_video)
        {
            categoria="Video";
            cambiarFragment();
        }
        else if (id == R.id.nav_audio)
        {
            categoria="Audio";
            cambiarFragment();
        }
        else if (id == R.id.nav_pd)
        {
            categoria="PD";
            cambiarFragment();
        }
        else if(id==R.id.nav_web){
            categoria="Web";
            cambiarFragment();
        }
        else if(id==R.id.nav_pdf){
            categoria="Pdf";
            cambiarFragment();
        }
        else if(id==R.id.nav_ci){
            categoria="CI";
            cambiarFragment();
        }
        else if(id==R.id.nav_novedades){
           // Intent i = new Intent(MainActivity.this,NovedadesActivity.class);
            Intent i = new Intent(MainActivity.this,NuevaSeleccioonActivity.class);
            startActivity(i);
        }
        else if(id==R.id.nav_consultar)
        {
            enviarMail();
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
        data.putString("consulta", consulta);
        data.putInt("inicial",inicial);
        data.putInt("primaria",primaria);
        data.putInt("secundaria",secundaria);
        fragmentPrincipal.setArguments(data);
        consulta="";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem menuItem=menu.findItem(R.id.action_search);
        searchView = (SearchView)menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("texto", query); //cuando doy buscar cargo el fragment pasando esl texto
                categoria="todos";
                consulta=query;
                cambiarFragment();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


         return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       if(item.getItemId()==R.id.que_es_cajatic){
           Intent i = new Intent(MainActivity.this,QueEsCajaTICActivity.class);
           startActivity(i);
       }
       return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void enviarMail()
    {
        // Reemplazamos el email por algun otro real
        String[] to = { "cajarecursostic@gmail.com" };
        String mensaje="Aporte para mejorar la app";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sugerencia caja Tic");
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email "));
    }




}

