package com.example.mcbot.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mcbot.R;
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
    public void postChat(){

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("nickname", "nickname");
        parameters.put("chat", "싱그러운 자연의 햇살을 머금은 엄선된 포도만을 정성드레 담았습니다.");

        retroClient.postChat(parameters, new RetroCallback() {

            @Override
            public void onError(Throwable t) {
                Log.e("Retrofit", "onError(), " + t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.e("Retrofit", receivedData.toString());
            }

            @Override
            public void onFailure(int code) {
                Log.e("Retrofit", "onFailure(), " + String.valueOf(code));
            }
        });
    }
}
