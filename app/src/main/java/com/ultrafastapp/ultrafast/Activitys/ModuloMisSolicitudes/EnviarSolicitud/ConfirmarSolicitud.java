package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.EnviarSolicitud;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;
import com.ultrafastapp.ultrafast.Models.Choferes;
import com.ultrafastapp.ultrafast.Models.FCMBody;
import com.ultrafastapp.ultrafast.Models.IFCMResponse;
import com.ultrafastapp.ultrafast.Models.SolicitudesModel;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.Providers.ImagePaqueteProvider;
import com.ultrafastapp.ultrafast.Providers.NotificationProvider;
import com.ultrafastapp.ultrafast.Providers.SolicitudesProvider;
import com.ultrafastapp.ultrafast.Providers.TokenProvider;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.Utils.FileUtil;
import com.ultrafastapp.ultrafast.channel.SNTPClient;

import java.io.File;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmarSolicitud extends AppCompatActivity {
    double mExtraOrigenLat;
    double mExtraOrigenLog;
    double mExtraDestinoLat;
    double mExtraDestinoLog;
    private Authprovider authprovider;
    CircleImageView circleImageView;
    int c=0;
    private TextView origen;
    private TextView destino;private TextView fechasali;private TextView horasali;private TextView peso,dimen;
    TextView descrip;
    String viajesuser;
    private ProgressDialog mdialog;
    private TokenProvider tokenProvider;
    private ImageView imageUser;
    private NotificationProvider mnotificationProvider;

    private  final int GalleryRequest=1;
    private final int GALLERY_REQUEST = 1;
    private File mImageFile;
    ImagePaqueteProvider imageProvider;
    public SolicitudesProvider solicitudesProvider;

    private  String idUser=" ";
    private  String idcliente=" ";
    private  String idviaje=" ";
    private  String fechavali=" ";
    private TextView precio;
    public TextInputLayout persona;
    public TextInputLayout Telpersona;
    Choferprovider choferprovider;
    public TextInputLayout nota;
    String origeen;
    String destinoo;
    String preciooo;
    String costo;
    String fecha,hora,pesoo;
    String fechadepubl;
    String preciofinal;
    String descripcion;
    String kilos;
    String ancho;
    String alto;
    String largo;
    ImageView subir;
    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_solicitud);

        origen = findViewById(R.id.txtorigen);
        destino = findViewById(R.id.txtdestino);
        fechasali = findViewById(R.id.txtfechasalida);
        horasali = findViewById(R.id.txthorasalida);
        peso = findViewById(R.id.txtpeso);
        dimen = findViewById(R.id.txtdimen);
        precio=findViewById(R.id.txtprecio);
        circleImageView=findViewById(R.id.CirculeImagebackk);
        enviar=findViewById(R.id.btndialog);
        descrip=findViewById(R.id.txtdescripp);
        persona=findViewById(R.id.txtpersona);
        nota=findViewById(R.id.txtnotas);
        Telpersona=findViewById(R.id.txtpersonatel);

        imageUser = findViewById(R.id.imagepaquete);
        subir=findViewById(R.id.subirima);
        tokenProvider=new TokenProvider();
        imageProvider = new ImagePaqueteProvider("image_paquetes");

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
        costo =getIntent().getStringExtra("Costo");
        preciofinal =getIntent().getStringExtra("precio");
        descripcion =getIntent().getStringExtra("descripcion");
        kilos =getIntent().getStringExtra("kiloss");
        ancho =getIntent().getStringExtra("ancho");
        alto =getIntent().getStringExtra("alto");
        largo =getIntent().getStringExtra("largo");
        NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
        formatoImporte = NumberFormat.getCurrencyInstance(new Locale("spa","MX"));
        NumberFormat finalFormatoImporte = formatoImporte;
        mnotificationProvider = new NotificationProvider();

        choferprovider=new Choferprovider();
        authprovider=new Authprovider();
        solicitudesProvider=new SolicitudesProvider();

        origen.setText(origeen);
        destino.setText(destinoo);
       precio.setText(finalFormatoImporte.format(Double.parseDouble(preciofinal)));
        fechasali.setText(fecha);
        horasali.setText(hora);
        peso.setText(kilos+" kg");
        descrip.setText(descripcion);
        dimen.setText(ancho+" x "+largo+" x "+alto+" cm");

        mdialog = new ProgressDialog(this);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (persona.getEditText().getText().toString().equals("")||nota.getEditText().getText().toString().equals(""))
                {
                    Toast.makeText(ConfirmarSolicitud.this, "Debes de llenar todos los campos", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    SendNotification("","");

                }

            }
        });

        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    //Verifica permisos para Android 6.0+
                    int permissionCheck = ContextCompat.checkSelfPermission(
                            ConfirmarSolicitud.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        Log.i("Mensaje", "No se tiene permiso para leer.");
                        ActivityCompat.requestPermissions(ConfirmarSolicitud.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
                    } else {
                        Log.i("Mensaje", "Se tiene permiso para leer!");
                    }
                }
            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        choferprovider.getusuario(authprovider.getid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                     viajesuser=snapshot.child("solicitados").getValue().toString();
                     Log.d("solicitados","es "+viajesuser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void saveImage() {




    }
    private void SendNotification(final String time, final String dista) {

        if (mImageFile!=null)
        {
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

            final String[] date=datee[0].split(" ");
            final String[] fechae=date[0].split("/");
            final String[] fecha1=fechae[0].split("-");
            final String[] fecha2=fecha1[2].split("0");
           final String[] horamini=date[1].split(":");
            String contador=fecha2[1]+fecha1[1]+fecha1[0]+horamini[0]+horamini[1]+horamini[2];

            mdialog.setMessage("Enviando solicitud...");
            mdialog.setCanceledOnTouchOutside(false);
            mdialog.show();
            tokenProvider.getToken(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        String id = solicitudesProvider.mdatabase.push().getKey();


                        //handler.removeCallbacks(runnable);
                        //mCounter=20;
                        //handler.postDelayed(runnable,1000);
                        // Log.d("CONDUCTOR","SI ENTRO el metodo: "+mtokenList);
                        imageProvider.saveImage(ConfirmarSolicitud.this,UUID.randomUUID().toString(),mImageFile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful())
                                {
                                    Log.d("fallos s es",id);
                                    imageProvider.getstorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            sumarsolicitados();
                                            String telpersona=" ";
                                            if (!Telpersona.getEditText().getText().toString().equals(""))
                                            {
                                                telpersona=Telpersona.getEditText().getText().toString();
                                            }
                                            String image = uri.toString();
                                            String toke=snapshot.child("token").getValue().toString();
                                            Map<String,String> map = new HashMap<>();
                                            map.put("title","SOLICITUD DE SERVICIO");
                                            map.put("body",
                                                    "Un cliente esta solicitando un servicio");
                                            map.put("idnoty",id);
                                            map.put("idchofer",idUser);
                                            map.put("idcliente",authprovider.getid());
                                            map.put("contenido",descripcion);
                                            map.put("destino",destinoo);
                                            map.put("fechadepubli",fechadepubl);
                                            map.put("fechasalida",fecha);

                                            map.put("fechasoli", contador);
                                            map.put("imagen",image);
                                            map.put("kilos",pesoo);
                                            map.put("nota", nota.getEditText().getText().toString());
                                            map.put("origen",origeen);
                                            map.put("personarecibe", persona.getEditText().getText().toString());
                                            map.put("precio",preciofinal);
                                            map.put("seo",idUser+"_"+"creado");
                                            map.put("seocliente",contador+"_"+idcliente);
                                            map.put("telpersona",telpersona);
                                            FCMBody fcmBody =new FCMBody(toke,"high", map);

                                            mnotificationProvider.sendNotification(fcmBody).enqueue(new Callback<IFCMResponse>()
                                            {
                                                @Override
                                                public void onResponse(Call<IFCMResponse> call, Response<IFCMResponse> response) {
                                                    if (response!=null)
                                                    {
                                                        if (response.body().getSuccess()==1)
                                                        {
                                                            Log.d("fallos","si se envio correctamente");
                                                            //   Toast.makeText(ConfirmarSolicitud.this, "La peticion se envio correctamente", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else
                                                        {
                                                            Log.d("fallos"," no envio correctamente");
                                                            Toast.makeText(ConfirmarSolicitud.this, "La peticion no se pudo enviar", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(ConfirmarSolicitud.this, "No ubo respuesta del servidor", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                                @Override
                                                public void onFailure(Call<IFCMResponse> call, Throwable t) {
                                                    Toast.makeText(ConfirmarSolicitud.this, "Fallo al enviar la notificacion", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            Log.d("fallos","si se envio correctamente "+fecha   );
                                            String ff= String.valueOf(c++);

                                            SolicitudesModel solicitudesModel= new SolicitudesModel(
                                                    fecha,
                                                    hora,
                                                    contador+" ee",
                                                    fechadepubl,
                                                    origeen,// origen
                                                    destinoo,//destino
                                                    idcliente,//id cliente
                                                    idUser,// idDelChoer
                                                    id, //id de la notificaion
                                                    "creado",
                                                    preciofinal,
                                                    costo,
                                                    fechavali,
                                                    idUser+"_"+"creado",
                                                    contador+"_"+idcliente,
                                                    kilos,
                                                    mExtraOrigenLat,
                                                    mExtraOrigenLog,
                                                    mExtraDestinoLat,
                                                    mExtraDestinoLog,
                                                    new Date().getTime(),//fecha
                                                    image,
                                                   persona.getEditText().getText().toString(),
                                                    nota.getEditText().getText().toString(),
                                                    descripcion,
                                                    ancho+" x "+largo+" x "+alto+" cm"
                                                    ,telpersona,
                                                    "SIN PAGAR",
                                                    idviaje

                                            );


                                            Log.d("fallos s es",id);
                                            solicitudesProvider.create(solicitudesModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    solicitudesProvider=new SolicitudesProvider();
                                                    mdialog.dismiss();


                                                    Intent intent =new Intent(ConfirmarSolicitud.this,SolicitudEnviada.class);
                                                    intent.putExtra("fecha",fecha);
                                                    // intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);

                                                }
                                            });
                                        }
                                    });

                                    // Toast.makeText(ConfirmarSolicitud.this      , "Su informacion se actualizo correctamente", Toast.LENGTH_SHORT).show();


                                }
                                else
                                {
                                    mdialog.dismiss();
                                    Toast.makeText(ConfirmarSolicitud.this, "No se pudo subir la imagen", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });


                    }
                    else
                    {
                        Toast.makeText(ConfirmarSolicitud.this, "No existe el token", Toast.LENGTH_SHORT).show();
                        Log.d("fallos","fallo 2");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else
        {
            Toast.makeText(this, "Por favor ingrese una imagen de su paquete", Toast.LENGTH_SHORT).show();
        }



    }


    private void sumarsolicitados() {
        int viaje= Integer.parseInt(viajesuser);
        Choferes choferes=new Choferes();
        choferes.setSolicitados(String.valueOf(viaje+1));
        choferes.setId(authprovider.getid());
        choferprovider.Actializarsolicitados(choferes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {

                }
                else
                {
                    Toast.makeText(ConfirmarSolicitud.this, "Sucedio un error", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

    private void OpenGallery() {
        Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GalleryRequest);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALLERY_REQUEST && resultCode==RESULT_OK)
        {
            try {
                mImageFile = FileUtil.from(this, data.getData());
                imageUser.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
                imageUser.setVisibility(View.VISIBLE);
              //  updateProfile();

            }
            catch (Exception e)
            {
                Log.d("ERROR","Mensaje: "+e.getMessage());
            }
        }

    }

    private void updateProfile() {
        mdialog.setMessage("Enviando solicitud...");
        mdialog.setCanceledOnTouchOutside(false);
        mdialog.show();

        saveImage();


    }
}