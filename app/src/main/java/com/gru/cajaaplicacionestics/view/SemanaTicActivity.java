package com.gru.cajaaplicacionestics.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.gru.cajaaplicacionestics.R;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class SemanaTicActivity extends AppCompatActivity
{
    TextInputEditText txtDni;
    Button btnMostrarQR;
    ImageView imgQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semana_tic);

        showToolbar("Bienvenido a la semana TIC",true);

        txtDni        =(TextInputEditText) findViewById(R.id.acreditacionTxtDni);
        btnMostrarQR  =(Button) findViewById(R.id.acreditacionBtnGenerarQR);
        imgQR         =(ImageView)findViewById(R.id.acreditacionImgQR);

        btnMostrarQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarQR(v);
            }
        });

    }

    private void generarQR(View view)
    {
        //escondo el teclado para mostrar el QR
        InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(txtDni.getWindowToken(),0);
        String dni="--";
        dni= dni + txtDni.getText().toString();


        if(dni.equals("--"))
        {
            Snackbar.make(view,"Ingrese un dni" + dni,Snackbar.LENGTH_SHORT).show();
        }
        else
        {
            try{
                MultiFormatWriter multiFormatWriter= new MultiFormatWriter();

                BitMatrix bitMatrix= multiFormatWriter.encode(dni, BarcodeFormat.QR_CODE,500,500);
                BarcodeEncoder barcodeEncoder= new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imgQR.setImageBitmap(bitmap);
            }
            catch (WriterException e) {
                e.printStackTrace();
                Snackbar.make(view,"Ingrese un dni" + dni,Snackbar.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                imgQR.setImageBitmap(null);
                Snackbar.make(view,"Ingrese un dni" + dni,Snackbar.LENGTH_SHORT).show();
            }
        }


    }

    public void showToolbar(String tittle, boolean upButton)
    {
        //uso appcompatacty... xq la actividad que maneja esto tiene soporte y es de este tipo
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

}
