package com.ultrafastapp.ultrafast.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.EnviarSolicitud.DetallesViajeActivity;
import com.ultrafastapp.ultrafast.Models.ClienteBookin;
import com.ultrafastapp.ultrafast.Models.ViajesPublicados;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;

    public class Histori extends FirebaseRecyclerAdapter<ViajesPublicados, Histori.ViewHolder> {
    Choferprovider choferprovider;
    ClienteBookin clienteBookin;
    private Context mcontex;
    private ImageView mimageViewBookin;

    public Histori(@NonNull FirebaseRecyclerOptions<ViajesPublicados> options, Context context) {
        super(options);
        choferprovider=new Choferprovider();
                mcontex=context;
            clienteBookin = new ClienteBookin();
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ViajesPublicados model) {
        final String id=getRef(position).getKey();
            holder.origenn.setText(model.getOrigen());
            holder.destinoo.setText(model.getDestino());
            holder.precio.setText("$"+model.getPrecio()+" MXN");
            holder.kilos.setText(model.getPeso()+"disponibles");

            holder.hora.setText("Salikda: "+model.getFecha()+" a las "+ model.getHora());
            String fecha = model.getFecha();
            Log.d("loro 1",model.getIdUser());

            choferprovider.getusuario(model.getIdUser()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {

                        String apellidos =snapshot.child("apellidos").getValue().toString();
                        String nombre =snapshot.child("nombre").getValue().toString();
                        String id =snapshot.child("id").getValue().toString();

                        String image="";
                   /* if(snapshot.hasChild("image"))
                    {      image=snapshot.child("image").getValue().toString();
                        Log.d("loro id",id +nombre);
                           Picasso.with(mcontex).load(image).into(mimageViewBookin);
                    }*/

                        holder.nombre.setText(nombre+" "+apellidos);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            holder.mview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mcontex, DetallesViajeActivity.class);
                    intent.putExtra("idHistoryBooking", id);
                    mcontex.startActivity(intent);

                }
            });
            Log.d("contador","3");
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_search_booking,parent,false);
        return new ViewHolder( view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView origenn;
        private TextView destinoo;
        private TextView nombre,hora,precio,kilos;
        private View mview;

        public ViewHolder(View view){
            super(view);
            mview=view;
            origenn = view.findViewById(R.id.txtorigen);
            destinoo = view.findViewById(R.id.txtdestino);
            nombre = view.findViewById(R.id.txtname);
            hora=view.findViewById(R.id.cardhora);
            precio=view.findViewById(R.id.precio);
            kilos=view.findViewById(R.id.kilos);
            //mimageViewBookin = view.findViewById(R.id.imageviewumagebookin);
        }

    }
}
