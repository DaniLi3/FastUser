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
import com.squareup.picasso.Picasso;
import com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.DetallesDeMisSolicitudes.DetallesMijsViajesAceptado;
import com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.DetallesDeMisSolicitudes.DetallesMisViajes;
import com.ultrafastapp.ultrafast.Models.ClienteBookin;
import com.ultrafastapp.ultrafast.Models.SolicitudesModel;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;

public class AdapterMisViajes extends FirebaseRecyclerAdapter<SolicitudesModel, AdapterMisViajes.ViewHolder> {
Choferprovider choferprovider;
ClienteBookin clienteBookin;
    String nombre="";
    String apellidos="";
    String imagencliente;
    String fechaderegistrocliente;
    String descripcioncliente;
    String telefono;
    String valoracion;
    private Context mcontex;
private ImageView mimageViewBookin;

public AdapterMisViajes(@NonNull FirebaseRecyclerOptions<SolicitudesModel> options, Context context) {
    super(options);
    choferprovider=new Choferprovider();
            mcontex=context;
        clienteBookin = new ClienteBookin();
}

@Override
protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull SolicitudesModel model) {
    final String id=getRef(position).getKey();

    String idd = model.getIdchofer();
    String status = model.getStatus();
    if (status.equals("aceptado"))
    {
        holder.ICancel.setVisibility(View.GONE);
        holder.IAcept.setVisibility(View.VISIBLE);
        holder.IPendin.setVisibility(View.GONE);
        holder.estado.setText("Aceptado");

    }
    if (status.equals("cancelado"))
    {
        holder.ICancel.setVisibility(View.VISIBLE);
        holder.IAcept.setVisibility(View.GONE);
        holder.IPendin.setVisibility(View.GONE);

        holder.estado.setText("Cancelado");

    }
    if (status.equals("creado"))
    {
        holder.ICancel.setVisibility(View.GONE);
        holder.IAcept.setVisibility(View.GONE);
        holder.IPendin.setVisibility(View.VISIBLE);
        holder.estado.setText("Pendiente");
    }

    String idcliente = model.getIdcliente();
    String idchofer = model.getIdchofer();
    String origen = model.getOrigen();
    String destino = model.getDestino();
    String fechasalida = model.getFechadesalida();
    String imagen = model.getImage();
    String idnoty = model.getIdnoty();


    String precio = model.getPrecio();
    String personaquerecibe = model.getPersonaquerecibe();
    String contenidopaquete = model.getContenidopaquete();
    String dimensiones = model.getDimensiones();

    String kiloss = model.getKilos();
    String costopaquete = model.getCostopaquete();
    String horasalida = model.getHorasalida();
    String fechavalidar = model.getFechavalidar();
    String seo=model.seo;
    String fechasolicitud=model.getFechadesolicitud();
    String notapaquete=model.getNotapaquete();
    String estado=model.getEstado();
    String idviaje=model.getIdviaje();

    double destinolat=model.getDestinolatt();
    double destiolat=model.getDestinolatt();
    double destinolog=model.getDestinolog();
    double origenlat=model.getOrigenlat();
    double origenlog=model.getOrigenlog();




    String[]fechaa=fechasolicitud.split(" ");
    holder.fecha.setText(fechaa[0]);
    holder.origenn.setText(origen);
    holder.destinoo.setText(destino );

    // holder.destinoo.setText(solicitudesModel.getDestino());

    choferprovider.getusuario(idd).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists())
            {
                apellidos =snapshot.child("apellidos").getValue().toString();
                nombre =snapshot.child("nombre").getValue().toString();
                descripcioncliente=snapshot.child("bibliografia").getValue().toString();
                fechaderegistrocliente=snapshot.child("fechaactual").getValue().toString();
                telefono = snapshot.child("telefono").getValue().toString();
                valoracion = snapshot.child("calificacion").getValue().toString();

                String id =snapshot.child("id").getValue().toString();


                if(snapshot.hasChild("image"))
                {      imagencliente=snapshot.child("image").getValue().toString();

                    Picasso.with(mcontex).load(imagencliente).into(holder.imageView);
                }

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

                if (status.equals("aceptado"))
                {
                    Intent intent=new Intent(mcontex, DetallesMijsViajesAceptado.class);
                    intent.putExtra("idnoty", idnoty);
                    intent.putExtra("idcliente", idcliente);
                    intent.putExtra("personaquerecibe", personaquerecibe);
                    intent.putExtra("notapaquete", notapaquete);
                    intent.putExtra("contenidopaquete", contenidopaquete);
                    intent.putExtra("dimensiones", dimensiones);
                    intent.putExtra("kilos", kiloss);
                    intent.putExtra("costopaquete", costopaquete);
                    intent.putExtra("horasalida", horasalida);
                    intent.putExtra("fechavalidar", fechavalidar);
                    intent.putExtra("origen", origen);
                    intent.putExtra("telefono", telefono);
                    intent.putExtra("destino", destino);
                    intent.putExtra("fechasalida", fechasalida);
                    intent.putExtra("fechasolicitud", fechaa[0]);
                    intent.putExtra("imagen", imagen);
                    intent.putExtra("precio", precio);
                    intent.putExtra("idHistoryBooking", id);
                    intent.putExtra("seo", seo);
                    intent.putExtra("nombre", nombre+" "+apellidos);
                    intent.putExtra("imagencliente", imagencliente);
                    intent.putExtra("fecharegistrocliente", fechaderegistrocliente);
                    intent.putExtra("descripcioncliente", descripcioncliente);
                    intent.putExtra("valoracion", valoracion);
                    intent.putExtra("estado", estado);
                    intent.putExtra("idchofer", idchofer);
                    intent.putExtra("idviaje", idviaje);
                    intent.putExtra("origenlat", origenlat);
                    intent.putExtra("origenlog", origenlog);
                    intent.putExtra("destinolat", destinolat);
                    intent.putExtra("destinolog", destinolog);
                    intent.putExtra("origenlat", origenlat);
                    intent.putExtra("origenlog", origenlog);
                    intent.putExtra("destinolat", destinolat);
                    intent.putExtra("destinolog", destinolog);

                    mcontex.startActivity(intent);
                    return;

                }
                Intent intent=new Intent(mcontex, DetallesMisViajes.class);
                intent.putExtra("idnoty", idnoty);
                intent.putExtra("idchofer", idchofer);
                intent.putExtra("idcliente", idcliente);
                intent.putExtra("personaquerecibe", personaquerecibe);
                intent.putExtra("notapaquete", notapaquete);
                intent.putExtra("contenidopaquete", contenidopaquete);
                intent.putExtra("dimensiones", dimensiones);
                intent.putExtra("kilos", kiloss);
                intent.putExtra("status", status);
                intent.putExtra("origen", origen);
                intent.putExtra("destino", destino);
                intent.putExtra("fechasalida", fechasalida);
                intent.putExtra("fechasolicitud", fechaa[0]);
                intent.putExtra("imagen", imagen);
                intent.putExtra("precio", precio);
                intent.putExtra("idHistoryBooking", id);
                intent.putExtra("seo", seo);
                intent.putExtra("nombre", nombre+" "+apellidos);
                intent.putExtra("imagencliente", imagencliente);
                intent.putExtra("fecharegistrocliente", fechaderegistrocliente);
                intent.putExtra("descripcioncliente", descripcioncliente);
                intent.putExtra("valoracion", valoracion);
                Log.d("hugo",imagen);
               // Log.d("hugo1",imagencliente);


                mcontex.startActivity(intent);

            }
        });
        Log.d("contador",""+imagen);
}


@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_solicitudes,parent,false);
    return new ViewHolder( view);
}

    public class ViewHolder extends RecyclerView.ViewHolder{
            TextView estado,modelo;
        private TextView origenn,fecha,destinoo;

        private TextView nombre,hora,precio,kilos;
        private View mview;
        ImageView imageView,IAcept,IPendin,ICancel;

        public ViewHolder(View view){
            super(view);
            nombre = itemView.findViewById(R.id.txtnombre);
            // destinoo=itemView.findViewById(R.id.txtdestino);

            fecha=itemView.findViewById(R.id.txtfecha);
            destinoo=itemView.findViewById(R.id.txtdestino);
            origenn=itemView.findViewById(R.id.txtorigen);
            imageView=itemView.findViewById(R.id.imageViewPro);
            estado=itemView.findViewById(R.id.txtstatus);
            IAcept=itemView.findViewById(R.id.iacept);
            IPendin=itemView.findViewById(R.id.ipendin);
            ICancel=itemView.findViewById(R.id.icancel);
            mview=itemView;
        }

    }
    }
