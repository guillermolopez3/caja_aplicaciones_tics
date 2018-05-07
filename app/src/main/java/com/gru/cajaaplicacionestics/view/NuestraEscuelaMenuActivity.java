package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.gru.cajaaplicacionestics.R;

public class NuestraEscuelaMenuActivity extends AppCompatActivity {

    Button doc_a,apuntes,asistencia,noticias,certificados,contacto;
    Button jornadas;
    FirebaseAnalytics analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuestra_escuela_menu);
        analytics=FirebaseAnalytics.getInstance(this);
        final Bundle bundle = new Bundle();

        jornadas        = findViewById(R.id.btnJornadas);
        doc_a           = findViewById(R.id.btnDocAcompa);
        apuntes         = findViewById(R.id.btnApuntesTrabajo);
        asistencia      = findViewById(R.id.btnAsistTecn);
        noticias        = findViewById(R.id.btnNoticia);
        certificados    = findViewById(R.id.btnCertificado);
        contacto        = findViewById(R.id.btnContacto);

        if(Build.VERSION.SDK_INT  < Build.VERSION_CODES.LOLLIPOP)
        {
            jornadas.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            doc_a.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            apuntes.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            asistencia.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            noticias.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            certificados.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            contacto.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        jornadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NuestraEscuelaMenuActivity.this,NEDrawerActivity.class);
                startActivity(i);
            }
        });
        doc_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("opcion_menu_ne","doc_acom");
                ingresarAlRecurso(2,"Documento de acompañamiento");
            }
        });

       apuntes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               bundle.putString("opcion_menu_ne","apuntes_tra");
               ingresarAlRecurso(3,"Apuntes de trabajo");
           }
       });

       asistencia.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               bundle.putString("opcion_menu_ne","asis_tec");
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
               bundle.putString("opcion_menu_ne","contacto");
               ingresarAlRecurso(7,"Contacto");
           }
       });

       analytics.logEvent("menu_ne",bundle);
    }

    private void ingresarAlRecurso(int id, String titulo)
    {
        Intent i = new Intent(NuestraEscuelaMenuActivity.this,NEActivity.class);
        i.putExtra("ne",id);
        i.putExtra("titulo",titulo);
        startActivity(i);
    }
}
