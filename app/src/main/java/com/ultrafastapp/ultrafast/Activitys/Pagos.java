    package com.ultrafastapp.ultrafast.Activitys;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;
import com.ultrafastapp.ultrafast.R;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Pagos extends AppCompatActivity {
   //    private static final String BACKEND_URL = "http://10.0.2.2:4242/";
    private static final String BACKEND_URL = "https://ultrafast-backend.herokuapp.com/";

    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private Stripe stripe;
    private ProgressDialog mdialog;
    public CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);
        mdialog = new ProgressDialog(this);
        circleImageView=findViewById(R.id.CirculeImagebackk);
        mdialog.setMessage("Procesando pago...");
        mdialog.setCanceledOnTouchOutside(false);
        // Configure the SDK with your Stripe publishable key so it can make requests to Stripe
        stripe = new Stripe(

                getApplicationContext(),
                Objects.requireNonNull("pk_test_51J9w2JJWyO5W90WGuQbQwcYfrb5zYGbzXKuvWvmOuPuRxVowFgHqoyFpkJjrQLHsnPHzaq4Rs1U1nhlN9DFnI4We00lY4H1DkZ")

        );
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        startCheckout();
    }

    private void startCheckout() {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        double monto=123.00*100;
      Map<String,Object> payMap=new HashMap<>();
      Map<String,Object> itemMap=new HashMap<>();
        List<Map<String,Object>> itemlist=new ArrayList<>();
        payMap.put("currency","mxn");
        itemMap.put("id","photo");
        itemMap.put("amount",monto);
        itemlist.add(itemMap);
        payMap.put("items",itemlist);
        String json=new Gson().toJson(payMap);

        /*String json = "{"
                + "\"currency\":\"usd\","
                + "\"items\":["
                + "{\"id\":\"photo_subscription\"}"
                + "]"
                + "}";*/
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()

                .url(BACKEND_URL + "create-payment-intent")

                .post(body)

                .build();

        httpClient.newCall(request)

                .enqueue(new PayCallback(this));
        Button button = findViewById(R.id.payButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.show();

                CardInputWidget cardInputWidget=findViewById(R.id.cardInputWidget);
                PaymentMethodCreateParams params=cardInputWidget.getPaymentMethodCreateParams();
                if (params!=null)
                {
                    ConfirmPaymentIntentParams confirmPayment=ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params,paymentIntentClientSecret);
                    stripe.confirmPayment(Pagos.this,confirmPayment);
                }


            }
        });
    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment

        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));

    }
    private void onPaymentSuccess(@NonNull final Response response) throws IOException {

        Gson gson = new Gson();

        Type type = new TypeToken<Map<String, String>>(){}.getType();

        Map<String, String> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).string(),
                type
        );

        paymentIntentClientSecret = responseMap.get("clientSecret");

    }


    private static final class PayCallback implements Callback {

        @NonNull private final WeakReference<Pagos> activityRef;

        PayCallback(@NonNull Pagos activity) {

            activityRef = new WeakReference<>(activity);

        }

        @Override

        public void onFailure(@NonNull Call call, @NonNull IOException e) {

            final Pagos activity = activityRef.get();

            if (activity == null) {

                return;

            }

            activity.runOnUiThread(() ->

                    Toast.makeText(activity, "Error: " + e.toString(), Toast.LENGTH_LONG).show()

            );

        }

        @Override

        public void onResponse(@NonNull Call call, @NonNull final Response response)

                throws IOException {

            final Pagos activity = activityRef.get();

            if (activity == null) {

                return;

            }

            if (!response.isSuccessful()) {

                activity.runOnUiThread(() ->

                        Toast.makeText(

                                activity, "Error: " + response.toString(), Toast.LENGTH_LONG

                        ).show()

                );

            } else {

                activity.onPaymentSuccess(response);

            }

        }

    }
    private static final class PaymentResultCallback

            implements ApiResultCallback<PaymentIntentResult> {

        @NonNull private final WeakReference<Pagos> activityRef;

        PaymentResultCallback(@NonNull Pagos activity) {

            activityRef = new WeakReference<>(activity);

        }

        @Override

        public void onSuccess(@NonNull PaymentIntentResult result) {



            final Pagos activity = activityRef.get();

            if (activity == null) {

                return;

            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();

            if (status == PaymentIntent.Status.Succeeded) {

                // Payment completed successfully
                String name="";


                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Toast.makeText(activity, "Se a hecho el pago correctamente", Toast.LENGTH_SHORT).show();
                RegistrarPago() ;
                Log.d("gson",paymentIntent.getPaymentMethodId());
                

                /*activity.displayAlert(

                        "Payment completed",

                        gson.toJson(paymentIntent)

                );}}}}}}}}}}}}}}}*/


            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {

                // Payment failed – allow retrying using a different payment method

                activity.displayAlert(

                        "Payment failed",

                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()

                );

            }

        }

        private void RegistrarPago() {
            
        }

        @Override

        public void onError(@NonNull Exception e) {

            final Pagos activity = activityRef.get();

            if (activity == null) {

                return;

            }

            // Payment request failed – allow retrying using the same payment method

            activity.displayAlert("Error", e.toString());

        }

    }
    private void displayAlert(@NonNull String title,

                              @Nullable String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)

                .setTitle(title)

                .setMessage(message);

        builder.setPositiveButton("Ok", null);

        builder.create().show();

    }
}