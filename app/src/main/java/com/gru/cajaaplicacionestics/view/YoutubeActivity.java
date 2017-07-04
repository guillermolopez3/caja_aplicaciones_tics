package com.gru.cajaaplicacionestics.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.model.Post;

public class YoutubeActivity extends YouTubeBaseActivity
{
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private Post post;
    TextView titulo,tag,descripcion;
    private String url_video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayerView);
        titulo=(TextView)findViewById(R.id.txtTituloDescripcionRecurso);
        tag=(TextView) findViewById(R.id.txtTagsDescripcionRecurso);
        descripcion=(TextView)findViewById(R.id.txtDescripcionRecurso);

        post=new Post();
        post = (Post)getIntent().getExtras().get("data");

        titulo.setText(post.getNombreRecurso());
        tag.setText(post.getHastag());
        descripcion.setText(post.getDescripcion());
        url_video=post.getVideo_id();

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                //https://www.youtube.com/watch?v=a4NT5iBFuZs en load video poner lo que esta despues de v=

              //  youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);

                if(!b)
                {
                    //youTubePlayer.loadVideo(url_video);
                    youTubePlayer.cueVideo(url_video);
                }
                else
                {
                    //youTubePlayer.play(); // cundo cambia a fullscreen o normal, se sigue reproduciendo
                }
                youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                    @Override
                    public void onFullscreen(boolean b) {
                        if(b)
                        {
                            youTubePlayer.play();
                        }
                        else
                        {
                            youTubePlayer.pause();
                        }
                    }
                });

                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {

                    }

                    @Override
                    public void onAdStarted() {
                        youTubePlayer.setFullscreen(true);
                       // youTubePlayer.play();
                    }

                    @Override
                    public void onVideoStarted() {
                       // youTubePlayer.setFullscreen(true);
                      //  youTubePlayer.play();
                    }

                    @Override
                    public void onVideoEnded() {

                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });



            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(),"Error al cargar el video",Toast.LENGTH_LONG).show();
            }


        };



        //inicio el video con el api key y llamando al metodo
        youTubePlayerView.initialize(getResources().getString(R.string.KEY_API_YOUTUBE), onInitializedListener);
    }
}
