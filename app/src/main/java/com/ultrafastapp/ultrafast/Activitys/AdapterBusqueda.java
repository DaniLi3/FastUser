package com.ultrafastapp.ultrafast.Activitys;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.EnviarSolicitud.DetallesViajeActivity;
import com.ultrafastapp.ultrafast.Models.ClienteBookin;
import com.ultrafastapp.ultrafast.Models.ViajesPublicados;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;

import java.util.List;

public class AdapterBusqueda extends RecyclerView.Adapter<AdapterBusqueda.CocheviewHolder>{

    public List<ViajesPublicados> cochess;
    Choferprovider choferprovider;
    ImageView mimageViewBookin;
    ClienteBookin clienteBookin;
    public Authprovider authprovider;
    public int posicionmarca=0;
    private Context mcontex;

    public AdapterBusqueda(List<ViajesPublicados> cochess, Context context) {
        this.cochess = cochess;
        choferprovider=new Choferprovider();
        clienteBookin = new ClienteBookin();
        authprovider=new Authprovider();
        mcontex=context;
    }

    @NonNull
    @Override
    public CocheviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_prueba,parent,false);
       CocheviewHolder cocheviewHolder=new CocheviewHolder(view);
       return cocheviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CocheviewHolder holder, int position) {
        final String id=cochess.get(position).getIdViajes();

        ViajesPublicados viajesPublicados=cochess.get(position);
        holder.origenn.setText(viajesPublicados.getOrigen());
        holder.destinoo.setText(viajesPublicados.getDestino());
        holder.precio.setText(viajesPublicados.getPeso()+" kg");

        holder.Cityorigen.setText(viajesPublicados.getCityOrigen());
        holder.Citydestino.setText(viajesPublicados.getCityDestino());

        holder.hora.setText("Salida: "+viajesPublicados.getFecha());
        String iduser = viajesPublicados.getIdUser();
        choferprovider.getusuario(viajesPublicados.getIdUser()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {

                    String apellidos =snapshot.child("apellidos").getValue().toString();
                    String nombre =snapshot.child("nombre").getValue().toString();

                    String id =snapshot.child("id").getValue().toString();
                    Log.d("loro",id+" y "+nombre);

                    String image="";
                   if(snapshot.hasChild("image"))
                    {      image=snapshot.child("image").getValue().toString();
                        Log.d("loro id",id +nombre);
                           Picasso.with(mcontex).load(image).into(holder.imageView);
                    }
                   String calificacion=snapshot.child("calificacion").getValue().toString();

                    holder.nombre.setText(nombre+" "+apellidos);
                    holder.Calificacion.setText(calificacion);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iduser.equals(authprovider.getid()))
                {
                    Toast.makeText(mcontex, "No puedes abrir tu propio viaje", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent=new Intent(mcontex, DetallesViajeActivity.class);
                    intent.putExtra("idHistoryBooking", id);
                    mcontex.startActivity(intent);

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return cochess.size() ;
    }

    public static class CocheviewHolder extends RecyclerView.ViewHolder{

        TextView Cityorigen,Citydestino;
        private TextView origenn;
        private TextView destinoo;
        private TextView nombre,hora,precio,kilos,Calificacion;
        private View mview;
         ImageView imageView;

        public CocheviewHolder(@NonNull View itemView) {
            super(itemView);
            origenn = itemView.findViewById(R.id.txtorigen);
            destinoo = itemView.findViewById(R.id.txtdestino);
            nombre = itemView.findViewById(R.id.txtname);
            hora=itemView.findViewById(R.id.cardhora);
            precio=itemView.findViewById(R.id.precio);
            kilos=itemView.findViewById(R.id.kilos);
            imageView=itemView.findViewById(R.id.imageViewPro);
            Cityorigen=itemView.findViewById(R.id.txtcityorigen);
            Citydestino=itemView.findViewById(R.id.txtcitydestino);
            Calificacion=itemView.findViewById(R.id.txtcali);
            mview=itemView;


        }
    }
}
