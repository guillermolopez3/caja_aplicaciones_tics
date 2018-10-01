package com.gru.cajaaplicacionestics.view;

import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.model.ModelPost;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class DetalleRecursoAudio extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    ImageButton play, stop;
    ImageView imagen;
    MediaPlayer mediaPlayer;
    private boolean reproduciendo=false;
    private TextView titulo,tag,detalle, fecha;
    private String url_audio="";
    ModelPost post=new ModelPost();

    ProgressBar progressBar;

    private boolean audio_descargado=false; //cuando entro al activity todavia no cargo el audio

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_recurso_audio);
        mediaPlayer=new MediaPlayer();

        post = (ModelPost) getIntent().getExtras().get("data");

        imagen      = findViewById(R.id.detalleRecursoAudioImg);
        titulo      = findViewById(R.id.txtTituloDescripcionRecurso);
        tag         = findViewById(R.id.txtTagsDescripcionRecurso);
        fecha       = findViewById(R.id.txtFechaDescripcionRecurso);
        detalle     = findViewById(R.id.txtDescripcionRecurso);
        play        = findViewById(R.id.detalleRecursoAudioBtnPlay);
        stop        = findViewById(R.id.detalleRecursoAudioBtnStop);
        progressBar = findViewById(R.id.progressAudio);


        url_audio= MetodosComunes.verificarUrl(post.getLink());
        Picasso.with(this).load(MetodosComunes.verificarUrl(post.getImage())).into(imagen);

        cargarDatosActivity();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audio_descargado==false)
                {
                    cargarAudio(); //cargo el audio desde internet
                }
                else //si ya descargúe el audio, veo si pauso o inicio el audio
                {
                    if(reproduciendo==false)
                    {
                        mediaPlayer.start();
                        stop.setImageResource(R.drawable.ic_action_playback_pause);
                    }
                }


            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reproduciendo==true) //si el audio se estaba reproduciendo, lo pauso
                {
                    mediaPlayer.pause();
                    reproduciendo=false; //dejo de reproducion, si lo presiono de vuelta cancelo todo
                }
                else //si presiono el boton de stop, limpio todo y preparo para cargar de vuelta
                    {
                    mediaPlayer.stop();
                    mediaPlayer=null;
                    mediaPlayer = new MediaPlayer();
                    audio_descargado=false;
                }
                stop.setImageResource(R.drawable.ic_action_playback_stop);

            }
        });
    }

    private void cargarDatosActivity()//lleno el activity con los datos
    {
        titulo.setText(post.getTitle());
        fecha.setText("Ultima actualización: " + post.getCreated_at());

        if((post.getTags() !=null) && (post.getTags()!="") && (!post.getTags().equals("null")))
        {
            tag.setText(post.getTags());
        } else {
            tag.setVisibility(View.GONE);
        }
        if((post.getDescription() !=null) && (post.getDescription()!="") && (!post.getDescription().equals("null")))
        {
            detalle.setText(post.getDescription());
        } else {
            detalle.setVisibility(View.INVISIBLE);
        }
    }




    private void cargarAudio() //descarga el audio y comienza a reproducirlo
    {
        mediaPlayer.setOnPreparedListener(this);
        try{
            mediaPlayer.setDataSource(url_audio);
            mediaPlayer.prepareAsync();
            audio_descargado=true;
        }
        catch (IOException e)
        {
            audio_descargado=false;
            Toast.makeText(DetalleRecursoAudio.this,"Error al cargar el tema",Toast.LENGTH_LONG).show();
        }
        Toast.makeText(DetalleRecursoAudio.this,"El audio se esta descargando, iniciará en un momento",Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        //mediaPlayer.release();
        mediaPlayer=null;
    }

    //una vez que descargó, empieza a reproducir el audio
    @Override
    public void onPrepared(MediaPlayer mp) {
        progressBar.setVisibility(View.GONE);
        mediaPlayer.start();
        reproduciendo=true;
        stop.setImageResource(R.drawable.ic_action_playback_pause);
    }



}
