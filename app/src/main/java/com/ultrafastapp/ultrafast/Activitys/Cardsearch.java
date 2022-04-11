package com.ultrafastapp.ultrafast.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
import com.ultrafastapp.ultrafast.Models.ViajesPublicados;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.Utils.DialogClass;
import com.ultrafastapp.ultrafast.adapter.Histori;

import de.hdodenhof.circleimageview.CircleImageView;

public class Cardsearch extends AppCompatActivity {

    RecyclerView recyclerView;
    DialogClass dialogClass;
    CircleImageView bac;
    private Histori madapter;
    private Authprovider authprovider;
    String seo;
    View vista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cardsearch);
        bac=findViewById(R.id.Cir);
        recyclerView = findViewById(R.id.reci);
        vista=findViewById(R.id.verr);


        seo=getIntent().getStringExtra("seo");


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cardsearch.this,MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        dialogClass=new DialogClass(Cardsearch.this);
        authprovider = new Authprovider();
        Query query=FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("ViajesPublicados").orderByChild("seo").equalTo("seo");
        FirebaseRecyclerOptions<ViajesPublicados> options=new FirebaseRecyclerOptions.Builder<ViajesPublicados>()
                .setQuery(query,ViajesPublicados.class).build();

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

        madapter=new Histori(options,Cardsearch.this);
        recyclerView.setAdapter(madapter);
        madapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        madapter.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            finish();
        }


}