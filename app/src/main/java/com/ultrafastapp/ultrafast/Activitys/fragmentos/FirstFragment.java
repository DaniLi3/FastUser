  package com.ultrafastapp.ultrafast.Activitys.fragmentos;

  import android.app.ProgressDialog;
  import android.content.Context;
  import android.content.Intent;
  import android.content.SharedPreferences;
  import android.net.Uri;
  import android.os.Bundle;
  import android.text.InputType;
  import android.util.Log;
  import android.view.LayoutInflater;
  import android.view.View;
  import android.view.ViewGroup;
  import android.widget.Button;
  import android.widget.EditText;
  import android.widget.ImageView;
  import android.widget.TextView;
  import android.widget.Toast;

  import androidx.annotation.NonNull;
  import androidx.annotation.Nullable;
  import androidx.fragment.app.Fragment;
  import androidx.fragment.app.FragmentActivity;
  import androidx.fragment.app.FragmentTransaction;
  import androidx.recyclerview.widget.LinearLayoutManager;
  import androidx.recyclerview.widget.RecyclerView;

  import com.firebase.ui.database.FirebaseRecyclerOptions;
  import com.google.android.gms.tasks.OnCompleteListener;
  import com.google.android.gms.tasks.OnSuccessListener;
  import com.google.android.gms.tasks.Task;
  import com.google.android.libraries.places.api.net.PlacesClient;
  import com.google.firebase.database.DataSnapshot;
  import com.google.firebase.database.DatabaseError;
  import com.google.firebase.database.DatabaseReference;
  import com.google.firebase.database.FirebaseDatabase;
  import com.google.firebase.database.Query;
  import com.google.firebase.database.ValueEventListener;
  import com.google.firebase.storage.FirebaseStorage;
  import com.google.firebase.storage.OnProgressListener;
  import com.google.firebase.storage.StorageReference;
  import com.google.firebase.storage.UploadTask;
  import com.ultrafastapp.ultrafast.Activitys.PrincipalPaquete.SeleccionarFecha;
  import com.ultrafastapp.ultrafast.Activitys.Registro.Registro1;
  import com.ultrafastapp.ultrafast.Activitys.Registro.Registro2;
  import com.ultrafastapp.ultrafast.Models.ViajesPublicados;
  import com.ultrafastapp.ultrafast.Providers.Authprovider;
  import com.ultrafastapp.ultrafast.Providers.ViajesPubliProvider;
  import com.ultrafastapp.ultrafast.R;
  import com.ultrafastapp.ultrafast.Recyclers.RecyclerBusqueda;
  import com.ultrafastapp.ultrafast.Utils.ValidarFecha;
  import com.ultrafastapp.ultrafast.adapter.AdapterRecientes;
  import com.ultrafastapp.ultrafast.putPdf;

  import java.util.List;

  import static android.app.Activity.RESULT_OK;


  public class FirstFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViajesPubliProvider viajesPubliProvider;
    public ValidarFecha validarFecha;

    private FragmentActivity myContext;
    Button bntpublicar,Buscar;
    Button Viajescerca;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private View view;
    private View vieww;
    TextView selecfecha;

    private ImageView ClearDestino;
    private ImageView ClearOrigen;

    private EditText ori,dest;
    private Context context;
    RecyclerView rv;
    List<ViajesPublicados> coches;
    AdapterRecientes adapterr;

    Button bn;
    EditText pdf;
    String texto="V";
    String origenlatt;
    String origenlogg;

    String destinolatt;
    String destinologg;

    String cityOrigen="";
    String citydestino="";

    String preOrigenLat;
    String preOrigenLog;
    String preDestinoLat;
    String preDestinoLog;
    String precityOrigen;
    String precityDestino;
    Authprovider authprovider;
      ProgressDialog progressDialog;



    private PlacesClient mplaces;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first,container, false);
        FirstFragment f = new FirstFragment();

        ori = view.findViewById(R.id.clicorigen);
        bntpublicar = view.findViewById(R.id.bntpublicar);
        vieww = view.findViewById(R.id.mostrarrecientes);
        ori.setInputType(InputType.TYPE_NULL);
        dest = view.findViewById(R.id.clicdestino);
        dest.setInputType(InputType.TYPE_NULL);
        selecfecha=view.findViewById(R.id.selecFecha);
        Buscar=view.findViewById(R.id.btnbuscar);
        Viajescerca=view.findViewById(R.id.bntviajescerca);
        ClearDestino = view.findViewById(R.id.clearDestino);
        pdf=view.findViewById(R.id.pdf);

        ClearOrigen = view.findViewById(R.id.clearorigen);
        rv=view.findViewById(R.id.reci);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager1);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();


        SharedPreferences prefs = this.getActivity().getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        ClearOrigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ori.setText("");
                editor.remove("Origen");
                editor.remove("OrigenLat");
                editor.remove("OrigenLog");
                editor.apply();
                ClearOrigen.setVisibility(view.INVISIBLE);
            }
        });
        ClearDestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dest.setText("");
                editor.remove("Destino");
                editor.remove("DestinoLat");
                editor.remove("DestinoLog");
                editor.apply();
                ClearDestino.setVisibility(view.INVISIBLE);
            }
        });

        if (this.getArguments()!=null)
        {
            String myValue = this.getArguments().getString("message");
            String Origenn = this.getArguments().getString("origen");
            String Destinoo=this.getArguments().getString("destino");
            cityOrigen=this.getArguments().getString("cityOrigen");
            citydestino=this.getArguments().getString("cityDestino");
            origenlatt = this.getArguments().getString("origenlata");
            origenlogg= this.getArguments().getString("origenlog");
            destinolatt = this.getArguments().getString("destinolata");
            destinologg= this.getArguments().getString("destinolog");

            if (citydestino!=null)
            {
                editor.putString("cityDestino", citydestino);
                Log.d("maricon",citydestino);
                editor.commit();
            }

            if (cityOrigen!=null)
            {
                editor.putString("cityOrigen", cityOrigen);
                Log.d("maricon",cityOrigen);
                editor.commit();
            }

            if (origenlatt!=null || origenlogg!=null)
            {
                editor.putString("OrigenLat", origenlatt);
                editor.putString("OrigenLog", origenlogg);
                Log.d("maricon",origenlatt+" "+origenlogg);
                editor.commit();
            }
            if (destinolatt!=null || destinologg!=null)
            {
                editor.putString("DestinoLat", destinolatt);
                editor.putString("DestinoLog", destinologg);
                Log.d("maricon",destinolatt+" "+destinologg);
                editor.commit();
            }

            if (myValue!=null)
            {
                editor.putString("Fechas", myValue);
                editor.commit();
            }
            else
            {
                Log.d("kiko","FEcha Esta vacia");
            }
            if (Origenn!=null)
            {
                editor.putString("Origen", Origenn);
                editor.commit();
            }
            else
            {
                Log.d("kiko","Origen Esta vacia");
            }
            if (Destinoo!=null)
            {
                editor.putString("Destino", Destinoo);
                editor.commit();
            }
            else
            {
                Log.d("kiko","Destino Esta vacia");
            }

            String correo = prefs.getString("Origen", "texto por defecto");
            preOrigenLat = prefs.getString("OrigenLat", "texto por defecto");
            preOrigenLog = prefs.getString("OrigenLog", "texto por defecto");
            preDestinoLat = prefs.getString("DestinoLat", "texto por defecto");
            preDestinoLog = prefs.getString("DestinoLog", "texto por defecto");
            precityOrigen = prefs.getString("cityOrigen", "texto por defecto");
            precityDestino = prefs.getString("cityDestino", "texto por defecto");

            String Des = prefs.getString("Destino", "texto por defecto");
            String fech = prefs.getString("Fechas", "texto por defecto");


            if (!correo.equals("texto por defecto"))
            {
                ori.setText(correo);
                ClearOrigen.setVisibility(view.VISIBLE);
            }

            if (!fech.equals("texto por defecto"))
            {
                Log.d("lupe","texto por defecto");
                selecfecha.setText(fech);

            }
            if (!Des.equals("texto por defecto"))
            {
                dest.setText(Des);
                ClearDestino.setVisibility(view.VISIBLE);
            }


        }else {
            editor.clear();
            editor.commit();
        }

        bntpublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getContext(), PublicarViajeOrigen.class);
                startActivity(intent);*/
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.Yivao.yivao"));
                startActivity(i);
            }
        });  Viajescerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadFragment1();
               // subirodf();

            }
        });
        Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ori.getText().toString().trim().equalsIgnoreCase(""))
                {
                    ori.setError("This field can not be blank");
                    return;
                }
                else if (dest.getText().toString().trim().equalsIgnoreCase("")){
                    dest.setError("This field can not be blank");

                }
                else{
                    Intent intent = new Intent(getContext(),  RecyclerBusqueda.class);
                    intent.putExtra("seo",precityOrigen+"_"+precityDestino);
                    startActivity(intent);
                }






            }
        });
        ori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Registro1.class);
                startActivity(intent);

            }
        });
        dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Registro2.class);
                startActivity(intent);
            }
        });

        selecfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SeleccionarFecha.class);
                startActivity(intent);
            }
        });


        return view;
    }
    public void loadFragment1() {
        Viajescercadeti viajescercadeti=new Viajescercadeti();

        Bundle bun = new Bundle();
       
        Log.d("solotuu", "String.valueOf(mlatLng");

        viajescercadeti.setArguments(bun);



        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.menu,viajescercadeti);
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        validarFecha = new ValidarFecha();
        authprovider=new Authprovider();
        viajesPubliProvider = new ViajesPubliProvider("ViajesPublicados");

        viajesPubliProvider.getViajestodos().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String Dato = dataSnapshot.child("fechavali").getValue().toString();
                    String id = dataSnapshot.child("idUser").getValue().toString();

                    if (!validarFecha.validarfecha(Dato)&&!id.equals(authprovider.getid()))
                    {

                        viajesPubliProvider = new ViajesPubliProvider("ViajesCaducados");
                        String origen = dataSnapshot.child("origen").getValue().toString();
                        String destino = dataSnapshot.child("destino").getValue().toString();
                        String fecha = dataSnapshot.child("fecha").getValue().toString();
                        String hora = dataSnapshot.child("hora").getValue().toString();
                        String idUser = dataSnapshot.child("idUser").getValue().toString();
                        String idViajes = dataSnapshot.child("idViajes").getValue().toString();
                        String seo = dataSnapshot.child("seo").getValue().toString();
                        String fechaactual = dataSnapshot.child("fechaactual").getValue().toString();
                        String comentario = dataSnapshot.child("comentario").getValue().toString();
                        String peso = dataSnapshot.child("peso").getValue().toString();
                        String cantidadentregas = dataSnapshot.child("cantidadentregas").getValue().toString();

                        viajesPubliProvider.createcaducados(validarFecha.viajesCaducadoss(origen,destino,fecha,hora,idUser,idViajes,seo,fechaactual,comentario,peso,cantidadentregas)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                viajesPubliProvider = new ViajesPubliProvider("ViajesPublicados");
                               viajesPubliProvider.delete(idViajes);

                            }
                        });
                    }else
                    {
                        vieww.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        authprovider = new Authprovider();
        Query query = FirebaseDatabase.getInstance().getReference().child("ViajesPublicados").orderByChild("peso").limitToLast(5);
        FirebaseRecyclerOptions<ViajesPublicados> options=new FirebaseRecyclerOptions.Builder<ViajesPublicados>()
                .setQuery(query,ViajesPublicados.class).build();
        adapterr=new AdapterRecientes(options, getContext());
        rv.setAdapter(adapterr);
        adapterr.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterr.stopListening();
    }
    public void subirodf()
    {
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference=FirebaseDatabase.getInstance().getReference("");
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"SELECCIONAR PDF"),12);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==12&&resultCode==RESULT_OK&&data.getData()!=null)
        {
            pdf.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));
            pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pdf(data.getData());
                }
            });

        }
    }
    public void pdf(Uri data )
    {
        final ProgressDialog progressDialog =new ProgressDialog(getContext());
        progressDialog.setTitle("Subiendo archivo...");
        progressDialog.show();
        StorageReference reference=storageReference.child("upload"+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri =uriTask.getResult();
                putPdf putPdf=new putPdf(pdf.getText().toString(),uri.toString());
                databaseReference.child(databaseReference.push().getKey()).setValue(putPdf);
                Toast.makeText(getContext(), "Archivo subido", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progess=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Subiendo archivo.. "+(int) progess+ "%");

            }
        });


    }
}