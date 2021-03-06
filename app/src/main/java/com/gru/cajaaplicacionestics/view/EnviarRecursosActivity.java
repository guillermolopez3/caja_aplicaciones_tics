package com.gru.cajaaplicacionestics.view;

import android.app.ProgressDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EnviarRecursosActivity extends AppCompatActivity {
    ScrollView scrollView;
    TextInputEditText nombre,titulo,link,categoria,descripcion;
    Button enviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_recursos);

        MetodosComunes.showToolbar("Compartir Recurso",false,this);

        scrollView  = findViewById(R.id.scrollEnviar);
        enviar      = findViewById(R.id.recursoEnviarBtnEnviar);
        nombre      = findViewById(R.id.recursoEnviarTxtUsuario);
        titulo      = findViewById(R.id.recursoEnviarTxtTitulo);
        link        = findViewById(R.id.recursoEnviarTxtUrl);
        categoria   = findViewById(R.id.recursoEnviarTxtCategoria);
        descripcion = findViewById(R.id.recursoEnviarTxtDetalle);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarCampos();
            }
        });
    }

    private void verificarCampos() //verifico que todos los campos esten llenos
    {
        if((nombre.getText().toString().isEmpty()) || (titulo.getText().toString().isEmpty()) || (link.getText().toString().isEmpty()) || (categoria.getText().toString().isEmpty())
                || (descripcion.getText().toString().isEmpty()))
        {
            Snackbar.make(scrollView,"Todos los campos son obligatorios",Snackbar.LENGTH_LONG).show();
        }
        else {
            insertarCapacitacion();
        }
    }

    private void insertarCapacitacion() //trae los datos de la BD, los parsea con volley y lo carga a la lista
    {
        //String URL = "http://www.muniap.com/CajaTic/InsertarNuevoRecurso.php";
        String URL= "https://www.igualdadycalidadcba.gov.ar/CajaTIC/consultas_app/InsertarNuevoRecurso.php";

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Enviando pedido...");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.e("respuesta",response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int cod_rta= jsonObject.getInt("estado");

                            if(cod_rta==1)
                            {
                                //Snackbar.make(scrollView,"Pedido enviado. Gracias",Snackbar.LENGTH_LONG).show();
                                Toast.makeText(EnviarRecursosActivity.this,"Pedido enviado. Gracias",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else if(cod_rta==2)
                            {
                                Snackbar.make(scrollView,"Error al enviar el pedido,intente nuevamente",Snackbar.LENGTH_LONG).show();
                                Log.e("Insetrado","error");
                            }

                            dialog.dismiss();
                        } catch (JSONException e) {
                            Snackbar.make(scrollView,"Error al enviar el pedido,intente nuevamente",Snackbar.LENGTH_LONG).show();
                            Log.e("Insetrado","error");
                            Log.e("error cue", e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(scrollView,"Error al enviar el pedido,intente nuevamente",Snackbar.LENGTH_LONG).show();
                Log.e("Insetrado","error");
                Log.e("error insertar", error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("nom",nombre.getText().toString());
                hashMap.put("titulo",titulo.getText().toString());
                hashMap.put("link",link.getText().toString());
                hashMap.put("categ",categoria.getText().toString());
                hashMap.put("detalle",descripcion.getText().toString());
                return hashMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
