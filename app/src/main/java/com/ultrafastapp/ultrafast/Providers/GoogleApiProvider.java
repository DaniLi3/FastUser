package com.ultrafastapp.ultrafast.Providers;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.retrofi.IGoogle;
import com.ultrafastapp.ultrafast.retrofi.Retrofitcliente;

import java.util.Date;

import retrofit2.Call;

public class GoogleApiProvider {
    private Context context;

    public GoogleApiProvider(Context context)
    {
        this.context=context;

    }
    public Call<String> getDirections(LatLng originLatLon, LatLng destinoLatLon)
    {
        String baseurl = "https://maps.googleapis.com";
        String query="/maps/api/directions/json?mode=driving&transit_routing_preferences=less_driving&"
                +"origin="+originLatLon.latitude+","+originLatLon.longitude+"&"
                +"destination="+destinoLatLon.latitude+","+destinoLatLon.longitude+"&"
                + "departure_time=" + (new Date().getTime() + (60*60*1000)) + "&"
                + "traffic_model=best_guess&"
                +"key="+context.getResources().getString(R.string.google_maps_key);
               // +"key="+context.getResources().getString(R.string.google_maps_key);

        return Retrofitcliente.getcliente(baseurl).create(IGoogle.class).getDirection(baseurl+query);
    }
}