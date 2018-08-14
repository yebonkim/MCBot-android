package com.example.mcbot.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mcbot.R;
import com.example.mcbot.adapter.UserSelectAdapter;
import com.example.mcbot.model.Chat;
import com.example.mcbot.model.ToDo;
import com.example.mcbot.model.ToDoUser;
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
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewToDoActivity extends AppCompatActivity {
    @BindView(R.id.toDoETV)
    EditText toDoETV;
    @BindView(R.id.dateBtn)
    Button dateBtn;
    @BindView(R.id.userRV)
    RecyclerView userRV;

    FirebaseDatabase database;
    DatabaseReference toDoDB, userDB, toDoUserDB, chatDB;

    UserSelectAdapter adapter;

    ArrayList<User> users;
    Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_to_do);
        ButterKnife.bind(this);

        selectedDate = Calendar.getInstance();
        initDatabase();
        getUsers();
    }

    protected void initDatabase() {
        database = FirebaseDatabase.getInstance();
        userDB = database.getReference().child("User");
        toDoDB = database.getReference().child("ToDo");
        toDoUserDB = database.getReference().child("ToDoUser");
        chatDB = database.getReference().child("Chat/ChatRoom6");
    }

    protected void getUsers() {
        Query userQuery = userDB.limitToLast(100);
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users = new ArrayList<>();
                User temp;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    temp = child.getValue(User.class);
                    if(temp.getBot()==0)
                        users.add(temp);
                }

                setRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void setRecyclerView() {
        adapter = new UserSelectAdapter(users);
        userRV.setLayoutManager(new LinearLayoutManager(this));
        userRV.setAdapter(adapter);
    }

    @OnClick(R.id.confirmBtn)
    public void addNewToDo() {
        postNewToDo(collectData());
        postNewChat(collectChatData());
    }

    protected Chat collectChatData() {
        String msg = "새로운 업무 ["+toDoETV.getText().toString()+"]가 등록되었습니다";
        String username = "MC봇";

        return new Chat(msg, username, 5, TimeUtil.getNowTimestamp(), 0);
    }


    protected String getChatName(long timestamp) {
        return SharedPreferencesManager.getInstance(this).getUserName()+(String.valueOf(timestamp));
    }

    protected void postNewChat(Chat chat) {
        String chatName = getChatName(chat.getTimestamp());
        chatDB.child(chatName).setValue(chat);
        chatDB.child(chatName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
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

    protected String getToDoName(long timestamp) {
        return SharedPreferencesManager.getInstance(this).getUserName()+(String.valueOf(timestamp));
    }

    protected void postNewToDoUser(String toDoName) {
        ArrayList<User> taker = adapter.getTaker();


        for(User u : taker) {
            ToDoUser toDoUser = new ToDoUser(toDoName, u.getUsername());
            toDoUserDB.child(getKeyName()).setValue(toDoUser);
        }
    }

    protected String getKeyName() {
        return SharedPreferencesManager.getInstance(this).getUserName()+ TimeUtil.getNowTimestamp();
    }

    protected ToDo collectData() {
        ToDo newToDo = new ToDo();

        newToDo.setToDo(toDoETV.getText().toString());
        newToDo.setDeadline(selectedDate.getTimeInMillis());
        newToDo.setIsDone(false);
        newToDo.setIsWarned(false);
        newToDo.setToDoName(getToDoName(selectedDate.getTimeInMillis()));

        return newToDo;
    }

    protected void postNewToDo(ToDo newToDo) {
        final String toDoName = newToDo.getToDoName();
        postNewToDoUser(toDoName);
        toDoDB.child(toDoName).setValue(newToDo);
        toDoDB.child(toDoName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Toast.makeText(NewToDoActivity.this, getString(R.string.newToDoRegistered), Toast.LENGTH_SHORT).show();
                goToMain();
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

    protected void goToMain() {
        finish();
    }

    @OnClick(R.id.dateBtn)
    public void showDatePickerDialog() {
        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                selectedDate.set(year, monthOfYear, dayOfMonth);
                setDate(monthOfYear, dayOfMonth);
            }
        }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));

        dateDialog.show();
    }

    protected void setDate(int month, int day) {
        dateBtn.setText(month+"월 "+day+"일");
    }
}
