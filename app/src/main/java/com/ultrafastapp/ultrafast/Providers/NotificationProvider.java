package com.ultrafastapp.ultrafast.Providers;


import com.ultrafastapp.ultrafast.Models.FCMBody;
import com.ultrafastapp.ultrafast.Models.IFCMResponse;
import com.ultrafastapp.ultrafast.retrofi.IFCMApi;
import com.ultrafastapp.ultrafast.retrofi.Retrofitcliente;

import retrofit2.Call;


public class NotificationProvider {

    private String url = "https://fcm.googleapis.com";

    public NotificationProvider() {
    }

    public Call<IFCMResponse> sendNotification(FCMBody body) {
        return Retrofitcliente.getclienteObjet(url).create(IFCMApi.class).sendt(body);
    }
}
