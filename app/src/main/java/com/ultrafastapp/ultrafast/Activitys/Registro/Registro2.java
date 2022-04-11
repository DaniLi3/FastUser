package com.ultrafastapp.ultrafast.Activitys.Registro;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.maps.android.SphericalUtil;
import com.ultrafastapp.ultrafast.Activitys.Cardsearch;
import com.ultrafastapp.ultrafast.Activitys.MenuActivity;
import com.ultrafastapp.ultrafast.Models.ClienteBookin;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.bookinProvider;
import com.ultrafastapp.ultrafast.R;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registro2 extends AppCompatActivity {

    TextView cel, correo;
    Button boton;
    CircleImageView next;
    CircleImageView nextt;
    String cityOrigen=" ";
    ClienteBookin clienteBookin;
    Authprovider authprovider;
    Bundle bundle = new Bundle();
    com.ultrafastapp.ultrafast.Providers.bookinProvider bookinProvider;


    private SupportMapFragment mapFragment;
    private boolean marcador = false;
    String extraNombre, extraApelli;
    private boolean mOriginSelect = true;

    private String mDestino;
    private LocationRequest mLocationrequest;
    private GoogleMap.OnCameraIdleListener mcameraListener;
    LatLng mOriginlnLo;
    private LatLng mlatLng;
    String origen;



    private final static int SETTINGS_REQUEAST = 2;

    private PlacesClient mplaces;
    private AutocompleteSupportFragment mAutocomplete;

    private final int REQUEST_CHECHK_SETIING = 0x1;

    private SupportMapFragment mmapFragment;
    GoogleMap mMapM;
    private final static int LOCATION_REQUEST_CODE = 1;

    private GoogleApiClient mGoogleApiClient;

    private CircleImageView mCircleImageBack;

    private FusedLocationProviderClient mFusedLocation;
    private Marker marker;

    LocationCallback mLocationCallbal = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                if (marcador==false) {
                    if (getApplicationContext() != null) {
                    /*marker = mMapM.addMarker(new MarkerOptions().position(
                            new LatLng(location.getLatitude(), location.getLongitude())).title("Tu posicion").icon(BitmapDescriptorFactory.fromResource(R.drawable.point)));*/

                        mlatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        mMapM.moveCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder()
                                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                        .zoom(15f)
                                        .build()
                        ));
                        LimitadSearch();
                        marcador = true;
                    }
                    else{

                    }
                }


            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);
        next = findViewById(R.id.fin);
        nextt = findViewById(R.id.atrass);
        origen=getIntent().getStringExtra("origen");

        bookinProvider = new bookinProvider();
        authprovider = new Authprovider();

        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this::onMapReady);
        mGoogleApiClient = getApiCientInstance();
        if (mGoogleApiClient!=null)
        {
            mGoogleApiClient.connect();
        }
        checklocationpermiss();
        startLocation();


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        }
        mplaces = Places.createClient(this);
        instanceAutocompleteOrigin();
        onCameramove();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!equals(""))
                {
                    if (cityOrigen.equals("Centro")||cityOrigen=="Centro"||cityOrigen==null||cityOrigen==" ")
                    {
                        Toast.makeText(Registro2.this, "Por favor arrastra el icono a otro punto "+cityOrigen, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Savedata(origen,mDestino);
                        Intent intent = new Intent(v.getContext(), MenuActivity.class);
                        bundle.putString("fromDestino", mDestino);
                        bundle.putString("latdestino", String.valueOf(mOriginlnLo.latitude));
                        bundle.putString("logdestino", String.valueOf(mOriginlnLo.longitude));
                        bundle.putString("cityDestino", String.valueOf(cityOrigen));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }


            }
        });
        nextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!equals(""))
                {
                    Intent intentt = new Intent(Registro2.this, Registro1.class);
                    startActivity(intentt);
                    finish();
                }


            }
        });


    }

    private void LimitadSearch() {
        LatLng northSide = SphericalUtil.computeOffset(mlatLng, 5000, 0);
        LatLng southSide = SphericalUtil.computeOffset(mlatLng, 5000, 180);
        //  mAutocomplete.setCountry("MX");
        mAutocomplete.setLocationBias(RectangularBounds.newInstance(southSide, northSide));
        mAutocomplete.setCountry("MX");

        mAutocomplete.setLocationBias(RectangularBounds.newInstance(southSide, northSide));

    }

    private void instanceAutocompleteOrigin() {

        mAutocomplete = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.placeAutoCompleteDes);
        mAutocomplete.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));
        mAutocomplete.setHint("Selecionar destino");
        mAutocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                mDestino = place.getName();
                mOriginlnLo = place.getLatLng();
                mMapM.moveCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder()
                                .target(new LatLng(mOriginlnLo.latitude, mOriginlnLo.longitude))
                                .zoom(15f)
                                .build()
                ));

            }

            @Override
            public void onError(@NonNull Status status) {
            }
        });
    }
    public void onCameramove()
    {
        mcameraListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                try {
                    Geocoder geocoder = new Geocoder(Registro2.this);

                    mOriginlnLo = mMapM.getCameraPosition().target;
                    List<Address> addressList = geocoder.getFromLocation(mOriginlnLo.latitude, mOriginlnLo.longitude, 1);
                    cityOrigen = addressList.get(0).getLocality().trim();
                    String pais = addressList.get(0).getCountryName();
                    String addres = addressList.get(0).getAddressLine(0);
                    mDestino = addres + " " + cityOrigen;
                    mAutocomplete.setText(addres + " " + cityOrigen);
                    mOriginSelect = false;


                } catch (Exception e) {
                    Log.d("Error" + " Error", e.getMessage());
                }

            }
        };

    }


    public void onMapReady(GoogleMap googleMap) {
        mMapM = googleMap;
        mMapM.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMapM.getUiSettings().setZoomControlsEnabled(true);

        mMapM.setOnCameraIdleListener(mcameraListener);
        mLocationrequest = new LocationRequest();
        mLocationrequest.setInterval(1000);
        mLocationrequest.setFastestInterval(1000);
        mLocationrequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationrequest.setSmallestDisplacement(5);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }



        startLocation();
        //mMapM.setMyLocationEnabled(true);


    }
    private boolean gpsActive()
    {
        boolean isActv=false;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            isActv=true;

        }
        return isActv;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (gpsActive()) {
                        mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallbal, Looper.myLooper());


                    }
                    else {
                        // alertDialog();
                        RequestGPSSetting();
                    }
                } else {
                    checklocationpermiss();

                }
            } else {
                checklocationpermiss();

            }

        }
    }
    public void     Savedata(String origen, String Destino)
    {

        clienteBookin = new ClienteBookin();
        clienteBookin.setIdClient(authprovider.getid());
        clienteBookin.setOrigin(origen);
        clienteBookin.setDestination(Destino);
        bookinProvider.create(clienteBookin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(Registro2.this, "Estos son los viajes más cercanos", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registro2.this, Cardsearch.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(Registro2.this, "Algo fallo", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
    private void checklocationpermiss()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                new AlertDialog.Builder(this)
                        .setTitle("Proporciona los permisos para continuar")
                        .setMessage("Esta aplicacion necesita permisos para usarse")
                        .setPositiveButton("Otorgar permisos", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(Registro2.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);


                            }
                        })
                        .create()
                        .show();

            }
            else
            {
                ActivityCompat.requestPermissions(Registro2.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEAST && gpsActive()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallbal, Looper.myLooper());
            mMapM.setMyLocationEnabled(true);
        }
        else if (requestCode == SETTINGS_REQUEAST && !gpsActive()){
            //alertDialog();
            RequestGPSSetting();

        }
    }

    private void alertDialog()
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Por favor activa tu ubicación para continuar")
                .setPositiveButton("Configuraciones", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), SETTINGS_REQUEAST);
                    }
                }).create().show();
    }
    private void startLocation()
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            {
                if (gpsActive())
                {

                    mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallbal, Looper.myLooper());
                }
                else
                {
                    //alertDialog();
                    RequestGPSSetting();
                }
            }
            else
            {
                checklocationpermiss();
            }

        }
        else {
            if (gpsActive())
            {
                mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallbal, Looper.myLooper());
            }
            else
            {
                // alertDialog();
                RequestGPSSetting();

            }
        }

    }
    private void RequestGPSSetting() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationrequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                if (status.getStatusCode() == LocationSettingsStatusCodes.SUCCESS) {
                    Toast.makeText(Registro2.this, "El gps ya está activado", Toast.LENGTH_SHORT).show();
                } else if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                    try {
                        status.startResolutionForResult(Registro2.this, REQUEST_CHECHK_SETIING);

                    } catch (IntentSender.SendIntentException e) {
                        Toast.makeText(Registro2.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if (ActivityCompat.checkSelfPermission(Registro2.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Registro2.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallbal, Looper.myLooper());
                    mMapM.setMyLocationEnabled(false);

                } else if (status.getStatusCode() == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE) {
                    Toast.makeText(Registro2.this, "La configuracion del gps tiene un error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private GoogleApiClient getApiCientInstance() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).build();
        return googleApiClient;

    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

}