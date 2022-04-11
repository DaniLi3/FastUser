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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes.EnviarSolicitud.VerUsuarioPublico;
import com.ultrafastapp.ultrafast.Models.Choferes;
import com.ultrafastapp.ultrafast.R;

import java.util.List;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.ViewHolderDatos> {
   List<Choferes>Listdatos;
    private Context mcontex;
private String idchofer;
    String idnoty;
    String nombrechofer;
    String telchofer;
    String origen;
    String destino;
    String fechasalida;

    double destinolat;
    double destinolog;
    double origenlat;
    double origenlog;



    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_compartir_viaje,parent,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {


        final String ide=Listdatos.get(position).getId();
        final String image=Listdatos.get(position).getImage();
        final String cali=Listdatos.get(position).getCalificacion();
        final String nombre=Listdatos.get(position).getNombre();
        final String apellidos=Listdatos.get(position).getApellidos();
        final String bibliografia=Listdatos.get(position).getBibliografia();
        final String fechausuario=Listdatos.get(position).getFechaactual();
        final String viajesrealizados=Listdatos.get(position).getRealizados();
        final String viajessolicitados=Listdatos.get(position).getSolicitados();
        final String tipousuario=Listdatos.get(position).getTipousuario();


        if (image!=null)
        {

            Picasso.with(mcontex).load(image).into(holder.imageView);
        }

        holder.Nombre.setText(Listdatos.get(position).getNombre());
        holder.Cali.setText(Listdatos.get(position).getCalificacion());


        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontex, VerUsuarioPublico.class);

                intent.putExtra("id",ide);
                intent.putExtra("origen",origen);
                intent.putExtra("destino",destino);
                intent.putExtra("idchofer",idchofer);
                intent.putExtra("nombrechofer",nombrechofer);
                intent.putExtra("telchofer",telchofer);
                intent.putExtra("destinolat",destinolat);
                intent.putExtra("destinolog",destinolog);
                intent.putExtra("origenlat",origenlat);
                intent.putExtra("origenlog",origenlog);
                intent.putExtra("imagen",image);
                intent.putExtra("bibliografia",bibliografia);
                intent.putExtra("fechausuario",fechausuario);
                intent.putExtra("viajesrealizados",viajesrealizados);
                intent.putExtra("viajessolicitados",viajessolicitados);
                intent.putExtra("tipousuario",tipousuario);
                intent.putExtra("cali",cali);
                intent.putExtra("fechasalida",fechasalida);
                intent.putExtra("nombre",nombre+" "+apellidos);
                Log.d("cobardeea", nombrechofer+" "+telchofer);

                mcontex.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return Listdatos.size();
    }
    public List<Choferes> getItem()
    {
        return this.Listdatos;
    }

    /*public AdapterUsuarios(List<Choferes> listdatos, Context context,String idchoferr) {
        Listdatos = listdatos;
        mcontex=context;
        idchofer=idchoferr;
    }*/

    public AdapterUsuarios(List<Choferes> listdatos, Context mcontex, String idchofer, String idnoty, double destinolat, double destinolog, double origenlat, double origenlog,String nomrechofer,String telchofer,String origen,String destino,String fechasalida) {
        Listdatos = listdatos;
        this.mcontex = mcontex;
        this.idchofer = idchofer;
        this.idnoty = idnoty;
        this.destinolat = destinolat;
        this.destinolog = destinolog;
        this.origenlat = origenlat;
        this.origenlog = origenlog;
        this.nombrechofer = nomrechofer;
        this.telchofer = telchofer;
        this.origen = origen;
        this.destino = destino;
        this.fechasalida = fechasalida;
    }


    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView Nombre;
        TextView Cali;
        CardView cardView;
        ImageView imageView;
        private TextView ciudadorigen,ciudaddestino ,asignados,kilos;
        private TextView origenn,fecha,destinoo;
        private View mview;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            Nombre=itemView.findViewById(R.id.txtname);
            imageView=itemView.findViewById(R.id.imageViewPro);
            Cali=itemView.findViewById(R.id.txtcali);


            mview=itemView;
        }
    }
}
