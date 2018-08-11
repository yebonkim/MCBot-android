package com.example.mcbot.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mcbot.fragment.BotFragment;
import com.example.mcbot.fragment.ChattingFragment;
import com.example.mcbot.fragment.ToDoFragment;

/**
 * Created by Kimyebon on 2018-08-11.
 */

public class MainViewPageAdapter extends FragmentPagerAdapter {

    //탭 이름은 SlidingTabLayout.java 에서 변경해줘야함
    Fragment[] fragmentList = { new ChattingFragment(), new BotFragment(), new ToDoFragment()};
    public MainViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position < fragmentList.length) {
            return fragmentList[position];
        }
        return null;
    }

    @Override
    public String getPageTitle(int position) {
        //not used
        if (position < fragmentList.length) {
            return "";
        }
        return null;
    }
    @Override
    public int getCount() {
        return fragmentList.length;
    }
}