package com.ultrafastapp.ultrafast.retrofi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Retrofitcliente {


    public static Retrofit getcliente(String url)
    {

           Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

        return retrofit;

    }
    public static Retrofit getclienteObjet(String url)
    {

           Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofit;

    }
}
