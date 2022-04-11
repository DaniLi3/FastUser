package com.ultrafastapp.ultrafast.Utils;

import com.ultrafastapp.ultrafast.Models.ViajesCaducados;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidarFecha {

    public boolean validarfecha(String fecha)
    {
        boolean keyy = false;

            String valid_until = fecha;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date strDate = null;
            try {
                strDate = sdf.parse(valid_until);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (new Date().after(strDate)) {
               keyy=false;
            }
            else
            {
                keyy=true;

            }

          return keyy;

    }
    public ViajesCaducados viajesCaducadoss(String origen,String destino,String fecha,String hora,String idUser,String idViajes,String seo, String fechaactual,String comentario ,String peso,String cantidadentregas)
    {
        ViajesCaducados viajesCaducados=new ViajesCaducados();
        viajesCaducados.setOrigen(origen);
        viajesCaducados.setDestino(destino);
        viajesCaducados.setFecha(fecha);
        viajesCaducados.setHora(hora);
        viajesCaducados.setIdUser(idUser);
        viajesCaducados.setIdViajes(idViajes);
        viajesCaducados.setSeo(seo);
        viajesCaducados.setFechaactual(fechaactual);
        viajesCaducados.setComentario(comentario);
        viajesCaducados.setPeso(peso);
        viajesCaducados.setCantidadentregas(cantidadentregas);

        return viajesCaducados;

    }

}
