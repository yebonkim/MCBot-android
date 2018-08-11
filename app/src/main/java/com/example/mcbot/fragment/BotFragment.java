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
import android.widget.LinearLayout;

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

//    @BindView(R.id.bot_btn)
//    Button bot_btn;

    @BindView(R.id.bot_1_iv)
    ImageView bot1_Iv;

    @BindView(R.id.bot_2_iv)
    ImageView bot2_Iv;

    @BindView(R.id.bot_3_iv)
    ImageView bot3_Iv;

    @BindView(R.id.bot_check1_iv)
    ImageView check1_iv;

    @BindView(R.id.bot_check2_iv)
    ImageView check2_iv;

    @BindView(R.id.bot_check3_iv)
    ImageView check3_iv;

    @BindView(R.id.bot1_ll)
    LinearLayout bot1LL;

    @BindView(R.id.bot2_ll)
    LinearLayout bot2LL;

    @BindView(R.id.bot3_ll)
    LinearLayout bot3LL;

    Boolean[] chk;

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


        chk = new Boolean[3];
        chk[0] = true;
        chk[1] = true;
        chk[2] = true;

        imageInit();
        checkClick(context);
    }

    void checkClick(final Context context){
        bot1LL.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
              if( chk[0] ){
                  Glide.with(context)
                          .load(R.drawable.check_off)
                          .into(check1_iv);
                  chk[0] = false;
              }else{
                  Glide.with(context)
                          .load(R.drawable.check_on)
                          .into(check1_iv);
                  chk[0] = true;
              }
            }
        });

        bot2LL.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( chk[1] ){
                    Glide.with(context)
                            .load(R.drawable.check_off)
                            .into(check2_iv);
                    chk[1] = false;
                }else{
                    Glide.with(context)
                            .load(R.drawable.check_on)
                            .into(check2_iv);
                    chk[1] = true;
                }
            }
        });

        bot3LL.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( chk[2] ){
                    Glide.with(context)
                            .load(R.drawable.check_off)
                            .into(check3_iv);
                    chk[2] = false;
                }else{
                    Glide.with(context)
                            .load(R.drawable.check_on)
                            .into(check3_iv);
                    chk[2] = true;
                }
            }
        });
    }

    void imageInit(){
        Glide.with(this)
                .load(R.drawable.jaeseok)
                .into(bot1_Iv);

        Glide.with(this)
                .load(R.drawable.youngcheul)
                .into(bot2_Iv);

        Glide.with(this)
                .load(R.drawable.dongseok)
                .into(bot3_Iv);

        Glide.with(this)
                .load(R.drawable.check_on)
                .into(check1_iv);

        Glide.with(this)
                .load(R.drawable.check_on)
                .into(check2_iv);

        Glide.with(this)
                .load(R.drawable.check_on)
                .into(check3_iv);
    }

}
