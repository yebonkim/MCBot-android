package com.example.mcbot.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mcbot.R;
import com.example.mcbot.activity.LocationActivity;
import com.example.mcbot.activity.NaverApiMapActivity;
import com.example.mcbot.adapter.recyclerview.RecyclerViewHolder;
import com.example.mcbot.model.Chat;
import com.example.mcbot.model.User;
import com.example.mcbot.util.ImageUtil;
import com.example.mcbot.util.SharedPreferencesManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

/**
 * Created by yebonkim on 2018. 8. 9..
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_MY_CHAT = 1;
    public static final int VIEW_TYPE_THIER_CHAT = 2;
    public static final int VIEW_TYPE_ATTENDANCE_CHAT = 3;
    public static final int VIEW_TYPE_SET_MEETING_CHAT = 4;
    public static final int VIEW_TYPE_MEETING_CHAT = 5;

    Activity activity;
    ArrayList<Chat> chats;
    ArrayList<User> users;
    String username;

    // database 접근용
    String roomName = "ChatRoom6";
    FirebaseDatabase database;
    DatabaseReference attendanceDB;


    protected void initDatabase() {
        database = FirebaseDatabase.getInstance();
        attendanceDB = database.getReference().child("Attendance/"+roomName);
    }

    public ChatAdapter(Activity activity, ArrayList<Chat> chats, ArrayList<User> users) {
        this.activity = activity;
        this.chats = chats;
        this.users = users;

        this.username = SharedPreferencesManager.getInstance(activity).getUserName();
        setHasStableIds(true);
        initDatabase();
    }

    @Override
    public int getItemViewType(int position) {
        // 내가 한 말
        if(chats.get(position).getUsername().equals(username))
            return VIEW_TYPE_MY_CHAT;
        else {
            switch(chats.get(position).getType()) {
                //show attendance message
                case 2:
                    if(chats.get(position).getMessage().equals(username+"님 계십니까"))
                        return VIEW_TYPE_ATTENDANCE_CHAT;
                case 0:
                    //other user`s message
                    return VIEW_TYPE_THIER_CHAT;
                    //case 1 is not occurred
                case 3:
                    //show location & date pick message
                    return VIEW_TYPE_SET_MEETING_CHAT;
                case 5:
                    return VIEW_TYPE_MEETING_CHAT;

            }
        }

        return VIEW_TYPE_THIER_CHAT;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case VIEW_TYPE_MY_CHAT:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_my_chat, parent, false);
                return new MyView(view);
            case VIEW_TYPE_THIER_CHAT:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_their_chat, parent, false);
                return new TheirView(view);
            case VIEW_TYPE_ATTENDANCE_CHAT:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_attendance_uncomplete_chat, parent, false);
                return new AtndCheckView(view);
            case VIEW_TYPE_SET_MEETING_CHAT:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_attendance_uncomplete_chat, parent, false);
                return new SetMeetingView(view);
            case VIEW_TYPE_MEETING_CHAT:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_attendance_uncomplete_chat, parent, false);
                return new MeetingView(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
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
        Context context;

        public MyView(View view) {
            super(view);
            this.view = view;
            context =view.getContext();
            ButterKnife.bind(this, view);
        }

        @Override
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
        @BindView(R.id.chatTimeTv)
        TextView timeTv;

        View view;
        Context context;

        public TheirView(View view) {
            super(view);
            this.view = view;
            context =view.getContext();
            ButterKnife.bind(this, view);
        }

        @Override
        public void setData(int position) {
            User user = getUserByUsername(chats.get(position).getUsername());

            if(user != null) {
                nameTV.setText(user.getShowingName() + "");
                msgTV.setText(chats.get(position).getMessage());
                ImageUtil.setProfileImage(context, user.getProfileName(), profileIV);

                Date d = new Date(chats.get(position).getTimestamp());
                String timeStr = new SimpleDateFormat("HH:mm").format(d);
                timeTv.setText( timeStr );
            }
        }
    }

    public class AtndCheckView extends RecyclerView.ViewHolder implements RecyclerViewHolder{
        @BindView(R.id.msgTV)
        TextView msgTV;
        @BindView(R.id.profileIV)
        ImageView profileIV;
        @BindView(R.id.nameTV)
        TextView nameTV;
        @BindView(R.id.atndBtn)
        Button btnYes;

        View view;
        Context context;

        public AtndCheckView(View view) {
            super(view);
            this.view = view;
            context =view.getContext();
            ButterKnife.bind(this, view);
        }

        public void setData(int position) {
            User user = getUserByUsername(chats.get(position).getUsername());

            if(user != null) {
                nameTV.setText(user.getShowingName() + "");
                msgTV.setText(chats.get(position).getMessage());
                btnYes.setText("대답하기");
                btnYes.setOnClickListener(listener);
                ImageUtil.setProfileImage(context, user.getProfileName(), profileIV);
            }
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.atndBtn:
                        btnYes.setText("출석 확인됨");
                        btnYes.setEnabled(false);
                        attendanceDB.child(username).setValue(true);
                        break;
                }
            }
        };

    }


    public class SetMeetingView extends RecyclerView.ViewHolder implements RecyclerViewHolder{
        @BindView(R.id.msgTV)
        TextView msgTV;
        @BindView(R.id.profileIV)
        ImageView profileIV;
        @BindView(R.id.nameTV)
        TextView nameTV;
        @BindView(R.id.atndBtn)
        Button btnYes;

        View view;
        Context context;

        public SetMeetingView(View view) {
            super(view);
            this.view = view;
            context =view.getContext();
            ButterKnife.bind(this, view);
        }

        public void setData(int position) {
            User user = getUserByUsername(chats.get(position).getUsername());

            if(user != null) {
                nameTV.setText(user.getShowingName() + "");
                msgTV.setText(chats.get(position).getMessage());
                btnYes.setText("설정하기");
                btnYes.setOnClickListener(listener);
                ImageUtil.setProfileImage(context, user.getProfileName(), profileIV);
            }
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.atndBtn:
                        activity.startActivity(new Intent(activity, NaverApiMapActivity.class));
                        break;
                }
            }
        };

    }


    public class MeetingView extends RecyclerView.ViewHolder implements RecyclerViewHolder{
        @BindView(R.id.msgTV)
        TextView msgTV;
        @BindView(R.id.profileIV)
        ImageView profileIV;
        @BindView(R.id.nameTV)
        TextView nameTV;
        @BindView(R.id.atndBtn)
        Button btnYes;

        View view;
        Context context;
        double lat, lan;

        public MeetingView(View view) {
            super(view);
            this.view = view;
            context =view.getContext();
            ButterKnife.bind(this, view);
        }

        public void setData(int position) {
            User user = getUserByUsername(chats.get(position).getUsername());
            StringTokenizer stk = new StringTokenizer(chats.get(position).getMessage(), ",");
            lat = Double.parseDouble(stk.nextElement().toString());
            lan = Double.parseDouble(stk.nextElement().toString());


            if(user != null) {
                nameTV.setText(user.getShowingName() + "");
                msgTV.setText("약속장소가 정해졌습니다!");
                btnYes.setText("장소보기");
                btnYes.setOnClickListener(listener);
                ImageUtil.setProfileImage(context, user.getProfileName(), profileIV);
            }
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.atndBtn:
                        activity.startActivity(new Intent(activity, LocationActivity.class).putExtra("lat", lat).putExtra("lan", lan));
                        break;
                }
            }
        };

    }


}