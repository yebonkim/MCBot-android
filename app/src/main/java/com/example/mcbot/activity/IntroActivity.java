package com.example.mcbot.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mcbot.R;
import com.example.mcbot.model.Chat;
import com.example.mcbot.model.ChatResult;
import com.example.mcbot.util.retro.RetroCallback;
import com.example.mcbot.util.retro.RetroClient;

import java.util.HashMap;

public class IntroActivity extends AppCompatActivity {

    RetroClient retroClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        retroClient = RetroClient.getInstance(this).createBaseApi();
    }

    //서버(Flask)로 채팅내용 전송
    public void postChat(Chat chat){

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("message", chat.getMessage());
        parameters.put("timestamp", chat.getTimestamp());
        parameters.put("unreadCnt", 5);
        parameters.put("username", chat.getUsername() );


        retroClient.postChat(parameters, new RetroCallback() {

            @Override
            public void onError(Throwable t) {
                Log.e("Retrofit", "onError(), " + t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                ChatResult data = (ChatResult) receivedData;
                Log.e("Retrofit", "Retrofit Response: " + data.isResult());
            }

            @Override
            public void onFailure(int code) {
                Log.e("Retrofit", "onFailure(), " + String.valueOf(code));
            }
        });
    }
}
