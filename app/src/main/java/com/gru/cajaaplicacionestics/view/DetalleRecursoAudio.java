package com.gru.cajaaplicacionestics.view;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.model.NewPost;
import com.gru.cajaaplicacionestics.model.Post;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class DetalleRecursoAudio extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    ImageButton play, stop;
    ImageView imagen;
    MediaPlayer mediaPlayer;
    private boolean reproduciendo=false;
    private TextView titulo,tag,detalle;
    private String url_audio="";
    NewPost post=new NewPost();

    ProgressBar progressBar;

    private boolean audio_descargado=false; //cuando entro al activity todavia no cargo el audio

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_recurso_audio);
        mediaPlayer=new MediaPlayer();

        post = (NewPost) getIntent().getExtras().get("data");

        imagen=(ImageView)findViewById(R.id.detalleRecursoAudioImg);
        titulo=(TextView)findViewById(R.id.txtTituloDescripcionRecurso);
        tag=(TextView)findViewById(R.id.txtTagsDescripcionRecurso);
        detalle=(TextView)findViewById(R.id.txtDescripcionRecurso);
        play = (ImageButton)findViewById(R.id.detalleRecursoAudioBtnPlay);
        stop = (ImageButton)findViewById(R.id.detalleRecursoAudioBtnStop);
        progressBar=(ProgressBar)findViewById(R.id.progressAudio);

        titulo.setText(post.getNombre());
        tag.setText(post.getTag());
        detalle.setText(post.getDetalle());
        url_audio= post.getUlr_mas();
        Picasso.with(this).load(post.getUrl_img()).into(imagen);

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
