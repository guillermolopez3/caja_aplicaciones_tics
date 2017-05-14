package com.gru.cajaaplicacionestics.view.fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gru.cajaaplicacionestics.R;

/**
 * Created by guill on 10/05/2017.
 */

public class FragmentPrincipal extends Fragment
{
    public FragmentPrincipal(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prueba,container,false);
        return view;

    }
}
