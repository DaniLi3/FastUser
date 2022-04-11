package com.ultrafastapp.ultrafast.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.EnviarSolicitud.DetallesViajeActivity;
import com.ultrafastapp.ultrafast.Models.ModelViajesCerca;
import com.ultrafastapp.ultrafast.R;

import java.util.List;

public class AdapterViajesCerca  extends RecyclerView.Adapter<AdapterViajesCerca.ViewHolderDatos> {
   List<ModelViajesCerca>Listdatos;
    private Context mcontex;


    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_viajespubli,parent,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        Log.d("ella","entra");
        final String id=Listdatos.get(position).getIdViajes();
        holder.Origen.setText(Listdatos.get(position).getOrigen());
        holder.Destino.setText(Listdatos.get(position).getDestino());
      ;
        holder.ciudadorigen.setText(Listdatos.get(position).getCityorigen());

        holder.ciudaddestino.setText(Listdatos.get(position).getCitydestino());
        holder.fecha.setText("Fecha de salida: "+Listdatos.get(position).getFechapublicado());
            holder.kilos.setText("Peso disponible en el auto: "+Listdatos.get(position).getPeso()+" kg");
        holder.asignados.setText("Paquetes asignados: "+Listdatos.get(position).getPaquetesasignados());

        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontex, DetallesViajeActivity.class);
                intent.putExtra("idHistoryBooking",id);
                mcontex.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return Listdatos.size();
    }
    public List<ModelViajesCerca> getItem()
    {
        return this.Listdatos;
    }

    public AdapterViajesCerca(List<ModelViajesCerca> listdatos,Context context) {
        Listdatos = listdatos;
        mcontex=context;
    }


    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView Origen;
        TextView Destino;
        CardView cardView;
        private TextView ciudadorigen,ciudaddestino ,asignados,kilos;
        private TextView origenn,fecha,destinoo;
        private View mview;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            Origen=itemView.findViewById(R.id.txtorigen);
            Destino=itemView.findViewById(R.id.txtdestino);
            fecha=itemView.findViewById(R.id.cardhora);
            ciudadorigen=itemView.findViewById(R.id.txtciudadorigen);
            ciudaddestino=itemView.findViewById(R.id.txtciudaddestino);
            kilos=itemView.findViewById(R.id.txtpesodis);
            asignados=itemView.findViewById(R.id.txtasignados);
            mview=itemView;
        }
    }
}
