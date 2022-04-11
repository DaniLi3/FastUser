package com.ultrafastapp.ultrafast.Providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ultrafastapp.ultrafast.Models.ClienteBookin;

import java.util.HashMap;
import java.util.Map;

public class bookinProvider {

    private DatabaseReference mDatabase;


    public  bookinProvider() {
        mDatabase = FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("ClientBooking");
    }

    public Task<Void> create(ClienteBookin clientBooking) {
        return mDatabase.child(clientBooking.getIdClient()).setValue(clientBooking);
    }

    public Task<Void> updateStatus(String idClientBooking, String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        return mDatabase.child(idClientBooking).updateChildren(map);
    }
    public Task<Void> updateIdHistoryBookin(String idClientBooking) {
        String idPush = mDatabase.push().getKey();
        Map<String, Object> map = new HashMap<>();
        map.put("idHistoryBooking", idPush);
        return mDatabase.child(idClientBooking).updateChildren(map);
    }

    public DatabaseReference getStatus(String idClientBooking) {
        return mDatabase.child(idClientBooking).child("status");
    }

    public DatabaseReference getClientBooking(String idClientBooking) {
        return mDatabase.child(idClientBooking);
    }
    public Query getClientBookingbyDriver(String idDriver) {
        return mDatabase.orderByChild("idDriver").equalTo(idDriver);
    }
    public Task<Void> delete(String idClientBooking) {
        return mDatabase.child(idClientBooking).removeValue();
    }
    public DatabaseReference getDriver(String idDriver)
    {
        return mDatabase.child(idDriver);

    }

}
