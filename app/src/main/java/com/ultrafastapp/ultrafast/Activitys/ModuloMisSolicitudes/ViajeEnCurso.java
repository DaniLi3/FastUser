package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.Utils.DialogClass;
import com.ultrafastapp.ultrafast.adapter.AdapterMisViajes;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViajeEnCurso#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViajeEnCurso extends Fragment {
    RecyclerView recyclerView;
    DialogClass dialogClass;
    List<SolicitudesModel> coches;
    String seo;

    ProgressDialog progressDialog;
    Authprovider authprovider;
    Context context;
    TextView gonee;
    View vista;
    private AdapterMisViajes madapter;


    public ViajeEnCurso() {
        // Required empty public constructor
        context=getContext();
    }


    public static ViajeEnCurso newInstance(String param1, String param2) {
        ViajeEnCurso fragment = new ViajeEnCurso();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_viaje_en_curso, container, false);
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
        dialogClass=new DialogClass(getActivity());
        authprovider = new Authprovider();

        Query query= FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("SolicitudesAceptadas").orderByChild("seocliente")
                .equalTo(authprovider.getid()+"_aceptado");
        FirebaseRecyclerOptions<SolicitudesModel> options=new FirebaseRecyclerOptions.Builder<SolicitudesModel>()
                .setQuery(query,SolicitudesModel.class).build();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    vista.setVisibility(View.GONE);
                }
                else
                {

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

    @Override
    public void onStop() {
        super.onStop();
        madapter.stopListening();
    }
}