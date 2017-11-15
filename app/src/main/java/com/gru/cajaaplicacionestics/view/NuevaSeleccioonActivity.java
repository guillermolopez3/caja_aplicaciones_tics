package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gru.cajaaplicacionestics.R;

public class NuevaSeleccioonActivity extends AppCompatActivity {

    ImageButton pd,ci,ne,re;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_seleccioon);
        showToolbar("Selección de categoría",false);
        pd=(ImageButton) findViewById(R.id.seleccionPd);
        ci=(ImageButton) findViewById(R.id.seleccionCi);
        ne=(ImageButton) findViewById(R.id.seleccionNe);
        re=(ImageButton) findViewById(R.id.seleccionRecursos);

        pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NuevaSeleccioonActivity.this,PDActivity.class);
                startActivity(i);

            }
        });

    }

    public void showToolbar(String tittle, boolean upButton)
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }
}
