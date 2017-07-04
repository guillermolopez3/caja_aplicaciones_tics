package com.gru.cajaaplicacionestics.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterRecursos;
import com.gru.cajaaplicacionestics.model.ModelAudio;
import com.gru.cajaaplicacionestics.model.ModelRecursos;
import com.gru.cajaaplicacionestics.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guill on 10/05/2017.
 */

public class FragmentPrincipal extends Fragment
{
    RecyclerView recyclerView;
    AdapterRecursos adapter;
    ArrayList<Post> lista; //este lo voy a usar cuando traiga datos de internet
    String categoria=""; //con esto se en que categoría estoy
    String consulta="";
    LinearLayout sin_internet;

    private ArrayList<Post> arrayTodos;//array que carga todos los recursos

    private String URL_BASE = "http://faltaequipo.xyz";
    private static final String URL_JSON = "/obtener_post.php";


    public FragmentPrincipal(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(getArguments()!= null)
        {
            categoria = getArguments().getString("categoria");
            consulta= getArguments().getString("consulta");
        }


        View view = inflater.inflate(R.layout.fragment_principal,container,false);

        sin_internet= (LinearLayout) view.findViewById(R.id.sin_internet);
        sin_internet.setVisibility(View.GONE);

        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerPrincipal);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        //list= new ArrayList<>();
        arrayTodos= new ArrayList<>();


        elegirAdapter();

        if(consulta!="")
        {
            buscar(view);
        }

