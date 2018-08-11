package com.example.mcbot.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mcbot.R;
import com.example.mcbot.activity.BotSettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kimyebon on 2018-08-11.
 */

public class BotFragment extends Fragment {
    Context context;

    @BindView(R.id.bot_btn)
    Button bot_btn;

    @BindView(R.id.bot_1_iv)
    ImageView bot1_Iv;

    @BindView(R.id.bot_2_iv)
    ImageView bot2_Iv;

    @BindView(R.id.bot_3_iv)
    ImageView bot3_Iv;

    public BotFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ChattingFragment", "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_bot, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        context = getContext();

        bot_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iT = new Intent(context, BotSettingActivity.class);
                startActivity(iT);
            }
        });

        Glide.with(this)
                .load(R.drawable.jaeseok)
                .into(bot1_Iv);

        Glide.with(this)
                .load(R.drawable.youngcheul)
                .into(bot2_Iv);

        Glide.with(this)
                .load(R.drawable.dongseok)
                .into(bot3_Iv);
    }

}
