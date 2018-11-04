package com.gru.cajaaplicacionestics.view.qr;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.model.ModelPost;
import com.gru.cajaaplicacionestics.view.WebViewActivity;
import com.gru.cajaaplicacionestics.view.YoutubeActivity;

import androidx.annotation.Nullable;

public class QrReadActivity extends Activity
{
    private IntentIntegrator qr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qr = new IntentIntegrator(this);
        qr.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null)
        {
            if(result.getContents()==null)
            {
                Toast.makeText(this, "Ningún código QR leido", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                /*try{
                    JSONObject object = new JSONObject(result.getContents());
                    Toast.makeText(this, "Datos:" + object.getString("name") , Toast.LENGTH_LONG).show();
                }catch (JSONException e){
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }*/
                try {
                    String dire = result.getContents();
                    /*Uri marketUri = Uri.parse(dire);
                    Intent url = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(url);*/
                    verificarTextoQr(dire);

                } catch (Exception e) {
                    Toast.makeText(this, "Error al leer el QR", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }else {
            super.onActivityResult(requestCode,resultCode,data);
            finish();
        }
    }

    private void verificarTextoQr(String dato)
    {
        if(!dato.isEmpty())
        {
            int tamano = dato.length();
            String primera_letra = dato.substring(0,1);
            String link = dato.substring(2,tamano);
            abrirActivity(link,primera_letra);
        }
    }

    private void abrirActivity(String link,String activity)
    {
        Intent i;
        ModelPost p = new ModelPost();
        p.setTitle("Experiencias Practicar");
        p.setLink(link);
        p.setCreated_at("05/11/2018");
        if(activity.equals("y")) //link de youtube
        {
            i = new Intent(this,YoutubeActivity.class);
            i.putExtra("data",p);
            startActivity(i);
        }
        else if(activity.equals("p")) //link que es un pdf
        {
            i = new Intent(this, WebViewActivity.class);
            i.putExtra("link",MetodosComunes.verificarUrl(link));
            startActivity(i);
        }
        else if(activity.equals("w")) //link de una pagina web
        {
            Uri uri = Uri.parse(link);
            i = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(i);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
