package com.gru.cajaaplicacionestics.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.backend.Paginacion;
import com.srx.widget.PullToLoadView;

/**
 * Created by guill on 21/11/2017.
 */

public class FragmentRecursosNIvel extends Fragment
{
    private String NIVEL="";
    PullToLoadView pullToLoadView; //se encarga de ir llenando el recycler

    private static FragmentRecursosNIvel instancia;

    public static FragmentRecursosNIvel getInstancia()
    {
        if(instancia==null)
        {
            instancia= new FragmentRecursosNIvel();
            Log.e("crea una","nueva instancia");
        }
        return instancia;
    }

    public FragmentRecursosNIvel(){}

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(getArguments()!=null)
        {
            NIVEL= getArguments().getString("nivel");
        }

        View view = inflater.inflate(R.layout.fragment_recursos_nivel,container,false);

       pullToLoadView=(PullToLoadView)view.findViewById(R.id.recyclerRecursos);
       new Paginacion(getActivity(),pullToLoadView,4).iniciarPaginacionRecursosxNivel(NIVEL);


        return view;
    }
}
