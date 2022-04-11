package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.DetallesDeMisSolicitudes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastapp.ultrafast.Models.ModelCompartir;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.adapter.AdapterViajesCompartidos;

import java.util.ArrayList;

public class ViajesCompartidos2 extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    AdapterViajesCompartidos adapterUsuarios;
    LinearLayoutManager lm;
    ArrayList<ModelCompartir> list;
    Authprovider authprovider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viajes_compartidos);
        authprovider=new Authprovider();
        databaseReference= (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("ViajesCompartidos").orderByChild("nombreusuariocompartido").equalTo(authprovider.getid());
        recyclerView=findViewById(R.id.recycleruser);
        lm=new LinearLayoutManager(this);
        list=new ArrayList<>();
        recyclerView.setLayoutManager(lm);
        //adapterUsuarios=new AdapterUsuarios(list,this);

        adapterUsuarios=new AdapterViajesCompartidos(list,this,"idchofer");
        recyclerView.setAdapter(adapterUsuarios);




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                   // VerBuscar.setVisibility(View.GONE);
                    for (DataSnapshot snap : snapshot.getChildren())
                    {
                        ModelCompartir ms=snap.getValue(ModelCompartir.class);
                        list.add(ms);
                    }
                    adapterUsuarios.notifyDataSetChanged();
                }
                else
                {
                   // VerBuscar.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        
    }
}