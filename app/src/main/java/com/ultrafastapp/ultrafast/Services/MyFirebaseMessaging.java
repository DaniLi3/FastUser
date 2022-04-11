package com.ultrafastapp.ultrafast.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ultrafastapp.ultrafast.Activitys.MenuActivity;
import com.ultrafastapp.ultrafast.R;

import java.util.Random;


public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("tokenn",s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("token","Mensaje recibido");

        if (remoteMessage.getNotification()!=null)
        {
            RemoteMessage.Notification notification=remoteMessage.getNotification();
            String title=  notification.getTitle();
            String msg=notification.getBody();

            Log.d("lola","este es el titulo "+notification.getTitle());
            Log.d("lola","este es el cuerpo "+notification.getBody());

            sendNotification(title,msg);
        }
        if (remoteMessage.getData().size()>0)
        {
           String titulo=remoteMessage.getData().get("titulo");
            String cuerpo=remoteMessage.getData().get("cuerpo");
            mayorqueOreo(titulo,cuerpo);

        }

    }

    public void mayorqueOreo(String ti,String cu)
    {
        String id="Mensaje";
        NotificationManager nm= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,id);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc=new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            nm.createNotificationChannel(nc);
        }
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(ti)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentText("")
                .setContentIntent(clicnoty())
        .setContentInfo(cu);

        Random random=new Random();
        int notify=random.nextInt(8000);
        nm.notify(notify,builder.build());

    }
    public PendingIntent clicnoty()
    {
        Intent nf=new Intent(getApplicationContext(), MenuActivity.class);
        nf.putExtra("datos","El dato es");
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0,nf,0);
    }


    private void sendNotification(String title, String msg) {
        Intent intent=new Intent(this,MyFirebaseMessaging.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,myNotification.NOTIFICATION_ID,intent,PendingIntent.FLAG_ONE_SHOT);

        myNotification notification=new myNotification(this,myNotification.CHANNEL_ID_NOTIFICATIONS);
        notification.build(R.drawable.ic_bak,title,msg,pendingIntent);
        notification.addChannel("Notificaciones", NotificationManager.IMPORTANCE_HIGH);
      //  notification.createChannelGroup(myNotification.CHANNEL_GROUP_GENERAL,2);
        notification.show(myNotification.NOTIFICATION_ID);
    }
}
