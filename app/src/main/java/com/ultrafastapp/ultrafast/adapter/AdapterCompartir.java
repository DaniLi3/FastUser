package com.ultrafastapp.ultrafast.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;
import com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.DetallesDeMisSolicitudes.ViajeIniciado;
import com.ultrafastapp.ultrafast.Models.ClienteBookin;
import com.ultrafastapp.ultrafast.Models.ModelCompartir;
import com.ultrafastapp.ultrafast.Providers.Choferprovider;
import com.ultrafastapp.ultrafast.R;

public class AdapterCompartir extends FirebaseRecyclerAdapter<ModelCompartir, AdapterCompartir.ViewHolder> {
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

public AdapterCompartir(@NonNull FirebaseRecyclerOptions<ModelCompartir> options, Context context) {
    super(options);
    choferprovider=new Choferprovider();
            mcontex=context;
        clienteBookin = new ClienteBookin();
}

@Override
protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ModelCompartir model) {
    final String ide=model.getId();
    final String image=model.getImagenusuario();
    final String idchofer=model.getIdchofer();
    final String nombrechofer=model.getNombrechofer();
    final String telchofer=model.getTel();
    final String origen=model.getOrigen();
    final String destino=model.getDestino();
    final String fechasalida=model.getFechasalida();
    final double destinolat=model.getDestinolat();
    final double destinolog=model.getDestinolog();
    final double origenlat=model.getOrigenlat();
    final double origenlog=model.getOrigenlog();
    if (image!=null)
    {

        Picasso.with(mcontex).load(image).into(holder.imageView);
    }
    holder.Nombre.setText(model.getNombreusuarioviaje());
    holder.Cali.setText("");
    holder.icono.setVisibility(View.GONE);

    holder.mview.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(mcontex, ViajeIniciado.class);

            intent.putExtra("id",ide);
            intent.putExtra("idchofer",idchofer);
            intent.putExtra("nombre",nombrechofer);
            intent.putExtra("origen",origen);
            intent.putExtra("destino",destino);
            intent.putExtra("tel",telchofer);
            intent.putExtra("destinolat",destinolat);
            intent.putExtra("destinolog",destinolog);
            intent.putExtra("origenlat",origenlat);
            intent.putExtra("origenlog",origenlog);
            intent.putExtra("fechasalida",fechasalida);
            mcontex.startActivity(intent);
        }
    });


}


@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_compartir_viaje,parent,false);
    return new ViewHolder( view);
}


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Nombre;
        TextView Cali;
        CardView cardView;
        ImageView imageView;
        ImageView icono;
        private TextView ciudadorigen,ciudaddestino ,asignados,kilos;
        private TextView origenn,fecha,destinoo;
        private View mview;


        public ViewHolder(View view){
            super(view);
            Nombre=itemView.findViewById(R.id.txtname);
            icono=itemView.findViewById(R.id.iconoo);
            imageView=itemView.findViewById(R.id.imageViewPro);
            Cali=itemView.findViewById(R.id.txtcali);


            mview=itemView;

        }

    }
    }
