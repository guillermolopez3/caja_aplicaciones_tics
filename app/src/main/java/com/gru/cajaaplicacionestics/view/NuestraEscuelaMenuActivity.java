package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gru.cajaaplicacionestics.R;

public class NuestraEscuelaMenuActivity extends AppCompatActivity {

    Button doc_a,ori_adm,rec_pc,resol_ap,doc_aut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuestra_escuela_menu);

        doc_a       =(Button)findViewById(R.id.btnDocAcompa);
        ori_adm     =(Button)findViewById(R.id.btnOrientAdm);
        rec_pc      =(Button)findViewById(R.id.btnRecPcial);
        resol_ap    =(Button)findViewById(R.id.btnResolucion);
        doc_aut     =(Button)findViewById(R.id.btnDocAutoeval);

        doc_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(NuestraEscuelaMenuActivity.this,NEActivity.class);
                i.putExtra("ne",2);
                i.putExtra("titulo","Documento de acompañamiento");
                startActivity(i);
            }
        });

        rec_pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NuestraEscuelaMenuActivity.this,RecursosProvincialesActivity.class);
                startActivity(i);
            }
        });

    }
}
