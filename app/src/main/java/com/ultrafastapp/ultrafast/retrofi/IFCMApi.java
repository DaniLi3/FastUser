package com.ultrafastapp.ultrafast.retrofi;


import com.ultrafastapp.ultrafast.Models.FCMBody;
import com.ultrafastapp.ultrafast.Models.IFCMResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMApi {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA735MMq0:APA91bHqS-u0ZsqJTy78h-Dc9He8QYVFw3oMI-d-oVLI-Ok6ffBvDVrxhO147iKzXTLg7J3cl8REzqGRpMDciEE_IzaYiE5myaOeix1iJbIMEeMabisuPT2fe9jloP_a_Xaw2v_NDSCz"
    })

    @POST("fcm/send")
    Call<IFCMResponse> sendt(@Body FCMBody body);
}
