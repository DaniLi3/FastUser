package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.EnviarSolicitud;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.ultrafastapp.ultrafast.Models.Choferes;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilChofer extends AppCompatActivity {

   private TextView Modelo;
    private TextView Marca;
    private TextView Matricula;
    private TextView Viajespubli;
    private TextView Viajesconclu;
    private TextView fechausuario;
    private TextView Valoracion;
    private TextView Tipousuario;
    private TextView Nombre;
    private TextView Bibliografia;
    CircleImageView bak;

    Choferprovider choferprovider;
    Choferes choferes;


    CircleImageView circleImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_chofer);
        Modelo=findViewById(R.id.txtmodelo);
        Marca=findViewById(R.id.txtmarca);
        Matricula=findViewById(R.id.txtmatricula);
        Viajespubli=findViewById(R.id.txtviajespubli);
        Viajesconclu=findViewById(R.id.txtviajesconcluidos);
        Valoracion=findViewById(R.id.txtvaloracion);
        Tipousuario=findViewById(R.id.tipousuario);
        fechausuario=findViewById(R.id.txtfe);
        Nombre=findViewById(R.id.username);
        Bibliografia=findViewById(R.id.txtdes);
        circleImageView=findViewById(R.id.imageViewPro);
        choferprovider = new Choferprovider();
        bak=findViewById(R.id.CirculeImageback);
        choferes=new Choferes();
        String id = getIntent().getStringExtra("id");
        obtenerdatos(id);

        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void obtenerdatos(String id)
    {
        choferprovider.getusuario(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String nombre=snapshot.child("nombre").getValue().toString();
                    String apellidos=snapshot.child("apellidos").getValue().toString();
                    String anio=snapshot.child("anio").getValue().toString();
                    String calificacion=snapshot.child("calificacion").getValue().toString();
                    String domicilio=snapshot.child("domicilio").getValue().toString();
                    String email=snapshot.child("email").getValue().toString();
                    String genero=snapshot.child("genero").getValue().toString();
                    String modelo=snapshot.child("modelo").getValue().toString();
                    String matricula=snapshot.child("matricula").getValue().toString();
                    String marca=snapshot.child("marca").getValue().toString();
                    String tipousuario=snapshot.child("tipousuario").getValue().toString();
                    String realizados=snapshot.child("realizados").getValue().toString();
                    String fechaactual=snapshot.child("fechaactual").getValue().toString();
                    String telefono=snapshot.child("telefono").getValue().toString();
                    String bibliografia=snapshot.child("bibliografia").getValue().toString();
                  //  String publicados=snapshot.child("publicados").getValue().toString();
                    String fecha[]=fechaactual.split(" ");
                    if (snapshot.child("image").exists())
                    {

                        String imagen=snapshot.child("image").getValue().toString();
                        if (imagen!=null||!imagen.equals(""))
                        {
                            Picasso.with(PerfilChofer.this).load(imagen).into(circleImageView);
                            Log.d("placoso","si");
                        }
                        else
                        {
                            Log.d("placoso","no");
                        }
                    }
                    Nombre.setText(nombre+" "+apellidos);
                    Modelo.setText(modelo);
                    fechausuario.setText("Usuario desde "+fecha[0]);
                    Marca.setText(marca);
                    Matricula.setText(matricula);
                    Valoracion.setText(calificacion);
                   // Viajespubli.setText(viajespubli);
                    Viajesconclu.setText(realizados);
                    Tipousuario.setText(tipousuario);
                    Bibliografia.setText(bibliografia);




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}