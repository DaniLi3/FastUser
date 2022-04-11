package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.EnviarSolicitud;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.ultrafastapp.ultrafast.Models.Choferes;
import com.ultrafastapp.ultrafast.Models.FCMBody;
import com.ultrafastapp.ultrafast.Models.IFCMResponse;
import com.ultrafastapp.ultrafast.Models.ModelCompartir;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.Providers.CompartirViajeProvider;
import com.ultrafastapp.ultrafast.Providers.NotificationProvider;
import com.ultrafastapp.ultrafast.Providers.TokenProvider;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.channel.SNTPClient;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerUsuarioPublico extends AppCompatActivity {



    private TextView Viajespubli;
    private TextView Viajesconclu;
    private TextView Valoracion;
    private TextView Tipousuario;
    private TextView Nombre;
    private TextView Bibliografia;
    private ProgressDialog mdialog;
    private Button compartir;
    private  String id;
    private  String idchofer;
    private  String nombre;
    private  String apellidos;
    String imagen=" ";
    String imagencompartir=" ";
    Choferprovider choferprovider;
    private TokenProvider tokenProvider;
    Choferes choferes;
    CircleImageView circleImageView;
    CircleImageView circulo;
    private NotificationProvider mnotificationProvider;
    public CompartirViajeProvider CompartirViajeProvider;
    public Authprovider authprovider;
    String apellidoss;
    String nombree;
    String origen;
    String destino;
    String fechasalida;
    double destinolat;
    double destinolog;
    double origenlat;
    double origenlog;
    String idnoty;
    String nombrechofer;
    String tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuercompartir);
        Viajespubli=findViewById(R.id.txtviajespubli);
        Viajesconclu=findViewById(R.id.txtviajesconcluidos);
        Valoracion=findViewById(R.id.txtvaloracion);
        Tipousuario=findViewById(R.id.tipousuario);
        Nombre=findViewById(R.id.username);
        Bibliografia=findViewById(R.id.txtdes);
        circleImageView=findViewById(R.id.imageViewPro);
        circulo=findViewById(R.id.imageView);
        compartir=findViewById(R.id.btncompartir);
        choferprovider = new Choferprovider();
        choferes=new Choferes();
        mdialog = new ProgressDialog(this);
        tokenProvider=new TokenProvider();
        mnotificationProvider = new NotificationProvider();
        CompartirViajeProvider=new CompartirViajeProvider();
        authprovider=new Authprovider();

        id = getIntent().getStringExtra("id");
        idchofer = getIntent().getStringExtra("idchofer");
        nombre = getIntent().getStringExtra("nombre");
        fechasalida = getIntent().getStringExtra("fechasalida");
        imagen = getIntent().getStringExtra("imagen");
        String bibliografia = getIntent().getStringExtra("bibliografia");
        String fechausuario = getIntent().getStringExtra("fechausuario");
        String viajesrealizados = getIntent().getStringExtra("viajesrealizados");
        String viajessolicitados = getIntent().getStringExtra("viajessolicitados");
        String tipousuario = getIntent().getStringExtra("tipousuario");
        String cali = getIntent().getStringExtra("cali");
         origen = getIntent().getStringExtra("origen");
         destino = getIntent().getStringExtra("destino");
        destinolat = getIntent().getDoubleExtra("destinolat",0);
        destinolog = getIntent().getDoubleExtra("destinolog",0);
        origenlat = getIntent().getDoubleExtra("origenlat",0);
        origenlog = getIntent().getDoubleExtra("origenlog",0);
         nombrechofer = getIntent().getStringExtra("nombrechofer");
         tel = getIntent().getStringExtra("telchofer");
        String fecha = getIntent().getStringExtra("fecha");
        idnoty = getIntent().getStringExtra("idnoty");
        Log.d("cobarde", origen+" "+destino);

        Valoracion.setText(cali);
        if (imagen!=null) {
            if (!imagen.equals(""))
            {
                Picasso.with(VerUsuarioPublico.this).load(imagen).into(circleImageView);
                circulo.setVisibility(View.INVISIBLE);
            }
        }

        Nombre.setText(nombre);
        Viajesconclu.setText(viajesrealizados);
        Tipousuario.setText(tipousuario);
        Bibliografia.setText(bibliografia);





       obtenerdatos();

        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendNotification();
            }
        });

    }
    private void SendNotification() {

            final String[] datee = {(DateFormat.format("dd-MM-yyyy hh:mm:ss", new Date()).toString())};
            SNTPClient.getDate(TimeZone.getTimeZone("America/Mexico_City"), new SNTPClient.Listener() {
                @Override
                public void onTimeReceived(String rawDate) {
                    // rawDate -> 2019-11-05T17:51:01+0530
                    Log.e(SNTPClient.TAG, rawDate);
                    datee[0] = rawDate;
                    //Toast.makeText(getContext(), "es "+rawDate, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Exception ex) {
                    Log.e(SNTPClient.TAG, ex.getMessage());
                }
            });

            mdialog.setMessage("Compartiendo viaje...");
            mdialog.setCanceledOnTouchOutside(false);
            mdialog.show();
            tokenProvider.getToken(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String toke=snapshot.child("token").getValue().toString();
                    Map<String,String> map = new HashMap<>();
                        map.put("title","VIAJE COMPARTIDO");
                    map.put("body",
                            nombree+" Compartió el viaje contigo");
                    map.put("id","dsd");

                    FCMBody fcmBody =new FCMBody(toke,"high", map);

                    mnotificationProvider.sendNotification(fcmBody).enqueue(new Callback<IFCMResponse>()
                    {
                        @Override
                        public void onResponse(Call<IFCMResponse> call, Response<IFCMResponse> response) {
                            if (response!=null)
                            {
                                if (response.body().getSuccess()==1)
                                {
                                CrearViajecompartido();
                                mdialog.dismiss();
                                 }
                                else
                                {
                                    Log.d("fallos"," no envio correctamente");
                                    Toast.makeText(VerUsuarioPublico.this, "La peticion no se pudo enviar", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(VerUsuarioPublico.this, "No ubo respuesta del servidor", Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onFailure(Call<IFCMResponse> call, Throwable t) {
                            Toast.makeText(VerUsuarioPublico.this, "Fallo al enviar la notificacion", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    private void CrearViajecompartido() {
        String idd=CompartirViajeProvider.mdatabase.push().getKey();
        ModelCompartir modelCompartir=new ModelCompartir();
        modelCompartir.setId(idd);
        modelCompartir.setIdusercompartido(id);
        modelCompartir.setIdchofer(idchofer);
        modelCompartir.setNombrechofer(nombrechofer);
        modelCompartir.setTel(tel);
        modelCompartir.setIdusuario(authprovider.getid());
        modelCompartir.setNombreusuariocompartido(nombre);
        modelCompartir.setNombreusuarioviaje(nombree +" "+apellidoss);
        modelCompartir.setImagenusuario(imagencompartir);
        modelCompartir.setDestinolat(destinolat);
        modelCompartir.setDestinolog(destinolog);
        modelCompartir.setOrigenlat(origenlat);
        modelCompartir.setOrigenlog(origenlog);
        modelCompartir.setOrigen(origen);
        modelCompartir.setDestino(destino);
        modelCompartir.setFechasalida(fechasalida);


        CompartirViajeProvider.create(modelCompartir).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(VerUsuarioPublico.this, "Se compartió correctamente", Toast.LENGTH_SHORT).show();

            }
        });




    }

    public void obtenerdatos()
    {
        choferprovider.getusuario(authprovider.getid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    nombree=snapshot.child("nombre").getValue().toString();
                     apellidoss=snapshot.child("apellidos").getValue().toString();


                    if (snapshot.child("calificacion").exists())
                    {
                        String calificacion=snapshot.child("calificacion").getValue().toString();


                    }

                    String tipousuario=snapshot.child("tipousuario").getValue().toString();
                    String realizados=snapshot.child("realizados").getValue().toString();

                    String bibliografia=snapshot.child("bibliografia").getValue().toString();
                  //  String publicados=snapshot.child("publicados").getValue().toString();
                    if (snapshot.child("image").exists())
                    {

                        imagencompartir=snapshot.child("image").getValue().toString();

                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}