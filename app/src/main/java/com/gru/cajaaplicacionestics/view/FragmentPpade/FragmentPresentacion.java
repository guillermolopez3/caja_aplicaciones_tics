package com.gru.cajaaplicacionestics.view.FragmentPpade;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.Constantes;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;

public class FragmentPresentacion extends Fragment
{
    WebView webView;
    CoordinatorLayout coordinatorLayout;

    public FragmentPresentacion(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_web_view,container,false);

        coordinatorLayout = view.findViewById(R.id.coordinator);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.hide();
        webView = view.findViewById(R.id.webView);
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

        if(MetodosComunes.isOnline(getActivity()))
        {
            cargarUrl();
        }else {
            recargar();
        }

        cargarUrl();

        return view;
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
       webView.loadUrl(Constantes.URL_PPADE_PRESENTACION);
   }
}
