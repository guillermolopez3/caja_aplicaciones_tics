package com.gru.cajaaplicacionestics.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.AnalitycsAplication;
import com.gru.cajaaplicacionestics.model.ColegioModel;
import com.gru.cajaaplicacionestics.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioTecnicoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Tracker mTracker;
    CoordinatorLayout coordinatorLayout;
    ColegioModel colegioModel;

    TextView cueCole,colegio,ar,fp;
    TextInputEditText persona,tel,mail,detalle;
    NestedScrollView scrollView;
    Button btn_enviar;
    int id_cole;
    int id_problema;

    Spinner spinner;

    ArrayList<String> nombre_spinner;
    ArrayList<Integer> id_spinner;

    String cue;
    String URL_BASE,URL_COLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_tecnico);

        AnalitycsAplication aplication = (AnalitycsAplication) getApplication();
        mTracker = aplication.getDefaultTracker();

        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorST);
        coordinatorLayout.setVisibility(View.INVISIBLE);

        URL_BASE= getResources().getString(R.string.URL_BASE);
        URL_COLE=getResources().getString(R.string.URL_GET_COLE);

        showToolbar("Pedido de Servicio Técnico",false);

        colegioModel= new ColegioModel();
        nombre_spinner= new ArrayList<>();
        id_spinner= new ArrayList<>();
        abrirDialog(this);
    }

    private void abrirDialog(Context c)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater inflater = getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_cue,null);

        builder.setView(view);

        final TextInputEditText txtCue = (TextInputEditText)view.findViewById(R.id.recursoEnviarTxtUsuario);
        final TextInputLayout txtLay = (TextInputLayout)view.findViewById(R.id.textInputLayoutDialogCue);
        Button no = (Button) view.findViewById(R.id.dialogNo);
        Button si = (Button) view.findViewById(R.id.dialogSi);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertDialog.dismiss();
                finish();
            }
        });

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cue = txtCue.getText().toString();
                if(cue.length()<9){
                    txtLay.setError("Ingrese un CUE válido");
                }
                else {
                    txtLay.setError(null);
                    obtenerDatosCole(alertDialog,txtLay);
                }

               // alertDialog.dismiss();
            }
        });

    }

    private void obtenerDatosCole(final AlertDialog dial, final TextInputLayout txtL) {
        final ProgressDialog dialog=new ProgressDialog(ServicioTecnicoActivity.this);
        dialog.setMessage("Cargando datos...");
        dialog.show();
        Log.e("entrando", "obtener");
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_BASE + URL_COLE + "?cue="+cue,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("data");
                            Log.e("array","cant" + array.length());

                            if(array.length()==1)
                            {
                                JSONObject o = array.getJSONObject(0);
                                ColegioModel cole = new ColegioModel(
                                        o.getInt("id"),
                                        o.getString("cue"),
                                        o.getString("nombre_cole"),
                                        o.getString("localidad"),
                                        o.getString("inspeccion"),
                                        o.getString("modalidad"),
                                        o.getString("ar"),
                                        o.getString("fp")
                                );
                                cargarDatos(cole,txtL);
                                dial.dismiss();
                            }
                            else {
                                txtL.setError("CUE no encontrado");
                            }

                        } catch (JSONException e) {
                            Log.e("error cue", e.toString());
                            dialog.dismiss();
                            txtL.setError("CUE no encontrado");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.e("error cue", error.getMessage());
                txtL.setError("Verifique la conexión a internet");
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void cargarDatos(ColegioModel c, TextInputLayout lay)
    {
        if((c.getNombre_cole()!="")&&(c.getNombre_cole()!=null)&&(!c.getNombre_cole().equals("null")))
        {
            coordinatorLayout.setVisibility(View.VISIBLE);
            cueCole     = (TextView)findViewById(R.id.txtCueST);
            colegio     = (TextView)findViewById(R.id.txtColegioST);
            ar          = (TextView)findViewById(R.id.txtArST);
            fp          = (TextView)findViewById(R.id.txtFpST);
            persona         = (TextInputEditText)findViewById(R.id.txtNombreContacto);
            tel             = (TextInputEditText)findViewById(R.id.txtTelContacto);
            mail            = (TextInputEditText)findViewById(R.id.txtMailContacto);
            detalle         = (TextInputEditText)findViewById(R.id.txtMensaje);

            spinner=(Spinner)findViewById(R.id.spinner);
            spinner.setOnItemSelectedListener(this);

            llenarSpinner();

            scrollView =(NestedScrollView)findViewById(R.id.netScroll);
            btn_enviar=(Button)findViewById(R.id.btnEnviarPedido);

            id_cole= c.getId();
            cueCole.setText("CUE: "+c.getCue());
            colegio.setText("COLEGIO: "+c.getNombre_cole());
            ar.setText("AR: "+c.getAr());
            fp.setText("FACILITADOR: "+c.getFp());

            btn_enviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verificarCampos();
                }
            });

        }
        else {
            lay.setError("CUE no encontrado");
        }
    }

    private void verificarCampos() //verifico que todos los campos esten llenos
    {
        if((persona.getText().toString().isEmpty()) || (tel.getText().toString().isEmpty()) || (mail.getText().toString().isEmpty()) || (detalle.getText().toString().isEmpty()))
        {
            Snackbar.make(scrollView,"Todos los campos son obligatorios",Snackbar.LENGTH_LONG).show();
        }
        else {
            insertarCapacitacion();
        }
    }

    private void llenarSpinner() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://www.muniap.com/CajaTic/llenarSpinner.php?id=1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("post");

                            for(int i=0; i< array.length();i++)
                            {
                                JSONObject o = array.getJSONObject(i);

                                nombre_spinner.add(o.getString("nombre"));
                                id_spinner.add(o.getInt("id"));
                            }
                            mostrarSpinner();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void mostrarSpinner()
    {
        List<String> label = new ArrayList<String>();

        for(int i=0; i<nombre_spinner.size();i++)
        {
            label.add(nombre_spinner.get(i));
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,label);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

    }

    public void showToolbar(String tittle, boolean upButton)
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        id_problema= id_spinner.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void insertarCapacitacion() //trae los datos de la BD, los parsea con volley y lo carga a la lista
    {
        String URL = "http://www.igualdadycalidadcba.gov.ar/CajaTIC/consultas_app/InsertarReclamo.php";

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
                                Toast.makeText(ServicioTecnicoActivity.this,"Pedido enviado. Gracias",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else if(cod_rta==2)
                            {
                                Snackbar.make(scrollView,"Error al enviar el pedido,intente nuevamente",Snackbar.LENGTH_LONG).show();
                                Log.e("Insetrado","error");
                            }

                            dialog.dismiss();
                        } catch (JSONException e) {
                            Log.e("error cue", e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("categ","1");
                hashMap.put("id_cole",String.valueOf(id_cole));
                hashMap.put("persona",persona.getText().toString());
                hashMap.put("tel",tel.getText().toString());
                hashMap.put("mail",mail.getText().toString());
                hashMap.put("problema",String.valueOf(id_problema));
                hashMap.put("detalle",detalle.getText().toString());
                return hashMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("ST");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
