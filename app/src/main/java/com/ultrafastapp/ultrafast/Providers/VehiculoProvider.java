package com.ultrafastapp.ultrafast.Providers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VehiculoProvider {
    DatabaseReference mdatarefe;

    public VehiculoProvider()
    {
        mdatarefe= FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("Vehiculos");

    }
    public DatabaseReference getusuario(String idUsuario) {
        return mdatarefe.child(idUsuario);
    }


}
