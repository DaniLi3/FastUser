package com.ultrafastapp.ultrafast.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ultrafastapp.ultrafast.Models.Choferes;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.Utils.CompressorBitmapImage;
import com.ultrafastapp.ultrafast.Utils.FileUtil;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private CircleImageView mCircleImageBack;
    Choferprovider choferprovider;

    private ImageView imageUser,cambiar;
    Button button;
    Authprovider authprovider;
    TextView marca,matricula,modelo;
    private final int GALLERY_REQUEST = 1;
    private ProgressDialog mdialog;
    View view;
    private File mImageFile;
    private String imageUrl;
    private  final int GalleryRequest=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mCircleImageBack = findViewById(R.id.bac);
        marca = findViewById(R.id.txtmarca);
        modelo = findViewById(R.id.txtmodelo);
        matricula = findViewById(R.id.txtmatricula);
        button = findViewById(R.id.guardarcoche);
        choferprovider = new Choferprovider();
        authprovider = new Authprovider();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guardar();

            }
        });

        mCircleImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void Guardar()
    {
        String mmarca,mmodelo,mmatri;
        mmarca = marca.getText().toString();
        mmodelo = marca.getText().toString();
        mmatri = marca.getText().toString();
        Choferes choferes=new Choferes();
        choferes.setMarca(mmarca);
        choferes.setModelo(mmodelo);
        choferes.setMatricula(mmatri);
        choferes.setId(authprovider.getid());

        choferprovider.Registrarcoche(choferes).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivity.this, "Su información se guardo correctamente", Toast.LENGTH_SHORT).show();

            }
        });




    }





}