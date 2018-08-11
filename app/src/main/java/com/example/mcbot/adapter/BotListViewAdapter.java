package com.example.mcbot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mcbot.R;
import com.example.mcbot.model.BotSkill;

import java.util.ArrayList;


/**
 * Created by Sunpil on 2016-07-13.
 */
public class BotListViewAdapter extends BaseAdapter {

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<BotSkill> listViewItem = new ArrayList<>() ;


    // ListViewAdapter의 생성자
    public BotListViewAdapter() {

    }


    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItem.size() ;
    }


    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // menulist.xml을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.viewholder_bot_skill, parent, false);
        }

        ImageView checkIv = convertView.findViewById(R.id.botskill_check_iv);
        TextView skillTv = convertView.findViewById(R.id.botskill_skill_tv);

        if(listViewItem.get(pos).getUse() ){
            Glide.with(context)
                    .load( R.drawable.check_on )
                    .into(checkIv);
        }else{
            Glide.with(context)
                    .load( R.drawable.check_off )
                    .into(checkIv);
        }
        checkIv.setScaleType(ImageView.ScaleType.FIT_XY);

        skillTv.setText( listViewItem.get(pos).getSkill() );
        return convertView;
    }


    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItem.get(position) ;
    }


    // 아이템 데이터 추가
    public void addItem(boolean using, String skill) {

        BotSkill item = new BotSkill(skill, using);
        listViewItem.add(item);
    }

    public void cleaarItem(){
        listViewItem.clear();
    }

    public void getCheck(int position){
        if( listViewItem.get(position).getUse() ){
            listViewItem.get(position).setUse(false);
        }else{
            listViewItem.get(position).setUse(true);
        }
    }
}