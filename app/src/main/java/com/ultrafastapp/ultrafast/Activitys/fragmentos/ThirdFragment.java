package com.ultrafastapp.ultrafast.Activitys.fragmentos;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;
import com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.DetallesDeMisSolicitudes.ViajesCompartidos;
import com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.TapMisViajes;
import com.ultrafastapp.ultrafast.Activitys.PerfilUsuario.EditarPerfil;
import com.ultrafastapp.ultrafast.Activitys.PerfilUsuario.InfoCuenta.InfoCuenta;
import com.ultrafastapp.ultrafast.Models.Choferes;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.Providers.ImageProvider;
import com.ultrafastapp.ultrafast.Providers.ViajesPubliProvider;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.Utils.FileUtil;
import com.ultrafastapp.ultrafast.channel.SNTPClient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class   ThirdFragment extends Fragment {
    TextView Calificacion,Realizados,Solicitados,Tipousuario;

    CardView infocuentacard,history,infopersonal,compartir,missolicitudes;

    ViajesPubliProvider viajesPubliProvider;

    Choferprovider choferprovider;
    private ImageView imageUser,cambiar;
    private final int GALLERY_REQUEST = 1;
    Button button;
    ProgressDialog progressDialog;
    Authprovider authprovider;
    TextView username;
    ImageProvider imageProvider;
    View misviajes;
    private ProgressDialog mdialog;
    CardView vieew;
    private File mImageFile;
    private String imageUrl;
    private  final int GalleryRequest=1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String nombre;
    private String apellidos;
    private String genero;
    DatabaseReference mdatabas;
  //  private String nombre;
   // private String nombre;

    public ThirdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



        choferprovider = new Choferprovider();
        viajesPubliProvider=new ViajesPubliProvider("ViajesPublicados");
        imageProvider = new ImageProvider("image_users");
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Procesando");
        progressDialog.setMessage("Cargando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mdialog = new ProgressDialog(getContext());
        mdatabas = FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference();



        getUserInfo();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_third, container, false);

        username = view.findViewById(R.id.username);
        cambiar = view.findViewById(R.id.fotoProfile);
        imageUser = view.findViewById(R.id.imageViewPro);

        Calificacion=view.findViewById(R.id.txtcalificacion);
        Realizados=view.findViewById(R.id.txtrealizados);
        Solicitados=view.findViewById(R.id.txtsolicitados);
        Tipousuario=view.findViewById(R.id.tipousuario);
        missolicitudes=view.findViewById(R.id.cardmissolicitudes);

        infopersonal=view.findViewById(R.id.cardinfopersonal);
        compartir=view.findViewById(R.id.cardviajecompar);
        history=view.findViewById(R.id.cardhistoryviaje);
        infocuentacard=view.findViewById(R.id.cardinfocuenta);


        infopersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SNTPClient.getDate(TimeZone.getTimeZone("America/Mexico_City"), new SNTPClient.Listener() {
                    @Override
                    public void onTimeReceived(String rawDate) {
                        // rawDate -> 2019-11-05T17:51:01+0530
                        Log.e(SNTPClient.TAG, rawDate);
                        //Toast.makeText(getContext(), "es "+rawDate, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Exception ex) {
                        Log.e(SNTPClient.TAG, ex.getMessage());
                    }
                });
                Intent intent = new Intent(getContext(), EditarPerfil.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("genero",genero);
                intent.putExtra("apellidos",apellidos);
                startActivity(intent);
               // sasa();


            }
        });


        infocuentacard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), InfoCuenta.class);
                startActivity(intent);
            }
        }); missolicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentt = new Intent(getContext(), TapMisViajes.class);
                startActivity(intentt);
            }
        });
        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ViajesCompartidos.class);
                startActivity(intent);
            }
        });
        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenGallery();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    //Verifica permisos para Android 6.0+
                    int permissionCheck = ContextCompat.checkSelfPermission(
                            getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        Log.i("Mensaje", "No se tiene permiso para leer.");
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
                    } else {
                        Log.i("Mensaje", "Se tiene permiso para leer!");
                    }
                }



            }
        });
        return view;

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
                mImageFile = FileUtil.from(getContext(), data.getData());
                imageUser.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
                updateProfile();

            }
            catch (Exception e)
            {
                Log.d("ERROR","Mensaje: "+e.getMessage());
            }
        }

    }

    private void updateProfile() {
        mdialog.setMessage("Subiendo imagen...");
        mdialog.setCanceledOnTouchOutside(false);
        mdialog.show();

        saveImage();


    }

    private void saveImage() {

        imageProvider.saveImage(getContext(), UUID.randomUUID().toString(),mImageFile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful())
                {
                    imageProvider.getstorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String image = uri.toString();
                            Choferes choferes=new Choferes();
                            choferes.setImage(image);
                            choferes.setId(authprovider.getid());
                            choferprovider.updateima(choferes).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mdialog.dismiss();
                                    Toast.makeText(getContext(), "Su informacion se actualizo correctamente", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                else
                {
                    Toast.makeText(getContext(), "No se pudo subir la imagen", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    public void sasa() {
        SNTPClient.getDate(TimeZone.getTimeZone("America/Mexico_City"), new SNTPClient.Listener() {
            @Override
            public void onTimeReceived(String rawDate) {
                // rawDate -> 2019-11-05T17:51:01+0530
                long localJsonDate = GetTimeStamp("22-07-2021 01:57:44");
                long remoteJsondate = GetTimeStamp("21-07-2021 01:57:44");


                if (remoteJsondate > localJsonDate)  {
                    //Se debe actualizar datos
                    Log.d("llave","se necestita actualizar"+localJsonDate);
                } else {
                    Log.d("llave","datos actualizados"+localJsonDate);
                }

                Log.d("llave","datos actualizados "+getDate(localJsonDate));
            }

            @Override
            public void onError(Exception ex) {
                Log.e(SNTPClient.TAG, ex.getMessage());
            }
        });



    }

    private void getUserInfo()
  {
      authprovider = new Authprovider();
      choferprovider.getusuario(authprovider.getid()).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.exists())
              {
                  nombre="";
                  apellidos=snapshot.child("apellidos").getValue().toString();
                  nombre=snapshot.child("nombre").getValue().toString();
                  genero=snapshot.child("genero").getValue().toString();
                  String calificacion=snapshot.child("calificacion").getValue().toString();
                  String realizados=snapshot.child("realizados").getValue().toString();
                  String solicitados=snapshot.child("solicitados").getValue().toString();
                  String tipousuarioo=snapshot.child("tipousuario").getValue().toString();
                  String image="";
                  if(snapshot.hasChild("image"))
                  {
                      image=snapshot.child("image").getValue().toString();
                    //  Picasso.with(getContext()).load(image).into(imageUser);
                  }
                  username.setVisibility(View.VISIBLE);
                  username.setText(nombre+" "+apellidos);
                  Calificacion.setText(calificacion);
                  Realizados.setText(realizados);
                  Solicitados.setText(solicitados);
                  Tipousuario.setText(tipousuarioo);

                  progressDialog.dismiss();


              }

          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });


  }
    public static long GetTimeStamp(String TimeStampDB) {
        Date fechaConvertida = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            fechaConvertida = dateFormat.parse(TimeStampDB);
        } catch(Exception e) {
            System.out.println("Error occurred"+ e.getMessage());
        }
        return fechaConvertida.getTime();
    }
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
        return date;
    }


}