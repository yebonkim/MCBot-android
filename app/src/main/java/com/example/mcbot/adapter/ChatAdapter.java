package com.example.mcbot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mcbot.R;
import com.example.mcbot.adapter.recyclerview.RecyclerViewHolder;
import com.example.mcbot.model.Chat;
import com.example.mcbot.model.User;
import com.example.mcbot.util.ImageUtil;
import com.example.mcbot.util.SharedPreferencesManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yebonkim on 2018. 8. 9..
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_MY_CHAT = 1;
    public static final int VIEW_TYPE_THIER_CHAT = 2;

    Context context;

    ArrayList<Chat> chats;
    ArrayList<User> users;
    String username;

    public ChatAdapter(Context context, ArrayList<Chat> chats, ArrayList<User> users) {
        this.context = context;
        this.chats = chats;
        this.users = users;

        this.username = SharedPreferencesManager.getInstance(context).getUserName();
        setHasStableIds(true);
    }

    @Override
    public int getItemViewType(int position) {
        if(chats.get(position).getUsername().equals(username))
            return VIEW_TYPE_MY_CHAT;
        else
            return VIEW_TYPE_THIER_CHAT;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case VIEW_TYPE_MY_CHAT :
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_my_chat, parent, false);
                return new MyView(view);
            case VIEW_TYPE_THIER_CHAT :
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_their_chat, parent, false);
                return new TheirView(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyView || holder instanceof  TheirView)
            ((RecyclerViewHolder)holder).setData(position);
    }

    @Override
    public long getItemId(int position) {
        return chats.get(position).getTimestamp();
    };

    @Override
    public int getItemCount() {
        return chats.size();
    }

    protected User getUserByUsername(String username) {
        for(User user : users) {
            if(user.getUsername().equals(username))
                return user;
        }

        return null;
    }

    public class MyView extends RecyclerView.ViewHolder implements RecyclerViewHolder{
        @BindView(R.id.msgTV)
        TextView msgTV;

        View view;

        public MyView(View view) {
            super(view);
            this.view = view;
            context =view.getContext();
            ButterKnife.bind(this, view);
        }

        public void setData(int position) {
            msgTV.setText(chats.get(position).getMessage());
        }
    }

    public class TheirView extends RecyclerView.ViewHolder implements RecyclerViewHolder{
        @BindView(R.id.msgTV)
        TextView msgTV;
        @BindView(R.id.profileIV)
        ImageView profileIV;
        @BindView(R.id.nameTV)
        TextView nameTV;

        View view;

        public TheirView(View view) {
            super(view);
            this.view = view;
            context =view.getContext();
            ButterKnife.bind(this, view);
        }

        public void setData(int position) {
            User user = getUserByUsername(chats.get(position).getUsername());
            nameTV.setText(user.getShowingName());
            msgTV.setText(chats.get(position).getMessage());
            ImageUtil.setProfileImage(context, user.getProfileName(), profileIV);
        }
    }
}