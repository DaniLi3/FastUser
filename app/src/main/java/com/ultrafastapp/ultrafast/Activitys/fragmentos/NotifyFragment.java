package com.ultrafastapp.ultrafast.Activitys.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastapp.ultrafast.Models.SolicitudesModel;
import com.ultrafastapp.ultrafast.Providers.Authprovider;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.adapter.SolicitudesAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifyFragment extends Fragment {


    RecyclerView rv;
    List<SolicitudesModel> coches;
    String seo;
    SolicitudesAdapter adapterr;
    ProgressDialog progressDialog;
    Authprovider authprovider;
    Context context;



    public NotifyFragment() {
        // Required empty public constructor
        context=getContext();
    }


    public static NotifyFragment newInstance(String param1, String param2) {
        NotifyFragment fragment = new NotifyFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_second, container, false);
        coches=new ArrayList<>();

        rv=view.findViewById(R.id.reci);
//        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        adapterr=new SolicitudesAdapter(coches, getContext());
       // rv.setLayoutManager(new LinearLayoutManager(getContext()));

        authprovider=new Authprovider();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Procesando");
        progressDialog.setMessage("Cargando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
       // seo=getIntent().getStringExtra("seo");
        rv.setAdapter(adapterr);

        DatabaseReference database = FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("Solicitudes");

        Query query=database.orderByChild("idchofer").equalTo(authprovider.getid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    coches.remove(coches);
                    for (DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        SolicitudesModel coche=dataSnapshot.getValue(SolicitudesModel.class);
                        coches.add(coche);
                    }
                    adapterr.notifyDataSetChanged();                }
                else
                {
                    Toast.makeText(getContext(), "no existe ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
         progressDialog.dismiss();



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
