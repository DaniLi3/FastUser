package com.ultrafastapp.ultrafast.Services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.GeofireProvider;
import com.ultrafastapp.ultrafast.R;

public class ForegrundService2 extends Service {

    public final String channelId = "com.ultrafastapp.ultrafast";
    private LatLng mcurrenLanLog;
    private GeofireProvider mGeofireprovider;
    LocationManager mlocationmanager;

    private Authprovider authprovider;
    private LocationRequest mLocationrequest;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d("Foregrund", "Temporizador ");
            handler.postDelayed(runnable, 1000);

        }
    };

    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            mcurrenLanLog = new LatLng(location.getLatitude(), location.getLongitude());
            updateLocation();

        }
    };


    private void startlocation() {
        mlocationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mLocationrequest = new LocationRequest();
        mLocationrequest.setInterval(1000);
        mLocationrequest.setFastestInterval(1000);
        mLocationrequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationrequest.setSmallestDisplacement(5);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mlocationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,10,locationListenerGPS);


    }
    private void updateLocation()
    {

        if (authprovider.existSesion() && mcurrenLanLog!=null)
        {
            mGeofireprovider.savelocation(authprovider.getid(), mcurrenLanLog);//es el que guarda la ubicacion en la base de datos

        }

    }
    private void stopLocation()
    {
        if (locationListenerGPS!=null)
        {
            mlocationmanager.removeUpdates(locationListenerGPS);
        }
    }

    @Override
    public void onDestroy() {
        stopLocation();
        super.onDestroy();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        //handler.postDelayed(runnable,1000);
        authprovider = new Authprovider();
        mGeofireprovider=new GeofireProvider("active_driver");
        startlocation();



    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification=new NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable.ic_ultra)
                .setContentTitle("Estas Conectado")
                .setContentText("Aplicación corriendo en segundo plano")
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            startMyForegrundService();
        }
        else
        {
            startForeground(50,notification);
        }
        return START_STICKY;

    }
    @RequiresApi(api= Build.VERSION_CODES.O)
    public  void startMyForegrundService()
    {

        String ChannelName="MyForeground Service";
        NotificationChannel channel = new NotificationChannel(channelId,ChannelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setLightColor(Color.BLUE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager!=null;
        manager.createNotificationChannel(channel);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,channelId);
        Notification notification= builder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_ultra)
                .setContentTitle("Viaje en curso")
                .setContentText("Aplicación corriendo en segundo plano")
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(50,notification);


    }
}
