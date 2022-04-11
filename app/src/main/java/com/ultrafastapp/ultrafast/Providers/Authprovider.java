package com.ultrafastapp.ultrafast.Providers;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Authprovider {
    FirebaseAuth mAut;
    public Authprovider(){
    mAut=FirebaseAuth.getInstance();
    }

    public Task<AuthResult>singPhone(String verificationID, String code)
    {
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationID, code);
        return mAut.signInWithCredential(credential);
    }
    public void sendCodeVerification(String phone, PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                callbacks
        );
    }
    public String getid()
    {
return mAut.getCurrentUser().getUid();
    }
    public void logut()
    {
        mAut.signOut();

    }
    public boolean existSesion()
    {
        boolean exist = false;
        if (mAut.getCurrentUser()!=null)
        {
         exist=true;
        }
        return exist;
    }

}
