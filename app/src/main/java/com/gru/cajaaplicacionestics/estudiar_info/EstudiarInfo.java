package com.gru.cajaaplicacionestics.estudiar_info;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.estudiar_info.adapter.EstudiarAdapter;
import com.gru.cajaaplicacionestics.estudiar_info.model.CarrerasModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstudiarInfo extends Fragment {

    RecyclerView recycler;
    ProgressBar progressBar;
    EstudiarAdapter adapter;

    public EstudiarInfo() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        recycler = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recycler.setHasFixedSize(true);
        adapter = new EstudiarAdapter(getCarreras(),getActivity());
        recycler.setAdapter(adapter);

        return view;
    }


    private ArrayList<CarrerasModel> getCarreras()
    {
        ArrayList<CarrerasModel> listModels = new ArrayList<>();
        CarrerasModel carrera;

        carrera = new CarrerasModel(1,"Analista de Sistemas","Instituto Santo Domingo", "3");
        listModels.add(carrera);

        carrera = new CarrerasModel(2,"Ing. en Sistemas","Instituto Santo Domingo", "5");
        listModels.add(carrera);

        carrera = new CarrerasModel(1,"Analista de Sistemas","Instituto Santo Domingo", "3");
        listModels.add(carrera);

        carrera = new CarrerasModel(2,"Ing. en Sistemas","Instituto Santo Domingo", "5");
        listModels.add(carrera);

        carrera = new CarrerasModel(1,"Analista de Sistemas","Instituto Santo Domingo", "3");
        listModels.add(carrera);

        carrera = new CarrerasModel(2,"Ing. en Sistemas","Instituto Santo Domingo", "5");
        listModels.add(carrera);

        return listModels;
    }

}
