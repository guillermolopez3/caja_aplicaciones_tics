package com.gru.cajaaplicacionestics.view;

import android.annotation.TargetApi;
import android.os.Build;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;

public class WebClieActivity extends AppCompatActivity
{
    WebView webView;
    CoordinatorLayout coordinatorLayout;
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_clie);

        coordinatorLayout = findViewById(R.id.coordinator);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.hide();
        webView = findViewById(R.id.webView);
        //webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true); //habilito el pinch and zoom
        webView.getSettings().setDisplayZoomControls(false); //deshabilito el boton que me pone para hacer o quitar el zoom
        webView.setWebViewClient(new WebViewClient(){

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                recargar();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.e("request 2 error", errorCode + "");
                recargar();
            }



        });

        //adapta la web al cel
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        URL = getIntent().getStringExtra("link");
        Log.e("link", URL);
        String titulo = getIntent().getStringExtra("titulo");

        MetodosComunes.showToolbar(titulo,false,this);

        if(MetodosComunes.isOnline(this))
        {
            cargarUrl();
        }else {
            recargar();
        }

        cargarUrl();
    }

    private void recargar()
    {
        String errorHtml = "<html>" +
                "<head></head>" +
                " <STYLE type='text/css'> H3 {text-align: center}  </STYLE>" +
                "<body>" +
                "    <h3>Verifique la conexión a internet</h3>" +
                "</body></html>";

        webView.loadData(errorHtml, "text/html", null);

        Snackbar.make(coordinatorLayout,"Comprobar conexión",Snackbar.LENGTH_INDEFINITE)
                .setAction("Recargar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cargarUrl();
                    }
                }).show();
    }

    private void cargarUrl()
    {
        webView.loadUrl(URL);
    }
}
