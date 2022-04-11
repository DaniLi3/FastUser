package com.ultrafastapp.ultrafast.Activitys.PerfilUsuario;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastapp.ultrafast.Activitys.MenuActivity;
import com.ultrafastapp.ultrafast.Models.Choferes;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;

public class EditarPerfil extends AppCompatActivity {

    Choferes choferes;
    Choferprovider choferprovider;
    String nombre;
    String apellidos;
    ProgressDialog progressDialog;
    String genero;
    View view;
    Authprovider authprovider;
    Button button;
    CardView cardView;
    ImageView closee;


    private TextInputLayout nombree;
    private TextInputLayout apell;

    private TextInputLayout domicilio;
    private TextInputLayout email;
    private TextInputLayout telefono;
    private TextInputLayout descripcion;
    private TextInputLayout marca;
    private TextInputLayout modelo;
    private TextInputLayout anio;
    private TextInputLayout matricula;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        nombree = findViewById(R.id.perfilnombre);
        apell = findViewById(R.id.perfilapellido);

        domicilio= findViewById(R.id.perfildomicilio);
        email= findViewById(R.id.perfilemail);
        telefono= findViewById(R.id.perfiltelefon);
        descripcion= findViewById(R.id.perfildescrip);
        button=findViewById(R.id.btnguardar);
        marca= findViewById(R.id.perfilmarca);
        modelo= findViewById(R.id.perfilmodelo);
        anio= findViewById(R.id.perfilanio);
        matricula= findViewById(R.id.perfilmatricula);
        cardView=findViewById(R.id.documentoscard);
        closee = findViewById(R.id.closs);
        choferes = new Choferes();
        choferprovider = new Choferprovider();
        authprovider=new Authprovider();
        progressDialog = new ProgressDialog(EditarPerfil.this);
        progressDialog.setTitle("Procesando");
        progressDialog.setMessage("Cargando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();



       getUserInfo();
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               if (!nombree.getEditText().getText().toString().equals("")&&!apell.getEditText().getText().toString().equals("")
               &&!domicilio.getEditText().getText().toString().equals("")
               &&!email.getEditText().getText().toString().equals("")&&!telefono.getEditText().getText().toString().equals("")
               &&!descripcion.getEditText().getText().toString().equals(""))
               {

                   actualizar();
               }
               else
               {
                   Toast.makeText(EditarPerfil.this, "Todos los datos deben estar completos", Toast.LENGTH_SHORT).show();
               }
           }
       });

       cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mostrarDialog();
           }
       });
       closee.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
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
                    if (snapshot.hasChild("nombre"))
                    {
                        nombree.getEditText().setText(snapshot.child("nombre").getValue().toString());
                    }
                    nombree.getEditText().setText(snapshot.child("nombre").getValue().toString());
                    apell.getEditText().setText(snapshot.child("apellidos").getValue().toString());

                    if (snapshot.hasChild("domicilio"))
                    {
                        domicilio.getEditText().setText(snapshot.child("domicilio").getValue().toString());
                    }
                    if (snapshot.hasChild("email"))
                    {
                        email.getEditText().setText(snapshot.child("email").getValue().toString());
                    }
                    if (snapshot.hasChild("telefono"))
                    {
                        telefono.getEditText().setText(snapshot.child("telefono").getValue().toString());
                    }
                    if (snapshot.hasChild("bibliografia"))
                    {
                        descripcion.getEditText().setText(snapshot.child("bibliografia").getValue().toString());
                    }
                    if (snapshot.hasChild("marca"))
                    {
                        marca.getEditText().setText(snapshot.child("marca").getValue().toString());
                    }
                    if (snapshot.hasChild("modelo"))
                    {
                        modelo.getEditText().setText(snapshot.child("modelo").getValue().toString());
                    }
                    if (snapshot.hasChild("anio"))
                    {
                        anio.getEditText().setText(snapshot.child("anio").getValue().toString());
                    }
                    if (snapshot.hasChild("matricula"))
                    {
                        matricula.getEditText().setText(snapshot.child("matricula").getValue().toString());
                    }
                    progressDialog.dismiss();



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void actualizar()
    {
        Choferes choferes=new Choferes();
        choferes.setNombre(nombree.getEditText().getText().toString());
        choferes.setApellidos(apell.getEditText().getText().toString());

        choferes.setTelefono(telefono.getEditText().getText().toString());
        choferes.setDireccion(domicilio.getEditText().getText().toString());
        choferes.setEmail(email.getEditText().getText().toString());
        choferes.setBibliografia(descripcion.getEditText().getText().toString());
        choferes.setAnio(anio.getEditText().getText().toString());
        choferes.setStatus("Sin validar");
        if (!marca.getEditText().getText().toString().equals("")&&!modelo.getEditText().getText().toString().equals("")&&!matricula.getEditText().getText().toString().equals(""))
        {
            choferes.setStatus("Validado");
            choferes.setMarca(marca.getEditText().getText().toString());
            choferes.setModelo(modelo.getEditText().getText().toString());
            choferes.setMatricula(matricula.getEditText().getText().toString());
        }


        choferes.setId(authprovider.getid());

        choferprovider.Actializar(choferes).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(EditarPerfil.this, MenuActivity.class);
                bundle.putString("locoo", "si");
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(EditarPerfil.this, "Su información se actualizo correctamente", Toast.LENGTH_SHORT).show();

            }
        });


    }
    public void mostrarDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(EditarPerfil.this);

        LayoutInflater inflater=getLayoutInflater();

        View view=inflater.inflate(R.layout.dialog_personalizado,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();

        TextView test=view.findViewById(R.id.txtsubirdocu);
        ImageView close=view.findViewById(R.id.close);
        Button continuar=view.findViewById(R.id.btndialog);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}