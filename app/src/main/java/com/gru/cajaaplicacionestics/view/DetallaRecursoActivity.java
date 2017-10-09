package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.model.NewPost;
import com.gru.cajaaplicacionestics.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetallaRecursoActivity extends AppCompatActivity
{
    Button acceder_recurso;
    ImageView imagen;
    TextView titulo,tag,descripcion;
    NewPost post= new NewPost();
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalla_recurso);


        acceder_recurso=(Button) findViewById(R.id.detalleBtnAccederRecurso);
        imagen=(ImageView)findViewById(R.id.imagenHeaderDetalle);
        titulo=(TextView)findViewById(R.id.txtTituloDescripcionRecurso);
        tag=(TextView) findViewById(R.id.txtTagsDescripcionRecurso);
        descripcion=(TextView)findViewById(R.id.txtDescripcionRecurso);
        collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapsingDetalle);

        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);

        post = (NewPost)getIntent().getExtras().get("data");

        showToolbar(post.getNombre(),false);


        if(post!=null)
        {
            titulo.setText(post.getNombre());
            tag.setText(post.getTag());

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                descripcion.setText(Html.fromHtml(post.getDetalle(),Html.FROM_HTML_MODE_LEGACY));
            } else {
                descripcion.setText(Html.fromHtml(post.getDetalle()));
            }

          //  descripcion.setText(Html.fromHtml(post.getDescripcionCorta()));
            Picasso.with(this).load(post.getUrl_img()).into(imagen);
        }

        acceder_recurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(post.getUlr_mas()!="")
                {
                    Uri uri = Uri.parse(post.getUlr_mas());
                    Intent i = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(i);
                }

            }
        });

    }

    public void showToolbar(String tittle, boolean upButton)
    {
        //uso appcompatacty... xq la actividad que maneja esto tiene soporte y es de este tipo
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_compartir,menu);*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
       /* Uri uri=Uri.parse("android.resource://com.gru.cajaaplicacionestics/drawable/"+ R.drawable.logo);
        if (item.getItemId()== R.id.menu_compartir) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
            startActivity(Intent.createChooser(shareIntent, "enviar"));
        }*/


        return super.onOptionsItemSelected(item);
    }
}
