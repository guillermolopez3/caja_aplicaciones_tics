package com.gru.cajaaplicacionestics.view.semana_tic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gru.cajaaplicacionestics.R;

public class FragmentMapaST extends Fragment implements OnMapReadyCallback
{
    private static final int LOCATION_REQUEST_CODE = 1;

    private FloatingActionButton fab;
    private ImageButton btn_bajar;
    private Button btnCba,btnRio,btnSan,btnMaria;

    private GoogleMap map;
    private MapView mapView;

    private BottomSheetBehavior bsb;
    private Toolbar toolbar;
    private SupportMapFragment mpFragment;
    private View view;
    private TextView titulo;

    public FragmentMapaST(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.map_dentro_fragment,container,false);

        toolbar = view.findViewById(R.id.toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Córdoba - Quórum Hotem");
        //getSupportActionBar().setTitle("Córdoba - Quórum Hotem");
        titulo = view.findViewById(R.id.txtTituloToolbar);
        titulo.setText("Córdoba - Quórum Hotem");


        fab         = view.findViewById(R.id.fab);
        btn_bajar   = view.findViewById(R.id.cerrarMenuBottonSeet);

        LinearLayout bts = view.findViewById(R.id.bottomSheet);
        btnCba      = view.findViewById(R.id.btnCba);
        btnRio      = view.findViewById(R.id.btnRio);
        btnSan      = view.findViewById(R.id.btnSan);
        btnMaria    = view.findViewById(R.id.btnMaria);

        bsb = BottomSheetBehavior.from(bts);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView)view1.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();;
        //mpFragment =(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //mpFragment.getMapAsync(this);
        mapView.getMapAsync(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
                fab.hide();
            }
        });

        btn_bajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedBottonSheetAndShowFab();
            }
        });

        btnMaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                establecerCoordenadas("maria");
                collapsedBottonSheetAndShowFab();
                //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Villa María - Tecnoteca");
                titulo.setText("Villa María - Tecnoteca");
            }
        });
        btnCba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                establecerCoordenadas("cba");
                collapsedBottonSheetAndShowFab();
                //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Córdoba - Quórum Hotem");
                titulo.setText("Córdoba - Quórum Hotem");
            }
        });
        btnSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                establecerCoordenadas("san");
                collapsedBottonSheetAndShowFab();
                //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("San Francisco - Superdomo");
                titulo.setText("San Francisco - Superdomo");
            }
        });
        btnRio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                establecerCoordenadas("rio");
                collapsedBottonSheetAndShowFab();
                //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Río Cuarto - Salón Blanco de la Municipalidad");
                titulo.setText("Río Cuarto - Salón Blanco de la Municipalidad");
            }
        });

    }

    private void collapsedBottonSheetAndShowFab()
    {
        bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
        fab.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Controles UI
       /* if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }*/
        establecerCoordenadas("cba");
    }

    private void establecerCoordenadas(String lugar)
    {
        String titulo = "";
        LatLng coordenadas = new LatLng(-31.337208, -64.207352);;
        if(lugar.equals("rio"))
        {
            coordenadas = new LatLng(-33.132513, -64.342408);
        }
        else if(lugar.equals("maria"))
        {
            coordenadas = new LatLng(-32.410745, -63.245819);
        }
        else if(lugar.equals("san"))
        {
            coordenadas = new LatLng(-31.430378, -62.080995);
        }

        MarkerOptions markerOptions= new MarkerOptions().position(coordenadas).title("Toca para ver como llegar");
        Marker marker=map.addMarker(markerOptions);
        marker.showInfoWindow();
        map.getUiSettings().setMapToolbarEnabled(true);

        CameraPosition position = CameraPosition.builder().target(coordenadas).zoom(15).build();

        map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
        modevrToolBarMap();
    }

    //mover la posición del toolbar (como llegar) del mapa
    private void modevrToolBarMap()
    {
        View toolBar = ((View)mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("4"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) toolBar.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 300, 370);
    }
}
