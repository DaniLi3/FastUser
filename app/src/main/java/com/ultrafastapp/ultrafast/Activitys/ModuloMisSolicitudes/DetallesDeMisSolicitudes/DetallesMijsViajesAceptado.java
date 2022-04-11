package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.DetallesDeMisSolicitudes;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.EnviarSolicitud.PerfilChofer;
import com.ultrafastapp.ultrafast.Models.SolicitudesCanceladasmodel;
import com.ultrafastapp.ultrafast.Models.ViajesPublicados;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.SolicitudesCanceladasprovider;
import com.ultrafastapp.ultrafast.Providers.ViajesPubliProvider;
import com.ultrafastapp.ultrafast.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetallesMijsViajesAceptado extends AppCompatActivity {
    CircleImageView circleImageView;
    CircleImageView bak;
    Button Pagar;
    View cancelar;
    Authprovider authprovider;
    private String mExtraId;
    SolicitudesCanceladasprovider solicitudesCanceladasprovider;
    TextView origen,nombre,comentario,destino,contenidopaquetee;
    TextView detalles;
    TextView fecha;
    View Rastrear;
    TextView dimensiones;
    TextView peso;
    TextView precio;
    TextView personarecibe;
    TextView Estado;
    View Compartir;
    String telefono;
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
    String nombrechofer;
    String comenta;
    String costopaquete;
    String horasalida;
    String imagen;
    String fechavalidar;

    String dimensioness;
    String pesoo;
    String valoracion;
    String estado;
    String idchofer;
    String idviaje;

    ImageView imagenpaquete;

    double destinolat;
    double destinolog;
    double origenlat;
    double origenlog;

    String imagencliente,fecharegistrocliente,descripcioncliente;

    CardView veruser;
    CardView vercontactar;
    View SolicitarPagoLayout;
    View vercontactardos;
    public ViajesPubliProvider viajesPubliProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_mijs_viajes_aceptado);
        View llamar;
        llamar=findViewById(R.id.llamar);
        circleImageView=findViewById(R.id.imageViewPro);
        bak=findViewById(R.id.CirculeImageback);
        cancelar=findViewById(R.id.solicancelar);
        veruser=findViewById(R.id.veruser);
        nombre=findViewById(R.id.txtnombre);
        origen=findViewById(R.id.txtdetallesorigen);
        destino=findViewById(R.id.txtdetallesdestino);
        fecha=findViewById(R.id.txtdetallesfecha);
        peso=findViewById(R.id.txtkilos);
        comentario=findViewById(R.id.txtcomenario);
        contenidopaquetee=findViewById(R.id.txtcontenido);
        clienteimage=findViewById(R.id.imageuser);
        personarecibe=findViewById(R.id.txtpersonarecibe);
        Pagar=findViewById(R.id.solipagar);
        SolicitarPagoLayout=findViewById(R.id.solicitarpagolayout);
        vercontactardos=findViewById(R.id.mostrarcontactardos);
        vercontactar=findViewById(R.id.mostrarcontactar);
        Estado=findViewById(R.id.txtestado);
        Rastrear=findViewById(R.id.txtrastrear);
        Compartir=findViewById(R.id.txtcompartir);

        precio=findViewById(R.id.txtprecioo);
        imagenpaquete=findViewById(R.id.imagenpaquete);
        dimensiones=findViewById(R.id.txtdimensaiones);
        authprovider=new Authprovider();
      
        mExtraId = getIntent().getStringExtra("idHistoryBooking");
        idnoty = getIntent().getStringExtra("idnoty");
        idcliente = getIntent().getStringExtra("idcliente");
        personaquerecibe = getIntent().getStringExtra("personaquerecibe");
        notapaquete = getIntent().getStringExtra("notapaquete");
        contenidopaquete = getIntent().getStringExtra("contenidopaquete");
        dimensioness = getIntent().getStringExtra("dimensiones");
        pesoo = getIntent().getStringExtra("kilos");
        costopaquete = getIntent().getStringExtra("costopaquete");
        fechavalidar = getIntent().getStringExtra("fechavalidar");
        horasalida = getIntent().getStringExtra("horasalida");
        origenn = getIntent().getStringExtra("origen");
        destinoo = getIntent().getStringExtra("destino");
        fechasalida = getIntent().getStringExtra("fechasalida");
        fechasolicitud = getIntent().getStringExtra("fechasolicitud");
        imagen = getIntent().getStringExtra("imagen");
        personaquerecibe = getIntent().getStringExtra("personaquerecibe");
        precioo = getIntent().getStringExtra("precio");
        nombrechofer = getIntent().getStringExtra("nombre");
        valoracion = getIntent().getStringExtra("valoracion");
        imagencliente = getIntent().getStringExtra("imagencliente");
        fecharegistrocliente = getIntent().getStringExtra("fecharegistrocliente");
        descripcioncliente = getIntent().getStringExtra("descripcioncliente");
        telefono = getIntent().getStringExtra("telefono");
        estado = getIntent().getStringExtra("estado");
        idchofer = getIntent().getStringExtra("idchofer");
        idviaje = getIntent().getStringExtra("idviaje");
        destinolat = getIntent().getDoubleExtra("destinolat",0);
        destinolog = getIntent().getDoubleExtra("destinolog",0);
        origenlat = getIntent().getDoubleExtra("origenlat",0);
        origenlog = getIntent().getDoubleExtra("origenlog",0);
        Estado.setTextColor(Color.GREEN);

        if (!estado.equals("PAGADO"))
        {
            SolicitarPagoLayout.setVisibility(View.VISIBLE);
            vercontactar.setVisibility(View.GONE);
            vercontactardos.setVisibility(View.GONE);
            Estado.setTextColor(Color.RED);

        }
        destino.setText(destinoo);
        Estado.setText(estado);
        fecha.setText(fechasalida);
        origen.setText(origenn);
        precio.setText("MXN $"+precioo);
        peso.setText(pesoo+" kg.");
        nombre.setText(nombrechofer);
        comentario.setText(notapaquete);

        dimensiones.setText(dimensioness);
        contenidopaquetee.setText(contenidopaquete);
        personarecibe.setText(personaquerecibe);

        imagenpaquete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialog(imagen);
            }
        });


        Picasso.with(DetallesMijsViajesAceptado.this).load(imagen).into(imagenpaquete);
        if (imagencliente!=null)
        {
            Picasso.with(DetallesMijsViajesAceptado.this).load(imagencliente).into(clienteimage);
        }
        Compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetallesMijsViajesAceptado.this,CompartirViaje.class);
                intent.putExtra("idnoty",idnoty);
                intent.putExtra("idchofer",idchofer);
                intent.putExtra("usuario",nombrechofer);
                intent.putExtra("origenlat",origenlat);
                intent.putExtra("origenlog",origenlog);
                intent.putExtra("destinolat",destinolat);
                intent.putExtra("destinolog",destinolog);
                intent.putExtra("imagen",imagencliente);
                intent.putExtra("fecha",fechasalida);
                intent.putExtra("telefono",telefono);
                intent.putExtra("origen",origenn);
                intent.putExtra("destino",destinoo);
                intent.putExtra("fechasalida",fechasalida);

                startActivity(intent);
            }
        });
        Rastrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetallesMijsViajesAceptado.this, ViajeIniciado.class);
                intent.putExtra("idnoty",idnoty);
                intent.putExtra("idchofer",idchofer);
                intent.putExtra("usuario",nombrechofer);
                intent.putExtra("origenlat",origenlat);
                intent.putExtra("origenlog",origenlog);
                intent.putExtra("destinolat",destinolat);
                intent.putExtra("destinolog",destinolog);
                intent.putExtra("imagen",imagencliente);
                intent.putExtra("fecha",fechasalida);
                intent.putExtra("telefono",telefono);
                startActivity(intent);
            }
        });

        veruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("junior","so");
                Intent intent=new Intent(DetallesMijsViajesAceptado.this, PerfilChofer.class);

                intent.putExtra("id",idchofer);

                startActivity(intent);
            }
        });
        Pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent=new Intent(DetallesMijsViajesAceptado.this, RealizarPago.class);
                double pre = Double.parseDouble(precioo);
                intent.putExtra("monto",pre);
                Log.d("culo",precioo);
                startActivity(intent);*/


                Toast.makeText(DetallesMijsViajesAceptado.this, "Se pago con éxito", Toast.LENGTH_SHORT).show();
                SolicitarPagoLayout.setVisibility(View.INVISIBLE);
                Cancelar("SolicitudesAceptadas","PAGADO","aceptado");
               // Cancelar("SolicitudesEncurso","PAGADO","aceptado");
                ActualizarViaje();
                finish();

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
               Cancelar("SolicitudesCanceladas","SIN PAGAR","cancelado");
            }
        });

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String dial = "tel:" + telefono;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));

            }
        });
    }


    private void ActualizarViaje() {

        viajesPubliProvider=new ViajesPubliProvider("ViajesPublicados");
        ViajesPublicados viajesPublicados = new ViajesPublicados();
        viajesPublicados.setEstado(idchofer+"_"+"EN CURSO");
        viajesPublicados.setIdViajes(idviaje);
        Log.d("sentado",idviaje);
        viajesPubliProvider.actualizar(viajesPublicados).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("sentado","entro");

            }

        });
    }

    private void Cancelar(String var, String estado, String status) {
        Log.d("sentado",estado);


        solicitudesCanceladasprovider=new SolicitudesCanceladasprovider(var);
        SolicitudesCanceladasmodel modelo = new SolicitudesCanceladasmodel();
        modelo.setContenidopaquete(contenidopaquete);
        modelo.setDestino(destinoo);
        modelo.setFechadesalida(fechasalida);
        modelo.setDimensiones(dimensioness);
        modelo.setFechadepublicacionviaje(fecharegistrocliente);
        modelo.setFechadesolicitud(fechasolicitud);
        modelo.setIdchofer(idchofer);
        modelo.setIdcliente(idcliente);
        modelo.setIdnoty(idnoty);
        modelo.setImage(imagen);
        modelo.setKilos(pesoo);
        modelo.setEstado(estado);
        modelo.setCostopaquete(costopaquete);
        modelo.setFechavalidar(fechavalidar);
        modelo.setHorasalida(horasalida);
        modelo.setNotapaquete(notapaquete);
        modelo.setOrigen(origenn);
        modelo.setPersonaquerecibe(personaquerecibe);
        modelo.setPrecio(precioo);
        modelo.setSeo(idchofer+"_"+status);
        modelo.setSeocliente(idcliente+"_"+status);
        modelo.setStatus(status);
        solicitudesCanceladasprovider.Cancelar(modelo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                solicitudesCanceladasprovider=new SolicitudesCanceladasprovider("SolicitudesAceptadas");
                if (status.equals("cancelado")) {
                    solicitudesCanceladasprovider.delete(idnoty).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DetallesMijsViajesAceptado.this, "Viaje cancelado", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                else
                    {
                        finish();
                    }
            }
        });
    }
    public void mostrarDialog(String ima)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(DetallesMijsViajesAceptado.this);

        LayoutInflater inflater=getLayoutInflater();
        ImageView imageView;

        View view=inflater.inflate(R.layout.dialog_ver_imagen,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();
        imageView=view.findViewById(R.id.imagenpaquete);
        Picasso.with(DetallesMijsViajesAceptado.this).load(ima).into(imageView);



    }
}