package com.gru.cajaaplicacionestics.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gru.cajaaplicacionestics.R;

public class FragmentSalaA extends Fragment
{
    private static FragmentSalaA instancia;

    public static FragmentSalaA getInstancia()
    {
        if(instancia==null)
        {
            instancia= new FragmentSalaA();
            Log.e("crea una","nueva instancia");
        }
        return instancia;
    }

    public FragmentSalaA() {
     }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_sala, container, false);
    }


}
