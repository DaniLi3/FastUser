package com.ultrafastapp.ultrafast.Providers;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeofireProvider {
    private DatabaseReference mDatabase;
    private GeoFire mgeofire;

    public GeofireProvider(String reference)
    {
        mDatabase= FirebaseDatabase.getInstance().getReference().child(reference);
        mgeofire = new GeoFire(mDatabase);

    }
    public GeofireProvider()
    {


    }
    public void savelocation(String idChofer, LatLng latLng)
    {
        mgeofire.setLocation(idChofer,new GeoLocation(latLng.latitude,latLng.longitude));

    }
    public void removelocation(String idchofer)
    {
        mgeofire.removeLocation(idchofer);

    }
    public GeoQuery getActiDriver(LatLng latitude, double radio)
    {
        GeoQuery geoquery = mgeofire.queryAtLocation(new GeoLocation(latitude.latitude,latitude.longitude),radio);
        geoquery.removeAllListeners();
        return  geoquery;
    }

    public DatabaseReference isDriverWorking(String idDriver)
    {
        return FirebaseDatabase.getInstance().getReference().child("driver_working").child(idDriver);
    }
    public DatabaseReference getDriverLocation(String idDriver)
    {
        return mDatabase.child(idDriver).child("l");

    }


}
