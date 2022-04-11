package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.DetallesDeMisSolicitudes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastapp.ultrafast.Models.Choferes;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.adapter.AdapterUsuarios;

import java.util.ArrayList;

public class CompartirViaje extends AppCompatActivity {
    DatabaseReference databaseReference;
    ArrayList<Choferes> list;
    RecyclerView recyclerView;
    SearchView searchView;
    Button buttonn;
    AdapterUsuarios adapterUsuarios;
    LinearLayoutManager lm;
    View VerBuscar;
    String idchofer;
    String idnoty;
    String origen;
    String fechasalida;
    String destino;
    double destinolat;
    double destinolog;
    double origenlat;
    double origenlog;
    String nombreee;
    String tel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir_viaje);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        recyclerView=findViewById(R.id.recycleruser);
        buttonn=findViewById(R.id.btnmodificarviaje);
       searchView=findViewById(R.id.seachrt);

        lm=new LinearLayoutManager(this);
        searchView.onActionViewExpanded();
        recyclerView.setLayoutManager(lm);
        list=new ArrayList<>();
        VerBuscar=findViewById(R.id.verbuscar);
        VerBuscar.setVisibility(View.GONE);
         idchofer = getIntent().getStringExtra("idchofer");
        destinolat = getIntent().getDoubleExtra("destinolat",0);
        destinolog = getIntent().getDoubleExtra("destinolog",0);
        origenlat = getIntent().getDoubleExtra("origenlat",0);
        origenlog = getIntent().getDoubleExtra("origenlog",0);
         nombreee = getIntent().getStringExtra("usuario");
         origen = getIntent().getStringExtra("origen");
         destino = getIntent().getStringExtra("destino");
         tel = getIntent().getStringExtra("telefono");
        String fecha = getIntent().getStringExtra("fecha");
        idnoty = getIntent().getStringExtra("idnoty");
        fechasalida = getIntent().getStringExtra("fechasalida");
        Log.d("cobardeeee", String.valueOf(nombreee)+" de "+tel);


        adapterUsuarios=new AdapterUsuarios(list,this,idchofer,"",destinolat,destinolog,origenlat,origenlog,nombreee,tel,origen,destino,fechasalida);
        recyclerView.setAdapter(adapterUsuarios);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    VerBuscar.setVisibility(View.GONE);
                    for (DataSnapshot snap : snapshot.getChildren())
                    {
                        Choferes ms=snap.getValue(Choferes.class);
                        list.add(ms);
                    }
                    adapterUsuarios.notifyDataSetChanged();
                }
                else
                {
                    VerBuscar.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        buttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
                compartir.setType("text/plain");
                String mensaje = "Te recomiendo esta App ";
                compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ultrafast");
                compartir.putExtra(android.content.Intent.EXTRA_TEXT, mensaje);
                startActivity(Intent.createChooser(compartir, "Compartir vía"));

            }
        });


       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals(""))
                {
                    Log.d("ether1", "vacio");
                    recyclerView.setVisibility(View.GONE);
                    VerBuscar.setVisibility(View.GONE);
                }
                else
                {

                    Buscar(newText);
                }
                return false;
            }
        });


    }


    private void Buscar(String newText) {
        ArrayList<Choferes>miLista =new ArrayList<>();
        for (Choferes obj:list)
        {
            if (obj.getNombre().toLowerCase().contains(newText.toLowerCase()))
            {
                miLista.add(obj);
                VerBuscar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE );

            }

        }


        if (miLista==null||miLista.size()==0)
        {
            Log.d("ether1", String.valueOf(miLista));
            VerBuscar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        AdapterUsuarios adapterUsuarios=new AdapterUsuarios(miLista,this,idchofer,"",destinolat,destinolog,origenlat,origenlog,nombreee,tel,origen,destino,fechasalida);
        recyclerView.setAdapter(adapterUsuarios);
    }

}