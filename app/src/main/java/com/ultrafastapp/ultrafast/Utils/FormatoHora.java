package com.ultrafastapp.ultrafast.Utils;

public class FormatoHora {
    public String formatohora(String hora)
    {
        String []horaforma=hora.split(":");
        int min=Integer.parseInt(horaforma[0]);

        return horaforma[0]+":"+horaforma[1];


    }
}
