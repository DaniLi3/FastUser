package com.ultrafastapp.ultrafast.Providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ultrafastapp.ultrafast.Models.SolicitudesCanceladasmodel;
import com.ultrafastapp.ultrafast.Models.ViajesCaducados;
import com.ultrafastapp.ultrafast.Models.ViajesPublicados;

import java.util.HashMap;
import java.util.Map;

public class ViajesPubliProvider {
    public DatabaseReference mDatabase;



    public ViajesPubliProvider(String child) {
        mDatabase = FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child(child);
    }

    public Task<Void> create(ViajesPublicados viajesPublicados) {
        return mDatabase.child(viajesPublicados.getIdViajes()).setValue(viajesPublicados);
    }
    public Task<Void> createcaducados(ViajesCaducados viajescaducados) {
        return mDatabase.child(viajescaducados.getIdViajes()).setValue(viajescaducados);
    }
    public Task<Void> actualizarviaje(ViajesPublicados viajesPublicados) {
        Map<String,Object> map=new HashMap();
        map.put("fecha",viajesPublicados.getFecha() );
        map.put("hora",viajesPublicados.getHora());
        map.put("peso",viajesPublicados.getPeso() );
        map.put("comentario",viajesPublicados.getComentario());
        map.put("cantidadentregas",viajesPublicados.getCantidadentregas() );
        map.put("idViajes",viajesPublicados.getIdViajes());
        return mDatabase.child(viajesPublicados.getIdViajes()).updateChildren(map);
    }
    public Task<Void> actualizar(ViajesPublicados viajesPublicados) {
        Map<String,Object> map=new HashMap();
        map.put("estado",viajesPublicados.getEstado());
        map.put("idViajes",viajesPublicados.getIdViajes());
        return mDatabase.child(viajesPublicados.getIdViajes()).updateChildren(map);
    }
    public Task<Void> actualizarstatus(ViajesPublicados viajesPublicados) {
        Map<String,Object> map=new HashMap();
        map.put("status",viajesPublicados.getStatus());
        map.put("idViajes",viajesPublicados.getIdViajes());
        return mDatabase.child(viajesPublicados.getIdViajes()).updateChildren(map);
    }
    public Task<Void> actualizarsoli(SolicitudesCanceladasmodel viajesPublicados) {
        Map<String,Object> map=new HashMap();
        map.put("status",viajesPublicados.getStatus());
        map.put("i",viajesPublicados.getIdnoty());
        return mDatabase.child(viajesPublicados.getIdnoty()).updateChildren(map);
    }

    public DatabaseReference getDriver(String idDriver)
    {
        return mDatabase.child(idDriver);

    }
    public DatabaseReference getViajes(String ViajesId) {
        return mDatabase.child(ViajesId);
    }

    public DatabaseReference getViajestodos() {
        return mDatabase;
    }

    public Task<Void> delete(String id)
    {
        return mDatabase.child(id).removeValue();
    }

    public Query getClientBookingbyDriver(String idDriver) {
        return mDatabase.orderByChild("idDriver").equalTo(idDriver);
    }

}
