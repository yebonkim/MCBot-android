package com.example.mcbot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mcbot.R;
import com.example.mcbot.adapter.recyclerview.RecyclerViewHolder;
import com.example.mcbot.model.ToDo;
import com.example.mcbot.model.User;
import com.example.mcbot.util.ImageUtil;
import com.example.mcbot.util.SharedPreferencesManager;
import com.example.mcbot.util.TimeFormat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yebonkim on 2018. 8. 9..
 */

public class MemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_PROFILE = 1;

    Context context;
    ArrayList<User> users;

    public MemberAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;

        setHasStableIds(true);
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_PROFILE;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case VIEW_TYPE_PROFILE:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_member, parent, false);
                return new ToDoView(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ToDoView)
            ((RecyclerViewHolder)holder).setData(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    };

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class ToDoView extends RecyclerView.ViewHolder implements RecyclerViewHolder{
        @BindView(R.id.profileIV)
        ImageView profileIV;

        View view;
        Context context;

        public ToDoView(View view) {
            super(view);
            this.view = view;
            context =view.getContext();
            ButterKnife.bind(this, view);
        }

        @Override
        public void setData(int position) {
            ImageUtil.setProfileImage(context, users.get(position).getProfileName(), profileIV);
        }
    }
}