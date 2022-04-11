package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.EnviarSolicitud;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.ultrafastapp.ultrafast.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompletarEnvio extends AppCompatActivity {

    private TextInputLayout descrip;
    private TextInputLayout peso;
    private TextInputLayout costo;
    private TextInputLayout largo;
    private TextInputLayout ancho;
    private TextInputLayout alto;
    CircleImageView circleImageView;
    TextView tarifa;
    private Button continuar;

    double mExtraOrigenLat;
    double mExtraOrigenLog;
    double mExtraDestinoLat;
    double mExtraDestinoLog;


    private  String idUser=" ";
    private  String distancetext=" ";
    private  String idcliente=" ";
    private  String idviaje=" ";
    private  String fechavali=" ";
    String origeen;
    String destinoo;
    String preciooo;
    String fecha,hora,pesoo;
    String fechadepubl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completar_envio);

        descrip=findViewById(R.id.txtarti);
        peso=findViewById(R.id.txtpeso);
        costo=findViewById(R.id.txtvalor);
        largo=findViewById(R.id.txtlargo);
        ancho=findViewById(R.id.txtancho);
        alto=findViewById(R.id.txtalto);
        continuar=findViewById(R.id.btnconti);
        tarifa=findViewById(R.id.txttari);
        circleImageView=findViewById(R.id.CirculeImagebackk);


        idUser =getIntent().getStringExtra("idchofer");
        idcliente =getIntent().getStringExtra("idcliente");
        idviaje =getIntent().getStringExtra("idviajes");
        origeen =getIntent().getStringExtra("Origen");
        mExtraOrigenLat = getIntent().getDoubleExtra("OrigenLat",0);
        mExtraOrigenLog = getIntent().getDoubleExtra("OrigenLog",0);
        destinoo =getIntent().getStringExtra("Destino");
        mExtraDestinoLat = getIntent().getDoubleExtra("DestinoLat",0);
        mExtraDestinoLog = getIntent().getDoubleExtra("DestinoLog",0);
        fecha =getIntent().getStringExtra("Fecha");
        fechadepubl =getIntent().getStringExtra("Fechapubli");
        fechavali =getIntent().getStringExtra("fechavali");
        preciooo =getIntent().getStringExtra("Precio");
        hora =getIntent().getStringExtra("Hora");
        pesoo =getIntent().getStringExtra("Peso");
        distancetext =getIntent().getStringExtra("distancetext");

      circleImageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
      });
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!costo.getEditText().getText().toString().equals("")&&!descrip.getEditText().getText().toString().equals("")&&!peso.getEditText().getText().toString().equals("")
                        &&!largo.getEditText().getText().toString().equals("")&&!ancho.getEditText().getText().toString().equals("")
                        &&!alto.getEditText().getText().toString().equals(""))
                {
                    String p = String.valueOf(CalcularPrecio());
                    Intent intent = new Intent(CompletarEnvio.this,ConfirmarSolicitud.class);
                    intent.putExtra("idchofer",idUser);
                    intent.putExtra("idcliente",idcliente);
                    intent.putExtra("idviajes",idviaje);
                    intent.putExtra("fechavali",fechavali);
                    intent.putExtra("Origen",origeen);
                    intent.putExtra("OrigenLat",mExtraOrigenLat);
                    intent.putExtra("OrigenLog",mExtraOrigenLog);
                    intent.putExtra("Destino",destinoo);
                    intent.putExtra("DestinoLat",mExtraDestinoLat);
                    intent.putExtra("DestinoLog",mExtraDestinoLog);
                    intent.putExtra("Fecha",fecha);
                    intent.putExtra("Fechapubli",fechadepubl);
                    intent.putExtra("Precio",preciooo);
                    intent.putExtra("Hora",hora);
                    intent.putExtra("Peso",pesoo);
                    intent.putExtra("precio",p);
                    intent.putExtra("Costo",costo.getEditText().getText().toString());
                    intent.putExtra("descripcion",descrip.getEditText().getText().toString());
                    intent.putExtra("kiloss",peso.getEditText().getText().toString());
                    intent.putExtra("ancho",ancho.getEditText().getText().toString());
                    intent.putExtra("alto",alto.getEditText().getText().toString());
                    intent.putExtra("largo",largo.getEditText().getText().toString());
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(CompletarEnvio.this, "Todos los datos deben estar completos", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private double CalcularPrecio() {
        String []km = distancetext.split(" ");
        double kilos = Integer.parseInt(peso.getEditText().getText().toString());
        double largoo = Integer.parseInt(largo.getEditText().getText().toString());
        double anchoo = Integer.parseInt(ancho.getEditText().getText().toString());
        double altoo = Integer.parseInt(alto.getEditText().getText().toString());
        double kilometros= Double.parseDouble(km[0]);
        double pesovolumetrico= 0;
        double pkdre= 0;
        pesovolumetrico=(largoo*anchoo*altoo)/5000;
        pkdre=((2.15)*kilometros)+(2.45*pesovolumetrico)+6.5;
        Log.d("lechugas", String.valueOf(pesovolumetrico));
        Log.d("lechugas", String.valueOf(kilometros));


        return pkdre;

    }
}