package com.ultrafastapp.ultrafast.Recyclers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.ultrafastapp.ultrafast.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerBusqueda extends AppCompatActivity {

    RecyclerView rv;
    List<ViajesPublicados> coches;
    String seo;
    CircleImageView bac;
    AdapterBusqueda adapterr;
    ProgressDialog progressDialog;
    Button alerta;
    View vista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_prueba);
        rv=findViewById(R.id.reci);
        rv.setLayoutManager(new LinearLayoutManager(this));
        bac=findViewById(R.id.Cir);
        alerta=findViewById(R.id.btnalerta);
        vista=findViewById(R.id.verr);
        seo=getIntent().getStringExtra("seo");

        coches=new ArrayList<>();
        adapterr=new AdapterBusqueda(coches, RecyclerBusqueda.this);
        rv.setAdapter(adapterr);

        DatabaseReference database = FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("ViajesPublicados");
        progressDialog = new ProgressDialog(RecyclerBusqueda.this);

        progressDialog.setMessage("Cargando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Query query=database.orderByChild("seo").equalTo(seo);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    vista.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    coches.remove(coches);
                    for (DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        ViajesPublicados coche=dataSnapshot.getValue(ViajesPublicados.class);
                        coches.add(coche);
                    }
                    adapterr.notifyDataSetChanged();                }
                else
                {
                    vista.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
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

        alerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerBusqueda.this, "Se te notificará cuando haya viajes hacia éste destino", Toast.LENGTH_SHORT).show();
            }
        });

    }
}