package com.gru.cajaaplicacionestics.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


//todo: acordarse de cambiar a false el contentprov cuando inicio sesion
public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{
    private static final String TAG = "ProfileActivity";
    private static final String CERRAR_SESION = "cerrar sesión";
    private static final String INICIAR_SESION = "iniciar sesión";

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private FirebaseUser user;
    private Button btnIniciarCerrarSesion;
    private TextView txtNombre,txtMail;
    private ImageView imgProfile;

    private GoogleApiClient googleApiClient;

    private AccessToken accessToken;

    private boolean loguea_face = false;
    private boolean logueo_google = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseInit(); //inicio firebase y me fijo si esta logueado o no

        MetodosComunes.showToolbar("Mi Perfil",true,this);

        txtNombre              = findViewById(R.id.txtNombre);
        txtMail                = findViewById(R.id.txtMail);
        btnIniciarCerrarSesion = findViewById(R.id.btnIniCerrar);
        imgProfile             = findViewById(R.id.imgProfile);


        btnIniciarCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnIniciarCerrarSesion.getText().toString().equals(INICIAR_SESION))
                {
                    startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                    finish();
                }
                else {
                    if(loguea_face){
                        signOutWithFacebook();
                    }else {
                        signOutWithGoogle();
                    }
                }

            }
        });

    }


    private void firebaseInit()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        accessToken = AccessToken.getCurrentAccessToken(); //instancia de facebook
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.e("user", user.toString());
                    btnIniciarCerrarSesion.setText(CERRAR_SESION);
                    if(accessToken != null && !accessToken.isExpired()) //esta logueado con face
                    {
                        Log.e("logueado","face");
                        loguea_face = true;
                        logueadoConFace();
                    }else //esta logueado con google
                    {
                        Log.e("logueado","google");
                        logueo_google = true;
                        logueadoConGoogle();
                    }
                }else {
                    Log.e("user","false");
                    btnIniciarCerrarSesion.setText(INICIAR_SESION);
                }
            }
        };


    }

    private void logueadoConFace()
    {
        mostrarDatosFace();
    }

    private void logueadoConGoogle()
    {
        iniciarGoogleApiClient();
        mostrarDatosUsuarioGoogle(user);
    }

    private void mostrarDatosUsuarioGoogle(FirebaseUser user)
    {
        txtNombre.setText(user.getDisplayName());
        txtMail.setText(user.getEmail());
        Glide.with(this).load(user.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(imgProfile);
    }

    private void iniciarGoogleApiClient()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestProfile()
                .requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    private void signOutWithFacebook() {
        firebaseAuth.signOut();

        if(LoginManager.getInstance() != null) //compruebo que haya una sesion abierta con face
        {
            LoginManager.getInstance().logOut();
            Toast.makeText(this, "Se cerró la sesión", Toast.LENGTH_SHORT).show();
        }
        actualizarSharePreff();
        eliminarPrefToken();
        irMenuPpal();
    }

    private void signOutWithGoogle()
    {
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess())
                {
                    irMenuPpal();
                }else {
                    Toast.makeText(ProfileActivity.this, "Error al cerrar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });
        actualizarSharePreff();
        eliminarPrefToken();
    }

    private void mostrarDatosFace()
    {
        if(accessToken != null)
        {
            Log.e("acces t true", accessToken.toString());
           GraphRequest request = GraphRequest.newMeRequest(accessToken ,new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        if(object.getString("name") != null)
                        {
                            txtNombre.setText(object.getString("name"));
                        }
                        if(object.getString("email") != null)
                        {
                            txtMail.setText(object.getString("email"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email");
            request.setParameters(parameters);
            request.executeAsync();
        }
        else {
            Log.e("acces t", "false");
        }
        mostrarImagenFace();
    }

    private void mostrarImagenFace()
    {
        Profile profile = Profile.getCurrentProfile();
        if(profile == null)
        {
            Log.e(TAG,"profile null");
        }else {
            Uri url = profile.getProfilePictureUri(900,900);
            Log.e("url",url.toString());
            //muestro la imagen redonda
            Glide.with(this).load(url).apply(RequestOptions.circleCropTransform()).into(imgProfile);
        }
    }

    private void actualizarSharePreff()
    {
        SharedPreferences pref = getSharedPreferences("anon",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("anon",false);
        editor.commit();
        //irMenuPpal();
    }

    private void irMenuPpal()
    {
        startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
       /* OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone())
        {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }*/
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess())
        {
            GoogleSignInAccount account = result.getSignInAccount();

            txtNombre.setText(account.getDisplayName());
            txtMail.setText(account.getEmail());
            Glide.with(this).load(account.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(imgProfile);
        }
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //elimino de las shared pref el valor para decir q el token ya lo tengo
    private void eliminarPrefToken()
    {
        SharedPreferences pref = getSharedPreferences("token",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("token",false);
        editor.commit();
    }
}
