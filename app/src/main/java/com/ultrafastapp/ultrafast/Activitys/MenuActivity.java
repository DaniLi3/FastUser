
package com.ultrafastapp.ultrafast.Activitys;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.TapMisViajes;
import com.ultrafastapp.ultrafast.Activitys.fragmentos.FirstFragment;
import com.ultrafastapp.ultrafast.Activitys.fragmentos.ThirdFragment;
import com.ultrafastapp.ultrafast.Activitys.fragmentos.Viajescercadeti;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.Providers.TokenProvider;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.Utils.VerificarConexionaInternet;

import java.util.List;

public class MenuActivity extends AppCompatActivity implements OnMapReadyCallback {
    String texto="";
    String Origen="";
    String Destino="";
    String OrigenLat="";
    String OrigenLog="";
    String DestinoLat="";
    String DestinoLog="";
    String cityOrigen="";
    String cityDestino="";
    Choferprovider choferprovider;
    String loco="";
    Authprovider authprovider;
    ProgressDialog  progressDialog;
    private TokenProvider mTokenprovider;

    FirstFragment firstFragment=new FirstFragment();
    Viajescercadeti viajescercadeti=new Viajescercadeti();
    ThirdFragment thirdFragment=new ThirdFragment();

    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;
    private LatLng mlatLng;
    GoogleMap mMapM;
    private GoogleMap.OnCameraIdleListener mcameraListener;
    LatLng mOriginlnLo;
    private LocationRequest mLocationrequest;
    private final int REQUEST_CHECHK_SETIING = 0x1;
    private final static int LOCATION_REQUEST_CODE = 1;
    private final static int SETTINGS_REQUEAST = 2;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocation;
    private SupportMapFragment mapFragment;

