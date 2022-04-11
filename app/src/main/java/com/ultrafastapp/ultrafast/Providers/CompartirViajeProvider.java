package com.ultrafastapp.ultrafast.Providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ultrafastapp.ultrafast.Models.ModelCompartir;

public class CompartirViajeProvider {
    public DatabaseReference mdatabase;

   public CompartirViajeProvider ()

    {
        mdatabase= FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("ViajesCompartidos");

    }
    public Task<Void> create(ModelCompartir viajesPublicados) {
        return mdatabase.child(viajesPublicados.getId()).setValue(viajesPublicados);
    }
}
