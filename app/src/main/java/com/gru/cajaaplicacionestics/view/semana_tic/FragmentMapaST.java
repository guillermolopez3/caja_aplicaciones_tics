package com.gru.cajaaplicacionestics.view.semana_tic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.gru.cajaaplicacionestics.R;

public class FragmentMapaST extends Fragment implements OnMapReadyCallback
{
    private static final int LOCATION_REQUEST_CODE = 1;

    private FloatingActionButton fab;
    private ImageButton btn_bajar;
    private Button btnCba,btnRio,btnSan,btnMaria;
    private GoogleMap map;
    private BottomSheetBehavior bsb;
    private Toolbar toolbar;
    private SupportMapFragment mpFragment;
    View view;

    public FragmentMapaST(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.conteiner_maps,container,false);

        toolbar = view.findViewById(R.id.toolbar);
       /* view.setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Córdoba - Quórum Hotem");


        fab         = findViewById(R.id.fab);
        btn_bajar   = findViewById(R.id.cerrarMenuBottonSeet);

        LinearLayout bts = findViewById(R.id.bottomSheet);
        btnCba      = findViewById(R.id.btnCba);
        btnRio      = findViewById(R.id.btnRio);
        btnSan      = findViewById(R.id.btnSan);
        btnMaria    = findViewById(R.id.btnMaria);

        bsb = BottomSheetBehavior.from(bts);

        mpFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mpFragment.getMapAsync(this);*/

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
