package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gru.cajaaplicacionestics.R;

public class NuestraEscuelaMenuActivity extends AppCompatActivity {

    Button doc_a,apuntes,asistencia,noticias,certificados,contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuestra_escuela_menu);

        doc_a           =(Button)findViewById(R.id.btnDocAcompa);
        apuntes         =(Button)findViewById(R.id.btnApuntesTrabajo);
        asistencia      =(Button)findViewById(R.id.btnAsistTecn);
        noticias        =(Button)findViewById(R.id.btnNoticia);
        certificados    =(Button)findViewById(R.id.btnCertificado);
        contacto        =(Button)findViewById(R.id.btnContacto);

        if(Build.VERSION.SDK_INT  < Build.VERSION_CODES.LOLLIPOP)
        {
            doc_a.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            apuntes.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            asistencia.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            noticias.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            certificados.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            contacto.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        doc_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresarAlRecurso(2,"Documento de acompañamiento");
            }
        });

       apuntes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ingresarAlRecurso(3,"Apuntes de trabajo");
           }
       });

       asistencia.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ingresarAlRecurso(4,"Asistencias técnicas sistematizadas");
           }
       });

       noticias.setVisibility(View.GONE);
       /*noticias.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ingresarAlRecurso(5,"Noticias/Novedades/Experiencias");
           }
       });*/

       certificados.setVisibility(View.GONE);
      /* certificados.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ingresarAlRecurso(6,"Certificados Eje 1 y Eje 2");
           }
       });*/

       contacto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ingresarAlRecurso(7,"Contacto");
           }
       });

    }

    private void ingresarAlRecurso(int id, String titulo)
    {
        Intent i = new Intent(NuestraEscuelaMenuActivity.this,NEActivity.class);
        i.putExtra("ne",id);
        i.putExtra("titulo",titulo);
        startActivity(i);
    }
}
