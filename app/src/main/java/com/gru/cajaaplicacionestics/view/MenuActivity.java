package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterMenu;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.backend.Paginacion;
import com.gru.cajaaplicacionestics.model.ModelMenu;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ModelMenu> array;
    SearchView searchView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView= (RecyclerView)findViewById(R.id.menuRecycler);

        llenarArray();

        AdapterMenu adapterMenu = new AdapterMenu(this,array);
        recyclerView.setAdapter(adapterMenu);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MetodosComunes.showToolbar(getResources().getString(R.string.nombre_app),false,this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_search){
            Intent i = new Intent(MenuActivity.this,PostActivity.class);
            i.putExtra("seleccion","search");
            i.putExtra("titulo","Busqueda");
            startActivity(i);
            Log.e("menu","apreto");
        }
        return super.onOptionsItemSelected(item);
    }

    private void llenarArray() //agrego las imagenes del menu
    {
        array = new ArrayList<>();
        array.add(new ModelMenu(R.drawable.primaria_digital));
        array.add(new ModelMenu(R.drawable.conectar_igualdad));
        array.add(new ModelMenu(R.drawable.nuestra_escuela));
        array.add(new ModelMenu(R.drawable.espacio_didactico));
        array.add(new ModelMenu(R.drawable.novedades));
        array.add(new ModelMenu(R.drawable.audiovisuales));
    }
}
