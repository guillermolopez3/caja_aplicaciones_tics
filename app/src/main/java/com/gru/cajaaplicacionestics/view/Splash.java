package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gru.cajaaplicacionestics.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by guill on 10/05/2017.
 */

public class Splash extends AppCompatActivity
{
    private long splashDelay = 3000;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private boolean estaAutenticado = false;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

       // FirebaseApp.initializeApp(this);

        //valido si el usuario esta logueado
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            estaAutenticado = true;
        }

      /*  mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    estaAutenticado = true;

                }
            }
        };*/

        TimerTask task = new TimerTask() {
            @Override
            public void run()
            {
                if(sharePref())
                {
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
    private boolean sharePref()
    {
        SharedPreferences pref = getSharedPreferences("anon",MODE_PRIVATE);
        boolean anon = pref.getBoolean("anon",false);
        return  anon;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //firebaseAuth.removeAuthStateListener(authStateListener);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
