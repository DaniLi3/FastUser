package com.ultrafastapp.ultrafast.Activitys.PerfilUsuario.InfoCuenta;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastapp.ultrafast.Activitys.Pagos;
import com.ultrafastapp.ultrafast.Models.Choferes;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;

public class InfoCuenta extends AppCompatActivity {

    View vista;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_cuenta);
        vista=findViewById(R.id.cardcuenta);






        vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InfoCuenta.this, Pagos.class);
                startActivity(intent);
               // mostrarDialog();
            }
        });

    }

    public void mostrarDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(InfoCuenta.this);

        LayoutInflater inflater=getLayoutInflater();

        View view=inflater.inflate(R.layout.dialog_datos_bancarios,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();
        TextInputEditText editText=view.findViewById(R.id.txttarjeta);
        TextInputLayout Banco= view.findViewById(R.id.txtnombrebanco);
        TextInputLayout Titular= view.findViewById(R.id.txttitular);
       TextInputLayout NumeroCuenta= view.findViewById(R.id.txtnumcuenta);
        TextInputLayout Numerotarjeta= view.findViewById(R.id.txtnumtarjera);
        TextView Alerta=view.findViewById(R.id.validarcasilla);
        CheckBox checkBox = view.findViewById(R.id.chec);
        Authprovider authprovider = new Authprovider();
        Choferprovider choferprovider = new Choferprovider();
        Choferes choferes = new Choferes();
        Button Confirmar=view.findViewById(R.id.btndialog);
        ImageView Close=view.findViewById(R.id.closee);
        choferprovider.getusuario(authprovider.getid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    if (snapshot.child("titularcuenta").exists())
                    {
                        String titularcuenta=snapshot.child("titularcuenta").getValue().toString();
                        Titular.getEditText().setText(titularcuenta);

                    }
                    if (snapshot.child("nombrebanco").exists())
                    {
                        String nombrebanco=snapshot.child("nombrebanco").getValue().toString();
                        Banco.getEditText().setText(nombrebanco);

                    }
                    if (snapshot.child("numerocuenta").exists())
                    {
                        String numerocuenta=snapshot.child("numerocuenta").getValue().toString();
                        NumeroCuenta.getEditText().setText(numerocuenta);

                    }
                    if (snapshot.child("numerotarjeta").exists())
                    {
                        String numerotarjeta=snapshot.child("numerotarjeta").getValue().toString();
                        Numerotarjeta.getEditText().setText(numerotarjeta);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Banco.getEditText().getText().toString().equals("")&&!Titular.getEditText().getText().toString().equals("")&&!NumeroCuenta.getEditText().getText().toString().equals("")&&!Numerotarjeta.getEditText().getText().toString().equals(""))
                {
                    if (checkBox.isChecked())
                    {

                        Guardardatos();
                        dialog.dismiss();
                    }
                    else
                    {
                        Alerta.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    Toast.makeText(InfoCuenta.this, "Debes ingresar todos los datos", Toast.LENGTH_SHORT).show();
                }
            }

            private void Guardardatos() {

                choferes.setId(authprovider.getid());
                choferes.setNombrebanco(Banco.getEditText().getText().toString());
                choferes.setTitularcuenta(Titular.getEditText().getText().toString());
                choferes.setNumerocuenta(NumeroCuenta.getEditText().getText().toString());
                choferes.setNumerotarjeta(Numerotarjeta.getEditText().getText().toString());

                choferprovider.guardarbanco(choferes).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(InfoCuenta.this, "Se guardo correctamente", Toast.LENGTH_SHORT).show();

                    }
                });

            }
            private void Consultardatos()
            {


            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            private static final int TOTAL_SYMBOLS = 19;
            // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            //
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '-';
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { // noop
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { // noop
            }
            @Override public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrecntString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }
            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider)
            {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++)
                { // chech that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0)
                    {
                        isCorrect &= divider == s.charAt(i);
                    }
                    else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            } private String buildCorrecntString(char[] digits, int dividerPosition, char divider)
            {
                final StringBuilder formatted = new StringBuilder();
                for (int i = 0; i < digits.length; i++)
                {
                    if (digits[i] != 0)
                    {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0))
                        {
                            formatted.append(divider);
                        }
                    }
                }
                return formatted.toString();
            }
            private char[] getDigitArray(final Editable s, final int size)
            {
                char[] digits = new char[size];
                int index = 0; for (int i = 0; i < s.length() && index < size; i++)
            {
                char current = s.charAt(i);
                if (Character.isDigit(current))
                {
                    digits[index] = current; index++;
                }
            }
                return digits;
            }
        });

    }
}


