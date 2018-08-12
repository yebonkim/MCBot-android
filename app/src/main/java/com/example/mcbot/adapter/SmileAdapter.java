package com.example.mcbot.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mcbot.R;
import com.example.mcbot.adapter.recyclerview.RecyclerViewHolder;
import com.example.mcbot.model.Emotion;
import com.example.mcbot.model.ToDo;
import com.example.mcbot.model.ToDoUser;
import com.example.mcbot.model.User;
import com.example.mcbot.util.ImageUtil;
import com.example.mcbot.util.SharedPreferencesManager;
import com.example.mcbot.util.TimeFormat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yebonkim on 2018. 8. 9..
 */

public class SmileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_SMILE = 1;

    Context context;
    ArrayList<Emotion> emotions;
    ArrayList<User> users;

    public SmileAdapter(Context context, ArrayList<Emotion> emotions, ArrayList<User> users) {
        this.context = context;
        this.emotions = emotions;
        this.users = users;

        setHasStableIds(true);
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_SMILE;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case VIEW_TYPE_SMILE:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_smile, parent, false);
                return new EmotionView(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof EmotionView)
            ((RecyclerViewHolder)holder).setData(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    };

    @Override
    public int getItemCount() {
        return emotions.size();
    }

    protected User getUserByUsername(String username) {
        for(User user : users) {
            if(user.getUsername().equals(username))
                return user;
        }

        return null;
    }

    public class EmotionView extends RecyclerView.ViewHolder implements RecyclerViewHolder{
        @BindView(R.id.smile_viewholder_face_iv)
        ImageView profileIV;
        @BindView(R.id.smile_viewholder_nickname_tv)
        TextView nicknameTV;
        @BindView(R.id.smile_viewholder_state_tv)
        TextView stateTV;
        @BindView(R.id.smile_viewholder_smile_iv)
        ImageView smileIV;

        View view;
        Context context;

        Emotion data;

        public EmotionView(View view) {
            super(view);
            this.view = view;
            context =view.getContext();
            ButterKnife.bind(this, view);
        }

        @Override
        public void setData(int position) {
            data = emotions.get(position);

            nicknameTV.setText(data.getUsername());
            if(data.isBool()) {
                smileIV.setBackground(context.getDrawable(R.drawable.ic_smile));
                stateTV.setText("웃었습니다!");
                stateTV.setTextColor(context.getColor(R.color.gray));
            } else {
                smileIV.setBackgroundColor(context.getColor(R.color.white));
                stateTV.setText("웃지 않았습니다");
                stateTV.setTextColor(context.getColor(R.color.red));
            }

            Log.d("Yebon", "user"+data.getUsername());
            Log.d("Yeobn", "pic"+getUserByUsername(data.getUsername()).getProfileName());

            ImageUtil.setProfileImage(context, getUserByUsername(data.getUsername()).getProfileName(), profileIV);

        }

    }
}