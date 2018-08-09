package com.example.mcbot.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mcbot.R;
import com.example.mcbot.constants.IntentConstants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, ChatRoomActivity.class).putExtra(IntentConstants.ROOM_NAME, "ChatRoom1"));
        finish();
    }
}
