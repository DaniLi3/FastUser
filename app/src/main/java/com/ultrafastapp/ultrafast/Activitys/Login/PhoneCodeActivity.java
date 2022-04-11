package com.ultrafastapp.ultrafast.Activitys.Login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastapp.ultrafast.Activitys.MenuActivity;
import com.ultrafastapp.ultrafast.Activitys.Registro.RegistroActivity;
import com.ultrafastapp.ultrafast.Models.Choferes;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;

public class PhoneCodeActivity extends AppCompatActivity {
    String mextraPhone;
    ProgressDialog progressDialog;
    AlertDialog mdialog;
    Button codeverifi;
    EditText EdiCodeveri;
    String mverificationId;
    Authprovider authprovider;
    Choferes choferes;



    Choferprovider choferprovider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_code);
        mextraPhone =getIntent().getStringExtra("phone");
        choferprovider = new Choferprovider();


        codeverifi=findViewById(R.id.btningresar);
        EdiCodeveri=findViewById(R.id.Editextcodeverifica);
        authprovider=new Authprovider();
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Espere...");
        progressDialog.setCanceledOnTouchOutside(false);

        authprovider.sendCodeVerification(mextraPhone, mcallback);


        codeverifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=EdiCodeveri.getText().toString();
                if (!code.equals("") && code.length()>=0)
                {

                    singIn(code);

                }
                else
                {
                    Toast.makeText(PhoneCodeActivity.this, "Debe ingresar un código valido  ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code=phoneAuthCredential.getSmsCode();

            if (code!=null)
            {
                EdiCodeveri.setText(code);
                singIn(code);
            }
            else
            {
                Toast.makeText(PhoneCodeActivity.this, "El código es nulo", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(PhoneCodeActivity.this, "Se produjo un error al enviar el codigo"+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Toast.makeText(PhoneCodeActivity.this, "El código se envío correctamente", Toast.LENGTH_LONG).show();
            mverificationId=s;
        }
    };
    private void singIn(String code) {
        progressDialog.show();
        authprovider.singPhone(mverificationId,code).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        choferprovider.getusuario(authprovider.getid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists())
                                {
                                    progressDialog.dismiss();

                                    Intent intent=new Intent(PhoneCodeActivity.this, MenuActivity.class);
                                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                                else
                                {
                                    progressDialog.dismiss();
                                    createInfo();

                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                    else
                    {
                        Toast.makeText(PhoneCodeActivity.this, "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    private void createInfo() {
        choferes = new Choferes();
        choferes.setId(authprovider.getid());
        choferes.setTel(mextraPhone);

        choferprovider.create(choferes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Intent intent =new Intent(PhoneCodeActivity.this,RegistroActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(PhoneCodeActivity.this, "No se pudo crear la informacion", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}