package com.example.mcbot.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mcbot.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BotSettingActivity extends AppCompatActivity {

    @BindView(R.id.botsetting_bot_iv)
    ImageView botIv;

    @BindView(R.id.botsetting_name_tv)
    TextView botTv;

    int botId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_setting);
        ButterKnife.bind(this);

        //호출할 때 같이 보낸 값 받아옴
        Intent iT = getIntent();
//        botId = iT.getExtras().getInt("botId", 0);

        botId = 0;
        setBot();
    }

    void setBot(){

        int imgPath;
        if(botId==0){
            imgPath = R.drawable.jaeseok;
        }else if(botId==1){
            imgPath = R.drawable.youngcheul;
        }else if(botId==2){
            imgPath = R.drawable.dongseok;
        }

        Glide.with(this)
                .load(R.drawable.jaeseok)
                .into(botIv);


    }


}
