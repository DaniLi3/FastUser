package com.ultrafastapp.ultrafast.Providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ultrafastapp.ultrafast.Models.SolicitudesModel;

import java.util.HashMap;
import java.util.Map;

public class    SolicitudesProvider {

    public DatabaseReference mdatabase;

   public SolicitudesProvider(String chil)
   {
       mdatabase=FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("Solicitudes").child(chil);
   }
    public SolicitudesProvider()
    {
        mdatabase=FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("Solicitudes");
    }

   public Task<Void> create(SolicitudesModel solicitudesModel)
   {
     return mdatabase.child(solicitudesModel.getIdnoty()).setValue(solicitudesModel);
   }
   public DatabaseReference getSolicitudesTodas()
   {
       return mdatabase;
   }

   public Task<Void> actuzalizar(SolicitudesModel solicitudesModel)
   {
       Map<String,Object> map=new HashMap<>();
       map.put("status",solicitudesModel.getStatus());
       map.put("seocliente",solicitudesModel.getSeocliente());
       map.put("seo",solicitudesModel.getSeo());
       return mdatabase.child(solicitudesModel.getIdnoty()).updateChildren(map);
   }
    public Task<Void> delete(String idClientBooking) {
        return mdatabase.child(idClientBooking).removeValue();
    }
    public Task<Void> updateStatus(String idClientBooking, String status,String seo,String seocliente) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("seo", seo);
        map.put("seocliente", seocliente);
        return mdatabase.child(idClientBooking).updateChildren(map);
    }


    public DatabaseReference getViajes(String ViajesId) {
        return mdatabase.child(ViajesId);
    }
}

