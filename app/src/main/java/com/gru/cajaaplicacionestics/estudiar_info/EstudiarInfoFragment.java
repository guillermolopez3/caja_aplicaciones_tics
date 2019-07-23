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
import com.gru.cajaaplicacionestics.backend.PaginacionNovedades;
import com.gru.cajaaplicacionestics.estudiar_info.adapter.EstudiarAdapter;
import com.gru.cajaaplicacionestics.estudiar_info.backend.PaginacionEstudiar;
import com.gru.cajaaplicacionestics.estudiar_info.backend.PaginacionInstitucion;
import com.gru.cajaaplicacionestics.estudiar_info.model.CarrerasModel;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstudiarInfoFragment extends Fragment {

    private String seccion;

    public EstudiarInfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novedad,container,false);

        if(getArguments()!= null)
        {
            seccion = getArguments().getString("seccion");
        }
        /*recycler = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recycler.setHasFixedSize(true);
        adapter = new EstudiarAdapter(getCarreras(),getActivity());
        recycler.setAdapter(adapter);

        return view;*/
        PullToLoadView pullToLoadView = view.findViewById(R.id.recyclerNovedades);
        if(seccion.equals("carrera"))
        {
            new PaginacionEstudiar(getActivity(),pullToLoadView).iniciarPaginacion();
        }
        else if(seccion.equals("institucion"))
        {
            new PaginacionInstitucion(getActivity(),pullToLoadView).iniciarPaginacion();
        }

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
