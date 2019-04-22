package com.gru.cajaaplicacionestics.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.Constantes;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/* TODO: PARA HACER EL LOGOUT:
  * Firebase
   firebaseAuth.signOut

  * Facebook:
    LoginManager.getInstance()
    logOut()
*/
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{
    private static final String TAG = "LoginActivity";
    private static final int SIGN_IN_CODE = 777;

    private CallbackManager callbackManager; //face
    private LoginButton btnLogInFacebbok; //face

    private FirebaseAuth firebaseAuth; //firebase
    private FirebaseAuth.AuthStateListener firebaseAuthListener; //firebase
    private GoogleApiClient googleApiClient;

    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton btnLoginGoogle;

    private ProgressBar progressBar;

    private String nombre,mail,id_social;

    private TextView txtAnon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        iniciarGoogleApiClient();

        progressBar = findViewById(R.id.progress);

        //login with face
        btnLogInFacebbok = findViewById(R.id.loginFacebook);
        callbackManager = CallbackManager.Factory.create();
        btnLogInFacebbok.setReadPermissions(Arrays.asList("email", "public_profile "));


        btnLogInFacebbok.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("Inicio face", "ok");
                signInFacebookFirebase(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.e("Inicio face", "cancel");
                Toast.makeText(LoginActivity.this, "Inicio de sesión cancelado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Inicio face", "error" + error.toString());
                Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_LONG).show();
            }
        });

        // Ingreso de manera anonima
        txtAnon = findViewById(R.id.txtAnonymus);
        txtAnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingresoModoAnonimo();
            }
        });

        //Google
        btnLoginGoogle = findViewById(R.id.loginGoogle);
        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInGoogleFirebase();
            }
        });

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

    }

    private void signInFacebookFirebase(final AccessToken accessToken)
    {
        //paso x parametro el token de face y con eso creo una credencial
        final AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Log.e(TAG,accessToken.getUserId()); //obtengo el id del usuario
                    insertarUsuarioEnBD();
                    //irMenuPpal();
                }else {

                }
            }
        });
    }

    private void iniciarGoogleApiClient()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestId()
                .requestProfile()
                .requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    private void signInGoogleFirebase()
    {
        Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(i,777); //nro unico para saber de quien vino el result


    }

    //guardo en sharepref que quiero ingresar en modo anonimo
    private void ingresoModoAnonimo()
    {
        SharedPreferences pref = getSharedPreferences("anon",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("anon",true);
        editor.commit();
        irMenuPpal();
    }

    private void irMenuPpal()
    {
        startActivity(new Intent(LoginActivity.this,MenuActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuthListener != null)
        {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data); //callback de face
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == 777)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data); //callback google
            handlerSignInResult(result);
        }

    }

    private void handlerSignInResult(GoogleSignInResult result)
    {
        if(result.isSuccess())
        {
            firebaseAuthWithGoogle(result.getSignInAccount()); //si se autentico en google bien autentico con firebase
        }else {
            Toast.makeText(this, "Error al iniciar sesión" , Toast.LENGTH_SHORT).show();
            Log.e("error", result.getStatus().toString());
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            insertarUsuarioEnBD();
                            //irMenuPpal();
                        }
                    }
                });
    }

    private void insertarUsuarioEnBD()
    {
        if(firebaseAuth != null)
        {
            try{
                mail= firebaseAuth.getCurrentUser().getEmail(); //obtengo el mail, tengo que controlar que no seanullo
            }catch (Exception e){}
            nombre = firebaseAuth.getCurrentUser().getDisplayName();
            id_social = firebaseAuth.getCurrentUser().getUid();
            insertarUsuario();
        }
    }

    private void insertarUsuario()
    {
        String URL= Constantes.URL_BASE + Constantes.URL_INSERTAR_USUARIO;

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Iniciando sesión...");
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
                            String cod_rta= jsonObject.getString("estado");

                            if(cod_rta.equals("ok"))
                            {
                                irMenuPpal();
                            }
                            else if(cod_rta.equals("error"))
                            {
                                Toast.makeText(LoginActivity.this, "Error logueo", Toast.LENGTH_SHORT).show();
                                cerrarSesion();
                            }

                            dialog.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this, "Error logueo", Toast.LENGTH_SHORT).show();
                           // Log.e("error insertar usuario", e.toString());
                            cerrarSesion();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error logueo", Toast.LENGTH_SHORT).show();
                //Log.e("error insertar usuario", error.toString());
                cerrarSesion();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("nombre",nombre);
                hashMap.put("mail",mail);
                hashMap.put("id_social",id_social);
                return hashMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //si no pude guardar en la bd, cierro las sesiones abiertas
    private void cerrarSesion()
    {
        if(LoginManager.getInstance() != null) //compruebo que haya una sesion abierta con face
        {
            LoginManager.getInstance().logOut();
            firebaseAuth.signOut();
        }
        else
        {
            Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if(status.isSuccess())
                    {
                       firebaseAuth.signOut();
                    }
                }
            });
        }
    }

}
