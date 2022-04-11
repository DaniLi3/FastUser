package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastapp.ultrafast.Models.SolicitudesModel;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.SolicitudesProvider;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.Utils.DialogClass;
import com.ultrafastapp.ultrafast.Utils.ValidarFecha;
import com.ultrafastapp.ultrafast.adapter.AdapterMisViajes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViajesSolicitados#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViajesSolicitados extends Fragment {

    RecyclerView recyclerView;
    public ValidarFecha validarFecha;
    DialogClass dialogClass;
    List<SolicitudesModel> coches;
    String seo;


    ProgressDialog progressDialog;
    Authprovider authprovider;
    Context context;
    TextView gonee;
    public SolicitudesProvider solicitudesProvider;
    View vista;
    private AdapterMisViajes madapter;




    public ViajesSolicitados() {
        context=getContext();

    }


    public static ViajesSolicitados newInstance(String param1, String param2) {
        ViajesSolicitados fragment = new ViajesSolicitados();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View view= inflater.inflate(R.layout.fragment_viajes_solicitados, container, false);
            coches=new ArrayList<>();

            recyclerView=view.findViewById(R.id.reci);
                
            vista=view.findViewById(R.id.verr);
    //        rv.setHasFixedSize(true);



            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            // rv.setLayoutManager(new LinearLayoutManager(getContext()));

            authprovider=new Authprovider();

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Procesando");
            progressDialog.setMessage("Cargando...");
            progressDialog.setCanceledOnTouchOutside(false);
            // progressDialog.show();
            // seo=getIntent().getStringExtra("seo");


            return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        validarFecha = new ValidarFecha();

        SolicitudesProvider solicitudesProvider = new SolicitudesProvider();
        solicitudesProvider.getSolicitudesTodas().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String fecha = dataSnapshot.child("fechavalidar").getValue().toString();
                if (!validarFecha.validarfecha(fecha))
                {
                    String origen = dataSnapshot.child("origen").getValue().toString();
                    String contenidopaquete = dataSnapshot.child("contenidopaquete").getValue().toString();
                    String costopaquete = dataSnapshot.child("costopaquete").getValue().toString();
                    String destinolatt = dataSnapshot.child("destinolatt").getValue().toString();
                    String destinolog = dataSnapshot.child("destinolog").getValue().toString();
                    String destino = dataSnapshot.child("destino").getValue().toString();
                    String dimensiones = dataSnapshot.child("dimensiones").getValue().toString();
                    String estado = dataSnapshot.child("estado").getValue().toString();
                    String fechadepublicacionviaje = dataSnapshot.child("fechadepublicacionviaje").getValue().toString();
                    String fechadesalida = dataSnapshot.child("fechadesalida").getValue().toString();
                    String fechadesolicitud = dataSnapshot.child("fechadesolicitud").getValue().toString();
                    String horasalida = dataSnapshot.child("horasalida").getValue().toString();
                    String idchofer  = dataSnapshot.child("idchofer").getValue().toString();
                    String idcliente = dataSnapshot.child("idcliente").getValue().toString();
                    String idnoty = dataSnapshot.child("idnoty").getValue().toString();
                    String idviaje = dataSnapshot.child("idviaje").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String kilos = dataSnapshot.child("kilos").getValue().toString();
                    String notapaquete = dataSnapshot.child("notapaquete").getValue().toString();
                    String origenlat = dataSnapshot.child("origenlat").getValue().toString();
                    String origenlog = dataSnapshot.child("origenlog").getValue().toString();
                    String personaquerecibe = dataSnapshot.child("personaquerecibe").getValue().toString();
                    String precio = dataSnapshot.child("precio").getValue().toString();
                    String seo = dataSnapshot.child("seo").getValue().toString();
                    String seocliente = dataSnapshot.child("seocliente").getValue().toString();
                    String telpersona = dataSnapshot.child("telpersona").getValue().toString();

                    Log.d("sumerjeme",origen);
                    Log.d("sumerjeme",destino);

                   // MandarASolicitudesCaducadas(origen,contenidopaquete,costopaquete,destino,destinolatt,destinolog,dimensiones,estado,fechadepublicacionviaje,fechadesalida,fechadesolicitud,horasalida,idchofer,idcliente,idnoty,notapaquete,origen,origenlat,origenlog,personaquerecibe,precio,seo,seocliente,telpersona);

                }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        authprovider=new Authprovider();
        dialogClass=new DialogClass(getActivity());
        authprovider = new Authprovider();
        progressDialog.show();
        Query query= FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("Solicitudes").orderByChild("seocliente").startAt("-220101192134_"+authprovider.getid()).endAt("_"+authprovider.getid());
        FirebaseRecyclerOptions<SolicitudesModel> options=new FirebaseRecyclerOptions.Builder<SolicitudesModel>()
                .setQuery(query,SolicitudesModel.class).build();


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    progressDialog.dismiss();
                    vista.setVisibility(View.GONE);
                }
                else
                {
                    progressDialog.dismiss();
                    vista.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        madapter=new AdapterMisViajes(options,getContext());
        recyclerView.setAdapter(madapter);
        madapter.startListening();


    }

    private void MandarASolicitudesCaducadas(String origen, String contenidopaquete, String costopaquete, String destino, String destinolatt, String destinolog, String dimensiones, String estado, String fechadepublicacionviaje, String fechadesalida, String fechadesolicitud, String horasalida, String idchofer, String idcliente, String idnoty, String notapaquete, String s, String origenlat, String origenlog, String personaquerecibe, String precio, String seo, String seocliente, String telpersona) {
        solicitudesProvider=new SolicitudesProvider();
        SolicitudesModel solicitudesModel= new SolicitudesModel(
                fechadesalida,
                horasalida,
                fechadesolicitud,
                fechadepublicacionviaje,
                origen,// origen
                destino,//destino
                idcliente,//id cliente
                idchofer,// idDelChoer
                idnoty, //id de la notificaion
                "Caducada",
                precio,
                costopaquete,
                "sa",
                idchofer+"_"+"caducado",
                idcliente+"_"+"caducado",
                "x",
                2,
                2,
                2,
                2,
                new Date().getTime(),//fecha
                "image",
               " persona.getEditText().getText().toString()",
             "   nota.getEditText().getText().toString()",
                "descripcion",
                ""
                ,"",
                "SIN PAGAR",
                "idviaje"

        );
        solicitudesProvider.create(solicitudesModel);

    }

    @Override
    public void onStop() {
        super.onStop();
        madapter.stopListening();
    }

}