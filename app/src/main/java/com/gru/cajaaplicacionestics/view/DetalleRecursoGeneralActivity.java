package com.gru.cajaaplicacionestics.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.model.ModelPost;
import com.squareup.picasso.Picasso;


//TODO:  verificar si la URL que viene empieza con http// ya que si no viene asi la app crashea

public class DetalleRecursoGeneralActivity extends AppCompatActivity
{
    Button                  acceder_recurso;
    ImageView               imagen;
    TextView                titulo,tag,descripcion;
    ModelPost               post= new ModelPost();
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalla_recurso);


        acceder_recurso            =findViewById(R.id.detalleBtnAccederRecurso);
        imagen                     =findViewById(R.id.imagenHeaderDetalle);
        titulo                     =findViewById(R.id.txtTituloDescripcionRecurso);
        tag                        =findViewById(R.id.txtTagsDescripcionRecurso);
        descripcion                =findViewById(R.id.txtDescripcionRecurso);
        collapsingToolbarLayout    =findViewById(R.id.collapsingDetalle);

        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);

        post = (ModelPost)getIntent().getExtras().get("data");

        MetodosComunes.showToolbar(post.getTitle(),false,this);


        if(post!=null)
        {
            Log.e("descripcion",post.getDescription());
            titulo.setText(post.getTitle());
            if((post.getTags() !=null) && (post.getTags()!="") && (!post.getTags().equals("null")))
            {
                tag.setText(post.getTags());
            }

            if((post.getDescription() !=null) && (post.getDescription()!="") && (!post.getDescription().equals("null")))
            {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    descripcion.setText(Html.fromHtml(post.getDescription(),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    descripcion.setText(Html.fromHtml(post.getDescription()));
                }
            }
            else {
                descripcion.setText("");
            }



          //  descripcion.setText(Html.fromHtml(post.getDescripcionCorta()));
            Picasso.with(this).load(MetodosComunes.verificarUrl(post.getImage())).into(imagen);
        }

        acceder_recurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(post.getLink()!="")
                {
                    Uri uri = Uri.parse(MetodosComunes.verificarUrl(post.getLink()));
                    Intent i = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(i);
                }

            }
        });

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
