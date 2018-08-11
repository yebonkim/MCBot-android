package com.example.mcbot.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.mcbot.R;
import com.example.mcbot.activity.RatingActivity;
import com.example.mcbot.adapter.ChatAdapter;
import com.example.mcbot.model.Chat;
import com.example.mcbot.model.ChatResult;
import com.example.mcbot.model.User;
import com.example.mcbot.util.SharedPreferencesManager;
import com.example.mcbot.util.TimeUtil;
import com.example.mcbot.util.retro.RetroCallback;
import com.example.mcbot.util.retro.RetroClient;
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
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kimyebon on 2018-08-11.
 */

public class ChattingFragment extends Fragment {
    Context context;
    RetroClient retroClient;


    @BindView(R.id.newChatET)
    EditText newChatET;
    @BindView(R.id.chatRV)
    RecyclerView chatRV;

    FirebaseDatabase database;
    DatabaseReference userDB, chatDB;

    ChatAdapter adapter;

    String roomName = "ChatRoom3";
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Chat> chats = new ArrayList<>();
    boolean isUsersGetDone, isChatsGetDone = false;

    public ChattingFragment() {

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

        return inflater.inflate(R.layout.fragment_chatting, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        context = getContext();
        retroClient = RetroClient.getInstance(context).createBaseApi(); //레트로핏 초기화


        initDatabase();
        getPreChats();
        getUsers();




    }

    protected void initDatabase() {
        database = FirebaseDatabase.getInstance();
        userDB = database.getReference().child("User");
        chatDB = database.getReference().child("Chat/"+roomName);
    }

    protected String getChatName(long timestamp) {
        return SharedPreferencesManager.getInstance(context).getUserName()+(String.valueOf(timestamp));
    }

    protected void getPreChats() {
        Query chatQuery = chatDB.limitToLast(100);
        chatQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats = new ArrayList<>();
<<<<<<< HEAD
                for (DataSnapshot child: dataSnapshot.getChildren())
                    chats.add(child.getValue(Chat.class));

                isChatsGetDone = true;
                sortChats();
                setRecyclerView();

                chatRV.scrollToPosition( adapter.getItemCount() -1 );
=======
                Chat temp;
                Boolean isLaugh = false;
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    temp = child.getValue(Chat.class);
                    chats.add(temp);
                    if(temp.getType() == 1)
                        isLaugh = true;
                }

                if(isLaugh) {
                    startActivity(new Intent(getActivity(), RatingActivity.class));
                } else {
                    isChatsGetDone = true;
                    sortChats();
                    setRecyclerView();
                }
>>>>>>> 1e6314a7e6ccf6d47ade152dc749b521a607b057
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

        chatRV.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ChatAdapter(getActivity(), chats, users);
        chatRV.setAdapter(adapter);
    }

    @OnClick(R.id.sendBtn)
    public void sendMsg() {
        Chat chat = collectData();

        postNewChat(chat); //to Firebase
        postChat(chat); //to Flast

//        chat_listview.setSelection(chat_adapter.getCount() - 1); //가장 아래쪽으로 스크롤다운


    }



    protected Chat collectData() {
        String msg = newChatET.getText().toString();
        String username = SharedPreferencesManager.getInstance(context).getUserName();

        return new Chat(msg, username, 5, TimeUtil.getNowTimestamp());
    }

    class ChatAscendingComparator implements Comparator<Chat> {

        @Override
        public int compare(Chat chat1, Chat chat2) {
            return (chat1.getTimestamp() >= chat2.getTimestamp())? 1 : -1;
        }
    }

    //디비(Firebase)로 채팅내용 전송
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


<<<<<<< HEAD
    //서버(Flask)로 채팅내용 전송
    public void postChat(Chat chat){
=======
        return new Chat(msg, username, 5, TimeUtil.getNowTimestamp(), 0);
    }
>>>>>>> 1e6314a7e6ccf6d47ade152dc749b521a607b057

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("message", chat.getMessage());
        parameters.put("timestamp", chat.getTimestamp());
        parameters.put("unreadCnt", 5);
        parameters.put("username", chat.getUsername() );


        retroClient.postChat(parameters, new RetroCallback() {

            @Override
            public void onError(Throwable t) {
                Log.e("Retrofit", "onError(), " + t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                ChatResult data = (ChatResult) receivedData;
                Log.e("Retrofit", "Retrofit Response: " + data.isResult());
            }

            @Override
            public void onFailure(int code) {
                Log.e("Retrofit", "onFailure(), " + String.valueOf(code));
            }
        });
    }

}
