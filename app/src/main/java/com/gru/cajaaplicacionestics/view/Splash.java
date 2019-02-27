package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.view.prueba.Prueba;

import java.util.Timer;
import java.util.TimerTask;


public class Splash extends AppCompatActivity
{
    private boolean estaAutenticado = false; //variable para saber si esta logueado el usuario

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        /*if(getIntent().hasExtra("valor") && getIntent().getStringExtra("valor").equals("abrir_activity")){
            startActivity(new Intent(this,Prueba.class));
            Toast.makeText(this, "recibo el pending", Toast.LENGTH_LONG).show();
            //finish();
        }*/

        //valido si el usuario esta logueado
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseMessaging.getInstance().subscribeToTopic("informacion");
        long splashDelay = 3000;

        if(firebaseAuth.getCurrentUser() != null){
            estaAutenticado = true;
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run()
            {
                if(sharePref()){
                    irAmenu();
                }else {
                    if(estaAutenticado){
                        irAmenu();
                    }else {
                        irLogin();
                    }

                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, splashDelay);//Pasado los 3 segundos dispara la tarea
    }

    private void irAmenu()
    {
        startActivity(new Intent(Splash.this,MenuActivity.class));
        finish();
    }

    private void irLogin()
    {
        startActivity(new Intent(Splash.this,LoginActivity.class));
        finish();
    }

    //compueba si el usuario decidió ingresar en modo anonimo
    private boolean sharePref(){
        SharedPreferences pref = getSharedPreferences("anon",MODE_PRIVATE);
        return  pref.getBoolean("anon",false);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
