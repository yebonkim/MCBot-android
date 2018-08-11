package com.example.mcbot.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mcbot.R;
import com.example.mcbot.adapter.recyclerview.RecyclerViewHolder;
import com.example.mcbot.model.ToDo;
import com.example.mcbot.model.ToDoUser;
import com.example.mcbot.model.User;
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

public class ToDoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_TO_DO = 1;

    Context context;
    ArrayList<ToDo> toDos;
    ArrayList<User> users;
    String username;

    // database 접근용
    FirebaseDatabase database;
    DatabaseReference toDoDB, toDoUserDB;


    protected void initDatabase() {
        database = FirebaseDatabase.getInstance();
        toDoDB = database.getReference().child("ToDo");
        toDoUserDB = database.getReference().child("ToDoUser");
    }

    public ToDoAdapter(Context context, ArrayList<ToDo> toDos, ArrayList<User> users) {
        this.context = context;
        this.toDos = toDos;
        this.users = users;

        this.username = SharedPreferencesManager.getInstance(context).getUserName();
        setHasStableIds(true);
        initDatabase();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_TO_DO;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case VIEW_TYPE_TO_DO:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_to_do, parent, false);
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
        return toDos.get(position).getDeadline();
    };

    @Override
    public int getItemCount() {
        return toDos.size();
    }

    protected User getUserByUsername(String username) {
        for(User user : users) {
            if(user.getUsername().equals(username))
                return user;
        }

        return null;
    }

    public class ToDoView extends RecyclerView.ViewHolder implements RecyclerViewHolder{
        @BindView(R.id.dateTV)
        TextView dateTV;
        @BindView(R.id.toDoTV)
        TextView toDoTV;
        @BindView(R.id.isDoneBtn)
        Button isDoneBtn;
        @BindView(R.id.deleteBtn)
        Button deleteBtn;
        @BindView(R.id.takerRV)
        RecyclerView takerRV;

        View view;
        Context context;

        ArrayList<User> taker;
        ToDo data;

        public ToDoView(View view) {
            super(view);
            this.view = view;
            context =view.getContext();
            ButterKnife.bind(this, view);
        }

        @Override
        public void setData(int position) {
            data = toDos.get(position);
            toDoTV.setText(data.getToDo());
            if(data.isDone()) {
                isDoneBtn.setBackground(context.getDrawable(R.drawable.ic_done_btn));
                isDoneBtn.setClickable(false);
            } else {
                isDoneBtn.setBackground(context.getDrawable(R.drawable.ic_not_done_btn));
                isDoneBtn.setClickable(true);
            }

            isDoneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.setDone(true);
                    toDoDB.child(data.getToDoName()).setValue(data);
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toDoDB.child(data.getToDoName()).removeValue();
                }
            });

            dateTV.setText(TimeFormat.timestampToString(data.getDeadline()));
            getTakerAndSetRV(data.getToDoName());
        }

        protected void setRecyclerView(ArrayList<User> taker) {
            MemberAdapter memberAdapter = new MemberAdapter(context, taker);
            takerRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            takerRV.setAdapter(memberAdapter);
        }

        protected void getTakerAndSetRV(final String toDoName) {
            Query toDoUserQuery = toDoUserDB.limitToLast(100);
            toDoUserQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    taker = new ArrayList<>();
                    ToDoUser toDoUser;
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        toDoUser = child.getValue(ToDoUser.class);

                        if(toDoUser.getToDoName().equals(toDoName))
                            taker.add(getUserByUsername(toDoUser.getUsername()));
                    }

                    setRecyclerView(taker);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}