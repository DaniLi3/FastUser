package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.EnviarSolicitud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ultrafastapp.ultrafast.Activitys.MenuActivity;
import com.ultrafastapp.ultrafast.R;

public class SolicitudEnviada extends AppCompatActivity {

    TextView tiempo;
    String fecha;
    Button listo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_enviada);
        tiempo=findViewById(R.id.tiempo);
        listo=findViewById(R.id.listoo);


        fecha=getIntent().getStringExtra("fecha");
        String []fechaa=fecha.split(" ");
        int dia=Integer.parseInt(fechaa[0])-1;


        tiempo.setText("El viajero tiene hasta el "+dia+" "+fechaa[2]+" para responder tu solicitud.");

        listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SolicitudEnviada.this, MenuActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }
}