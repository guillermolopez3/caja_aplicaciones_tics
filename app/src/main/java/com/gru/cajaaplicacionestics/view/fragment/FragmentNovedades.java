package com.gru.cajaaplicacionestics.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.backend.PaginacionNovedades;
import com.srx.widget.PullToLoadView;

/**
 * Created by guill on 02/03/2018.
 */

public class FragmentNovedades extends Fragment {

    private static FragmentNovedades instance;
    private String año;

    private PullToLoadView pullToLoadView;

    public static FragmentNovedades newInstance()
    {
        if(instance==null){
            instance= new FragmentNovedades();
        }
        return instance;
    }
    public FragmentNovedades(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_novedad,container,false);


        año= getArguments().getString("año");
        Log.e("año","" + año);

        pullToLoadView = v.findViewById(R.id.recyclerNovedades);
        new PaginacionNovedades(getActivity(),pullToLoadView).iniciarPaginacion(año);
        return v;
    }



}
