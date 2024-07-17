package com.example.bananasplit.groups;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;

import java.util.ArrayList;
import java.util.List;

public class SelectFriendsAdapter extends RecyclerView.Adapter<SelectFriendsAdapter.ViewHolder> {
    private List<Person> friends;
    private List<Person> selectedFriends = new ArrayList<>();

    public SelectFriendsAdapter(List<Person> friends) {
        this.friends = friends;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_select_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Person friend = friends.get(position);
        viewHolder.getFriendName().setText(friend.getName());
        viewHolder.getFriendCheckBox().setChecked(selectedFriends.contains(friend));

        viewHolder.itemView.setOnClickListener(v -> {
            if (selectedFriends.contains(friend)) {
                selectedFriends.remove(friend);
            } else {
                selectedFriends.add(friend);
            }
            notifyDataSetChanged();
        });

        viewHolder.getFriendCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!selectedFriends.contains(friend)) {
                    selectedFriends.add(friend);
                }
            } else {
                selectedFriends.remove(friend);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void updateFriends(List<Person> friends) {
        this.friends = friends;
        notifyDataSetChanged();
    }

    public Person getPersonAt(int position) {
        return friends.get(position);
    }

    public List<Person> getSelectedFriends() {
        return selectedFriends;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView friendName;
        private final CheckBox friendCheckBox;

        public ViewHolder(View view) {
            super(view);
            friendName = view.findViewById(R.id.friend_name);
            friendCheckBox = view.findViewById(R.id.friendCheckBox);
        }

        public TextView getFriendName() {
            return friendName;
        }

        public CheckBox getFriendCheckBox() {
            return friendCheckBox;
        }
    }
}
