package com.ultrafastapp.ultrafast.Activitys.RegistroCoche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ultrafastapp.ultrafast.Providers.VehiculoProvider;
import com.ultrafastapp.ultrafast.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistroCoche1 extends AppCompatActivity {

    Button btncoche;
    EditText txtmatri;
    CircleImageView circleImageBack;

    VehiculoProvider vehiculoProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro3);
        btncoche = findViewById(R.id.btncoche);
        txtmatri = findViewById(R.id.txtmatri);
        circleImageBack = findViewById(R.id.CirculeImagebackk);
        vehiculoProvider = new VehiculoProvider();

        btncoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Matricula = txtmatri.getText().toString();
                Intent intent = new Intent(RegistroCoche1.this,RegistroCoche2.class);
                intent.putExtra("Matricula",Matricula);
                startActivity(intent);
            }
        });
        circleImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ObtenerMarcas();


    }

    private void ObtenerMarcas() {

    }
}