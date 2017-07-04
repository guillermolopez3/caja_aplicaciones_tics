package com.gru.cajaaplicacionestics.view;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.model.Post;
import com.squareup.picasso.Picasso;

public class DetalleRecursoAudio extends AppCompatActivity {

    ImageButton play, stop;
    ImageView imagen;
    MediaPlayer mediaPlayer;
    private boolean reproduciendo=false;
    private int tema;
    private TextView titulo,tag,escuela,docente,detalle;
    Post post=new Post();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_recurso_audio);
        mediaPlayer=new MediaPlayer();

        post = (Post)getIntent().getExtras().get("data");

        imagen=(ImageView)findViewById(R.id.detalleRecursoAudioImg);
        titulo=(TextView)findViewById(R.id.txtTituloDescripcionRecurso);
        tag=(TextView)findViewById(R.id.txtTagsDescripcionRecurso);
        escuela=(TextView)findViewById(R.id.detalleRecursoAudioTxtEscuela);
        docente=(TextView)findViewById(R.id.detalleRecursoAudioTxtDocente);
        detalle=(TextView)findViewById(R.id.txtDescripcionRecurso);
        play = (ImageButton)findViewById(R.id.detalleRecursoAudioBtnPlay);
        stop = (ImageButton)findViewById(R.id.detalleRecursoAudioBtnStop);

        titulo.setText(post.getNombreRecurso());
        tag.setText(post.getHastag());
        escuela.setText(post.getEscuela());
        docente.setText(post.getDocente());
        detalle.setText(post.getDescripcionCorta());
        Picasso.with(this).load(post.getImagenLocal()).into(imagen);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reproduciendo==false)
                {
                    mediaPlayer= MediaPlayer.create(getApplicationContext(),post.getAudio_local());
                    mediaPlayer.start();
                    stop.setImageResource(R.drawable.ic_action_playback_pause);
                    reproduciendo=true;
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                stop.setImageResource(R.drawable.ic_action_playback_stop);
                reproduciendo=false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        //mediaPlayer.release();
        mediaPlayer=null;
    }
}
