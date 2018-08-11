package com.example.mcbot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mcbot.R;
import com.example.mcbot.adapter.recyclerview.RecyclerViewHolder;
import com.example.mcbot.model.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yebonkim on 2018-08-12.
 */
public class UserSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_ITEM = 1;

    ArrayList<User> originalUser, selectedUser;

    public UserSelectAdapter(ArrayList<User> originalUser) {
        this.originalUser = originalUser;
        this.selectedUser = new ArrayList<User>();
        setHasStableIds(true);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_user_list_select, parent, false);
                return new UserView(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof UserView) {
            ((RecyclerViewHolder) holder).setData(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return originalUser.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ITEM;
    }

    public ArrayList<User> getTaker() {
        return selectedUser;
    }


    protected void selectUser(User user) {
        for (User e : selectedUser) {
            if (e.getUsername().equals(user.getUsername())) {
                return;
            }
        }

        selectedUser.add(user);
    }

    protected void removeUser(User user) {
        for (int i = 0; i < selectedUser.size(); i++) {
            if (selectedUser.get(i).getUsername().equals(user.getUsername())) {
                selectedUser.remove(i);
                return;
            }
        }
    }

    protected boolean isUserSelected(String username) {
        for (User u : selectedUser) {
            if (u.getUsername().equals(username))
                return true;
        }

        return false;
    }

    public class UserView extends RecyclerView.ViewHolder implements RecyclerViewHolder {

        @BindView(R.id.userIV)
        ImageView userIV;
        @BindView(R.id.userNameTV)
        TextView userNameTV;

        View view;
        Context context;

        public UserView(View view) {
            super(view);
            this.view = view;
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        @Override
        public void setData(int position) {
            final User user = originalUser.get(position);
            boolean isChecked = isUserSelected(user.getUsername());

            userNameTV.setText(user.getUsername());

            setCheckedImage(isChecked);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isChecked = isUserSelected(user.getUsername());
                    if (isChecked) {
                        removeUser(user);
                    } else {
                        selectUser(user);
                        Log.d("Yebon", "click call");
                    }

                    setCheckedImage(!isChecked);
                }
            });
        }

        private void setCheckedImage(boolean isChecked) {
            if (isChecked) {
                userIV.setImageDrawable(context.getDrawable(R.drawable.ic_done_btn));
            } else {
                userIV.setImageDrawable(context.getDrawable(R.drawable.ic_not_done_btn));
            }
        }
    }
}