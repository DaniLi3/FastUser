package com.ultrafastapp.ultrafast.Providers;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ultrafastapp.ultrafast.Models.Tokens;

public class TokenProvider {

    DatabaseReference mdatabase;
public TokenProvider()
{
   mdatabase= FirebaseDatabase.getInstance().getReference().child("Tokens");
}

public void create(final String idUser) {
    if (idUser == null) {
        Log.d("esnull","es null");
        return;
    }
    Log.d("esnull","es no");
    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
        @Override
        public void onSuccess(InstanceIdResult instanceIdResult) {
            Tokens tokens=new Tokens(instanceIdResult.getToken());
            mdatabase.child(idUser).setValue(tokens);
        }
    });
}
    public DatabaseReference getToken(String idUser)
    {
        return mdatabase.child(idUser);
    }
}
