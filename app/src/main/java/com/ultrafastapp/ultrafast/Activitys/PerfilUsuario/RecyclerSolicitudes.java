package com.ultrafastapp.ultrafast.Activitys.PerfilUsuario;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastapp.ultrafast.Activitys.AdapterBusqueda;
import com.ultrafastapp.ultrafast.Models.ViajesPublicados;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerSolicitudes extends AppCompatActivity {

    RecyclerView rv;
    List<ViajesPublicados> coches;
    String seo;
    CircleImageView bac;
    AdapterBusqueda adapterr;
    ProgressDialog progressDialog;
    Authprovider authprovider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_solicitudes);

        rv=findViewById(R.id.reci);
        rv.setLayoutManager(new LinearLayoutManager(this));
        bac=findViewById(R.id.Cir);

        authprovider=new Authprovider();

        progressDialog = new ProgressDialog(RecyclerSolicitudes.this);
        progressDialog.setTitle("Procesando");
        progressDialog.setMessage("Cargando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        seo=getIntent().getStringExtra("seo");

        coches=new ArrayList<>();
        adapterr=new AdapterBusqueda(coches, RecyclerSolicitudes.this);
        rv.setAdapter(adapterr);

        DatabaseReference database = FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("Solicitudes");

        Query query=database.orderByChild("idchofer").equalTo(authprovider.getid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                { coches.remove(coches);
                    for (DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        ViajesPublicados coche=dataSnapshot.getValue(ViajesPublicados.class);
                        coches.add(coche);
                    }
                    adapterr.notifyDataSetChanged();                }
                else
                {
                    Toast.makeText(RecyclerSolicitudes.this, "no existe ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressDialog.dismiss();


    }
}