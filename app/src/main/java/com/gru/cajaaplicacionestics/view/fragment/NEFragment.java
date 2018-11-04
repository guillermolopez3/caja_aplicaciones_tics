package com.gru.cajaaplicacionestics.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterNPost;
import com.gru.cajaaplicacionestics.backend.RecursosNE;

public class NEFragment extends Fragment {

    RecyclerView recyclerView;
    AdapterNPost adapter;
    String seccion="cronograma";

    public NEFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ne, container, false);

        if(getArguments() != null)
        {
            seccion = getArguments().getString("seccion");
        }

        recyclerView = view.findViewById(R.id.recyclerFragmentNe);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapter = new AdapterNPost(getActivity(),false);
        cargarArray();
        recyclerView.setAdapter(adapter);

        return view;
    }


    private void cargarArray()
    {
        Log.e("seccion en fragment", seccion);
        if(seccion.equals("cronograma")){
           RecursosNE.obtenerRecursos("http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/orientacion.json"
                    ,getActivity(),"cronograma",adapter);
        }
        else if (seccion.equals("jornada")){
            RecursosNE.obtenerRecursos("http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/orientacion.json"
                    ,getActivity(),"jornadas",adapter);
        }
        else if (seccion.equals("orientacion")){
           RecursosNE.obtenerRecursos("http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/orientacion.json"
                    ,getActivity(),"orientacion",adapter);
        }
        else if(seccion.equals("recomendaciones"))
        {
            RecursosNE.obtenerRecursos("http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/orientacion.json"
                    ,getActivity(),"recomendaciones",adapter);
        }
        else if(seccion.equals("verEscuchar"))
        {
            RecursosNE.obtenerRecursos("http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/orientacion.json"
                    ,getActivity(),"verEscuchar",adapter);
        }
        else if(seccion.equals("circulo"))
        {
            RecursosNE.obtenerRecursos("http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/orientacion.json"
                    ,getActivity(),"circulo",adapter);
        }
        else if(seccion.equals("ateneo_modelo_aval"))
        {
            RecursosNE.obtenerRecursos("http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/orientacion.json"
                    ,getActivity(),"ateneo_modelo_aval",adapter);
        }
        else if(seccion.equals("ateneo_actividad"))
        {
            RecursosNE.obtenerRecursos("http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/orientacion.json"
                    ,getActivity(),"ateneo_actividad",adapter);
        }
        else if(seccion.equals("ateneo_memo"))
        {
            RecursosNE.obtenerRecursos("http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/orientacion.json"
                    ,getActivity(),"ateneo_memo",adapter);
        }
        // links para la semana tic
        else if(seccion.equals("agenda"))
        {
            RecursosNE.obtenerRecursos("http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/semana_tic.json"
                    ,getActivity(),"agenda",adapter);
        }
        else if(seccion.equals("noticia"))
        {
            RecursosNE.obtenerRecursos("http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/semana_tic.json"
                    ,getActivity(),"noticia",adapter);
        }
        // fin semana tic
        else {
            RecursosNE.obtenerRecursos("http://www.igualdadycalidadcba.gov.ar/CajaTIC/js/orientacion.json"
                    ,getActivity(),seccion,adapter);
        }
    }

}
