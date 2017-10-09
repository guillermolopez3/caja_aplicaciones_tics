package com.gru.cajaaplicacionestics.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SeleccionActivity extends AppCompatActivity {

    Button entrar;
    CoordinatorLayout coordinatorLayout;
    CheckBox inicial,primaria,secundaria,semana_tic;
    ImageView img_primaria,img_secundaria, img_inicial,img_sem_tic;
    boolean pasar; //variable para comprobar que haya un chk seleccionado
    boolean chkInicial,chkPrimaria,chkPD,chkSecundaria,chkCI; //variables para saber que selecciono para traer los datos

    //variables para saber que nivel selecciono
    int varini=-1;
    int varprim=-1;
    int varsec=-1;

    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion);
        showToolbar(getResources().getString(R.string.nombre_app),false);

        //abrirDialog(this);

        entrar= (Button)findViewById(R.id.btnEntrarActivitySeleccion);
        inicial= (CheckBox) findViewById(R.id.seleccionChkInicial);
        primaria= (CheckBox) findViewById(R.id.seleccionChkPrimaria);
        secundaria= (CheckBox) findViewById(R.id.seleccionChkSecundario);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorSeleccion);

        img_inicial= (ImageView) findViewById(R.id.imgEdInicial);
        img_inicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               seleccionarChk(inicial);
            }
        });

        img_primaria=(ImageView) findViewById(R.id.imgEdPrimaria);
        img_primaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarChk(primaria);
            }
        });

        img_secundaria= (ImageView) findViewById(R.id.imgEdSecundaria);
        img_secundaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarChk(secundaria);
            }
        });


    }

    private void estaSeleccionadoNivel() //verifico que haya un chk seleccionado
    {
        pasar=false;

        if(inicial.isChecked()){
            pasar=true;
            chkInicial=true;
            varini=1;} //valor nivel inicial en la bd
        if(primaria.isChecked()){
            pasar=true;
            chkPD=true;
            varprim=2; }//valor primaria en bd
        if(secundaria.isChecked()){
            pasar=true;
            chkSecundaria=true;
            varsec=3;
        }

    }

    private void seleccionarChk(CheckBox chk)
    {
        if(chk.isChecked()==true)
        {
            chk.setChecked(false);
        }
        else {
            chk.setChecked(true);
        }
    }

    public void ActivitySeleccionEntrar(View view)
    {
        estaSeleccionadoNivel();
        if(pasar==true)
        {
            Intent i = new Intent(this,MainActivity.class);
            i.putExtra("inicial",varini);
            i.putExtra("primaria",varprim);
            i.putExtra("secundaria",varsec);
            startActivity(i);
        }
        else
        {
           // Toast.makeText(view.getContext(),"Seleccione un nivel",Toast.LENGTH_LONG).show();
            Snackbar.make(view,"Seleccione un nivel",Snackbar.LENGTH_SHORT).show();
            Log.e("error","selecciona uno");
        }

    }

    public void showToolbar(String tittle, boolean upButton)
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private void abrirDialog(Context c)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater inflater = getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_semana_tic,null);

        builder.setView(view);

        Button no = (Button) view.findViewById(R.id.dialogNo);
        Button si = (Button) view.findViewById(R.id.dialogSi);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SeleccionActivity.this, EspecialSemanaTicActivity.class);
                startActivity(i);
            }
        });

    }

    private void obtenerUrlVideoVivo() {
        final ProgressDialog dialog=new ProgressDialog(SeleccionActivity.this);
        dialog.setMessage("Cargando datos...");
        dialog.show();
        Log.e("entrando", "obtener");
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://faltaequipoxyz.000webhostapp.com/obtener_url_video.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("post");

                            JSONObject o = array.getJSONObject(0);
                            Post post = new Post();
                            post.setNombreRecurso("Semana Tic");
                            post.setHastag("semana tic");
                            post.setDescripcion("Todo sobre la semana tic");

                            url = o.getString("url_video");
                            post.setVideo_id(url);
                            Intent i = new Intent(SeleccionActivity.this,YoutubeActivity.class);
                            i.putExtra("data",post);
                            startActivity(i);
                            Log.e("url video ob", url);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