        return view;

    }

    private void buscar(View view) //metodo que solo sirve para los datos harcodeados
    {
        ArrayList<Post> resultado=new ArrayList<>();
        for(Post p : arrayTodos){
            String titulo=p.getNombreRecurso();
            if(titulo.matches("(?i)(" +  consulta + ").*"))
            {
                resultado.add(p);
            }
        }

        if(resultado.isEmpty()) //si no encuentro resultados en la busqueda, muestro el mensaje
        {
            Log.e("sin datos","array");
            sin_internet.setVisibility(View.VISIBLE);
            ImageView imagen = (ImageView)view.findViewById(R.id.imgSinInternet);
            TextView texto = (TextView)view.findViewById(R.id.txtSinInternet);
            imagen.setVisibility(View.GONE);
            texto.setText("No se encontraron resultados");

        }

        adapter = new AdapterRecursos(getActivity(),resultado);
        recyclerView.setAdapter(adapter);
    }

    private void elegirAdapter()
    {
        if(new String(categoria).equals("video"))
        {
            adapter = new AdapterRecursos(getActivity(),buildVideo());
            recyclerView.setAdapter(adapter);
        }
        else if(new String(categoria).equals("audio")){
            adapter = new AdapterRecursos(getActivity(),buildAudio());
            recyclerView.setAdapter(adapter);
        }
        else if(new String(categoria).equals("pd")){
            adapter = new AdapterRecursos(getActivity(),buildPD());
            recyclerView.setAdapter(adapter);
        }
        else if(new String(categoria).equals("web")){
            adapter = new AdapterRecursos(getActivity(),buildWeb());
            recyclerView.setAdapter(adapter);
        }
        else if(new String(categoria).equals("pdf")){
            adapter = new AdapterRecursos(getActivity(),buildPdf());
            recyclerView.setAdapter(adapter);
        }
        else if(new String(categoria).equals("ci")){
            adapter = new AdapterRecursos(getActivity(),buildCi());
            recyclerView.setAdapter(adapter);
        }
        else if(new String(categoria).equals("ci")){
            adapter = new AdapterRecursos(getActivity(),buildCi());
            recyclerView.setAdapter(adapter);
        }
        else if(new String(categoria).equals("todos")){
            builTodos();
            adapter = new AdapterRecursos(getActivity(),arrayTodos);
            recyclerView.setAdapter(adapter);
        }
        else {
            // cargarLista();
        }

    }

    private void cargarLista()
    {
        final ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Cargando datos...");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_BASE + URL_JSON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array= jsonObject.getJSONArray("post");

                            for(int i=0; i< array.length();i++)
                            {
                                JSONObject o = array.getJSONObject(i);
                                Post post = new Post(
                                        o.getString("titulo"),
                                        o.getString("descripcion_corta"),
                                        o.getString("url_img_post")
                                );
                                lista.add(post);
                            }
                            if(lista.size()==0) //si la lista esta vacia, le digo q no hay internet Jaja
                            {
                                error_no_internet();
                            }
                            else
                            {
                                sin_internet.setVisibility(View.GONE);
                                adapter= new AdapterRecursos(getActivity(),lista);
                                recyclerView.setAdapter(adapter);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                error_no_internet();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void builTodos()
    {
        arrayTodos.addAll(buildAudio());
        arrayTodos.addAll(buildWeb());
        arrayTodos.addAll(buildPdf());
        arrayTodos.addAll(buildPD());
        arrayTodos.addAll(buildCi());
    }
    private void error_no_internet() //metodo por si hay algun error al traer los datos desde internet
    {
        sin_internet.setVisibility(View.VISIBLE);
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    private ArrayList<Post> BuildDummy()
    {
        ArrayList<Post> model = new ArrayList<>();
        Post rec = new Post();
        rec.setNombreRecurso("EXPERIENCIA PRIMARIA DIGITAL");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        model.add(rec);

        rec= new Post();
        rec.setNombreRecurso("VIDEO");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        rec.setTipo_recurso("video");
        model.add(rec);

        rec= new Post();
        rec.setNombreRecurso("Audio");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        rec.setTipo_recurso("audio");
        model.add(rec);

        rec= new Post();
        rec.setNombreRecurso("EXPERIENCIA PRIMARIA DIGITAL");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        model.add(rec);

        rec= new Post();
        rec.setNombreRecurso("EXPERIENCIA PRIMARIA DIGITAL");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        model.add(rec);


        return model;
    }

    private ArrayList<Post> buildAudio()
    {
        ArrayList<Post> list= new ArrayList<>();
        Post audio = new Post();

        audio.setNombreRecurso("Descripción de Romeo y Julieta");
        audio.setDescripcionCorta("Romeo y Julieta, una historia bien Gaucha");
        audio.setHastag("#Audio #Teatro");
        audio.setImagenLocal(R.drawable.audio_libro1);
        audio.setTipo_recurso("audio");
        audio.setEscuela("COLEGIO: MARIANO MORENO - LAS VARAS - CBA");
        audio.setDocente("DOCENTE: CLAUDIA RAMELLA");
        audio.setAudio_local(R.raw.romeo_julieta);
        list.add(audio);


        audio= new Post();
        audio.setNombreRecurso("Cuento aymara: “Una mágica víspera de carnaval” ");
        audio.setDescripcionCorta("Audio Libro realizado por estudiantes de nivel primario del Centro Educativo Escuela José Ingenieros");
        audio.setHastag("#Audio #Literatura");
        audio.setImagenLocal(R.drawable.audio_libro2);
        audio.setTipo_recurso("audio");
        audio.setEscuela("COLEGIO: C.E Gral. Manuel Belgrano- Río Tercero.");
        audio.setDocente("AUTORA: CARMEN MUÑOZ HURTADO");
        audio.setAudio_local(R.raw.cuento_aimara);
        list.add(audio);

        audio= new Post();
        audio.setNombreRecurso("Fábula El viento y el sol. Adaptación de la fábula de Esopo.");
        audio.setDescripcionCorta("Audio Libro realizado por estudiantes de la ESCUELA VÍCTOR MERCANTE. Sede de capacitación C.E Gral. Manuel Belgrano. Río Tercero.");
        audio.setHastag("#Audio #Literatura");
        audio.setImagenLocal(R.drawable.fondo_card);
        audio.setTipo_recurso("audio");
        audio.setEscuela("COLEGIO: ESCUELA VÍCTOR MERCANTE");
        audio.setDocente("");
        audio.setAudio_local(R.raw.fabula_el_viento);
        list.add(audio);

        audio= new Post();
        audio.setNombreRecurso("Todos Somos Diferentes. Adaptación de Pablo Zevallos");
        audio.setDescripcionCorta("Audio Libro realizado por estudiantes de la Escuela MARTIN GüEMES -Localidad. Calmayo, dpto: Calamuchita. Sede de capacitación C.E Gral. Manuel Belgrano. Río Tercero.");
        audio.setHastag("#Audio #Literatura");
        audio.setImagenLocal(R.drawable.audio_libro1);
        audio.setTipo_recurso("audio");
        audio.setEscuela("COLEGIO: MARTIN GüEMES - Localidad. Calmayo");
        audio.setDocente("");
        audio.setAudio_local(R.raw.todos_somos_diferentes);
        list.add(audio);


        return list;
    }

    private ArrayList<Post> buildWeb()
    {
        ArrayList<Post> list = new ArrayList<>();
        Post audio = new Post();
        String aux="";

        audio.setNombreRecurso("PLANIED - Orientaciones padagógicas ");
        audio.setDescripcionCorta("Esta publicación, que forma parte de la colección «Marcos pedagógicos PLANIED», incluye objetivos, abordaje y lineamientos del plan.");
        aux="Los lineamientos pedagógicos se proponen como orientaciones para promover la construcción de dispositivos transversales de innovación pedagógica, que ayuden a construir los cambios en la educación que demandan los modos emergentes de cultura y comunicación del siglo XXI.";
        aux += "Representados en diez dimensiones, son un recorte de una multiplicidad de aspectos que plantea el desafío de pensar la escuela como un espacio de encuentro con la cultura digital.";
        audio.setDescripcion(aux);
        audio.setHastag("#Primaria #Secundaria");
        audio.setImagenLocal(R.drawable.planied_compet);
        audio.setTipo_recurso("web");
        audio.setUrl_recurso("http://planied.educ.ar/wp-content/uploads/2016/04/Orientaciones_pedagogicas-1.pdf");

        list.add(audio);

        audio= new Post();

        audio.setNombreRecurso("PLANIED - Competencias de Educación Digital ");
        audio.setDescripcionCorta("Este documento de la colección «Marcos pedagógicos PLANIED» tiene como misión principal integrar la comunidad educativa en la cultura digital.");
        aux="Se busca aquí promover la alfabetización digital centrada en el aprendizaje de competencias y saberes necesarios para una inserción plena de los alumnos en la cultura contemporánea y la sociedad del futuro.";
        aux+= "El objetivo consiste en lograr el desarrollo integral de los alumnos como personas  y ciudadanos del siglo XXI, capaces de construir una mirada responsable y solidaria para transitar con confianza por distintos ámbitos sociales.";
        audio.setDescripcion(aux);
        audio.setHastag("#Primaria #Secundaria");
        audio.setImagenLocal(R.drawable.planied_orient);
        audio.setTipo_recurso("web");
        audio.setUrl_recurso("http://planied.educ.ar/wp-content/uploads/2016/04/Competencias_de_educacion_digital-1.pdf");

        list.add(audio);


        return list;
    }

    private ArrayList<Post> buildPdf()
    {
        ArrayList<Post> list = new ArrayList<>();
        Post audio = new Post();
        String aux = "";

        audio.setNombreRecurso("Edición de Audio con Audacity 2.0.0");
        audio.setDescripcionCorta("Tutorial de como editar audio con el software Audacity");
        aux = "Audacity es un so ftware de código abierto para la edición de sonidos en múltiples pistas. Con Audacity se puede grabar, reproducir y editar";
        aux+= " música y voces. Archivos de audio como wav, flac, mp3, m4a, wma y también ogg pueden ser importados y exportados. Audacity es capaz de";
        aux+= "realizar operaciones corrientes como cortar, copiar o pegar. Todos los pasos de trabajo se pueden deshacer infinitamente y se pueden añadir y";
        aux+= " mezclar efectos de sonido. Por supuesto, tiene una herramienta para nivelar el volumen fácilmente";
        audio.setDescripcion(aux);
        audio.setHastag("#Tutoriales #PDF");
        audio.setImagenLocal(R.drawable.audacity);
        audio.setTipo_recurso("pdf");
        audio.setUrl_recurso("https://drive.google.com/file/d/0B9wDhKNaUeAFdm1LaEtqZ1RJMUE/view?usp=sharing");

        list.add(audio);
        return list;
    }

    private ArrayList<Post> buildCi()
    {
        ArrayList<Post> list = new ArrayList<>();
        Post audio = new Post();
        String aux = "";

        audio.setNombreRecurso("INFOGRAFÍAS");
        audio.setDescripcionCorta("¿Qué es una infografía?, ¿Para qué sirve? y sobre todo ¿Cómo se hacen?");
        aux = "<p>Es una «combinación de elementos visuales que aporta un despliegue gráfico de la información». Este tipo de recurso se utiliza para brindar información de manera clara y concisa, a la vez que más atractiva.</>";
        aux+= " <p><b>¿Qué es una infografía?, ¿Para qué sirve? y sobre todo ¿Cómo se hacen?</b> ¡A continuación, las respuestas!</p>";
        audio.setDescripcion(aux);
        audio.setHastag("#infografias #Conectar Igualdad #Secundaria #Primaria #Docentes #Estudiantes");
        audio.setImagenLocal(R.drawable.background_ci);
        audio.setTipo_recurso("ci");
        audio.setUrl_recurso("http://conectarigualdadcordoba.com.ar/wp-content/uploads/2017/06/Infografías.pdf");

        list.add(audio);

        audio=new Post();
        audio.setNombreRecurso("AUDIOVISUALES");
        audio.setDescripcionCorta("Recomendaciones generales para crear productos audiovisuales");
        aux = "<p>En el presente documento se abordan algunas explicaciones básicas acerca de la relación entre los dispositivos de registro y de edición, que es necesario tener en cuenta en la clase con los estudiantes para poder realizar un buen diseño de producción audiovisual. Incluye instructivos para la accesibilidad en las producciones audiovisuales </p>";
        aux+= "<p> Además, se adjunta una serie de link para poder ampliar los conocimientos y tener orientaciones básicas sobre el manejo de la luz, cámara, sonido por dar algunos ejemplos.</p>";
        audio.setDescripcion(aux);
        audio.setHastag("#Audiovisuales #Conectar Igualdad #Secundaria #Primaria #Docentes #Estudiantes");
        audio.setImagenLocal(R.drawable.background_ci);
        audio.setTipo_recurso("ci");
        audio.setUrl_recurso("http://conectarigualdadcordoba.com.ar/wp-content/uploads/2017/06/Audiovisuales-Mutualismo.pdf");

        list.add(audio);

        audio=new Post();
        audio.setNombreRecurso("Actividades para aprender a Program.AR");
        audio.setDescripcionCorta("¿Por qué enseñar programación en la escuela argentina?");
        aux = "Esta pregunta no puede responderse sin antes analizar en el contexto en el que nace Program.AR, la iniciativa que se propone llevar la enseñanza y el aprendizaje de las Ciencias de la Computación a la escuela argentina. Este proyecto comenzó su gestación a mediados del año 2013, cuando las agencias gubernamentales que trabajan temas vinculados a educación y tecnología encararon la labor conjunta de analizar de qué manera la computación, su enseñanza y aprendizaje se implementaban en las aulas de nuestro país.";
        audio.setDescripcion(aux);
        audio.setHastag("#Program.ar #Secundaria #Primaria #Docentes #Estudiantes #Programación");
        audio.setImagenLocal(R.drawable.program_ar);
        audio.setTipo_recurso("ci");
        audio.setUrl_recurso("http://conectarigualdadcordoba.com.ar/wp-content/uploads/2017/06/Audiovisuales-Mutualismo.pdf");

        list.add(audio);

        audio=new Post();
        audio.setNombreRecurso("Ciudadanía Digital");
        audio.setDescripcionCorta("Un debate sobre prácticas y uso responsable de las TIC");
        aux = "Las Tecnologías de la Información y la Comunicación (TIC) nos proponen nuevas formas de interactuar e intercambiar información, y en este sentido, David Buckingham sostiene que “…las pantallas y los celulares se ha convertido en ubicuos…”, donde la realidad se vincula con lo virtual, resignificando otros espacios. En este sentido, sabemos que nues-tros jóvenes acceden constantemente a las tecnologías digitales, incluso han construido nuevas formas de relacionarse y concebir al otro, pero frecuentemente ese uso está orientado al entretenimiento, donde no siempre se consideran las consecuencias que esa interacción produce.";
        audio.setDescripcion(aux);
        audio.setHastag("#Ciudadania digital #Conectar Igualdad #Secundaria");
        audio.setImagenLocal(R.drawable.background_ci);
        audio.setTipo_recurso("ci");
        audio.setUrl_recurso("http://conectarigualdadcordoba.com.ar/wp-content/uploads/2017/06/Taller-Ciudadania_Digital.pdf");

        list.add(audio);

        audio=new Post();
        audio.setNombreRecurso("Miradas Cinéticas");
        audio.setDescripcionCorta("Cinemagrafía - Animación de infografías");
        aux = "Esta propuesta, en el marco del Programa Conectar Igualdad Córdoba, está dirigida al equipo docente frente al aula quienes actualmente tienen, entre otros objetivos, el de generar integración pedagógica de las tecnologías de la información y la comunicación (TIC) en sus prácticas de enseñanza y de aprendizaje. Promoviendo de este modo, la alfa-betización digital centrada en el aprendizaje de competencias y saberes necesarios para una inserción plena de los/as alumnos/as en la cultura contemporánea y la sociedad del futuro.";
        aux += " La presente propuesta apunta a potenciar la apropiación de los recursos de las Netbooks educativas para la exploración del lenguaje plástico visual, digital, multimedia.";
        audio.setDescripcion(aux);
        audio.setHastag("#Infografías #Conectar Igualdad #Secundaria #Docentes");
        audio.setImagenLocal(R.drawable.background_ci);
        audio.setTipo_recurso("ci");
        audio.setUrl_recurso("http://conectarigualdadcordoba.com.ar/wp-content/uploads/2017/06/Taller-Miradas-CinéTICas-sin_animacion.pdf");

        list.add(audio);

        return list;
    }

        private ArrayList<Post> buildPD()
    {
        ArrayList<Post> list = new ArrayList<>();
        Post audio = new Post();

        audio.setNombreRecurso("Preguntas Frecuentes");
        audio.setDescripcionCorta("Respuestas a preguntas frecuentes sobre Primaria Digital");
        audio.setDescripcion(preguntas());
        audio.setHastag("#Primaria Digital #Ayuda");
        audio.setImagenLocal(R.drawable.preguntas_frecuentes);
        audio.setTipo_recurso("pd");
        audio.setUrl_recurso("https://drive.google.com/file/d/0B9wDhKNaUeAFZTZyYUQzZXEzVGM/view?usp=sharing");

        list.add(audio);

        audio= new Post();

        audio.setNombreRecurso("Características y sugerencias en el uso de la PIZARRA DIGITAL");
        audio.setDescripcionCorta("Toda la información necesaria para conocer a fondo el uso de la Pizarra Digital");
        audio.setDescripcion(pizarraDigital());
        audio.setHastag("#Primaria Digital #Pizarra Digital");
        audio.setImagenLocal(R.drawable.preguntas_frecuentes);
        audio.setTipo_recurso("pd");
        audio.setUrl_recurso("https://drive.google.com/file/d/0B9wDhKNaUeAFdUNuV3gydFdCWEE/view?usp=sharing");

        list.add(audio);

        audio= new Post();

        audio.setNombreRecurso("Qué es Primaria Digital");
        audio.setDescripcionCorta("Breve explicación de la finalidad del programa y componentes del aula digital móvil");
        audio.setDescripcion(queesPD());
        audio.setHastag("#Primaria Digital #Pizarra Digital");
        audio.setImagenLocal(R.drawable.preguntas_frecuentes);
        audio.setTipo_recurso("pd");
        audio.setUrl_recurso("http://planied.educ.ar/programas/primaria-digital/");

        list.add(audio);

        return list;
    }


    private ArrayList<Post> buildVideo()
    {
        ArrayList<Post> list = new ArrayList<>();
        Post post = new Post();

        post.setNombreRecurso("Competencias de educación digital, el ciberespacio - Ma. Florencia Ripani");
        post.setVideo_id("V6aUGhecrYo");
        post.setUrlImagen("https://i.ytimg.com/vi/V6aUGhecrYo/hqdefault.jpg");
        post.setDescripcion("Te invitamos a ver un video en donde María Florencia Ripani, directora de Educación Digital y Contenidos Multiplataforma de Educar S.E., ilustra el concepto del ciberespacio.");
        post.setDescripcionCorta("Te invitamos a ver un video en donde María Florencia Ripani, directora de Educación Digital y Contenidos Multiplataforma de Educar S.E., ilustra el concepto del ciberespacio.");
        post.setHastag("#Videos #Ciberespacio");
        post.setTipo_recurso("video");
        list.add(post);

        post=new Post();
        post.setNombreRecurso("Aula digital móvil ADM PLANIED 121");
        post.setVideo_id("YjhCe1AX4Js");
        post.setUrlImagen("https://i.ytimg.com/vi/YjhCe1AX4Js/hqdefault.jpg");
        post.setDescripcion("Revisión de componentes del ADM");
        post.setDescripcionCorta("Revisión de componentes del ADM");
        post.setHastag("#Videos #Primaria Digital");
        post.setTipo_recurso("video");
        list.add(post);

        post=new Post();
        post.setNombreRecurso("Primaria Digital");
        post.setVideo_id("5rndeiJR1cU");
        post.setUrlImagen("https://i.ytimg.com/vi/5rndeiJR1cU/maxresdefault.jpg");
        post.setDescripcion("Llegada de las aulas digitales móviles a las escuelas");
        post.setDescripcionCorta("Llegada de las aulas digitales móviles a las escuelas");
        post.setHastag("#Videos #Primaria Digital");
        post.setTipo_recurso("video");
        list.add(post);

        post=new Post();
        post.setNombreRecurso("Presentación del Plan Nacional Integral de Educación Digital: PLANIED");
        post.setVideo_id("OhhOQv_xLqo");
        post.setUrlImagen("https://i.ytimg.com/vi/OhhOQv_xLqo/maxresdefault.jpg");
        post.setDescripcion("María Florencia Ripani, Directora de Educación Digital y Contenidos Multiplataforma.");
        post.setDescripcionCorta("María Florencia Ripani, Directora de Educación Digital y Contenidos Multiplataforma.");
        post.setHastag("#Videos #Primaria #Conectar Igualdad");
        post.setTipo_recurso("video");
        list.add(post);

        post=new Post();
        post.setNombreRecurso("Repensar las prácticas de enseñanza en el siglo XXI");
        post.setVideo_id("aPN1-nzeiQg");
        post.setUrlImagen("https://i.ytimg.com/vi/aPN1-nzeiQg/maxresdefault.jpg");
        post.setDescripcion("\"Planificación, gestión y evaluación\"\n" +
                "Presentación y contenido: Dr. Horacio A. Ferreyra (Subsecretario de Promoción de Igualdad y Calidad Educativa). Ministerio de Educación de la Provincia de Córdoba.");
        post.setDescripcionCorta("\"Planificación, gestión y evaluación\"\n" +
                "Presentación y contenido: Dr. Horacio A. Ferreyra (Subsecretario de Promoción de Igualdad y Calidad Educativa). Ministerio de Educación de la Provincia de Córdoba.");
        post.setHastag("#Videos #Primaria #Secundaria");
        post.setTipo_recurso("video");
        list.add(post);

        post=new Post();
        post.setNombreRecurso("Educación del futuro | Horacio Ferreyra | TEDxUniversidadCatólicadeCórdoba");
        post.setVideo_id("X-eJjZ9pz-8");
        post.setUrlImagen("https://i.ytimg.com/vi/X-eJjZ9pz-8/maxresdefault.jpg");
        post.setDescripcion("Horacio nos lleva a pensar cuales serían las principales claves para (re)pensar la educación actual y que podemos hacer para mejorarla.\n" +
                "\n" +
                "Es Doctor en Educación y Licenciado en Ciencias de la Educación, especializado en planeamiento, supervisión y administración educativa ");
        post.setDescripcionCorta("Horacio nos lleva a pensar cuales serían las principales claves para (re)pensar la educación actual y que podemos hacer para mejorarla.\n" +
                "\n" +
                "Es Doctor en Educación y Licenciado en Ciencias de la Educación, especializado en planeamiento, supervisión y administración educativa ");
        post.setHastag("#Videos #Primaria #Conectar Igualdad");
        post.setTipo_recurso("video");
        list.add(post);

        return list;
    }

    private String preguntas()
    {
        String preguntas = "<p> <b>¿Cuáles son los lineamientos pedagógicos? </b> </p>" +
                "<p> El plan se aleja de modelos instrumentalistas de abordaje a las tecnologías de la información y la comunicación (TIC). Propone un cambios en la forma de enseñar y aprender: nuevas propuestas pedagógicas y éticas." +
                "<p> Sus ejes principales —asociados a cambios culturales— están delineados en los siguientes principios: </p>" +
                "<p> •integrar la cultura digital desde la innovación pedagógica; </p>" +
                "<p> •transitar nuevos roles en la comunidad educativa;</p>" +
                "<p> •abrir la puerta al conocimiento continuo y social;</p>" +
                "<p> •hablar el lenguaje de los nuevos medios;</p>\n" +
                "<p> •explorar nuevos modos de entender y construir la realidad;</p>\n" +
                "<p> •aprender y jugar en entornos digitales;</p>\n" +
                "<p> •construir una mirada crítica, responsable y solidaria;</p>\n" +
                "<p> •garantizar el acceso a la igualdad de oportunidades y posibilidades;</p>\n" +
                "<p> •transitar el presente con la mirada puesta en el futuro;</p>\n" +
                "<p> •aprender juntos.</p>";

        preguntas += "<p><b>¿Qué contenidos hay en las máquinas de Primaria Digital?</b></p>\n" +
                "<p>Las máquinas cuentan con un «entorno multimedia para trabajar en el aula». Esto significa un conjunto de contenidos videos, juegos, etc. para aprovechar el formato digital. También se incluyen tutoriales.\n" +
                "Ahora bien, los contenidos no están cargados de la misma manera en todas las entregas del Plan y según qué sistema operativo se utiliza.</p>\n" +
                "<p>1.\tHuayra: en algunas máquinas, hay una aplicación con los contenidos, a los que se puede acceder desde cada máquina, sin conexión a internet. En otras aulas digitales móviles (ADM), los contenidos se encuentran en el servidor; al conectar las netbooks al servidor se puede acceder a un menú con todas las opciones.</p>" +
                "<p>2.\tWindows: no hay contenidos precargados. El usuario tiene que conectarse a internet, seleccionar contenidos y descargarlos. En el portal educ.ar hay contenidos educativos descargables organizados por nivel educativo y area. En Conectate hay materiales de televisión del Canal Encuentro y Pakapaka para descargar.</p>";

        preguntas += "<p><b>Tanto en Primaria Digital como en Conectar Igualdad, si tengo internet, ¿cómo puedo acceder a otros  contenidos educativos?</b></p>\n" +
                "<p>No solo es posible, sino que es deseable. Con conexión a  internet, además de a los contenidos que hay en la máquina es posible acceder a los recursos del portal educ.ar que se pueden descargar y utilizar libremente.\n" +
                "Además hay portales educativos de las provincias de la Argentina, de América Latina. Hay materiales en museos, centros culturales, etc.\n" +
                "La búsqueda de contenidos en internet requiere sistematización, criterio y cuidado. Para pensar este tema se puede consultar el artículo «Estrategias para buscar en internet» del portal <b>educ.ar</b>.</p>\n" +
                "<p><b>¿Qué pasa que no tengo internet en la escuela?</b></p>\n" +
                "<p>•\tPuedo trabajar con los contenidos y herramientas de las netbooks o del servidor. </p>\n" +
                "<p>•\tPuedo traer recursos descargados previamente en un pendrive y cargarlos en el servidor.</p>\n" +
                "\n" +
                "<p><b>¿Puedo descargar contenidos de internet y cargarlos en las máquinas?</b></p>\n" +
                "<p>Sí. Es posible cargar y descargar contenidos en las netbooks. Se pueden cargar contenidos desde discos externos o pendrives y también descargarlos desde internet. Para este último caso, disponen de un tutorial. Cuando se descarga un archivo de internet hay que tomar algunos recaudos:</p>\n" +
                "<p>•\tcomprobar que sea compatible para el sistema operativo de la netbook;</p>\n" +
                "<p>•\tcomprobar que sea gratuito y de un sitio confiable;</p>\n" +
                "<p>•\tsi el sistema le dice que va a tomar datos de su computadora, comprobar cuáles, si es pertinente y si está dispuesto a aceptarlos.</p>\n" +
                "\n" +
                "<p><b>¿Cómo puedo utilizar los recursos digitales?</b></p>\n" +
                "<p>Esta es una pregunta clave. Lo importante en la educación digital no son solo las herramientas, sino cómo utilizarlas para incorporar nuevas prácticas, innovar y trabajar de una nueva manera con la información.\n" +
                "El objetivo es que los docentes exploren libremente los recursos y los seleccionen de acuerdo con su grupo, situación y necesidades.</p>\n" +
                "<p>En un aula 1 a 1 pueden desarrollarse múltiples formas de trabajo que cada docente organizará a partir de diversas variables. Sin embargo, existen ciertas constantes y características básicas de este tipo de entornos de aprendizaje que merecen tenerse en cuenta, ya que brindan un panorama de las posibilidades didácticas, cognitivas y comunicativas del mundo educativo digital.</p>";
        return preguntas;
    }

    private String pizarraDigital()
    {
        String pizarra="<p><b>Las actividades que podés realizar con la pizarra son: </b></p>\n" +
                "\t<p> * Utilizar texto con observaciones, guardar, y que se lo puedan llevar los estudiantes, imprimir.</p>\n" +
                "<p> * Recurrir a imágenes, texto, presentaciones e intervenir sobre ellas con las herramientas de lápiz, líneas, flechas, tachado, figuras, imágenes, plantillas.</p>\n" +
                "<p> * Utilizar reflector, dirigir y centrar la atención en determinadas áreas de lo que se esta proyectando. Aplicar cortinas, que permiten ir revelando la información en pequeños pasos, descubriendo en diferentes espacios. </p>\n" +
                "<p> * Galería de imágenes por categorías, con información disponible para trabajar sobre el tablero. \n" +
                "Trabajar sobre un mapa conceptual o crearlo, con las diferentes herramientas de mano alzada o flechas, resaltador, foco, objetos.</p>\n" +
                "<p> * Generar archivos con diferentes extensiones para ser enviados a otros dispositivos. </p>\n" +
                "<p> * Refuerzo audiovisual. Trabajar e intervenir sobre videos que se proyectan.</p>\n" +
                "<p> * Intervenir sobre geolocalizadores, mapotecas, generando trayectos, ingresos, comunicaciones, distancias.</p>\n" +
                "<p> * Creaciones artísticas colaborativas.</p>\n" +
                "<p> * Matemática. Geogebra. Geometría (figuras, ángulos, superficie, perímetro y circunferencia).</p>\n" +
                "<p> *  Tablero. Dibujo técnico. </p>\n" +
                "<p> * Descubrir texto detrás de objetos.</p>\n" +
                "<p> * Cuestionarios. Duplicar, mover, tildar, indicar.</p>\n" +
                "<p> * Actividades de sitios web, por ejemplo: armado de puzzle, unir con flechas, mover imágenes, seleccionar, marcar, ordenar, etc.  </p>\n" +
                "<p> * Paseos virtuales. Simuladores.</p>\n" +
                "<p> * Trabajar sobre formatos de diarios, noticias, publicidades, prospectos, etc. </p>";
        return  pizarra;
    }

    private String queesPD()
    {
        String pd="<p>Primaria Digital tiene como fin promover la alfabetización digital entre la población infantil de la Argentina, asumiendo que el acceso a las tecnologías forma parte del derecho a una educación de calidad. Este programa está comprendido en el Plan Nacional Integral de Educación Digital (PLANIED).</p>\n" +
                "\n" +
                "<p>En el Nivel Primario, la incorporación pedagógica de las tecnologías de la información y la comunicación (TIC) se desarrolla a través de las siguientes acciones:</p>\n" +
                "\n" +
                "<p>*Equipamiento con aulas digitales móviles (ADM) para todas las escuelas primarias públicas del país.</p>\n" +
                "<p>Formación docente: Se realiza formación continua y en servicio con los equipos territoriales del PLANIED que trabajan en las escuelas primarias de todo el país, a partir de propuestas de enseñanza que toman los núcleos de aprendizaje prioritario como eje central para la mejora y renovación de las prácticas pedagógicas.</p>\n" +
                "<p><b>¿Cuáles son los componentes del aula digital móvil (ADM)?</b></p>\n" +
                "\n" +
                "<p>Desde el Ministerio de Educación y Deportes de la Nación se ha decidido equipar a las escuelas primarias con la modalidad de aula digital móvil ya que su estructura modular posibilita trabajar en distintas configuraciones (de manera local en la netbook, en red conectados al servidor, grupalmente o de manera individual).</p>\n" +
                "\n" +
                "<p>El equipamiento acompaña la flexibilidad de las diversas propuestas pedagógicas para cumplir los objetivos políticos pedagógicos del plan.</p>\n" +
                "\n" +
                "<p>El ADM se compone de:</p>\n" +
                "\n" +
                "<p>*Netbooks: cada escuela recibe un número específico de netbooks (5, 10, 20 o 30) en función de la matrícula. Por otro lado, en las primeras distribuciones de ADM se identificó una netbook para uso docente específicamente, y en el caso de las nuevas ADM, cualquier netbook puede ser utilizada por los docentes.</p>\n" +
                "<p>*Un servidor pedagógico: para su funcionamiento se incluye un monitor, un mouse y un teclado.</p>\n" +
                "<p>*Router inalámbrico.</p>\n" +
                "<p>*Proyector.</p>\n" +
                "<p>*Cámara fotográfica.</p>\n" +
                "<p>*Pendrive.</p>\n" +
                "<p>*Pizarra digital.</p>\n" +
                "<p>*Carro de guarda, carga y transporte.</p>";
        return pd;
    }



}
