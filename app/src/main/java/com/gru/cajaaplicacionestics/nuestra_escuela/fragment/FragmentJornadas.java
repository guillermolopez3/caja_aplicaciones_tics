package com.gru.cajaaplicacionestics.nuestra_escuela.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.nuestra_escuela.adapter.SectionRecyclerViewAdapter;
import com.gru.cajaaplicacionestics.nuestra_escuela.auxiliares.IReturnPeticionGet;
import com.gru.cajaaplicacionestics.nuestra_escuela.backend.ObtenerRecursosJornadasNE;
import com.gru.cajaaplicacionestics.nuestra_escuela.model.SectionRecyclerNEModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentJornadas extends Fragment implements IReturnPeticionGet
{
    private ArrayList<SectionRecyclerNEModel> lista;
    private SectionRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    public FragmentJornadas(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_recycler,container,false);

        progressBar  = view.findViewById(R.id.progress);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        lista = new ArrayList<>();

        ObtenerRecursosJornadasNE recursos = new ObtenerRecursosJornadasNE();
        recursos.setRetornoArrayDatos(this);
        recursos.obtenerJson(getActivity());
        return view;
    }


    @Override
    public void returnArray(ArrayList<SectionRecyclerNEModel> rta) {
        Log.e("respuesta", rta.size() + "");
        progressBar.setVisibility(View.GONE);
        if(rta != null){
            lista = rta;
            adapter = new SectionRecyclerViewAdapter(getActivity(), lista);
            recyclerView.setAdapter(adapter);

        }else {
            Toast.makeText(getActivity(), "Verifique la conexión a internet", Toast.LENGTH_SHORT).show();
        }

    }
}
