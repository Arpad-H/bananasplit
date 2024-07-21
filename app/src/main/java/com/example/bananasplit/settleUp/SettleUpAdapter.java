package com.example.bananasplit.settleUp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.ListItemHolder;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.friends.FriendsAdapter;

import java.util.List;

public class SettleUpAdapter extends RecyclerView.Adapter<SettleUpAdapter.SettleUpViewHolder> {
    private List<Person> friends;
    private final ListItemHolder listener;

    public SettleUpAdapter(List<Person> friends, ListItemHolder listener) {
        this.friends = friends;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SettleUpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settle_up_list_item, parent, false);

        return new SettleUpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettleUpViewHolder viewHolder, int position) {
        Person friend = friends.get(position);
        viewHolder.getFriendName().setText(friend.getName());
        viewHolder.getFriendMail().setText(friend.getEmail());

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public Person getPersonAt(int position) {
        return friends.get(position);
    }


    public void updateFriends(List<Person> friends) {
        this.friends = friends;
        notifyDataSetChanged();
    }

    public class SettleUpViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView friendName;
        private final TextView amount;
        private final TextView friendMail;
        private final TextView oweOwed;

        public SettleUpViewHolder(View view) {
            super(view);
            friendName = view.findViewById(R.id.settleFriendName);
            amount = view.findViewById(R.id.settleAmount);
            friendMail = view.findViewById(R.id.settleUpMail);
            oweOwed = view.findViewById(R.id.settleUpOweOwed);
            // Define click listener for the ViewHolder's View
//            friendName.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        public TextView getFriendName() {
            return friendName;
        }

        public TextView getAmount() {
            return amount;
        }

        public TextView getFriendMail() {
            return friendMail;
        }

        public TextView getOweOwed() {
            return oweOwed;
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                listener.onItemClicked(pos);
            }
        }

    }
}