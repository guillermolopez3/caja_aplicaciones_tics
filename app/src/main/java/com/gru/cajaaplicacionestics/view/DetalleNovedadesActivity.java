package com.gru.cajaaplicacionestics.view;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;
import com.gru.cajaaplicacionestics.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetalleNovedadesActivity extends AppCompatActivity {

    PDFView pdfView;
    String url_pdf;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_novedades);

        if(getIntent().getExtras()!=null)
        {
            url_pdf= getIntent().getExtras().getString("link");
        }
        Log.e("url-pdf",url_pdf);
        pdfView= (PDFView)findViewById(R.id.pdfView);
        progressBar=(ProgressBar)findViewById(R.id.progressNovedad);
        //Uri uri = Uri.parse("http://www.muniap.com/CajaTic/pdf/p02.pdf");
        //pdfView.fromUri(uri).load();
        //url_pdf = "http://www.muniap.com/CajaTic/pdf/p02.pdf";

        new obtenerPdf().execute(url_pdf);
    }

    class obtenerPdf extends AsyncTask<String,Void,InputStream>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdfView.setVisibility(View.INVISIBLE);
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
            progressBar.setVisibility(View.GONE);
            pdfView.setVisibility(View.VISIBLE);
            pdfView.fromStream(inputStream).load();
        }
    }
}
