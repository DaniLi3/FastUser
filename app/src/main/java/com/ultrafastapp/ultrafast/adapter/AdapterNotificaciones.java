package com.ultrafastapp.ultrafast.adapter;

import android.content.Context;
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
import com.ultrafastapp.ultrafast.Models.ClienteBookin;
import com.ultrafastapp.ultrafast.Models.SolicitudesModel;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;

public class AdapterNotificaciones extends FirebaseRecyclerAdapter<SolicitudesModel, AdapterNotificaciones.ViewHolder> {
Choferprovider choferprovider;
ClienteBookin clienteBookin;
    String nombre="";
    String apellidos="";
    String imagencliente;
    String fechaderegistrocliente;
    String descripcioncliente;
    String telefono;
    String calificacion;

    private Context mcontex;
private ImageView mimageViewBookin;

public AdapterNotificaciones(@NonNull FirebaseRecyclerOptions<SolicitudesModel> options, Context context) {
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

        holder.IPendin.setVisibility(View.GONE);
        holder.estado.setText("Aceptado");

    }
    if (status.equals("cancelado"))
    {

        holder.IAcept.setVisibility(View.GONE);
        holder.IPendin.setVisibility(View.GONE);

        holder.estado.setText("Cancelado");


    }
    if (status.equals("creado"))
    {
        holder.ICancel.setVisibility(View.GONE);
        holder.IAcept.setVisibility(View.GONE);


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
    String telpersonarecibe = model.getTelpersona();
    String contenidopaquete = model.getContenidopaquete();
    String dimensiones = model.getDimensiones();
   String fechadepublicacionviaje=model.getFechadepublicacionviaje();
    String kiloss = model.getKilos();
    String seo=model.seo;
    String fechasolicitud=model.getFechadesolicitud();
    String notapaquete=model.getNotapaquete();
    String horasalida=model.getHorasalida();
    String fechavalidar=model.getFechavalidar();
    String costopaquete=model.getCostopaquete();
    String idviaje=model.getIdviaje();
    String estado=model.getEstado();
    double destinolat=model.getDestinolatt();
    double destinolog=model.getDestinolog();
    double origenlat=model.getOrigenlat();
    double origenlog=model.getOrigenlog();

    String[]fechaa=fechasolicitud.split(" ");

//    String []horaformato=fechaa[1].split(":");
//    holder.fecha.setText(fechaa[0]);
    holder.kilos.setText("$"+kiloss);
    holder.origen.setText(origen);
    holder.destino.setText(destino);
    holder.fecha.setText("Recibido el: "+fechaa[0]);



    // holder.destinoo.setText(solicitudesModel.getDestino());

    choferprovider.getusuario(idcliente).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists())
            {

                apellidos =snapshot.child("apellidos").getValue().toString();
                nombre =snapshot.child("nombre").getValue().toString();
                descripcioncliente=snapshot.child("bibliografia").getValue().toString();
                fechaderegistrocliente=snapshot.child("fechaactual").getValue().toString();
                telefono = snapshot.child("tel").getValue().toString();
                calificacion=snapshot.child("calificacion").getValue().toString();
                String id =snapshot.child("id").getValue().toString();


                if(snapshot.hasChild("image"))
                {      imagencliente=snapshot.child("image").getValue().toString();

                    Picasso.with(mcontex).load(imagencliente).into(holder.imageView);
                }

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
                /*

                if (status.equals("aceptado"))
                {
                    Intent intent=new Intent(mcontex, DetallesViajeAceptado.class);
                    intent.putExtra("idnoty", idnoty);
                    intent.putExtra("idcliente", idcliente);
                    intent.putExtra("id", idchofer);
                    intent.putExtra("personaquerecibe", personaquerecibe);
                    intent.putExtra("telpersonarecibe", telpersonarecibe);
                    intent.putExtra("notapaquete", notapaquete);
                    intent.putExtra("contenidopaquete", contenidopaquete);
                    intent.putExtra("dimensiones", dimensiones);
                    intent.putExtra("kilos", kiloss);
                    intent.putExtra("origen", origen);
                    intent.putExtra("telefono", telefono);
                    intent.putExtra("destino", destino);
                    intent.putExtra("fechasalida", fechasalida);
                    intent.putExtra("fechasolicitud", fechaa[0]);
                    intent.putExtra("fechapublicacionviaje", fechadepublicacionviaje);
                    intent.putExtra("imagen", imagen);
                    intent.putExtra("precio", precio);
                    intent.putExtra("idHistoryBooking", id);
                    intent.putExtra("seo", seo);
                    intent.putExtra("calificacion", calificacion);
                    intent.putExtra("nombre", nombre+" "+apellidos);
                    intent.putExtra("imagencliente", imagencliente);
                    intent.putExtra("fecharegistrocliente", fechaderegistrocliente);
                    intent.putExtra("descripcioncliente", descripcioncliente);
                    intent.putExtra("costopaquete", costopaquete);
                    intent.putExtra("idviaje", idviaje);
                    intent.putExtra("estado", estado);
                    mcontex.startActivity(intent);
                    return;

                }
                Intent intent=new Intent(mcontex, DetalleSolicitudClientes.class);
                intent.putExtra("idnoty", idnoty);
                intent.putExtra("idcliente", idcliente);
                intent.putExtra("id", idchofer);
                intent.putExtra("personaquerecibe", personaquerecibe);
                intent.putExtra("telpersonarecibe", telpersonarecibe);
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
                intent.putExtra("calificacion", calificacion);
                intent.putExtra("fechadepublicacionviaje", fechadepublicacionviaje);
                intent.putExtra("nombre", nombre+" "+apellidos);
                intent.putExtra("imagencliente", imagencliente);
                intent.putExtra("fecharegistrocliente", fechaderegistrocliente);
                intent.putExtra("descripcioncliente", descripcioncliente);
                intent.putExtra("cityorigen", fechaderegistrocliente);
                intent.putExtra("citydestino", descripcioncliente);
                intent.putExtra("horasalida", horasalida);
                intent.putExtra("fechavalidar", fechavalidar);
                intent.putExtra("costopaquete", costopaquete);
                intent.putExtra("idviaje", idviaje);
                intent.putExtra("origenlat", origenlat);
                intent.putExtra("origenlog", origenlog);
                intent.putExtra("destinolat", destinolat);
                intent.putExtra("destinolog", destinolog);
                mcontex.startActivity(intent);
                 */
                 

            }


        });
        Log.d("contadorres",""+imagencliente);


}


@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_solicitudes,parent,false);
    return new ViewHolder( view);
}

public class ViewHolder extends RecyclerView.ViewHolder{
    TextView estado,modelo;
    private TextView origenn,fecha;

    private TextView nombre,Calificacion,origen,destino,kilos;
    private View mview;
    ImageView imageView,IAcept,IPendin,ICancel;

    public ViewHolder(View view){
        super(view);
        nombre = itemView.findViewById(R.id.txtnombre);
        // destinoo=itemView.findViewById(R.id.txtdestino);

        fecha=itemView.findViewById(R.id.txtfecha);
        origenn=itemView.findViewById(R.id.txtorigen);
        imageView=itemView.findViewById(R.id.imageViewPro);
        estado=itemView.findViewById(R.id.txtstatus);
        IAcept=itemView.findViewById(R.id.iacept);
        IPendin=itemView.findViewById(R.id.ipendin);
        ICancel=itemView.findViewById(R.id.icancel);
        Calificacion=itemView.findViewById(R.id.txtcalii);
        kilos=itemView.findViewById(R.id.txtprecio);
        origen=itemView.findViewById(R.id.txtorigen);
        destino=itemView.findViewById(R.id.txtdestino);
        mview=itemView;
    }

}
}
