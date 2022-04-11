package com.ultrafastapp.ultrafast.retrofi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IGoogle {

    @GET
    Call<String> getDirection(@Url String url);

}
