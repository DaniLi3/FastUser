package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.DetallesDeMisSolicitudes;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastapp.ultrafast.Models.ModelCompartir;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.Utils.DialogClass;
import com.ultrafastapp.ultrafast.adapter.AdapterCompartir;

import java.util.ArrayList;
import java.util.List;

public class ViajesCompartidos extends AppCompatActivity {

    RecyclerView recyclerView;
    DialogClass dialogClass;
    List<ModelCompartir> coches;
    ProgressDialog progressDialog;
    Authprovider authprovider;
    Context context;
    private AdapterCompartir madapter;

    public ViajesCompartidos() {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viajes_compartidos);
        coches=new ArrayList<>();
        recyclerView=findViewById(R.id.recycleruser);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        authprovider=new Authprovider();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Procesando");
        progressDialog.setMessage("Cargando...");
        progressDialog.setCanceledOnTouchOutside(false);


        
    }

    @Override
    protected void onStart() {
        super.onStart();
        dialogClass=new DialogClass(this);
        authprovider = new Authprovider();
        progressDialog.show();
        Query query= FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("ViajesCompartidos").orderByChild("idusercompartido")
                .equalTo(authprovider.getid());
        FirebaseRecyclerOptions<ModelCompartir> options=new FirebaseRecyclerOptions.Builder<ModelCompartir>()
                .setQuery(query,ModelCompartir.class).build();


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    progressDialog.dismiss();

                }
                else
                {
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        madapter=new AdapterCompartir(options,this);
        recyclerView.setAdapter(madapter);
        madapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        madapter.stopListening();
    }

}