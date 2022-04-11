package com.ultrafastapp.ultrafast.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.EnviarSolicitud.DetallesViajeActivity;
import com.ultrafastapp.ultrafast.Models.ViajesPublicados;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;

public class AdapterRecientes extends FirebaseRecyclerAdapter<ViajesPublicados, AdapterRecientes.ViewHolder> {

    private Choferprovider mClientprovider;
    private Context mcontex;
    public Authprovider authprovider;


    public AdapterRecientes(@NonNull FirebaseRecyclerOptions<ViajesPublicados> options, Context context) {
        super(options);
        mClientprovider = new Choferprovider();
        authprovider=new Authprovider();
        mcontex=context;
    }

    @Override
    public AdapterRecientes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_viajes_recientes,parent,false);
        return new AdapterRecientes.ViewHolder(view);
    }

@Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ViajesPublicados model) {
    final String id=getRef(position).getKey();
    String iduser = model.getIdUser();
    String origen = model.getOrigen();
    String destino = model.getDestino();
    String fechasalida = model.getFecha()   ;
    String hora = model.getHora()   ;
    String fechapublicado = model.getFechaactual();
    String peso = model.getPeso();
    String idviaje = model.getIdViajes();
    String cantidaddeentregas = model.getCantidadentregas();
    String cityorigen=model.getCityOrigen();
    String citydestino=model.getCityDestino();
    String paquetesasignados=model.getPaquetesasignados();

    holder.origenn.setText(origen);
    holder.ciudadorigen.setText(cityorigen);
    holder.destinoo.setText(destino);
    holder.ciudaddestino.setText(citydestino);
    holder.fecha.setText("Fecha de salida: "+fechasalida);
    holder.kilos.setText("Peso disponible en el auto: "+peso+" kg");
    holder.asignados.setText("Paquetes asignados: "+paquetesasignados);
    holder.mview.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


                Intent intent=new Intent(mcontex, DetallesViajeActivity.class);
                intent.putExtra("idHistoryBooking",id);
                mcontex.startActivity(intent);

        }
    });






    }
    public class  ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView ciudadorigen,ciudaddestino ,asignados,kilos;
        private TextView origenn,fecha,destinoo;
        private View mview;


        public ViewHolder(View view){

            super(view);
            origenn=itemView.findViewById(R.id.txtorigen);
            destinoo=itemView.findViewById(R.id.txtdestino);
            fecha=itemView.findViewById(R.id.cardhora);
            ciudadorigen=itemView.findViewById(R.id.txtciudadorigen);
            ciudaddestino=itemView.findViewById(R.id.txtciudaddestino);
            kilos=itemView.findViewById(R.id.txtpesodis);
            asignados=itemView.findViewById(R.id.txtasignados);

            mview=itemView;

        }
    }
}
