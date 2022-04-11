package com.ultrafastapp.ultrafast.Activitys.RegistroCoche;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.ultrafastapp.ultrafast.Activitys.ProfileActivity;
import com.ultrafastapp.ultrafast.Models.Choferes;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;

public class RegistroCoche4 extends AppCompatActivity {
    CardView cardaveo;
    TextView elin;
    String Marca,Modelo,Matricula;
    Choferprovider choferprovider;
    Authprovider authprovider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_coche4);
        cardaveo = findViewById(R.id.cardaveo);
        elin = findViewById(R.id.alin);
        Marca = getIntent().getStringExtra("Marca");
        Matricula = getIntent().getStringExtra("Matricula");
        Modelo = getIntent().getStringExtra("Modelo");

        choferprovider = new Choferprovider();
        authprovider = new Authprovider();


        cardaveo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elin.setText(Matricula+" + "+Marca+" + "+Modelo);
                Toast.makeText(RegistroCoche4.this, "Aveo", Toast.LENGTH_SHORT).show();
            }
        });
    }


}