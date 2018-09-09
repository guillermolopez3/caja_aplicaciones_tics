package com.gru.cajaaplicacionestics.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;

import java.net.URI;
import java.net.URL;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    FloatingActionButton fab;
    String url="";

    public static int MILISEGUNDOS_ESPERA = 3500;
    private boolean fab_visible=true;

    boolean aceptar=false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        if(getIntent().getExtras()!=null)
        {
            Log.e("url",getIntent().getExtras().getString("link") );
            verificarSiLinkEsDeGoogleDocs(getIntent().getExtras().getString("link"));

        }


        webView = findViewById(R.id.webView);
        webView.setVisibility(View.GONE);
        progressBar = findViewById(R.id.progressWebView);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }

        });

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                WebView.HitTestResult hr = ((WebView)v).getHitTestResult();

                Log.e("valor", "getExtra = " + hr.getExtra() + "\t\t Type=" + hr.getType());
                String valor = hr.getExtra();

               try{
                   if(valor!=null && hr.getType()==7)
                   {
                       if(valor.endsWith(".pdf")) //al hacer click en un pdf lo abro
                       {
                           Log.e("termina con ","pdf");
                           WebViewActivity.this.finish();
                           getIntent().putExtra("link",valor);
                           startActivity(getIntent());
                       }
                       else {
                           Uri uri = Uri.parse(valor);
                           Intent in = new Intent(Intent.ACTION_VIEW,uri);
                           startActivity(in);
                       }
                   }

                }
                catch (Exception e){Log.e("error",e.getMessage());}

                return false;
            }
        });

        fab = findViewById(R.id.fab);

        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje= "Link de descarga: \n" + url;
                MetodosComunes.compartirRecursos(mensaje,WebViewActivity.this);
            }
        });

        ocultarFab();

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

    private void verificarSiLinkEsDeGoogleDocs(String link) //si el link viene desde google docs no agrego la ruta
    {
       /* Log.e("link",url);
        if(link.startsWith("https://docs"))
        {
            url=link;
        }
        else {
            //http://www.igualdadycalidadcba.gov.ar/CajaTIC/pdf_vie?url=
            //url = "http://docs.google.com/viewer?url=" + link;
            url = "http://www.igualdadycalidadcba.gov.ar/CajaTIC/pdf_vie?url=" + link;
            //url= link;
            Log.e("link",url);
        }*/

        if(link.endsWith("pdf"))
        {
            url = "http://www.igualdadycalidadcba.gov.ar/CajaTIC/pdf_vie?url=" + link;
            Log.e("link pdf",url);
        }
        else {
            url=link;
            Log.e("link comun",url);
        }


    }

}
