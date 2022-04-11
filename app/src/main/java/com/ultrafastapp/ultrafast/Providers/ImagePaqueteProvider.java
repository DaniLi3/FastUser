package com.ultrafastapp.ultrafast.Providers;

import android.content.Context;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ultrafastapp.ultrafast.Utils.CompressorBitmapImage;

import java.io.File;

public class ImagePaqueteProvider {
    private StorageReference mstorage;
    public  ImagePaqueteProvider(String ref)
    {
        mstorage=  FirebaseStorage.getInstance().getReference().child("image_paquetes").child(ref);
    }

    public UploadTask saveImage(Context context, String idUser, File file){
        byte[] imagebyte= CompressorBitmapImage.getImage(context,file.getPath(),300,300);
        StorageReference storage= mstorage.child("image_paquetes").child(idUser+".jpg");
        mstorage=storage;
        UploadTask uploadTask=storage.putBytes(imagebyte);
        return uploadTask;
    }
    public StorageReference getstorage()
    {
        return mstorage;
    }
}
