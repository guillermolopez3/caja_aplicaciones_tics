package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterMenu;
import com.gru.cajaaplicacionestics.model.ModelMenu;
import com.gru.cajaaplicacionestics.view.notificaciones.NotificacionesActivity;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ModelMenu> array;
    private boolean abroNotifPush=false; //variable para ver si hice click en una notif push
    private FloatingActionButton fab;

    public static int MILISEGUNDOS_ESPERA = 4000;
    private boolean fab_visible=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView= findViewById(R.id.menuRecycler);
        fab = findViewById(R.id.fab);

        llenarArray();

        AdapterMenu adapterMenu = new AdapterMenu(this,array);
        recyclerView.setAdapter(adapterMenu);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*if(getSupportActionBar()!=null) //metodo para poner el logo del ministerio en el actionbar
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

        }*/
        getSupportActionBar().setTitle("");

        if(getIntent().getExtras()!= null)
        {
            abroNotifPush = getIntent().getBooleanExtra("push",false);
            if(abroNotifPush){
                String url = "http://www.igualdadycalidadcba.gov.ar/SIPEC-CBA/capacitacion-v2/capacitacion.php?opt=estatal";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        }
        ocultarFab();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, NotificacionesActivity.class));
            }
        });
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
            startActivity(i);
        }
        else if(item.getItemId() == R.id.nav_perfil)
        {
            startActivity(new Intent(MenuActivity.this,ProfileActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void llenarArray() //agrego las imagenes del menu
    {
        array = new ArrayList<>();
        array.add(new ModelMenu(R.drawable.novedades));
        array.add(new ModelMenu(R.drawable.espacio_didactico));
        array.add(new ModelMenu(R.drawable.aprender_conectados));
        array.add(new ModelMenu(R.drawable.nuestra_escuela));
        array.add(new ModelMenu(R.drawable.audiovisuales));
        array.add(new ModelMenu(R.drawable.programa_docentesyescuela));
        array.add(new ModelMenu(R.drawable.favoritos));
        array.add(new ModelMenu(R.drawable.qrlogo));
    }

    //pasados los 3.5 seg se oculta
    private void ocultarFab()
    {
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fab.hide();
                fab_visible=false;
            }
        },MILISEGUNDOS_ESPERA);
    }

    //evento al hacer click en la pantalla, dependiendo si se esta mostrando el fab o no es q se lanza el ocultar
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(fab_visible==false)
        {
            Log.e("entra al if","false");
            fab.show();
            ocultarFab();
            fab_visible=true;
        }
        return super.dispatchTouchEvent(ev);
    }

}
