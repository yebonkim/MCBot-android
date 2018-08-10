package com.example.mcbot.util;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by yebonkim on 2018. 8. 10..
 */

public class ImageUtil {

    protected static void setImage(final Context context, String filePath, final ImageView imageView) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child(filePath);

        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageURL = uri.toString();
                Glide.with(context).load(imageURL).into(imageView);
            }
        });
    }

    public static void setProfileImage(final Context context, String filename, final ImageView imageView) {
        setImage(context, "profile/"+filename+".png", imageView);
    }

    public static void setDefaultProfileImage(final Context context, final ImageView imageView) {
        setImage(context, "profile/default_profile.png", imageView);
    }
}
