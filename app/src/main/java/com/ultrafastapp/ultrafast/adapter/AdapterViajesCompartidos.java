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

import com.squareup.picasso.Picasso;
import com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.DetallesDeMisSolicitudes.ViajeIniciado;
import com.ultrafastapp.ultrafast.Models.ModelCompartir;
import com.ultrafastapp.ultrafast.R;

import java.util.List;

public class AdapterViajesCompartidos extends RecyclerView.Adapter<AdapterViajesCompartidos.ViewHolderDatos> {
   List<ModelCompartir>Listdatos;
    private Context mcontex;
private String idchofer;



    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_compartir_viaje,parent,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {


        final String ide=Listdatos.get(position).getId();
        final String image=Listdatos.get(position).getImagenusuario();
       final String idchofer=Listdatos.get(position).getIdchofer();
        final String nombrechofer=Listdatos.get(position).getNombrechofer();
        final String telchofer=Listdatos.get(position).getTel();
        final double destinolat=Listdatos.get(position).getDestinolat();
        final double destinolog=Listdatos.get(position).getDestinolog();
        final double origenlat=Listdatos.get(position).getOrigenlat();
        final double origenlog=Listdatos.get(position).getOrigenlog();
        //final String apellidos=Listdatos.get(position).getApellidos();
        //final String bibliografia=Listdatos.get(position).getBibliografia();
        //final String fechausuario=Listdatos.get(position).getFechaactual();
       // final String viajesrealizados=Listdatos.get(position).getRealizados();
       // final String viajessolicitados=Listdatos.get(position).getSolicitados();
      //  final String tipousuario=Listdatos.get(position).getTipousuario();


        if (image!=null)
        {

            Picasso.with(mcontex).load(image).into(holder.imageView);
        }

        holder.Nombre.setText(Listdatos.get(position).getNombreusuarioviaje());
        holder.Cali.setText("");
        holder.icono.setVisibility(View.GONE);


        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(mcontex, ViajeIniciado.class);

                intent.putExtra("id",ide);
                intent.putExtra("idchofer",idchofer);
                intent.putExtra("nombre",nombrechofer);
                intent.putExtra("tel",telchofer);
                intent.putExtra("destinolat",destinolat);
                intent.putExtra("destinolog",destinolog);
                intent.putExtra("origenlat",origenlat);
                intent.putExtra("origenlog",origenlog);
                mcontex.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return Listdatos.size();
    }
    public List<ModelCompartir> getItem()
    {
        return this.Listdatos;
    }

    public AdapterViajesCompartidos(List<ModelCompartir> listdatos, Context context, String idchoferr) {
        Listdatos = listdatos;
        mcontex=context;
        idchofer=idchoferr;
    }


    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView Nombre;
        TextView Cali;
        CardView cardView;
        ImageView imageView;
        ImageView icono;
        private TextView ciudadorigen,ciudaddestino ,asignados,kilos;
        private TextView origenn,fecha,destinoo;
        private View mview;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            Nombre=itemView.findViewById(R.id.txtname);
            icono=itemView.findViewById(R.id.iconoo);
            imageView=itemView.findViewById(R.id.imageViewPro);
            Cali=itemView.findViewById(R.id.txtcali);


            mview=itemView;
        }
    }
}
