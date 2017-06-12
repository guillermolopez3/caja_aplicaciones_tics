package com.gru.cajaaplicacionestics.view.fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gru.cajaaplicacionestics.R;

/**
 * Created by guill on 10/05/2017.
 */

public class Fragment2 extends Fragment
{
    public Fragment2(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        String data="";
        if(getArguments()!= null)
        {
            data = getArguments().getString("dato");
        }



        View view = inflater.inflate(R.layout.fragment_prueba2,container,false);

        TextView texto = (TextView)view.findViewById(R.id.txtPrueba);
        texto.setText(data);

        return  view;
    }
}
