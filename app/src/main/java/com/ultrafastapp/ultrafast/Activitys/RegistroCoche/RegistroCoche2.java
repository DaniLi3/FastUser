package com.ultrafastapp.ultrafast.Activitys.RegistroCoche;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ultrafastapp.ultrafast.Activitys.Cardsearch;
import com.ultrafastapp.ultrafast.Models.ClienteBookin;
import com.ultrafastapp.ultrafast.Models.Vehiculos;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.adapter.Histori;
import com.ultrafastapp.ultrafast.adapter.Marcas;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistroCoche2 extends AppCompatActivity {
    String Matricula;
    Button btncoche;
    EditText txtmarcavehiculo;
    CircleImageView circleImageBack;
    RecyclerView recyclerView;
    private Marcas madapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_coche2);
        circleImageBack = findViewById(R.id.CirculeImagebackk);
        btncoche = findViewById(R.id.btncoche);
        //recyclerView = findViewById(R.id.recii);
        //LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        txtmarcavehiculo = findViewById(R.id.txtmarcavehiculo);

        Matricula = getIntent().getStringExtra("Matricula");
        Toast.makeText(this, ""+Matricula, Toast.LENGTH_SHORT).show();

        btncoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Marca = txtmarcavehiculo.getText().toString();
                Intent intent = new Intent(RegistroCoche2.this,RegistroCoche3.class);
                intent.putExtra("Matricula",Matricula);
                intent.putExtra("Marca",Marca);
                startActivity(intent);


            }
        });
        circleImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        /*Query query= FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("Vehiculos");
        FirebaseRecyclerOptions<Vehiculos> options=new FirebaseRecyclerOptions.Builder<Vehiculos>()
                .setQuery(query,Vehiculos.class).build();

        madapter=new Marcas(options, RegistroCoche2.this);
        recyclerView.setAdapter(madapter);
        madapter.startListening();*/
    }
}