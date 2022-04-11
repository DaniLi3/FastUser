package com.ultrafastapp.ultrafast.Activitys.ModuloMisSolicitudes;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastapp.ultrafast.Providers.ViajesPubliProvider;
import com.ultrafastapp.ultrafast.R;
import com.ultrafastapp.ultrafast.Utils.ValidarFecha;

import java.util.ArrayList;
import java.util.List;

public class TapMisViajes extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    public ValidarFecha validarFecha;
    ViajesPubliProvider viajesPubliProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_mis_viajes);
        tabLayout=findViewById(R.id.table);
        viewPager=findViewById(R.id.view_pague);

        ArrayList<String> arrayList=new ArrayList<>();

        prepareViewPague(viewPager,arrayList);

        //setup view pager

        tabLayout.setupWithViewPager(viewPager);
    }
    private void prepareViewPague(ViewPager viewPager,ArrayList<String> arrayList) {
        //iniciar el mainfragment
        TapMisViajes.MainAdapter adapter=new TapMisViajes.MainAdapter(getSupportFragmentManager());


        adapter.addFragment(new ViajesSolicitados(),"Viajes solicitados");
        adapter.addFragment(new ViajeEnCurso(),"En Curso");
        adapter.addFragment(new ViajesCancelados(),"Canceladas");
        viewPager.setAdapter(adapter);



    }
    private class MainAdapter extends FragmentPagerAdapter {
//iniciamos el arrarlist

        ArrayList<String> arrayList=new ArrayList<>();
        List<Fragment> fragmentslist=new ArrayList<>();

        //cramos el constructor
        public void addFragment(Fragment fragment,String title)
        {
            arrayList.add(title);
            fragmentslist.add(fragment);


        }
        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            //retornamos la posocion del fragmetn

            return fragmentslist.get(position);
        }

        @Override
        public int getCount() {
            // retornamos el tamaño de la lista

            return fragmentslist.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            //retornamos el array list de posocion,,jjj
            return arrayList.get(position);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        validarFecha = new ValidarFecha();
        viajesPubliProvider = new ViajesPubliProvider("Solicitudes");
        viajesPubliProvider.getViajestodos().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    if (dataSnapshot.child("fechavalidar").exists())
                    {

                        String Dato = dataSnapshot.child("fechavalidar").getValue().toString();
                        Log.d("loco",Dato);
                        if (!validarFecha.validarfecha(Dato)) {


                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}