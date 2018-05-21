package com.gru.cajaaplicacionestics.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        adapter = new AdapterNPost(getActivity());
        cargarArray();
        recyclerView.setAdapter(adapter);

        return view;
    }


    private void cargarArray()
    {
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
    }

}
