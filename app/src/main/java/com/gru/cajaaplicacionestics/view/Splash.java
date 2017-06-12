package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gru.cajaaplicacionestics.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by guill on 10/05/2017.
 */

public class Splash extends AppCompatActivity
{
    private long splashDelay = 1000;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(Splash.this,SeleccionActivity.class);
                startActivity(mainIntent);
                finish();//Destruimos esta activity para prevenit que el usuario retorne aqui presionando el boton Atras.
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, splashDelay);//Pasado los 3 segundos dispara la tarea
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
