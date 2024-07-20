package com.example.bananasplit.friends;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.ListItemHolder;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.util.ImageUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> {
    private List<Person> friends;
    private final ListItemHolder listener;
    private final List<Person> selectedFriends = new ArrayList<>();
    private boolean buttonVisibility = false;
    public FriendsAdapter(List<Person> friends, ListItemHolder listener) {
        this.friends = friends;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_list_item, parent, false);

        return new FriendViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FriendViewHolder viewHolder, final int position) {

        Person friend = friends.get(position);
        viewHolder.getFriendName().setText(friend.getName());
        viewHolder.itemView.setSelected(selectedFriends.contains(friend));
        viewHolder.editButton.setVisibility(this.buttonVisibility ? View.VISIBLE : View.INVISIBLE);
        viewHolder.deleteButton.setVisibility(this.buttonVisibility ? View.VISIBLE : View.INVISIBLE);
        ImageUtils.setProfileImage(viewHolder.itemView.findViewById(R.id.profilePicture), friend.getName());
//        viewHolder.itemView.setOnClickListener(v -> {
//            if (selectedFriends.contains(friend)) {
//                selectedFriends.remove(friend);
//            } else {
//                selectedFriends.add(friend);
//            }
//            notifyDataSetChanged();
//        });
    }

    public void toggleButtonVisibility() {
        this.buttonVisibility = !this.buttonVisibility;
        notifyDataSetChanged();
    }


    // Return the size of your dataset (invoked by the layout manager)
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

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public class FriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView friendName;
        final ImageButton editButton;
        final ImageButton deleteButton;

        public FriendViewHolder(View view) {
            super(view);
            friendName = view.findViewById(R.id.person_name);
            editButton = view.findViewById(R.id.friendEdit);
            deleteButton = view.findViewById(R.id.friendDelete);
            friendName.setOnClickListener(this);
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        public TextView getFriendName() {
            return friendName;
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                int id = view.getId();
                if (id == R.id.friendEdit) {
                    listener.onEdit(pos);
                } else if (id == R.id.friendDelete) {
                    listener.onDelete(pos);
                } else {
                    listener.onItemClicked(pos);
                }
            }
        }
    }


}
