package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.DetallesDeMisSolicitudes;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;
import com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.EnviarSolicitud.PerfilChofer;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.SolicitudesProvider;
import com.ultrafastapp.ultrafast.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetallesMisViajes extends AppCompatActivity {

    CircleImageView circleImageView;
    CircleImageView bak;
    Button aceptar;
    Button cancelar;
    Authprovider authprovider;
    private String mExtraId;

    TextView origen,nombre,comentario,destino,contenidopaquetee;
    TextView detalles;
    TextView fecha;
    TextView dimensiones;
    TextView peso;
    TextView precio;
    TextView personarecibe;

    String status;
    CircleImageView clienteimage;
    String idnoty;
    String idcliente;
    String personaquerecibe;
    String notapaquete;
    String contenidopaquete;
    String origenn;
    String destinoo;
    String fechasalida;
    String fechasolicitud;
    String precioo;
    String nombree;
    String comenta;

    String imagen;

    String dimensioness;
    String pesoo;
    String valoracion;
    String idchofer;
    ImageView imagenpaquete;
    View botones;

    String imagencliente,fecharegistrocliente,descripcioncliente;

    View veruser;

    SolicitudesProvider solicitudesProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_mis_viajes);
        circleImageView = findViewById(R.id.imageViewPro);
        bak = findViewById(R.id.CirculeImageback);
        aceptar = findViewById(R.id.soliaceptar);
        cancelar = findViewById(R.id.solicancelar);
        veruser = findViewById(R.id.veruser);
        nombre = findViewById(R.id.txtnombre);
        origen = findViewById(R.id.txtdetallesorigen);
        destino = findViewById(R.id.txtdetallesdestino);
        fecha = findViewById(R.id.txtdetallesfecha);
        peso = findViewById(R.id.txtkilos);
        comentario = findViewById(R.id.txtcomenario);
        contenidopaquetee = findViewById(R.id.txtcontenido);
        clienteimage = findViewById(R.id.imageuser);
        personarecibe = findViewById(R.id.txtpersonarecibe);
        botones = findViewById(R.id.botones);

        precio = findViewById(R.id.txtprecioo);
        imagenpaquete = findViewById(R.id.imagenpaquete);
        dimensiones = findViewById(R.id.txtdimensaiones);
        authprovider = new Authprovider();
        solicitudesProvider = new SolicitudesProvider(authprovider.getid());
        mExtraId = getIntent().getStringExtra("idHistoryBooking");
        idnoty = getIntent().getStringExtra("idnoty");
        idchofer = getIntent().getStringExtra("idchofer");
        idcliente = getIntent().getStringExtra("idcliente");
        personaquerecibe = getIntent().getStringExtra("personaquerecibe");
        notapaquete = getIntent().getStringExtra("notapaquete");
        contenidopaquete = getIntent().getStringExtra("contenidopaquete");
        dimensioness = getIntent().getStringExtra("dimensiones");
        pesoo = getIntent().getStringExtra("kilos");
        origenn = getIntent().getStringExtra("origen");
        status = getIntent().getStringExtra("status");
        destinoo = getIntent().getStringExtra("destino");
        fechasalida = getIntent().getStringExtra("fechasalida");
        fechasolicitud = getIntent().getStringExtra("fechasolicitud");
        imagen = getIntent().getStringExtra("imagen");
        personaquerecibe = getIntent().getStringExtra("personaquerecibe");
        precioo = getIntent().getStringExtra("precio");
        nombree = getIntent().getStringExtra("nombre");
        valoracion = getIntent().getStringExtra("valoracion");
        imagencliente = getIntent().getStringExtra("imagencliente");
        fecharegistrocliente = getIntent().getStringExtra("fecharegistrocliente");
        descripcioncliente = getIntent().getStringExtra("descripcioncliente");
        if (status.equals("cancelado")){
            cancelar.setVisibility(View.GONE);

        }





        destino.setText(destinoo);
        fecha.setText(fechasalida);
        origen.setText(origenn);
        precio.setText("$"+precioo+" MXN");
        peso.setText(pesoo+" kg");
        nombre.setText(nombree);
        comentario.setText(notapaquete);
        Log.d("contador 2",""+precioo);
        dimensiones.setText(dimensioness);
        contenidopaquetee.setText(contenidopaquete);
        personarecibe.setText(personaquerecibe);
        imagenpaquete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialog(imagen);
            }
        });

        Picasso.with(DetallesMisViajes.this).load(imagen).into(imagenpaquete);
        if (imagencliente!=null )
        {
            Picasso.with(DetallesMisViajes.this).load(imagencliente).into(clienteimage);
        }

        veruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("junior","so");
                Intent intent=new Intent(DetallesMisViajes.this, PerfilChofer.class);

                intent.putExtra("id",idchofer);

                startActivity(intent);
            }
        });
        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelarsoli(idnoty);
                finish();
            }
        });
        botones=findViewById(R.id.botones);

        status = getIntent().getStringExtra("status");


       /* if (status.equals("cancelado"))
        {
            botones.setVisibility(View.GONE);
        }*/
    }

    private void cancelarsoli(String id) {

        SolicitudesProvider solicitudesProvider=new SolicitudesProvider();
     solicitudesProvider.delete(id).addOnSuccessListener(new OnSuccessListener<Void>() {
         @Override
         public void onSuccess(Void aVoid) {
             Toast.makeText(DetallesMisViajes.this, "Se eliminó tu solicitud", Toast.LENGTH_SHORT).show();
         }
     });
    }
    public void mostrarDialog(String ima)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(DetallesMisViajes.this);

        LayoutInflater inflater=getLayoutInflater();
        ImageView imageView;

        View view=inflater.inflate(R.layout.dialog_ver_imagen,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();
        imageView=view.findViewById(R.id.imagenpaquete);
        Picasso.with(DetallesMisViajes.this).load(ima).into(imageView);



    }
}