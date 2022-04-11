package com.ultrafastapp.ultrafast.Activitys.PrincipalPaquete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.ultrafastapp.ultrafast.R;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class SeleccionarFecha extends AppCompatActivity {
    Button selecfechaa;
    CalendarView calendarView;
    int fecha=13;
    int dia;
CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_fecha);
        selecfechaa = findViewById(R.id.selecfechaa);
        calendarView = findViewById(R.id.datepic);
        circleImageView=findViewById(R.id.Cir);
            selecfechaa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    */
                    if (fecha!=13)
                    {

                        Intent intent = new Intent(SeleccionarFecha.this,SeleccionarHora.class);
                        intent.putExtra("Fecha",meses(fecha));
                        intent.putExtra("Dia",dia);
                        startActivity(intent);

                        //Toast.makeText(SeleccionarFecha.this, meses(fecha), Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(SeleccionarFecha.this, "Debes de seleccionar una fecha", Toast.LENGTH_SHORT).show();
                    }


                }
            });
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                     fecha =month;
                     dia=dayOfMonth;
                   // Toast.makeText(SeleccionarFecha.this, fecha, Toast.LENGTH_LONG).show();
                }
            });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public String meses(int valor)

    {
        String mes="";
        switch (valor){
            case 0:
                mes="Enero";break;

            case 1:
                mes="Febrero"; break;
            case 2:
                mes="Marzo"; break;
            case 3:
                mes="Abril"; break;
            case 4:
                mes="Mayo"; break;
            case 5:
                mes="Junio"; break;
            case 6:
                mes="Julio"; break;
            case 7:
                mes="Agosto"; break;
            case 8:
                mes="Septiembre"; break;
            case 9:
                mes="Octubre"; break;
            case 10:
                mes="Noviembre"; break;
            case 11:
                mes="Diciembre"; break;
            case 13:
                Toast.makeText(this, "Seleccione una fecha", Toast.LENGTH_SHORT).show();; break;




        }
        return mes;

    }









    public void AbrirCalendario()
    {
        Calendar cal = Calendar.getInstance();
        int Ano = cal.get(Calendar.YEAR);
        int mes=cal.get(Calendar.MONTH);
        int dia=cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog= new DatePickerDialog(SeleccionarFecha.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth+" de "+month;
                Toast.makeText(SeleccionarFecha.this, fecha, Toast.LENGTH_SHORT).show();

            }
        },dia,mes,Ano);
    }
}