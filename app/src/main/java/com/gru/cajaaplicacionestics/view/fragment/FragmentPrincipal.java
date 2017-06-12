package com.gru.cajaaplicacionestics.view.fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.adapter.AdapterRecursos;
import com.gru.cajaaplicacionestics.model.ModelRecursos;

import java.util.ArrayList;

/**
 * Created by guill on 10/05/2017.
 */

public class FragmentPrincipal extends Fragment
{
    String consulta;
    public FragmentPrincipal(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal,container,false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerPrincipal);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        AdapterRecursos adapterRecursos = new AdapterRecursos(getActivity(),BuildDummy());
        recyclerView.setAdapter(adapterRecursos);
        return view;

    }

    private ArrayList<ModelRecursos> BuildDummy()
    {
        ArrayList<ModelRecursos> model = new ArrayList<>();
        ModelRecursos rec = new ModelRecursos();
        rec.setNombreRecurso("EXPERIENCIA PRIMARIA DIGITAL");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        model.add(rec);

        rec= new ModelRecursos();
        rec.setNombreRecurso("EXPERIENCIA PRIMARIA DIGITAL");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        model.add(rec);

        rec= new ModelRecursos();
        rec.setNombreRecurso("EXPERIENCIA PRIMARIA DIGITAL");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        model.add(rec);

        rec= new ModelRecursos();
        rec.setNombreRecurso("EXPERIENCIA PRIMARIA DIGITAL");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        model.add(rec);

        rec= new ModelRecursos();
        rec.setNombreRecurso("EXPERIENCIA PRIMARIA DIGITAL");
        rec.setDescripcionCorta("La comunidad educativa del C.E. Usandivaras nos cuenta sobre la experiencia que tuvieron en la integración de las TIC después de la llegada de las ADM");
        rec.setHastag("#Primaria");
        model.add(rec);


        return model;
    }
}
