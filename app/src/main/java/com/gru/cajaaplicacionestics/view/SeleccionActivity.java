package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.gru.cajaaplicacionestics.R;

public class SeleccionActivity extends AppCompatActivity {

    Button entrar;
    CheckBox inicial,primaria,pd,secundaria,ci;
    boolean pasar; //variable para comprobar que haya un chk seleccionado
    boolean chkInicial,chkPrimaria,chkPD,chkSecundaria,chkCI; //variables para saber que selecciono para traer los datos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion);
        showToolbar(getResources().getString(R.string.nombre_app),false);

        entrar= (Button)findViewById(R.id.btnEntrarActivitySeleccion);
        inicial= (CheckBox) findViewById(R.id.seleccionChkInicial);
        primaria= (CheckBox) findViewById(R.id.seleccionChkPrimaria);
        pd= (CheckBox) findViewById(R.id.seleccionChkPD);
        secundaria= (CheckBox) findViewById(R.id.seleccionChkSecundario);
        ci= (CheckBox) findViewById(R.id.seleccionChkCI);


    }

    private void estaSeleccionadoNivel() //verifico que haya un chk seleccionado
    {
        pasar=false;

        if(inicial.isChecked()){
            pasar=true;
            chkInicial=true;}
        if(primaria.isChecked()){
            pasar=true;
            chkPD=true;}
        if(pd.isChecked()){
            pasar=true;
            chkPD=true;}
        if(secundaria.isChecked()){
            pasar=true;
            chkSecundaria=true;}
        if(ci.isChecked()){
            pasar=true;
            chkCI=true;}
    }

    public void ActivitySeleccionEntrar(View view)
    {
        estaSeleccionadoNivel();
        if(pasar==true)
        {
            Intent i = new Intent(this,MainActivity.class);
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

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
