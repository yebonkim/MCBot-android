package com.example.mcbot.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mcbot.R;
import com.example.mcbot.adapter.BotListViewAdapter;
import com.example.mcbot.model.BotSkill;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BotSettingActivity extends AppCompatActivity {

    @BindView(R.id.botsetting_bot_iv)
    ImageView botIv;
    @BindView(R.id.botsetting_name_tv)
    TextView botTv;
    @BindView(R.id.bot_complete_btn)
    ImageView botBtn;
    @BindView(R.id.bot_listview)
    ListView listView;

    BotListViewAdapter adapter;

    int botId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_setting);
        ButterKnife.bind(this);

        //툴바 없에기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //호출할 때 같이 보낸 값 받아옴
        Intent iT = getIntent();
        botId = iT.getExtras().getInt("botId", 0);



        Glide.with(this)
                .load(R.drawable.bot_complete_btn)
                .into(botBtn);

        //리스트뷰
        adapter = new BotListViewAdapter();
        listView.setAdapter(adapter);

        //리스트뷰의 클릭리스너
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                BotSkill botSkill = (BotSkill) parent.getAdapter().getItem(position);
                adapter.getCheck(position);
                adapter.notifyDataSetChanged();

            }
        });

        setBot();

    }

    void setBot(){

        int imgPath= R.drawable.jaeseok;
        if(botId==0){
            imgPath = R.drawable.jaeseok;
            botTv.setText("MC봇");
            adapter.addItem(true, "팀플 일정조율");
            adapter.addItem(false, "자기소개 시키기");
            adapter.addItem(true, "일정독촉");
            adapter.addItem(true, "TODO리스트 자동생성");
        }else if(botId==1){
            imgPath = R.drawable.youngcheul;
            botTv.setText("칭호봇");
            adapter.addItem(true, "긍정적 칭호부여");
            adapter.addItem(true, "부정적 칭호부여");
        }else if(botId==2){
            imgPath = R.drawable.dongseok;
            adapter.addItem(true, "갑분싸 방지 웃음요구");
            adapter.addItem(false, "불시 출석부");
            adapter.addItem(true, "독촉전화");
            adapter.addItem(true, "평가결과 교수님께 전달");
            botTv.setText("조교봇");
        }

        adapter.notifyDataSetChanged();

        Glide.with(this)
                .load(imgPath)
                .into(botIv);
    }

    public void onClick_BotSetting(View v){
        finish();
    }
}