    LocationCallback mLocationCallbal = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {


            for (Location location : locationResult.getLocations()) {
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
                    progressDialog.dismiss();
                    Log.d("solotu", String.valueOf(mlatLng));
                } else {
                    Log.d("solotu", "as");
                    progressDialog.dismiss();
                }
            }
        }

    };

   /* @Override
   public void onBackPressed(){
     /*   if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
        int count = getFragmentManager().getBackStackEntryCount();
        Log.d("zombi", String.valueOf(count));

        if (count == 0) {
            super.onBackPressed();
            getFragmentManager().popBackStack();
            if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
                super.onBackPressed();
                return;
            }else {
                Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
            }
            tiempoPrimerClick = System.currentTimeMillis();


        } else {
            Log.d("zombi","no");

            getFragmentManager().popBackStack();//No se porqué puse lo mismo O.o
        }

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle bundle = getIntent().getExtras();
        mGoogleApiClient = getApiCientInstance();
        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this::onMapReady);
        progressDialog = new ProgressDialog(MenuActivity.this);

        progressDialog.setMessage("Espere un momento..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (mGoogleApiClient!=null)
        {
            mGoogleApiClient.connect();
        }

        // checklocationpermiss();

        startLocation();

        mTokenprovider=new TokenProvider();
        onCameramove();


        if (bundle!=null)
        {
            loco= bundle.getString("locoo");
            texto = bundle.getString("textFromActivityA");

            Origen = bundle.getString("fromOrigen");
            OrigenLat = bundle.getString("latorigen");
            OrigenLog = bundle.getString("logorigen");
            DestinoLat= bundle.getString("latdestino");
            DestinoLog= bundle.getString("logdestino");
            cityOrigen=bundle.getString("cityOrigen");
            cityDestino=bundle.getString("cityDestino");

            Destino = bundle.getString("fromDestino");/*
            Bundle args = new Bundle();
            args.putString("textFromActivityB", texto);
            Log.i("textFromActivityA",texto);
            FirstFragment firstFragmentt=new FirstFragment();
            firstFragmentt.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menu,firstFragmentt);
            transaction.commit();
            return;*/
            Bundle bun = new Bundle();

            bun.putString("message", texto );
            bun.putString("origen", Origen);
            bun.putString("origenlata", OrigenLat);
            bun.putString("origenlog", OrigenLog);
            bun.putString("destino", Destino);
            bun.putString("destinolata", DestinoLat);
            bun.putString("destinolog", DestinoLog);
            bun.putString("cityOrigen", cityOrigen);
            bun.putString("cityDestino", cityDestino);


            FirstFragment fragInfo = new FirstFragment();
            fragInfo.setArguments(bun);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menu,fragInfo);
            transaction.commit();

            BottomNavigationView navigationView = findViewById(R.id.BottomNavigation);
            navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelecedListener);

            Log.d("loqui", String.valueOf(loco));
            if (loco!=null)
            {
                if (loco.equals("si"))
                {

                    navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelecedListener);
                    loadFragment(thirdFragment);
                    return;
                }
                else {
                    Log.d("loqui", String.valueOf(loco));
                }

            }
            return;
        }
        authprovider = new Authprovider();
        generartoken();
        BottomNavigationView navigationView = findViewById(R.id.BottomNavigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelecedListener);

        loadFragment(firstFragment);

    }
    void generartoken()
    {
        mTokenprovider.create(authprovider.getid());



    }
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelecedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    loadFragment(firstFragment);
                    return true;
                case R.id.navigation_dashboard:
                    Intent intentt = new Intent(MenuActivity.this, TapMisViajes.class);
                    startActivity(intentt);
                    return true;
                case R.id.navigation_notifications:
                    loadFragment(thirdFragment);
                   // Intent inten = new Intent(MenuActivity.this, ProfileActivity.class);
                   // startActivity(inten);
                    return true;
                case R.id.navigation_rec:
                    loadFragment1(viajescercadeti);
                    return true;


            }
            return false;
        }
    };
    public void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.menu,fragment);
        transaction.commit();
    }
    public void loadFragment1(Fragment fragment) {
        Viajescercadeti viajescercadeti=new Viajescercadeti();

        Bundle bun = new Bundle();
        Log.d("solotuu", String.valueOf(mlatLng));
        Log.d("solotuu", "String.valueOf(mlatLng");

        bun.putDouble("message", mlatLng.latitude );
        bun.putDouble("message1", mlatLng.longitude );
        viajescercadeti.setArguments(bun);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.menu,viajescercadeti);
        transaction.commit();
    }

    private BroadcastReceiver netword=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni=manager.getActiveNetworkInfo();
            onNetword(ni);
        }
    };

    private void onNetword(NetworkInfo ni) {
        if (ni!=null)
        {
            if (ni.getState()==NetworkInfo.State.CONNECTED)
            {


            }else
            {
                Toast.makeText(this, "Se a perdido la conexcion a internet", Toast.LENGTH_SHORT).show();


            }
        }
        else
        {
            Intent intent = new Intent(this, VerificarConexionaInternet.class);
            startActivity(intent);
        }
    }
    public void onCameramove()
    {

        mcameraListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                try {


                    Geocoder geocoder = new Geocoder(MenuActivity.this);

                    mOriginlnLo = mMapM.getCameraPosition().target;
                    List<Address> addressList = geocoder.getFromLocation(mOriginlnLo.latitude, mOriginlnLo.longitude, 1);
                    cityOrigen = addressList.get(0).getLocality().trim();
                    String pais = addressList.get(0).getCountryName();
                    String addres = addressList.get(0).getAddressLine(0);
                   // mOrigin = cityOrigen + " " + addres+" "+pais;
                   // mAutocomplete.setText(cityOrigen + " " + addres+" "+pais);
                  //  mOriginSelect = false;
                    Log.d("placenta","ciudad: "+cityOrigen+", Pais: "+pais+", direccion: "+addres);

                } catch (Exception e) {
                    Log.d("Error" + " Error", e.getMessage());
                }

            }
        };


    }
    public void onMapReady(GoogleMap googleMap) {
        Log.d("solotu", "onmapready");

        mMapM = googleMap;
        mMapM.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMapM.getUiSettings().setZoomControlsEnabled(true);

        mMapM.setOnCameraIdleListener(mcameraListener);
        mLocationrequest = new LocationRequest();
        mLocationrequest.setInterval(1000);
        mLocationrequest.setFastestInterval(1000);
        mLocationrequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationrequest.setSmallestDisplacement(5);
        Log.d("solotu", String.valueOf(mLocationrequest));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        startLocation();
        mMapM.setMyLocationEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(netword,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    protected void onPause() {
        unregisterReceiver(netword);
        super.onPause();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (gpsActive()) {
                        Log.d("solotu", "mfisetd");
                        mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallbal, Looper.myLooper());
                    }
                    else {
                        Log.d("solotu", "else");

                        // alertDialog();
                        RequestGPSSetting();
                    }
                } else {
                    Log.d("solotu", "else 2");

                    checklocationpermiss();

                }
            } else {
                Log.d("solotu", "else3)");

                checklocationpermiss();

            }

        }
    }
    private void checklocationpermiss()
    {
        Log.d("solotu", "check");

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
                                ActivityCompat.requestPermissions(MenuActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);


                            }
                        })
                        .create()
                        .show();

            }
            else
            {
                ActivityCompat.requestPermissions(MenuActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }


        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("solotu", "nofisedonac");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEAST && gpsActive()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Log.d("solotu", "onActi");

            mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallbal, Looper.myLooper());
            mMapM.setMyLocationEnabled(true);
        }
        else if (requestCode == SETTINGS_REQUEAST && !gpsActive()){
            Log.d("solotu", "nofised");
            //alertDialog();
            RequestGPSSetting();

        }
        else
        {
            Log.d("solotu", "nofised");
        }
    }
    private void startLocation()
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            Log.d("solotu", "start 1");

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            {
                Log.d("solotu", "start 2");

                if (gpsActive())
                {
                    Log.d("solotu", "start 3");
                    mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallbal, Looper.myLooper());
                }
                else
                {
                    Log.d("solotu", "start 5");
                    RequestGPSSetting();
                }
            }
            else
            {
                Log.d("solotu", "start 6");

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
                    Toast.makeText(MenuActivity.this, "El gps ya está activado", Toast.LENGTH_SHORT).show();
                } else if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                    try {
                        status.startResolutionForResult(MenuActivity.this, REQUEST_CHECHK_SETIING);

                    } catch (IntentSender.SendIntentException e) {
                        Toast.makeText(MenuActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if (ActivityCompat.checkSelfPermission(MenuActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MenuActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallbal, Looper.myLooper());
                    mMapM.setMyLocationEnabled(false);

                } else if (status.getStatusCode() == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE) {
                    Toast.makeText(MenuActivity.this, "La configuracion del gps tiene un error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private GoogleApiClient getApiCientInstance() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).build();
        return googleApiClient;

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
    protected void onStart() {
        super.onStart();

    }
}