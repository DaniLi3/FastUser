package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.DetallesDeMisSolicitudes;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.Providers.ClienteBookingProvider;
import com.ultrafastapp.ultrafast.Providers.GeofireProvider;
import com.ultrafastapp.ultrafast.Providers.GoogleApiProvider;
import com.ultrafastapp.ultrafast.Providers.TokenProvider;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.Utils.CarMoveAnim;
import com.ultrafastapp.ultrafast.Utils.Decode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViajeIniciado extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    Authprovider authprovider;
    private SupportMapFragment mapFragment;
    private GeofireProvider geoFireprovider;
    private ValueEventListener mListener;
    private TextView cance1;
    private ImageView cance2;
    private TextView mcali;

    private Button bllamar;

    SharedPreferences mpref;
    SharedPreferences.Editor meditor;
    ImageView Cancelar;

    private LatLng mDriverlatLng;
    private Button mCancelarViaje;


    private String mIdDriver;

    private GoogleApiProvider mGoogleApi;
    private List<LatLng> mPolyLineList;
    private PolylineOptions mPolylineOption;

    private Marker mMarkerDriver;
    private TextView txtfechallegada;
    private TextView txttiempo;
    private TextView txtdistancia;
    private TextView txtnombre;
    private TextView txtorigen;
    private TextView txtdestino;
    String caliA="";
    String Cviajes="";
    String califi="";
    String fechasalida="";
    String Origen="";
    String Destino="";
    private FusedLocationProviderClient mFusedLocation;


    private boolean mISFirstRTime = true;
    boolean isConect = false;

    private PlacesClient mplaces;
    private Choferprovider mDriverProvider;


    private String mOrigin;
    private LatLng mOriginlnLo;

    private String mDestino;
    private LatLng  mDestinoLnLo;

    private String costo;
    String phonenumero;


    private TextView mtextEstado;

    private ClienteBookingProvider mClienteBookingProvider;



    private TokenProvider mTokenprovider;
    private ValueEventListener mListenerStatus;
    double destinolat;
    double destinolog;
    double origenlat;
    double origenlog;
    String idnoty;

    LatLng mstartLanLon;
    LatLng mEndLanLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viaje_iniciado);
        txtdistancia = findViewById(R.id.txtdistancia);
        txttiempo = findViewById(R.id.txttiempo);
        txtfechallegada = findViewById(R.id.txtfechallegada);
        txtnombre = findViewById(R.id.txtnomusser);
        txtorigen = findViewById(R.id.txtorigen);
        txtdestino = findViewById(R.id.txtdestino);
        bllamar=findViewById(R.id.btnllamarc);




        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);

        geoFireprovider = new GeofireProvider("active_driver");
        mGoogleApi=new GoogleApiProvider(ViajeIniciado.this);
        mTokenprovider = new TokenProvider();
        //mClienteBookingProvider = new ClienteBookingProvider();
       // mimageViewBookin = findViewById(R.id.imageviewumagebookin);
        destinolat = getIntent().getDoubleExtra("destinolat",0);
        destinolog = getIntent().getDoubleExtra("destinolog",0);
        origenlat = getIntent().getDoubleExtra("origenlat",0);
        origenlog = getIntent().getDoubleExtra("origenlog",0);
        String nombre = getIntent().getStringExtra("nombre");
        String tel = getIntent().getStringExtra("tel");
        String fecha = getIntent().getStringExtra("fecha");
        idnoty = getIntent().getStringExtra("idnoty");
        String idchofer = getIntent().getStringExtra("idchofer");
        Origen = getIntent().getStringExtra("origen");
        Destino= getIntent().getStringExtra("destino");
        fechasalida= getIntent().getStringExtra("fechasalida");

        txtnombre.setText(nombre);
        txtfechallegada.setText(fechasalida);
        txtorigen.setText(Origen);
        txtdestino.setText(Destino);


        mOriginlnLo=new LatLng(origenlat,origenlog);
        mDestinoLnLo=new LatLng(destinolat,destinolog);




      //  mCancelarViaje = findViewById(R.id.butoncancel);
        mClienteBookingProvider = new ClienteBookingProvider();
        mTokenprovider = new TokenProvider();
        authprovider = new Authprovider();
        mDriverProvider = new Choferprovider();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        }
        mpref= getApplicationContext().getSharedPreferences("RideStatus",MODE_PRIVATE);
        meditor=mpref.edit();
        mIdDriver=getIntent().getStringExtra("idDriver");
       // getstatus(idnoty);
        getDriverLocation(idchofer);
        //getClienteBooking();

        bllamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dial = "tel:" + tel;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));

            }
        });

    }
    private void getstatus( String idnoty ) {
        //Log.d("finiq","vale queso 33333");

        mListenerStatus= mClienteBookingProvider.getStatus(idnoty).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String status;
                    status= snapshot.getValue().toString();
                    if (status.equals(""))
                    {
                        Log.d("finiq","vale queso 3");

                        //finisBooking2();
                    }

                    if (status.equals("aceptado"))
                    {
                        Log.d("finiq","vale queso 34");
                        startBooking();

                       // mtextEstado.setText("Estado: Aceptado");

                    }

                    if (status.equals("start") ) {
                      //  mtextEstado.setText("Estado: Viaje iniciado");
                        String statuspref=mpref.getString("status","");
                        if (!statuspref.equals("start"))
                        {


                            Log.d("finiq","vstart 4");

                        }
                    }
                    else if (status.equals("finish")){
                       // mtextEstado.setText("Estado: Viaje finalizado");
                       // finisBooking();
                        Log.d("finiq","finis");

                    }
                    else if (status.equals("finishh")){
                        Log.d("finiq","vale queso 4");
                      //  finisBooking2();

                    }

                }
                else
                {
                    Log.d("finiq","vale queso 73");
                    //finisBooking2();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void startBooking() {
        Log.d("cheto", String.valueOf(mDestinoLnLo));
        meditor.putString("status","start");
        meditor.putString("idDriver",mIdDriver);
        meditor.apply();
        mMap.clear();

        mMap.addMarker(new MarkerOptions().position(mOriginlnLo).title("Origen").icon(BitmapDescriptorFactory.fromResource(R.drawable.poin)));
        mMap.addMarker(new MarkerOptions().position(mDestinoLnLo).title("Destino").icon(BitmapDescriptorFactory.fromResource(R.drawable.poin)));
        if (mDriverlatLng!=null)
        {

            mMarkerDriver=mMap.addMarker(new MarkerOptions().position(
                    new LatLng(mDriverlatLng.latitude,mDriverlatLng.longitude)
            ).title("Tu conductor")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ubercart)));


        }
        drawRute(mDestinoLnLo,"ini");


    }

    public void drawRute(LatLng latLng,String ini)
    {
        mGoogleApi.getDirections(mOriginlnLo,latLng ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.body());
                    JSONArray jsonArray = jsonObject.getJSONArray("routes");
                    JSONObject route= jsonArray.getJSONObject(0);
                    JSONObject polylines=route.getJSONObject("overview_polyline");
                    String points =polylines.getString("points");

                    mPolyLineList= Decode.decodePoly(points);
                    mPolylineOption = new PolylineOptions();
                    mPolylineOption.color(Color.DKGRAY);
                    mPolylineOption.width(13f);
                    mPolylineOption.startCap(new SquareCap());
                    mPolylineOption.jointType(JointType.ROUND);
                    mPolylineOption.addAll(mPolyLineList);
                    mMap.addPolyline(mPolylineOption);

                    JSONArray legs=route.getJSONArray("legs");
                    JSONObject leg=legs.getJSONObject(0);
                    JSONObject distance=leg.getJSONObject("distance");
                    JSONObject duration=leg.getJSONObject("duration");
                    String distancetext=distance.getString("text");
                    String durationtext=duration.getString("text");
                    if (ini.equals("ini"))
                    {
                        txtdistancia.setText(durationtext);
                        //txttiempo.setText("Para llegar al destino");
                        txttiempo.setText(distancetext);
                        return;

                    }

                    txttiempo.setText(durationtext);



                }
                catch (Exception e)
                {
                    Log.d("Error","Error encontrado"+e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e("Mapa", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("Mapa", "Can't find style. Error: ", e);
        }*/

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
       // mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(mOriginlnLo)
                .zoom(9f)
                .build()
        ));

    }
    private void getDriverLocation(String idDriver) {


        mListener= geoFireprovider.getDriverLocation(idDriver).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    double lat = Double.parseDouble(snapshot.child("0").getValue().toString());
                    double log = Double.parseDouble(snapshot.child("1").getValue().toString());

                    mDriverlatLng = new LatLng(lat,log);


                    if (mISFirstRTime) {//preguntamos si es la primera vez que ingresamos para mover la camara al conductor6
                        mISFirstRTime = false;
                        mMarkerDriver=mMap.addMarker(new MarkerOptions().position(
                                new LatLng(lat,log))
                                .title("Tu conductor")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.uber)));
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder()
                                        .target(mDriverlatLng)
                                        .zoom(12f)
                                        .build()
                        ));

                        String status = mpref.getString("status","");
                        if (status.equals("start"))

                        {
                            startBooking();
                        }
                        else
                        {
                            meditor.putString("status","ride");
                            meditor.putString("idDriver",mIdDriver);
                            meditor.apply();
                            drawRute(mOriginlnLo,"");
                        }
                    }

                    if (mstartLanLon!=null)
                    {
                        mEndLanLog=mstartLanLon;

                    }
                    mstartLanLon=new LatLng(lat,log);
                    if (mEndLanLog!=null)
                    {
                        CarMoveAnim.carAnim(mMarkerDriver,mEndLanLog,mstartLanLon);
                    }
                    Log.d("finiq","vale queso 96");
                }
                else
                {
                    Log.d("finiq","vale queso 6");
                    //finisBooking2();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}