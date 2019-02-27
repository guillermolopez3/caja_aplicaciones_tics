package com.gru.cajaaplicacionestics.view.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.gru.cajaaplicacionestics.R;

public class Prueba extends AppCompatActivity {

    private EditText txt;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        txt = findViewById(R.id.txtId);
        btn = findViewById(R.id.Obtener);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText(FirebaseInstanceId.getInstance().getToken());
            }
        });
    }
}
