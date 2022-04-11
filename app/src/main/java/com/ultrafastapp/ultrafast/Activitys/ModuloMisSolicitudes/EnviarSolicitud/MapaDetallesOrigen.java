package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.EnviarSolicitud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ultrafastapp.ultrafast.R;

public class MapaDetallesOrigen extends AppCompatActivity {
    double Origenlat;
    double Origenlog;
    LatLng OrigenLatLog;
    GoogleMap mMap;
    SupportMapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_detalles_origen);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this::onMapReady);

        Origenlat=getIntent().getDoubleExtra("OrigenLat",0);
        Origenlog=getIntent().getDoubleExtra("OrigenLog",0);

        OrigenLatLog= new LatLng(Origenlat,Origenlog);


    }

    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.addMarker(new MarkerOptions().position(OrigenLatLog).title("Origen").icon(BitmapDescriptorFactory.fromResource(R.drawable.point)));


        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(OrigenLatLog)
                        .zoom(14f)
                        .build()
        ));
    }

}