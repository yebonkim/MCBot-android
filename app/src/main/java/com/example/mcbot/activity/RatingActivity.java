package com.example.mcbot.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.mcbot.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.MaskTransformation;

public class RatingActivity extends AppCompatActivity {

    @BindView(R.id.rating_bot_profile_iv)
    ImageView botIv;

    @BindView(R.id.rating_user_profile_iv)
    ImageView userIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ButterKnife.bind(this);
        initRating();
    }


    void initRating(){
        Glide.with(this)
                .load(R.drawable.jaeseok)
                .into(botIv);

        Glide.with(this)
                .load("https://img1.daumcdn.net/thumb/S600x434/?scode=1boon&fname=http://t1.daumcdn.net/liveboard/jobsN/df0e1a961fe64733aff578274e83aaac.png")
                .bitmapTransform(
                        new CenterCrop(this),
                        new MaskTransformation(this, R.drawable.round_box))
                .into(userIv);
        userIv.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}

