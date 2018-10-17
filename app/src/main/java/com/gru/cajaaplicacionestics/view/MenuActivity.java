package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterMenu;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.backend.VolleySingleton;
import com.gru.cajaaplicacionestics.model.ModelMenu;
import com.gru.cajaaplicacionestics.view.semana_tic.MenuSemanaTicActivity;
import com.gru.cajaaplicacionestics.view.semana_tic.ObtenerRecursosST;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

public class MenuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ModelMenu> array;
    private FirebaseAnalytics firebaseAnalytics;

    private static ArrayList<ObtenerRecursosST.PopUpModel> list_pp;
    SharedPreferences.Editor editor;

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

        //dialogST();
       // obtenerPopup();



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
    }



    //SEMANA TIC

    private void obtenerPopup()
    {
        list_pp = new ArrayList<>();
        cargarListaPopUp();
    }

    private void obtenerUrlImagen() //muestro la imagen y verifico si vio o no el popup
    {
        try{
            SharedPreferences pref = getSharedPreferences("popup",MODE_PRIVATE);
            editor = pref.edit();
            boolean band = pref.getBoolean("bandera",false);
            String fecha = pref.getString("fecha","12-6-2018");


            Date fecha_guardada = MetodosComunes.convertirStringFecha(fecha);
            Date fecha_internet = MetodosComunes.convertirStringFecha(list_pp.get(0).getFecha_hasta());

            if(fecha_guardada.compareTo(fecha_internet) == 0) //si la fecha guardad es igual a la de internet
            {
                if(!band) //veo si ya vio el popup, si no se lo muestro
                {
                    editor.putString("fecha",list_pp.get(0).getFecha_hasta());
                    editor.commit();
                    String url = list_pp.get(0).getUrl();
                    dialogST(url);
                }
            }
            else {
                editor.putString("fecha",list_pp.get(0).getFecha_hasta());
                editor.commit();
                String url = list_pp.get(0).getUrl();
                dialogST(url);
            }
        }
        catch (Exception e){}
    }

    private AlertDialog dialogST(String url)
    {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sem_tic_img,null);
        dialog.setView(view);

        ImageView img = view.findViewById(R.id.imgST);
        Button si = view.findViewById(R.id.btnSemTicSi);
        ImageButton no = view.findViewById(R.id.btnSemTicNo);

        Picasso.with(this).load(url).into(img);

        final AlertDialog alertDialog = dialog.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startActivity(new Intent(MenuActivity.this,MenuSemanaTicActivity.class));
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("bandera",true);
                editor.commit();
                alertDialog.dismiss();
            }
        });
        return dialog.create();
    }

    private void cargarListaPopUp()
    {
        StringRequest request;
        VolleySingleton.getInstancia(this).
                addToRequestQueue(request=new StringRequest(Request.Method.GET,
                        "http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/popup.json",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    //de esta manera me toma los acentos
                                    response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray array= jsonObject.getJSONArray("popup");

                                    for(int i=0; i< array.length();i++)
                                    {
                                        JSONObject o = array.getJSONObject(i);
                                        Log.e("json", o.toString());
                                        ObtenerRecursosST.PopUpModel pu = new ObtenerRecursosST.PopUpModel(
                                                o.getString("fecha_desde"),
                                                o.getString("fecha_hasta"),
                                                o.getString("url_img")
                                        );
                                        list_pp.add(pu);
                                        obtenerUrlImagen();
                                    }

                                } catch (JSONException e) {
                                   // Log.e("error",e.getMessage());
                                   // e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    //e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Log.e("error volley",error.getMessage());
                    }
                }));
    }


}
