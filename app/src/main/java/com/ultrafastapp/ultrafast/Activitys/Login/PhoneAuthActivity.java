package com.ultrafastapp.ultrafast.Activitys.Login;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.ultrafastapp.ultrafast.Activitys.MenuActivity;
import com.ultrafastapp.ultrafast.Activitys.Registro.RegistroActivity;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;

import dmax.dialog.SpotsDialog;

public class PhoneAuthActivity extends AppCompatActivity {
    CountryCodePicker countryCodePicker;
    Authprovider authprovider;
    Button registrar;
    EditText mphone;
    SharedPreferences mpref;
    String status ="";
    AlertDialog mdialog;

    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;



    @Override
    public void onBackPressed(){
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }


    Choferprovider choferprovider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        authprovider=new Authprovider();
        choferprovider = new Choferprovider();


        registrar= findViewById(R.id.btnregi);
        mphone=findViewById(R.id.edittexfond);
        countryCodePicker=findViewById(R.id.ccp);

        mpref = getApplicationContext().getSharedPreferences("RideStatuss", MODE_PRIVATE);
        status = mpref.getString("statuss","");

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = countryCodePicker.getSelectedCountryCodeWithPlus();
                String phone = mphone.getText().toString();
                if (!phone.equals(""))
                {
                    Intent intent = new Intent(PhoneAuthActivity.this, PhoneCodeActivity.class);
                    intent.putExtra("phone",code+phone);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(PhoneAuthActivity.this, "Debes ingresar un telefono valido", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mdialog=new SpotsDialog.Builder().setContext(PhoneAuthActivity.this).setMessage("Espere...").build();

        if (status.equals("ridee")) {

            if (authprovider.existSesion()) {


                Intent intent = new Intent(PhoneAuthActivity.this, RegistroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }
        else if (authprovider.existSesion())
        {
            mdialog.show();
            getDriverInfo();
        }


    }

    private void getDriverInfo() {
        choferprovider.getusuario(authprovider.getid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    mdialog.dismiss();
                    Intent intent = new Intent(PhoneAuthActivity.this, MenuActivity.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    authprovider.logut();
                    mdialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}