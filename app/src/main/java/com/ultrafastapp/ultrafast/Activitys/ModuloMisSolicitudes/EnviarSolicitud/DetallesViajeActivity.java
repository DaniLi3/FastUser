package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.EnviarSolicitud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.ultrafastapp.ultrafast.Activitys.NotificacionesPrueba;
import com.ultrafastapp.ultrafast.Models.FCMBody;
import com.ultrafastapp.ultrafast.Models.IFCMResponse;
import com.ultrafastapp.ultrafast.Models.ViajesPublicados;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.Providers.GoogleApiProvider;
import com.ultrafastapp.ultrafast.Providers.NotificationProvider;
import com.ultrafastapp.ultrafast.Providers.SolicitudesProvider;
import com.ultrafastapp.ultrafast.Providers.TokenProvider;
import com.ultrafastapp.ultrafast.Providers.ViajesPubliProvider;
import com.ultrafastapp.ultrafast.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallesViajeActivity extends AppCompatActivity {

    TextView fecha,cityorigen,citydestino,origen,destino,precio,nombre,coche,cantidad;
    private String mExtraId;
    private ViajesPubliProvider viajesPubliProvider;
    private Choferprovider choferprovider;
    private TextView comentario;
    private TextView fechadepubli;
    private TextView peso;
    private TextView verperfilcom;
    private TextView descripcion;
    String distancetext;
    double mExtraOrigenLat;
    double mExtraOrigenLog;
    double mExtraDestinoLat;
    double mExtraDestinoLog;
    private  String idUser=" ";
    private GoogleApiProvider mGoogleApi;
    String imagen;
    private LatLng mOriginlnLo;
    private LatLng mdestinoLanLog;

    String origeen;
    String destinoo;

    String fechadepubl;
    String fechaa;
    String horaa;
    String pesoo;
    String idViaje;

    String viajespubli;
    String viajesconclu;
    String valoracion;
    String modelo;
    String marca;
    String matricula;
    String tipousuario;
    String nombree;
    String descripcionn;




    private ProgressDialog mdialog;
    private TokenProvider tokenProvider;
    private Authprovider authprovider;
    private NotificationProvider mnotificationProvider;
    public SolicitudesProvider solicitudesProvider;
    private ArrayList<String> mtokenList = new ArrayList<>();


    Button enviar;

    NotificacionesPrueba notificacionesPrueba;
   // private TextView precioo;

    CircleImageView circleImageView;
    private ImageView verori,verdesti;
    View dorigen;
    View ddestino;
    View detallesperfil;
    View Cargando;
    String canti;
    String fechavali;


    CircleImageView bak;
private ViajesPublicados viajesPublicados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_viaje);

        origen=findViewById(R.id.txtdetallesorigen);
        destino=findViewById(R.id.txtdetallesdestino);
        precio=findViewById(R.id.txtdetallesprecio);
        fecha=findViewById(R.id.txtdetallesfecha);
        nombre=findViewById(R.id.txtnomconductor);
        coche=findViewById(R.id.txtdetallescoche);
        circleImageView=findViewById(R.id.imageViewPro);
        bak=findViewById(R.id.CirculeImageback);
        verori=findViewById(R.id.vero);
        verdesti=findViewById(R.id.verde);
        descripcion=findViewById(R.id.txtdescripcion);
        detallesperfil = findViewById(R.id.verdetallesperfil);
        comentario=findViewById(R.id.txtdetallecomenta);
        fechadepubli=findViewById(R.id.txtdetallefechacre);
        peso=findViewById(R.id.txtdetalleskilos);
        dorigen=findViewById(R.id.deorigen);
        ddestino=findViewById(R.id.dedestino);
        cantidad=findViewById(R.id.txtdetaprecio);
        Cargando=findViewById(R.id.cargando);
        cityorigen=findViewById(R.id.txtcityorigen);
        citydestino=findViewById(R.id.txtcitydestino);
        verperfilcom=findViewById(R.id.verpe);
        mGoogleApi=new GoogleApiProvider(DetallesViajeActivity.this);

        enviar=findViewById(R.id.bntenviar);
        mdialog=new ProgressDialog(this);
        mdialog.setMessage("cargando..");
        mdialog.setCanceledOnTouchOutside(false);


        tokenProvider=new TokenProvider();
        mnotificationProvider = new NotificationProvider();

        authprovider=new Authprovider();
        solicitudesProvider=new SolicitudesProvider(authprovider.getid());

        notificacionesPrueba=new NotificacionesPrueba();



        dorigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirOrigen();
            }
        });
        ddestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirDestino();
            }
        });
        verori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirOrigen();
            }
        });
        verdesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirDestino();
            }
        });
        detallesperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirdetallesperfil();

            }
        });
        verperfilcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirdetallesperfil();

            }
        });

        mExtraId = getIntent().getStringExtra("idHistoryBooking");

        viajesPubliProvider= new ViajesPubliProvider("ViajesPublicados");
        choferprovider = new Choferprovider();
        viajesPublicados = new ViajesPublicados();

        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canti.equals("0")||pesoo.equals("0"))
                {
                    Toast.makeText(DetallesViajeActivity.this, "Este viaje ya está lleno", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Intent intent = new Intent(DetallesViajeActivity.this,CompletarEnvio.class);
                    intent.putExtra("idchofer",idUser);
                    intent.putExtra("idcliente",authprovider.getid());
                    intent.putExtra("idviajes",idViaje);
                    intent.putExtra("Origen",origeen);
                    intent.putExtra("OrigenLat",mExtraOrigenLat);
                    intent.putExtra("OrigenLog",mExtraOrigenLog);
                    intent.putExtra("Destino",destinoo);
                    intent.putExtra("DestinoLat",mExtraDestinoLat);
                    intent.putExtra("DestinoLog",mExtraDestinoLog);
                    intent.putExtra("Fecha",fechaa);
                    intent.putExtra("Fechapubli",fechadepubl);
                    intent.putExtra("fechavali",fechavali);
                    intent.putExtra("distancetext",distancetext);

                    intent.putExtra("Hora",horaa);
                    intent.putExtra("Peso",pesoo);
                    startActivity(intent);
                }
                //SendNotification("","");

            }
        });
        
        ObtenerDatos();

    }
    private void ObtenerDatos() {
        mdialog.show();
        viajesPubliProvider.getViajes(mExtraId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    fechaa =snapshot.child("fecha").getValue().toString();
                    fechavali = snapshot.child("fechavali").getValue().toString();
                    idUser =snapshot.child("idUser").getValue().toString();
                    idViaje =snapshot.child("idViajes").getValue().toString();
                    destinoo =snapshot.child("destino").getValue().toString();
                    origeen =snapshot.child("origen").getValue().toString();
                    horaa =snapshot.child("hora").getValue().toString();

                    pesoo =snapshot.child("peso").getValue().toString();
                    String Cityorigen =snapshot.child("cityOrigen").getValue().toString();
                    String Citydestino  =snapshot.child("cityDestino").getValue().toString();
                    String comenta =snapshot.child("comentario").getValue().toString();
                    fechadepubl =snapshot.child("fechaactual").getValue().toString();
                    String mExtraOrigenLatt=snapshot.child("origenLat").getValue().toString();
                    String mExtraOrigenLogg=snapshot.child("origenLog").getValue().toString();
                    String mExtraDestinoLatt=snapshot.child("destinoLat").getValue().toString();
                    String mExtraDestinoLogg=snapshot.child("destinoLog").getValue().toString();
                     canti = snapshot.child("cantidadentregas").getValue().toString();
                    mExtraOrigenLat= Double.parseDouble(mExtraOrigenLatt);
                    mExtraOrigenLog= Double.parseDouble(mExtraOrigenLogg);
                    mExtraDestinoLat= Double.parseDouble(mExtraDestinoLatt);
                    mExtraDestinoLog= Double.parseDouble(mExtraDestinoLogg);
                    mOriginlnLo=new LatLng(mExtraOrigenLat,mExtraOrigenLog);
                    mdestinoLanLog=new LatLng(mExtraDestinoLat,mExtraDestinoLog);
                    Log.d("saulo","distancetext"+" y "+"distancetext");
                    drawRute(mdestinoLanLog,"ini");
                    cantidad.setText(canti);
                    cityorigen.setText(Cityorigen);
                    citydestino.setText(Citydestino);
                    peso.setText(pesoo+" kg");
                    comentario.setText(comenta);
                    String fe[]=fechadepubl.split(" ");
                    fechadepubli.setText(fe[0]);

                    destino.setText(destinoo);
                    if (horaa.length()<4)
                    {                        horaa=horaa+"0";
                    }

                    fecha.setText(fechaa);
                    origen.setText(origeen);

                    choferprovider.getusuario(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                            {
                                 nombree = snapshot.child("nombre").getValue().toString();
                                String apellidos = snapshot.child("apellidos").getValue().toString();
                                 descripcionn = snapshot.child("bibliografia").getValue().toString();
                                modelo = snapshot.child("modelo").getValue().toString();
                                marca = snapshot.child("marca").getValue().toString();
                                matricula = snapshot.child("matricula").getValue().toString();
                                tipousuario = snapshot.child("tipousuario").getValue().toString();
                                viajesconclu = snapshot.child("realizados").getValue().toString();
                                viajespubli="0";
                                if (snapshot.child("publicados").exists())
                                {
                                    viajespubli = snapshot.child("publicados").getValue().toString();

                                }

                                valoracion = snapshot.child("calificacion").getValue().toString();





                                 imagen ="";
                                if (snapshot.child("image").exists())
                                {
                                    imagen=snapshot.child("image").getValue().toString();
                                    Picasso.with(DetallesViajeActivity.this).load(imagen).into(circleImageView);
                                }
                                String cochee = snapshot.child("marca").getValue().toString();

                                nombre.setText(nombree+" "+apellidos);
                                coche.setText(cochee);

                                descripcion.setText(descripcionn);
                                Cargando.setVisibility(View.GONE);
                                mdialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Cargando.setVisibility(View.GONE);
                            mdialog.dismiss();

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Cargando.setVisibility(View.GONE);
                mdialog.dismiss();

            }
        });
    }
    private void abrirdetallesperfil() {

        Intent intent = new Intent(DetallesViajeActivity.this, PerfilChofer.class);
        intent.putExtra("id",idUser);
        startActivity(intent);
    }

    private void EnviarNotificacion() {
        tokenProvider.getToken(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String toke=snapshot.child("token").getValue().toString();
                    Log.d("noty",toke);
                    llamarespe(toke);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void llamarespe(String toke) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            json.put("to",toke);
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo","soy un titulo");
            notificacion.put("detalle","soy un detalle");
            json.put("data",notificacion);

            String URL ="https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,json,null, null){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> header=new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAA735MMq0:APA91bHqS-u0ZsqJTy78h-Dc9He8QYVFw3oMI-d-oVLI-Ok6ffBvDVrxhO147iKzXTLg7J3cl8REzqGRpMDciEE_IzaYiE5myaOeix1iJbIMEeMabisuPT2fe9jloP_a_Xaw2v_NDSCz");
                    return  header;
                }
            };
            requestQueue.add(request);

        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }


    private void AbrirOrigen() {

        Intent intent = new Intent(DetallesViajeActivity.this, MapaDetallesOrigen.class);
        intent.putExtra("OrigenLat",mExtraOrigenLat);
        intent.putExtra("OrigenLog",mExtraOrigenLog);
        startActivity(intent);
    }
    private void AbrirDestino() {

        Intent intent = new Intent(DetallesViajeActivity.this, MapaDetallesDestino.class);
        intent.putExtra("DestinoLat",mExtraDestinoLat);
        intent.putExtra("DestinoLog",mExtraDestinoLog);
        startActivity(intent);
    }



    private void  SendNotification(final String time, final String dista) {

        tokenProvider.getToken(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {    //handler.removeCallbacks(runnable);
                    //mCounter=20;
                    //handler.postDelayed(runnable,1000);
                    // Log.d("CONDUCTOR","SI ENTRO el metodo: "+mtokenList);

                    String toke=snapshot.child("token").getValue().toString();

                    Log.d("notyy",toke);
                    Map<String,String> map = new HashMap<>();
                    map.put("title","SOLICITUD DE SERVICIO");
                    map.put("body",
                            "Un cliente esta solicitando un servicio");
                    map.put("idCliente",idUser);
                    map.put("origin","mExtraorigen");
                    map.put("destination","mdestino");
                    map.put("min","time");
                    map.put("distance","dista");
                    map.put("precio","String.valueOf(precio)");
                    map.put("searchById","false"); //ya que no estamos haciendo una busqueda por id
                    FCMBody fcmBody =new FCMBody(toke,"high", map);

                    mnotificationProvider.sendNotification(fcmBody).enqueue(new Callback<IFCMResponse>()
                    {
                        @Override
                        public void onResponse(Call<IFCMResponse> call, Response<IFCMResponse> response) {
                            if (response!=null)
                            {
                                if (response.body().getSuccess()==1)
                                {
                                    Toast.makeText(DetallesViajeActivity.this, "La peticion se envio correctamente", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(DetallesViajeActivity.this, "La peticion no se pudo enviar", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(DetallesViajeActivity.this, "No ubo respuesta del servidor", Toast.LENGTH_SHORT).show();
                            }
                            long timee=0;
                            String datee = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());




                        }
                        @Override
                        public void onFailure(Call<IFCMResponse> call, Throwable t) {
                            Toast.makeText(notificacionesPrueba, "Fallo al enviar la notificacion", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                else
                {
                    Toast.makeText(DetallesViajeActivity.this, "No existe el token", Toast.LENGTH_SHORT).show();
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void drawRute(LatLng latLng, String ini)
    {
        mGoogleApi.getDirections(mOriginlnLo,latLng ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.body());
                    JSONArray jsonArray = jsonObject.getJSONArray("routes");
                    JSONObject route= jsonArray.getJSONObject(0);
                    JSONObject polylines=route.getJSONObject("overview_polyline");
                    String points =polylines.getString("points");

                    JSONArray legs=route.getJSONArray("legs");
                    JSONObject leg=legs.getJSONObject(0);
                    JSONObject distance=leg.getJSONObject("distance");
                    JSONObject duration=leg.getJSONObject("duration");
                    distancetext=distance.getString("text");
                    String durationtext=duration.getString("text");








                }
                catch (Exception e)
                {
                    Log.d("Error","Error encontrado"+e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }


}