package com.gru.cajaaplicacionestics.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.backend.PaginacionPost;
import com.srx.widget.PullToLoadView;

public class FragmentAprenConec extends Fragment implements PaginationErrorCallBack
{
    private int seccion;
    PullToLoadView pullToLoadView; //se encarga de ir llenando el recycler

    public FragmentAprenConec(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aprender_conectados,container,false);

        if(getArguments()!= null)
        {
            seccion = getArguments().getInt("id");
        }

        pullToLoadView      = view.findViewById(R.id.recyclerPd);

        iniciarPaginacion();
        return view;
    }

    private void iniciarPaginacion()
    {
        if(pullToLoadView.getVisibility()==View.GONE)
        {
            pullToLoadView.setVisibility(View.VISIBLE);
        }

        new PaginacionPost(getActivity(),pullToLoadView,seccion).iniciarPaginacion();
    }

    @Override
    public void reintentar(int arrayCount, @Nullable Boolean hayError) {

    }
}
