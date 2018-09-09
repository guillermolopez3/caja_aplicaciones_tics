package com.gru.cajaaplicacionestics.view.FragmentPpade;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterPpade;
import com.gru.cajaaplicacionestics.auxiliares.Constantes;
import com.gru.cajaaplicacionestics.backend.VolleySingleton;
import com.gru.cajaaplicacionestics.model.ModelPpade;
import com.gru.cajaaplicacionestics.view.PdfActivity;
import com.gru.cajaaplicacionestics.view.WebViewActivity;
import com.gru.cajaaplicacionestics.view.semana_tic.ObtenerRecursosST;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCohortes extends Fragment
{
    String seccion = "";

    ModelPpade model_crono;
    ArrayList<ModelPpade> array_es;
    ArrayList<ModelPpade> array_eep;
    ArrayList<ModelPpade>  array_cl;

    AdapterPpade adapterPpade;
    RecyclerView recyclerView;
    CardView cardView;

    ImageView img_crono;
    TextView titulo_cohortes;
    Button btn_es,btn_eep,btn_cl;

    public FragmentCohortes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if(getArguments() != null)
        {
            seccion = getArguments().getString("seccion");
        }
        else {
            seccion = "cuarta_co";
            Log.e("arguments","sin arg");
        }
        //cargarLista();

        View view = inflater.inflate(R.layout.fragment_pestana_ppade, container, false);

        recyclerView = view.findViewById(R.id.recycler);


        cardView =view.findViewById(R.id.cardCronogramaPpade);
        titulo_cohortes = view.findViewById(R.id.txtTituloCohortes);
        titulo_cohortes.setVisibility(View.INVISIBLE);

        img_crono = view.findViewById(R.id.imgCronograma);

        btn_es = view.findViewById(R.id.btnES);
        btn_eep = view.findViewById(R.id.btnEep);
        btn_cl = view.findViewById(R.id.btnCl);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        array_cl = new ArrayList<>();
        array_eep = new ArrayList<>();
        array_es = new ArrayList<>();

        btn_es.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarES();
            }
        });

        btn_eep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterPpade = new AdapterPpade(getActivity(),array_eep,getFragmentManager());
                recyclerView.setAdapter(adapterPpade);
                titulo_cohortes.setText("Enseñanza entre pares");
                titulo_cohortes.setVisibility(View.VISIBLE);
            }
        });

        btn_cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterPpade = new AdapterPpade(getActivity(),array_cl,getFragmentManager());
                recyclerView.setAdapter(adapterPpade);
                titulo_cohortes.setText("Círculos de lectura");
                titulo_cohortes.setVisibility(View.VISIBLE);
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!model_crono.getUrl_link().isEmpty())
                {
                    Intent i = new Intent(getActivity(), WebViewActivity.class);
                    i.putExtra("link",model_crono.getUrl_link());
                    getActivity().startActivity(i);
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        cargarLista();
    }

    private void cargarES()
    {
        adapterPpade = new AdapterPpade(getActivity(),array_es,getFragmentManager());
        recyclerView.setAdapter(adapterPpade);
        titulo_cohortes.setText("Experiencias significativas");
        titulo_cohortes.setVisibility(View.VISIBLE);

    }

    private void cargarLista()
    {
        StringRequest request;
        VolleySingleton.getInstancia(getActivity()).
                addToRequestQueue(request=new StringRequest(Request.Method.GET,
                        Constantes.URL_PPADE_COHORTE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    //de esta manera me toma los acentos
                                    response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray array= jsonObject.getJSONArray(seccion);

                                    for(int i=0; i< array.length();i++)
                                    {
                                        JSONObject o = array.getJSONObject(i);

                                        if(o.get("sub_seccion").equals("crono"))
                                        {
                                            model_crono = new ModelPpade(
                                                    o.getString("title"),
                                                    o.getString("image"),
                                                    o.getInt("id_tipo_activity"),
                                                    o.getString("link"),
                                                    o.getString("sub_seccion")
                                            );
                                        }
                                        else if(o.get("sub_seccion").equals("es"))
                                        {
                                            ModelPpade es =  new ModelPpade(
                                                    o.getString("title"),
                                                    o.getString("image"),
                                                    o.getInt("id_tipo_activity"),
                                                    o.getString("link"),
                                                    o.getString("sub_seccion"));
                                            array_es.add(es);
                                        }
                                        else if(o.get("sub_seccion").equals("eep"))
                                        {
                                            ModelPpade ep =  new ModelPpade(
                                                    o.getString("title"),
                                                    o.getString("image"),
                                                    o.getInt("id_tipo_activity"),
                                                    o.getString("link"),
                                                    o.getString("sub_seccion"));
                                            array_eep.add(ep);
                                        }
                                        else if(o.get("sub_seccion").equals("cl"))
                                        {
                                            ModelPpade cl =  new ModelPpade(
                                                    o.getString("title"),
                                                    o.getString("image"),
                                                    o.getInt("id_tipo_activity"),
                                                    o.getString("link"),
                                                    o.getString("sub_seccion"));
                                            array_cl.add(cl);
                                        }

                                    }
                                    cargarES();
                                } catch (JSONException e) {
                                     Log.e("error jsonr",e.getMessage());
                                } catch (UnsupportedEncodingException e) {
                                    Log.e("error unsuported",e.getMessage());
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error volley",error.getMessage());
                    }
                }));
    }
}
