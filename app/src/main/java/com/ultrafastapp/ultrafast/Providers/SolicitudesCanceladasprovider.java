package com.ultrafastapp.ultrafast.Providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ultrafastapp.ultrafast.Models.SolicitudesCanceladasmodel;

import java.util.HashMap;
import java.util.Map;

public class SolicitudesCanceladasprovider { private DatabaseReference mdatabase;

    public SolicitudesCanceladasprovider(String child)
    {
        mdatabase=FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child(child);
    }
    public Task<Void> create(SolicitudesCanceladasmodel solicitudesModel)
    {
        return mdatabase.child(solicitudesModel.getIdnoty()).setValue(solicitudesModel);
    }

    public Task<Void> Cancelar(SolicitudesCanceladasmodel solicitudesModel)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("contenidopaquete",solicitudesModel.getContenidopaquete());
        map.put("destino",solicitudesModel.getDestino());
        map.put("dimensiones",solicitudesModel.getDimensiones());
        map.put("fechadepublicacionviaje",solicitudesModel.getFechadepublicacionviaje());
        map.put("fechadesalida",solicitudesModel.getFechadesalida());
        map.put("fechadesolicitud",solicitudesModel.getFechadesolicitud());
        map.put("idchofer",solicitudesModel.getIdchofer());
        map.put("idcliente",solicitudesModel.getIdcliente());
        map.put("idnoty",solicitudesModel.getIdnoty());
        map.put("image",solicitudesModel.getImage());
        map.put("kilos",solicitudesModel.getKilos());
        map.put("notapaquete",solicitudesModel.getNotapaquete());
        map.put("origen",solicitudesModel.getOrigen());
        map.put("personaquerecibe",solicitudesModel.getPersonaquerecibe());
        map.put("precio",solicitudesModel.getPrecio());
        map.put("seo",solicitudesModel.getSeo());
        map.put("seocliente",solicitudesModel.getSeocliente());
        map.put("status",solicitudesModel.getStatus());
        map.put("horasalida",solicitudesModel.getHorasalida());
        map.put("costopaquete",solicitudesModel.getCostopaquete());
        map.put("fechavalidar",solicitudesModel.getFechavalidar());
        map.put("estado",solicitudesModel.getEstado());

        return mdatabase.child(solicitudesModel.getIdnoty()).updateChildren(map);
    }
    public Task<Void> Aceptar(SolicitudesCanceladasmodel solicitudesModel)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("contenidopaquete",solicitudesModel.getContenidopaquete());
        map.put("destino",solicitudesModel.getDestino());
        map.put("dimensiones",solicitudesModel.getDimensiones());
        map.put("fechadepublicacionviaje",solicitudesModel.getFechadepublicacionviaje());
        map.put("fechadesalida",solicitudesModel.getFechadesalida());
        map.put("fechadesolicitud",solicitudesModel.getFechadesolicitud());
        map.put("idchofer",solicitudesModel.getIdchofer());
        map.put("idcliente",solicitudesModel.getIdcliente());
        map.put("idnoty",solicitudesModel.getIdnoty());
        map.put("image",solicitudesModel.getImage());
        map.put("kilos",solicitudesModel.getKilos());
        map.put("notapaquete",solicitudesModel.getNotapaquete());
        map.put("origen",solicitudesModel.getOrigen());
        map.put("personaquerecibe",solicitudesModel.getPersonaquerecibe());
        map.put("telpersonarecibe",solicitudesModel.getTelpersonaquerecibe());
        map.put("precio",solicitudesModel.getPrecio());
        map.put("seo",solicitudesModel.getSeo());
        map.put("seocliente",solicitudesModel.getSeocliente());
        map.put("status",solicitudesModel.getStatus());
        map.put("estado",solicitudesModel.getEstado());
        map.put("horasalida",solicitudesModel.getHorasalida());
        map.put("costopaquete",solicitudesModel.getCostopaquete());
        map.put("fechavalidar",solicitudesModel.getFechavalidar());
        map.put("idviaje",solicitudesModel.getIdviaje());
        map.put("origenlat",solicitudesModel.getOrigenLat());
        map.put("origenlog",solicitudesModel.getOrigenLog());
        map.put("destinolatt",solicitudesModel.getDestinoLatt());
        map.put("destinolog",solicitudesModel.getDestinoLog());

        return mdatabase.child(solicitudesModel.getIdnoty()).updateChildren(map);
    }


    public DatabaseReference getViajes(String ViajesId) {
        return mdatabase.child(ViajesId);
    }

    public Task<Void> delete(String idClientBooking) {
        return mdatabase.child(idClientBooking).removeValue();
    }
}
