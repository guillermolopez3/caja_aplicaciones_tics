package com.gru.cajaaplicacionestics.view.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.backend.PaginacionNovedades;
import com.srx.widget.PullToLoadView;


public class FragmentNovedades extends Fragment {

    private String año;
    private PullToLoadView pullToLoadView;

    public FragmentNovedades(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_novedad,container,false);
        año= getArguments().getString("año");
        pullToLoadView = v.findViewById(R.id.recyclerNovedades);
        new PaginacionNovedades(getActivity(),pullToLoadView).iniciarPaginacion(año);
        return v;
    }

}
