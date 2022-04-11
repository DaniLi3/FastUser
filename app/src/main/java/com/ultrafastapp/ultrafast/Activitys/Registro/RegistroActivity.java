package com.ultrafastapp.ultrafast.Activitys.Registro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.ultrafastapp.ultrafast.Activitys.MenuActivity;
import com.ultrafastapp.ultrafast.Models.Choferes;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.channel.SNTPClient;

import java.util.TimeZone;

public class RegistroActivity extends AppCompatActivity {
    Button sig;
    TextInputLayout nom,ape,email;
    Spinner spinner;
    Choferprovider choferprovider;
    Authprovider authprovider;
    SharedPreferences.Editor meditor;
    SharedPreferences mpref;
    String status;
    String tel;
    String apellidos;
   // ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        sig = findViewById(R.id.btnsiguiente);
        nom=findViewById(R.id.txtnombre);
        ape=findViewById(R.id.txtapellidos);
        email=findViewById(R.id.txtemail);
        spinner=findViewById(R.id.spinner);
        choferprovider = new Choferprovider();
        authprovider=new Authprovider();
       // progressDialog=new ProgressDialog(this);
       // progressDialog.setMessage("Iniciando...");
      //  progressDialog.setCanceledOnTouchOutside(false);


        String [] opciones={"Soy","Hombre", "Mujer"};
        ArrayAdapter <String> adapterr=new ArrayAdapter<String>(this, R.layout.spinner,opciones);
        spinner.setAdapter(adapterr);

       // ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.sexo, android.R.layout.simple_spinner_item);
      //  spinner.setAdapter(adapter);
        mpref = getApplicationContext().getSharedPreferences("RideStatuss", MODE_PRIVATE);
        meditor = mpref.edit();
        status = mpref.getString("statuss", "");//obtenemos el ultimo estado almacenado
        meditor.putString("statuss","ridee");
        meditor.apply();

        if (status.equals("starts"))
        {
            Intent intent = new Intent(RegistroActivity.this, MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else
        {
            //Aqui este valor se almacena cuando el conductor inicia por primera vez
            meditor.putString("statuss","ridee");
            meditor.apply();
        }

        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // public String getDateCurrentTimeZone(long timestamp);{
                final String[] datee = {(DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString())};
                SNTPClient.getDate(TimeZone.getTimeZone("America/Mexico_City"), new SNTPClient.Listener() {
                    @Override
                    public void onTimeReceived(String rawDate) {
                        // rawDate -> 2019-11-05T17:51:01+0530
                        Log.e(SNTPClient.TAG, rawDate);
                         datee[0] =rawDate;
                        //Toast.makeText(getContext(), "es "+rawDate, Toast.LENGTH_SHORT).show();

                        String nomb= nom.getEditText().getText().toString();
                        String ap= ape.getEditText().getText().toString();
                        String ema= email.getEditText().getText().toString();

                        String genero = spinner.getSelectedItem().toString();
                        if (!nomb.equals("") &&!ap.equals("")&&!ema.equals(""))
                        {
                            meditor.clear().commit();
                            Choferes choferes = new Choferes();
                            choferes.setId(authprovider.getid());
                            choferes.setNombre(nomb);
                            choferes.setEmail(ema);
                            choferes.setApellidos(ap);
                            choferes.setGenero(genero);
                            choferes.setFechaactual(datee[0]);
                            choferes.setBibliografia("Sin bibliografía");
                            choferes.setTipousuario("Estándar");
                            choferes.setRealizados("0");
                            choferes.setSolicitados("0");
                            choferes.setCalificacion("4.0");
                            Log.d("roxana", datee[0]);
                            choferes.setStatus("Sin validar");
                            Registrar(choferes);

                        }
                        else
                        {
                           // progressDialog.dismiss();
                            Toast.makeText(RegistroActivity.this, "Por favor ingresa todos los datos", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(Exception ex) {
                        Toast.makeText(RegistroActivity.this, "Ocurrio un error al registrar, por favor intenta más tarde", Toast.LENGTH_SHORT).show();
                    }
                });
                    //Toast.makeText(RegistroActivity.this, datee, Toast.LENGTH_SHORT).show();



            }
        });
    }

    private void Registrar( Choferes choferes) {
       // progressDialog.show();
        choferprovider.Registrarcoche(choferes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                   // progressDialog.dismiss();
                    Intent intent=new Intent(RegistroActivity.this, MenuActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    finish();
                    startActivity(intent);
                }
                else
                {
                    //progressDialog.dismiss();
                    Toast.makeText(RegistroActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


}