package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;

import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfActivity extends AppCompatActivity{
    PDFView pdfView;
    String url_pdf;
    FloatingActionButton fab;
    private ProgressBar progressBar;

    public static int MILISEGUNDOS_ESPERA = 3500;
    private boolean fab_visible=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        if(getIntent().getExtras()!=null)
        {
            url_pdf= getIntent().getExtras().getString("link");
        }
        Log.e("url-pdf",url_pdf);
        pdfView     = (PDFView)findViewById(R.id.pdfView);
        progressBar =(ProgressBar)findViewById(R.id.progressNovedad);
        fab         =(FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje= "Link de descarga: \n" + url_pdf;
                MetodosComunes.compartirRecursos(mensaje,PdfActivity.this);
            }
        });

        ocultarFab();

        new obtenerPdf().execute(url_pdf);

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

    //obtengo el pdf y lo muestro
    class obtenerPdf extends AsyncTask<String,Void,InputStream>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdfView.setVisibility(View.INVISIBLE);
            Toast.makeText(PdfActivity.this,"Descargando documento",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected InputStream doInBackground(String... params) {
            InputStream inputStream=null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode()==200)
                {
                    inputStream= new BufferedInputStream(urlConnection.getInputStream());
                }
            }catch (IOException e)
            {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).onError(new OnErrorListener() {
                @Override
                public void onError(Throwable t) {
                    Toast.makeText(PdfActivity.this,"Error al descargar el pdf",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                    Log.e("carga completa","completado");
                    //una vez descargado el pdf quito la barra de progreso
                    progressBar.setVisibility(View.GONE);
                    pdfView.setVisibility(View.VISIBLE);
                }
            }).load();
        }
    }
}
