package com.gru.cajaaplicacionestics.auxiliares;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        if(getIntent().getExtras()!=null)
        {
            url+= getIntent().getExtras().getString("link");
            Log.e("URL",url);
        }

        webView=(WebView)findViewById(R.id.webView);
        webView.setVisibility(View.GONE);
        progressBar=(ProgressBar)findViewById(R.id.progressWebView);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje= "Link de descarga: \n" + url;
                MetodosComunes.compartirRecursos(mensaje,WebViewActivity.this);
            }
        });



    }
}
