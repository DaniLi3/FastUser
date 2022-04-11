package com.ultrafastapp.ultrafast.Providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ultrafastapp.ultrafast.Models.Choferes;

import java.util.HashMap;
import java.util.Map;

public class Choferprovider {

    DatabaseReference mdatarefe;

    public Choferprovider()
    {
      mdatarefe= FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("Users");

    }
    public Task<Void>create(Choferes chofer)
    {
    return mdatarefe.child(chofer.getId()).setValue(chofer);
    }

    public DatabaseReference getusuario(String idUsuario) {
        return mdatarefe.child(idUsuario);
    }

    public Task<Void> updateima(Choferes driver) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", driver.getNombre());
        map.put("image", driver.getImage());
        return mdatarefe.child(driver.getId()).updateChildren(map);
    }
    public Task<Void> Registrarcoche(Choferes driver) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", driver.getNombre());
        map.put("apellidos", driver.getApellidos());
        map.put("genero", driver.getGenero());
        map.put("status", driver.getStatus());
        map.put("email", driver.getEmail());
        map.put("domicilio", driver.getDireccion());
        map.put("telefono", driver.getTelefono());
        map.put("bibliografia", driver.getBibliografia());
        map.put("anio", driver.getAnio());
        map.put("marca", driver.getMarca());
        map.put("modelo", driver.getModelo());
        map.put("matricula", driver.getMatricula());
        map.put("fechaactual", driver.getFechaactual()  );
        map.put("calificacion", driver.getCalificacion()  );
        map.put("tipousuario", driver.getTipousuario()  );
        map.put("solicitados", driver.getSolicitados()  );
        map.put("realizados", driver.getRealizados()  );



        return mdatarefe.child(driver.getId()).updateChildren(map);
    }
    public Task<Void> Actializar(Choferes driver) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", driver.getNombre());
        map.put("apellidos", driver.getApellidos());

        map.put("status", driver.getStatus());
        map.put("email", driver.getEmail());
        map.put("domicilio", driver.getDireccion());
        map.put("telefono", driver.getTelefono());
        map.put("bibliografia", driver.getBibliografia());
        map.put("anio", driver.getAnio());
        map.put("marca", driver.getMarca());
        map.put("modelo", driver.getModelo());
        map.put("matricula", driver.getMatricula());

        return mdatarefe.child(driver.getId()).updateChildren(map);
    }
    public Task<Void> Actializarsolicitados(Choferes driver) {
        Map<String, Object> map = new HashMap<>();

        map.put("solicitados", driver.getSolicitados());

        return mdatarefe.child(driver.getId()).updateChildren(map);
    }
    public Task<Void> guardarbanco(Choferes choferes)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("titularcuenta",choferes.getTitularcuenta());
        map.put("nombrebanco",choferes.getNombrebanco());
        map.put("numerocuenta",choferes.getNumerocuenta());
        map.put("numerotarjeta",choferes.getNumerotarjeta());

        return mdatarefe.child(choferes.getId()).updateChildren(map);

    }






}
