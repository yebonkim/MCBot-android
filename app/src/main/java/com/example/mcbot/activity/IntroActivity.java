package com.example.mcbot.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mcbot.R;
import com.example.mcbot.util.SharedPreferencesManager;
import com.example.mcbot.util.retro.RetroClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroActivity extends AppCompatActivity {

    RetroClient retroClient;

    @BindView(R.id.nicknameEt)
    EditText nicknameEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);

        retroClient = RetroClient.getInstance(this).createBaseApi();
    }

    public void onClick_Enter(View v){

        String nickname = nicknameEt.getText().toString();

        SharedPreferencesManager spm = SharedPreferencesManager.getInstance(this);
        spm.setUsername(nickname);

        Intent iT = new Intent(this, MainActivity.class);
        startActivity(iT);
        finish();
    }


}
