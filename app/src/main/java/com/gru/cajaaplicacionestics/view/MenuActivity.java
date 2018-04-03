package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterMenu;
import com.gru.cajaaplicacionestics.model.ModelMenu;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ModelMenu> array;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        firebaseAnalytics= FirebaseAnalytics.getInstance(this);

        recyclerView= findViewById(R.id.menuRecycler);

        llenarArray();

        AdapterMenu adapterMenu = new AdapterMenu(this,array,firebaseAnalytics);
        recyclerView.setAdapter(adapterMenu);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null) //metodo para poner el logo del ministerio en el actionbar
        {
            Display display = getWindowManager().getDefaultDisplay(); //obtengo datos de la resolucion de la pantalla
            Point point = new Point();
            display.getSize(point);
            int ancho = (int) (point.x /1.6); //obtengo el ancho de la pantalla y lo divido para obtener un ancho aprox de 430 q es el ancho q el logo se ve bien
            int alto = (int) (convertDpToPixel(56) / 1.6); //convierto los 56 px del ancho del toolbar y lo divido para obtener aprox 100 px

            Drawable drawable = getResources().getDrawable(R.drawable.logo_completo); //obtengo el logo del minsterio
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            Drawable newDrawable = new BitmapDrawable(getResources(),Bitmap.createScaledBitmap(bitmap,ancho,alto,true)); //lo muestro con sus medidas

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(newDrawable);
        }

        getSupportActionBar().setTitle("");



    }

    //metodo para convertir los dp a pixel
    public float convertDpToPixel(float dp) {
        float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        if(item.getItemId()==R.id.action_search){
            Intent i = new Intent(MenuActivity.this,PostActivity.class);
            i.putExtra("seleccion","search");
            i.putExtra("titulo","Busqueda");
            bundle.putString("nombre_pantalla","buscar");
            firebaseAnalytics.logEvent("pantalla",bundle);
            startActivity(i);
            Log.e("menu","apreto");
        }
        return super.onOptionsItemSelected(item);
    }

    private void llenarArray() //agrego las imagenes del menu
    {
        array = new ArrayList<>();
        array.add(new ModelMenu(R.drawable.novedades));
        array.add(new ModelMenu(R.drawable.espacio_didactico));
        array.add(new ModelMenu(R.drawable.primaria_digital));
        array.add(new ModelMenu(R.drawable.conectar_igualdad));
        array.add(new ModelMenu(R.drawable.nuestra_escuela));
        array.add(new ModelMenu(R.drawable.audiovisuales));
    }


}
