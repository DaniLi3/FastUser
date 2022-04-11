
package com.ultrafastapp.ultrafast.Activitys.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastapp.ultrafast.Models.ModelViajesCerca;
import com.ultrafastapp.ultrafast.Models.SolicitudesModel;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.GeofireProvider;
import com.ultrafastapp.ultrafast.Providers.ViajesPubliProvider;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.Utils.DialogClass;
import com.ultrafastapp.ultrafast.adapter.AdapterNotificaciones;
import com.ultrafastapp.ultrafast.adapter.AdapterViajesCerca;

import java.util.ArrayList;
import java.util.List;

public class Viajescercadeti extends Fragment {
    RecyclerView recyclerView;
    private List<ModelViajesCerca> listadatos=new ArrayList<>();
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager Manager;



    RecyclerView recycler;
    DialogClass dialogClass;
    List<SolicitudesModel> coches;
    String seo;
    AdapterNotificaciones adapterr;
    private int mCounter=1;

    ProgressDialog progressDialog;


    Authprovider authprovider;

    Context context;
    String cityOrigen=" ";

    LatLng mOriginlnLo;

    TextView gonee;
    Button publicar;
    GoogleMap mMapM;
    View vista;
    View vistaa;


    private GeofireProvider mGeoFireProvider;
    private LatLng mOriginLatLong;

    ViajesPubliProvider mclienteBookingProvider;
    private String mIdDriverFound = "";
    private boolean mDriverFound = false;
    private ArrayList<String> driverencontrados=new ArrayList<>();
    private List<String> mtokenlist=new ArrayList<>();
    private double mRadius = 0.1;
    private boolean isfinisSearch=false;
    private LatLng mlatLng;





    public Viajescercadeti() {
        // Required empty public constructor
        context=getActivity();
    }


    public static Viajescercadeti newInstance(String param1, String param2) {
        Viajescercadeti fragment = new Viajescercadeti();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_frakmen_publicados, container, false);
        coches=new ArrayList<>();


        recyclerView=view.findViewById(R.id.reci);
        gonee=view.findViewById(R.id.nofun);
        vista=view.findViewById(R.id.verr);
        vistaa=view.findViewById(R.id.verrr);
        publicar=view.findViewById(R.id.publi);
        mGeoFireProvider = new GeofireProvider("active_driver");
        mclienteBookingProvider=new ViajesPubliProvider("ViajesPublicados");

        recycler=(RecyclerView) view.findViewById(R.id.reci);
        recycler.setHasFixedSize(true);
        Manager=new LinearLayoutManager(getContext());
        recycler.setLayoutManager(Manager);



        authprovider=new Authprovider();
        mDriverFound=false;
        dialogClass=new DialogClass(getActivity());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Procesando");
        progressDialog.setMessage("Cargando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        if (this.getArguments()!=null)
        {
            double myValue = this.getArguments().getDouble("message",0);
            double myValue1 = this.getArguments().getDouble("message1",0);
            mOriginLatLong =new LatLng(myValue,myValue1);
            getClosestDriver();
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
      //  madapter.stopListening();
    }
    private void getClosestDriver()
    {

        mGeoFireProvider.getActiDriver(mOriginLatLong,10).addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(final String key, final GeoLocation location) {

                driverencontrados.add(key);


                if (!mDriverFound)
                {
                    mDriverFound=true;
                    mIdDriverFound=key;
                    isfinisSearch=true;




                    authprovider = new Authprovider();



                }
                progressDialog.dismiss();



            }




            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() { // entra  cuando ya finaliza la busqueda en un radio de 3 km
              for (String id:driverencontrados)
              {
                  Log.d("DRIVERo", "Listo "+id);
                  mclienteBookingProvider.getViajes(id).addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                          mCounter=mCounter+1;
                          if (snapshot.exists())
                          {
                              String origen = snapshot.child("origen").getValue().toString();
                              String destino= snapshot.child("destino").getValue().toString();
                              String idviajes= snapshot.child("idViajes").getValue().toString();
                              String cityorigen= snapshot.child("cityOrigen").getValue().toString();
                              String citydestino= snapshot.child("cityDestino").getValue().toString();
                              String fechapublicado= snapshot.child("fecha").getValue().toString();
                              String peso= snapshot.child("peso").getValue().toString();
                              String paquetesasignados= snapshot.child("paquetesasignados").getValue().toString();
                              Log.d("DRIVERo", " Origen "+origen);
                              Log.d("DRIVERo", " Destino "+destino);

                            //  mtokenlist.add(token);

                              listadatos.add(new ModelViajesCerca(origen,destino,idviajes,peso,cityorigen,citydestino,fechapublicado,paquetesasignados));

                              adaptador=new AdapterViajesCerca(listadatos,getContext());
                              recycler.setAdapter(adaptador);


                          }
                          else
                          {

                          }
                          if (mCounter==driverencontrados.size())
                          {
                              Log.d("DRIVERo", "Listo "+mCounter);
                          }
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError error) {

                      }
                  });

              }
                progressDialog.dismiss();

                if (!mDriverFound)
                {
                    mRadius=mRadius+0.1f;
                    if (mRadius>5)
                    {




                        vistaa.setVisibility(View.VISIBLE);
                        return;
                    }
                    else
                    {
                        getClosestDriver();
                    }
                }
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });


    }

 
}