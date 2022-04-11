package com.ultrafastapp.ultrafast.Providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ultrafastapp.ultrafast.Models.ViajesCaducados;

public class Viajescaducadosprovider {
    public DatabaseReference mDatabase;
    public Viajescaducadosprovider(String child) {
        mDatabase = FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("ViajesVencidos");
    }
    public Task<Void> create(ViajesCaducados viajescaducados) {
        return mDatabase.child(viajescaducados.getIdViajes()).setValue(viajescaducados);
    }
}
