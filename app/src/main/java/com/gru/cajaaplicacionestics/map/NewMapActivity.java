package com.gru.cajaaplicacionestics.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;

public class NewMapActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private static final int LOCATION_REQUEST_CODE = 1;

    private FloatingActionButton fab;
    private ImageButton btn_bajar;
    private Button btnCba,btnRio,btnSan,btnMaria;
    private GoogleMap map;
    private BottomSheetBehavior bsb;
    private Toolbar toolbar;
    private SupportMapFragment mpFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conteiner_maps);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        mpFragment.getMapAsync(this);

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
                getSupportActionBar().setTitle("Villa María - Tecnoteca");
            }
        });
        btnCba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                establecerCoordenadas("cba");
                collapsedBottonSheetAndShowFab();
                getSupportActionBar().setTitle("Córdoba - Quórum Hotem");
            }
        });
        btnSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                establecerCoordenadas("san");
                collapsedBottonSheetAndShowFab();
                getSupportActionBar().setTitle("San Francisco - Superdomo");
            }
        });
        btnRio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                establecerCoordenadas("rio");
                collapsedBottonSheetAndShowFab();
                getSupportActionBar().setTitle("Río Cuarto - Salón Blanco de la Municipalidad");
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }
        establecerCoordenadas("cba");
    }

    private void establecerCoordenadas(String lugar)
    {
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
        View toolBar = ((View)mpFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("4"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) toolBar.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 300, 300);
    }
}
