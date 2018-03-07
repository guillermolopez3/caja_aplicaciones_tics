package com.gru.cajaaplicacionestics.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.model.ModelPost;
import com.gru.cajaaplicacionestics.model.NewPost;
import com.gru.cajaaplicacionestics.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayer mYouTubePlayer;

    private ModelPost post;
    TextView titulo,tag,descripcion,txtFecha;
    private String url_video;
    private Button btnRecargar;

    String URL_BASE="";
    String URL_VIDEO="";

    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayerView);
        titulo=(TextView)findViewById(R.id.txtTituloDescripcionRecurso);
        tag=(TextView) findViewById(R.id.txtTagsDescripcionRecurso);
        descripcion=(TextView)findViewById(R.id.txtDescripcionRecurso);
        btnRecargar=(Button)findViewById(R.id.youtubeRecargar);
        txtFecha = (TextView)findViewById(R.id.txtFechaDescripcionRecurso);

        URL_BASE=getResources().getString(R.string.URL_BASE);
        URL_VIDEO= getResources().getString(R.string.URL_VIDEO);

        post=new ModelPost();
        post = (ModelPost) getIntent().getExtras().get("data");

       cargarActivity();

        Log.e("ulrmhgjmhg", url_video);
        //https://www.youtube.com/watch?v=a4NT5iBFuZs en load video poner lo que esta despues de v=

        btnRecargar.setVisibility(View.GONE);
        btnRecargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtenerUrlVideoVivo();
                mYouTubePlayer.loadVideo(url_video);

                Log.e("url",url_video);
            }
        });

    }

    private void cargarActivity()
    {
        titulo.setText(post.getTitle());
        tag.setText(post.getTags());
        descripcion.setText(post.getDescription());
        url_video= MetodosComunes.verificarUrl(post.getLink());
        txtFecha.setText("Ultima actualización: " + post.getCreated_at());

        youTubePlayerView.initialize(getResources().getString(R.string.KEY_API_YOUTUBE), this);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restaurado) {
        Log.e("restaurado comun",""+restaurado);
        //youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL); //no muestro los botones de youtube
        mYouTubePlayer=youTubePlayer;

        if(!restaurado) //cuando inicia
        {
            youTubePlayer.loadVideo(url_video);
            Log.e("restaurado negado",""+restaurado);
        }
        else {
            youTubePlayer.play();
            verificarPantallaRotada(youTubePlayer);
        }
        //youTubePlayer.setFullscreen(true);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError())
        {
            youTubeInitializationResult.getErrorDialog(YoutubeActivity.this,1).show();
        }
        else {
            Toast.makeText(getApplication(),"Error al cargar el video",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==1)
        {
            getProvider().initialize(getResources().getString(R.string.KEY_API_YOUTUBE),this);
        }
    }

    protected YouTubePlayer.Provider getProvider()
    {
        return youTubePlayerView;
    }

    private void verificarPantallaRotada(YouTubePlayer player)
    {
        final int rotado =getWindowManager().getDefaultDisplay().getRotation();
        if (rotado == Surface.ROTATION_0 || rotado == Surface.ROTATION_180) {
            player.setFullscreen(false);
            player.play();
        } else {
            player.setFullscreen(true);
            player.play();
        }
    }

    private void obtenerUrlVideoVivo() {
        final ProgressDialog dialog=new ProgressDialog(YoutubeActivity.this);
        dialog.setMessage("Cargando datos...");
        dialog.show();
        Log.e("entrando", "obtener");
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_BASE + URL_VIDEO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("post");

                            JSONObject o = array.getJSONObject(0);

                            url_video = o.getString("url_video");
                            Log.e("url",o.getString("url_video"));
                            if(mYouTubePlayer!=null)
                            {
                                mYouTubePlayer.loadVideo(url_video);
                            }

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




}
