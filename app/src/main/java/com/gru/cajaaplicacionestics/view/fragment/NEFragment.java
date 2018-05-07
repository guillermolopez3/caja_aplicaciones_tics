package com.gru.cajaaplicacionestics.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterNPost;
import com.gru.cajaaplicacionestics.auxiliares.ContenidoNE;
import com.gru.cajaaplicacionestics.model.ModelNE;
import com.gru.cajaaplicacionestics.model.ModelPost;

import java.util.ArrayList;
import java.util.List;

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
        List<ModelPost> array= new ArrayList<>();
        if(seccion.equals("cronograma")){
            array = ContenidoNE.cronograma();
        }
        else if (seccion.equals("jornada")){
            array = ContenidoNE.jornadas();
        }
        else if (seccion.equals("orientacion")){
            array = ContenidoNE.orientacion();
        }
        else if(seccion.equals("recomendaciones"))
        {
            array = ContenidoNE.recomendaciones();
        }
        for(ModelPost post : array)
        {
            adapter.add(post);
        }
    }

}
