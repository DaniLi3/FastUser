package com.ultrafastapp.ultrafast.Activitys.RegistroCoche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.ultrafastapp.ultrafast.Activitys.MenuActivity;
import com.ultrafastapp.ultrafast.Models.Choferes;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistroCoche3 extends AppCompatActivity {

    String Matricula,Marca;
    Button btncoche;
    EditText txtmodelovehi;
    CircleImageView circleImageBack;

    Choferprovider choferprovider;
    Authprovider authprovider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_coche3);
        circleImageBack = findViewById(R.id.CirculeImagebackk);
        txtmodelovehi = findViewById(R.id.txtmodelovehi);
        btncoche = findViewById(R.id.btncoche);

        choferprovider = new Choferprovider();
        authprovider = new Authprovider();

        Matricula=getIntent().getStringExtra("Matricula");
        Marca=getIntent().getStringExtra("Marca");

        btncoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String Modelo = txtmodelovehi.getText().toString();

               Guardar(Modelo);
                Intent intent = new Intent(RegistroCoche3.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

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
    public void Guardar(String Modelo)
    {
        Choferes choferes=new Choferes();
        choferes.setMarca(Marca);
        choferes.setModelo(Modelo);
        choferes.setMatricula(Matricula);
        choferes.setId(authprovider.getid());

        choferprovider.Registrarcoche(choferes).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(RegistroCoche3.this, "Su información se guardo correctamente", Toast.LENGTH_SHORT).show();
            }
        });

    }


}