package com.ultrafastapp.ultrafast.channel;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.ultrafastapp.ultrafast.R;


public class NotificationHelper extends ContextWrapper {

   private static final String CHANNEL_ID="com.ultrafastapp.ultrafast";
   private static final String CHANNEL_NAME="Ultrafast";

   private NotificationManager mmanager;
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
            createchannel();


        }


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createchannel()
    {
        NotificationChannel notificationChannel=new
                NotificationChannel(
                        CHANNEL_ID,
                CHANNEL_NAME
                ,NotificationManager.IMPORTANCE_HIGH
        );
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLightColor(Color.GRAY);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getMmanager().createNotificationChannel(notificationChannel);
    }

    public   NotificationManager getMmanager()
    {
        if (mmanager==null)
        {
            //AQUI PUEDE HABER UN ERROR
            mmanager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mmanager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  Notification.Builder getNotification(String title, String body, PendingIntent intent, Uri soundUrl)
    {
        return new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUrl)
                .setContentIntent(intent)
                .setSmallIcon(R.drawable.ic_ultra)
                .setStyle(new Notification.BigTextStyle()
                .bigText(body).setBigContentTitle(title));




    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public  Notification.Builder getNotificationAction(String title, String body,  Uri soundUrl, Notification.Action acceptAction)
    {
        return new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUrl)

                .setSmallIcon(R.drawable.ic_ultra)

                .addAction(acceptAction)

                .setStyle(new Notification.BigTextStyle()
                        .bigText(body).setBigContentTitle(title));




    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  NotificationCompat.Builder getNotificationOld(String title, String body, PendingIntent intent, Uri soundUrl)
    {
        return new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUrl)

                .setSmallIcon(R.drawable.ic_ultra)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(body).setBigContentTitle(title));




    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  NotificationCompat.Builder getNotificationOldAction(String title, String body, Uri soundUrl, NotificationCompat.Action acceptaction)
    {
        return new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUrl)

                .setSmallIcon(R.drawable.ic_ultra)
                .addAction(acceptaction)

                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body).setBigContentTitle(title));




    }
}
