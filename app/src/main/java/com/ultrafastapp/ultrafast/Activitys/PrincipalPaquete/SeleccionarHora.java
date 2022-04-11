package com.ultrafastapp.ultrafast.Activitys.PrincipalPaquete;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ultrafastapp.ultrafast.Activitys.MenuActivity;
import com.ultrafastapp.ultrafast.R;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class SeleccionarHora extends AppCompatActivity {
    CircleImageView circleImageView;
    ImageView down;
    TextView horas;
    String horaa="18",minn="00";
    String fecha;
    Button btnhora;
    int dia;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_hora);
        circleImageView=findViewById(R.id.Cir);
        down = findViewById(R.id.down);
        btnhora = findViewById(R.id.btnhora);
        horas=findViewById(R.id.horas);

        Bundle bundle = new Bundle();


        fecha =getIntent().getStringExtra("Fecha");
        dia=getIntent().getIntExtra("Dia",0);

        String f = dia+" de "+fecha;



                circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abrirhora();

            }
        });
        horas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirhora();
            }
    })
        ;
        btnhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuActivity.class);
                bundle.putString("textFromActivityA", f+" a las "+horaa+":"+minn);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
    public void abrirhora(){
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(SeleccionarHora.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horaa= String.valueOf(hourOfDay);
                minn= String.valueOf(minute);
                horas.setText(hourOfDay + ":"+minute);

            }
        },hora,min,true);


        timePickerDialog.show();


    }

}