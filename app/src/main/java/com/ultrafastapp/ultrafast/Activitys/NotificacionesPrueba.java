package com.ultrafastapp.ultrafast.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ultrafastapp.ultrafast.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificacionesPrueba extends AppCompatActivity {

    Button enviaratodos;
    Button enviaraespe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones_prueba);

        enviaratodos=findViewById(R.id.btntodos);
        enviaraespe=findViewById(R.id.btnespe);

        enviaratodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamaratodos();

            }
        });
        enviaraespe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //llamarespe();
                
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("enviaratodos").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(NotificacionesPrueba.this, "Se ah enviado a todos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void llamarespe(String toke) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            String token="cScivLzUSN696Z9SRQKu9E:APA91bF5hnzRwhzx8b0rlCy4S-13UK3loJQ1ll8Z0hQe70LzyhJUPeHhFLYvCLGbZxJis_EdIbqxHZK3557S2l10ItOP5ww9ok1hSk4wE_-m6vmw4vGuIfz9juWSGJM6VqUj7pLGwlYy";
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

    private void llamaratodos() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            json.put("to","/topics/"+"enviaratodos");
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
}