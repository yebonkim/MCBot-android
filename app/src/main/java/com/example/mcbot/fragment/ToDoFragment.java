package com.example.mcbot.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mcbot.R;
import com.example.mcbot.activity.RatingActivity;
import com.example.mcbot.adapter.ChatAdapter;
import com.example.mcbot.adapter.ToDoAdapter;
import com.example.mcbot.model.Chat;
import com.example.mcbot.model.ToDo;
import com.example.mcbot.model.User;
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

/**
 * Created by Kimyebon on 2018-08-11.
 */

public class ToDoFragment extends Fragment {
    Context context;
    @BindView(R.id.toDoRV)
    RecyclerView toDoRV;

    FirebaseDatabase database;
    DatabaseReference toDoDB, userDB;

    ArrayList<User> users;
    ArrayList<ToDo> toDos;

    boolean isToDosGetDone = false;
    boolean isUsersGetDone = false;

    ToDoAdapter adapter;

    public ToDoFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ToDoFragment", "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_todo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        context = getContext();
        initDatabase();
        getToDos();
        getUsers();
    }

    protected void getToDos() {
        Query toDoQuery = toDoDB.limitToLast(100);
        toDoQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                toDos = new ArrayList<>();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    toDos.add(child.getValue(ToDo.class));
                }

                isToDosGetDone = true;
                sortToDos();
                setRecyclerView();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        if(!(isToDosGetDone && isUsersGetDone))
            return;

        toDoRV.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ToDoAdapter(getActivity(), toDos, users);
        toDoRV.setAdapter(adapter);
    }

    protected void initDatabase() {
        database = FirebaseDatabase.getInstance();
        userDB = database.getReference().child("User");
        toDoDB = database.getReference().child("ToDo");
    }


    protected void sortToDos() {
        Collections.sort(toDos, new ToDoAscendingComparator());
    }

    @OnClick(R.id.addBtn)
    public void goToNewToDoActivity() {

    }

    class ToDoAscendingComparator implements Comparator<ToDo> {

        @Override
        public int compare(ToDo todo1, ToDo todo2) {
            return (todo1.getDeadline() >= todo2.getDeadline())? 1 : -1;
        }
    }
}
