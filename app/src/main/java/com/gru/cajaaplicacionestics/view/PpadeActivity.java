package com.gru.cajaaplicacionestics.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.view.FragmentPpade.FragmentCohortes;
import com.gru.cajaaplicacionestics.view.FragmentPpade.FragmentEquipo;
import com.gru.cajaaplicacionestics.view.FragmentPpade.FragmentPresentacion;

public class PpadeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nedrawer);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        View vi = navigationView.getHeaderView(0); //obtengo el header para el titulo
        TextView txtNav = vi.findViewById(R.id.navHeaderTitulo); //txt del header del nav
        txtNav.setText("PP-ADE");
        ImageView imagen = vi.findViewById(R.id.imageView);
        imagen.setImageResource(R.drawable.logo_redondo_ppade);

        LinearLayout linear = vi.findViewById(R.id.linearCabecera);
        linear.setBackgroundResource(R.drawable.side_nav_bar_ppade);


        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.menu_ppade); //seteo el menu
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new FragmentPresentacion();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
        getSupportActionBar().setTitle("Presentación");
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        bundle = new Bundle();
        switch (menuItem.getItemId())
        {
            case R.id.nav_presentacion:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragmentPresentacion())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
                getSupportActionBar().setTitle("Presentación");
                break;
            case R.id.nav_contacto:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragmentEquipo())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
                getSupportActionBar().setTitle("Equipo y vía de contacto");
                break;
            case R.id.nav_cuarta:
                abrirFragment("cuarta_co","Cuarta cohorte");
                break;
            case R.id.nav_tercero:
                abrirFragment("tercera_co","Tercera cohorte");
                break;
            case R.id.nav_segundo:
                abrirFragment("segunda_co","Segunda cohorte");
                break;
            case R.id.nav_primera:
                abrirFragment("primera_co","Primera cohorte");
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void abrirFragment(String seccion, String titulo)
    {
        Fragment fragment = new FragmentCohortes();
        bundle.putString("seccion",seccion);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
        getSupportActionBar().setTitle(titulo);
    }

}
