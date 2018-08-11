package com.example.mcbot.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.mcbot.R;
import com.example.mcbot.adapter.MainViewPageAdapter;
import com.example.mcbot.constants.IntentConstants;
import com.example.mcbot.view.tab.SlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;

    MainViewPageAdapter viewPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setTabView();
    }

    protected void setTabView() {
        viewPageAdapter = new MainViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setViewPager(viewPager);
        tabLayout.setCustomTabView(R.layout.sliding_tab, R.id.tabTV);
        viewPager.setOffscreenPageLimit(3);
    }


}
