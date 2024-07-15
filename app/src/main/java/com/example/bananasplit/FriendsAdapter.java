package com.example.bananasplit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.dataModel.Person;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private List<Person> friends;
    private ListItem items;

    public FriendsAdapter(List<Person> friends, ListItem items) {
        this.friends = friends;
        this.items = items;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_list_item, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getFriendName().setText(friends.get(position).name);
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

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView friendName;

        public ViewHolder(View view) {
            super(view);
            friendName = view.findViewById(R.id.friendNameTV);
            // Define click listener for the ViewHolder's View
            view.findViewById(R.id.friendDelete).setOnClickListener(this);
            view.findViewById(R.id.friendEdit).setOnClickListener(this);
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
                    items.onEdit(pos);
                } else if (id == R.id.friendDelete) {
                    items.onDelete(pos);
                } else {
                    items.onItemClicked(pos);
                }
            }
        }
    }

}
