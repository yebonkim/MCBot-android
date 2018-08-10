package com.example.mcbot.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.example.mcbot.R;
import com.example.mcbot.adapter.ChatAdapter;
import com.example.mcbot.constants.IntentConstants;
import com.example.mcbot.model.Chat;
import com.example.mcbot.model.User;
import com.example.mcbot.util.SharedPreferencesManager;
import com.example.mcbot.util.TimeUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatRoomActivity extends AppCompatActivity {

    @BindView(R.id.newChatET)
    EditText newChatET;
    @BindView(R.id.chatRV)
    RecyclerView chatRV;

    FirebaseDatabase database;
    DatabaseReference userDB, chatDB;

    ChatAdapter adapter;

    String roomName;
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Chat> chats = new ArrayList<>();
    boolean isUsersGetDone, isChatsGetDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ButterKnife.bind(this);

        getIntentData();
        initDatabase();
        getPreChats();
        getUsers();
    }

    protected void getIntentData() {
        roomName = getIntent().getStringExtra(IntentConstants.ROOM_NAME);
    }

    protected void initDatabase() {
        database = FirebaseDatabase.getInstance();
        userDB = database.getReference().child("User");
        chatDB = database.getReference().child("Chat/"+roomName);
    }

    protected String getChatName(long timestamp) {
        return SharedPreferencesManager.getInstance(this).getUserName()+(String.valueOf(timestamp));
    }

    protected void getPreChats() {
        Query chatQuery = chatDB.limitToLast(100);
        chatQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats = new ArrayList<>();
                for (DataSnapshot child: dataSnapshot.getChildren())
                    chats.add(child.getValue(Chat.class));

                isChatsGetDone = true;
                sortChats();
                setRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void sortChats() {
        Collections.sort(chats, new ChatAscendingComparator());
    }

    protected void getUsers() {
        Query userQuery = userDB.limitToLast(100);
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users = new ArrayList<>();
                for (DataSnapshot child: dataSnapshot.getChildren())
                    users.add(child.getValue(User.class));

                isUsersGetDone = true;
                setRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void setRecyclerView() {
        if(!(isChatsGetDone && isUsersGetDone))
            return;

        chatRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(this, chats, users);
        chatRV.setAdapter(adapter);
    }

    @OnClick(R.id.sendBtn)
    public void sendMsg() {
        postNewChat(collectData());
    }

    protected void postNewChat(Chat chat) {
        String chatName = getChatName(chat.getTimestamp());
        chatDB.child(chatName).setValue(chat);
        chatDB.child(chatName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                newChatET.setText("");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected Chat collectData() {
        String msg = newChatET.getText().toString();
        String username = SharedPreferencesManager.getInstance(this).getUserName();

        return new Chat(msg, username, 5, TimeUtil.getNowTimestamp());
    }

    class ChatAscendingComparator implements Comparator<Chat> {

        @Override
        public int compare(Chat chat1, Chat chat2) {
            return (chat1.getTimestamp() >= chat2.getTimestamp())? 1 : -1;
        }
    }
}
