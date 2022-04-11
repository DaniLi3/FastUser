package com.ultrafastapp.ultrafast.Receiber;

public class CancelarNotificacion  {
    /*  private ClienteBookingProvider mclienteBookingProvider;
    private DriverFoundProvider mDriverFoundprovider;
    MediaPlayerSingleton mediaPlayerSingleton;
    private Authprovider authprovider;
    @Override
    public void onReceive(Context context, Intent intent) {
        mclienteBookingProvider = new ClienteBookingProvider();
        mDriverFoundprovider = new DriverFoundProvider();
        authprovider = new Authprovider();
        String idClient = intent.getExtras().getString("idCliente");
        String searchById = intent.getExtras().getString("searchById");
        Log.d("Cm","Se cancelo");

        if (searchById.equals("true"))
        {

            mclienteBookingProvider.updateStatus(idClient, "cancel");
        }


        mDriverFoundprovider.delete(authprovider.getid());

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(2);
        mediaPlayerSingleton = MediaPlayerSingleton.getInstance();
        mediaPlayerSingleton.init(context.getApplicationContext());


        MediaPlayer ref = MediaPlayerSingleton.getSingletonMedia();
        ref.stop();

    }*/
}
