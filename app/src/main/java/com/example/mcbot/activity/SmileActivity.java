package com.example.mcbot.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mcbot.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mcbot.model.Chat;
import com.example.mcbot.model.PostResult;
import com.example.mcbot.util.SharedPreferencesManager;
import com.example.mcbot.util.retro.RetroCallback;
import com.example.mcbot.util.retro.RetroClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.support.annotation.NonNull;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import butterknife.ButterKnife;


public class SmileActivity extends AppCompatActivity implements View.OnClickListener{

    Button Button_Camera;
    private DatabaseReference mPostReference;
    RetroClient retroClient;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smile);
        ButterKnife.bind(this);

        retroClient = RetroClient.getInstance(this).createBaseApi(); //레트로핏 초기화

        Button_Camera = (Button)findViewById(R.id.btn_takephoto);
        Button_Camera.setOnClickListener(this);

        mPostReference = FirebaseDatabase.getInstance().getReference().child("Emotion");
        getBool();
    }

    protected void getBool() {
        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot child: dataSnapshot.getChildren())
//                    ToDo change status of emotion

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onClick(View view){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,3);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Toast.makeText(getApplicationContext(), "웃어주셔야 합니다^^", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==3)
        {
            Bundle extras = data.getExtras();
            Bitmap mImageBitmap = (Bitmap)extras.get("data");


//            fire base coding
            String imgName = "emotion_test";
            // Create a storage reference from our app
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();

            // Create a reference to "mountains.jpg"
            StorageReference mountainsRef = storageRef.child(imgName);

            // Create a reference to 'images/mountains.jpg'
            StorageReference mountainImagesRef = storageRef.child("emotion_user").child( imgName );

            // While the file names are the same, the references point to different files
            mountainsRef.getName().equals(mountainImagesRef.getName());    // true
            mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

//            humm
            mImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] datas = baos.toByteArray();

            UploadTask uploadTask = mountainsRef.putBytes(datas);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    Log.d("PPFF", "경로 " + downloadUrl);

                    postFace( downloadUrl.toString() );

                }
            });

        }
    }


    //서버(Flask)로 채팅내용 전송
    public void postFace(String uri){


        SharedPreferencesManager spm = SharedPreferencesManager.getInstance(this);

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("user", spm.getUserName());
        parameters.put("url", uri);


        retroClient.postFace(parameters, new RetroCallback() {

            @Override
            public void onError(Throwable t) {
                Log.e("Retrofit", "onError(), " + t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                PostResult data = (PostResult) receivedData;

                Log.e("FlaskFace", "결과: " + data.isResult() );
                if(data.isResult()){
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "웃는 얼굴로 찍어주세요 :)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int code) {
                Log.e("Retrofit", "onFailure(), " + String.valueOf(code));
            }
        });
    }

}