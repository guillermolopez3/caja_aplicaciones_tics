package com.gru.cajaaplicacionestics.auxiliares;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.view.PdfActivity;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    FloatingActionButton fab;
    String url= "http://docs.google.com/viewer?url=";

    public static int MILISEGUNDOS_ESPERA = 3500;
    private boolean fab_visible=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        if(getIntent().getExtras()!=null)
        {
            verificarSiLinkEsDeGoogleDocs(getIntent().getExtras().getString("link"));

        }

        webView=(WebView)findViewById(R.id.webView);
        webView.setVisibility(View.GONE);
        progressBar=(ProgressBar)findViewById(R.id.progressWebView);
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

        fab         =(FloatingActionButton)findViewById(R.id.fab);

        fab.setVisibility(View.INVISIBLE);
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
        if(link.startsWith("https://docs"))
        {
            url=link;
        }
        else {
            url+= link;
        }
    }
}
