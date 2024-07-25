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

/**
 * Adapter for displaying a list of friends with checkboxes to select them.
 * @author Arpad Horvath
 */
public class SelectFriendsAdapter extends RecyclerView.Adapter<SelectFriendsAdapter.ViewHolder> {

    private List<Person> friends;
    private final List<Person> selectedFriends = new ArrayList<>();

    /**
     * Constructor for SelectFriendsAdapter.
     *
     * @param friends List of friends to display.
     */
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
        viewHolder.bind(friend);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    /**
     * Updates the list of friends displayed by the adapter.
     *
     * @param friends The new list of friends.
     */
    public void updateFriends(List<Person> friends) {
        this.friends = friends;
        notifyDataSetChanged(); //small lists so hopefully fine
    }

    /**
     * Returns the friend at the specified position.
     *
     * @param position The position of the friend.
     * @return The friend at the specified position.
     */
    public Person getPersonAt(int position) {
        return friends.get(position);
    }

    /**
     * Returns the list of selected friends.
     *
     * @return List of selected friends.
     */
    public List<Person> getSelectedFriends() {
        return new ArrayList<>(selectedFriends);
    }

    /**
     * ViewHolder for displaying a friend with a checkbox.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView friendName;
        private final CheckBox friendCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.person_name);
            friendCheckBox = itemView.findViewById(R.id.friendCheckBox);
        }

        /**
         * Binds the friend data to the view.
         *
         * @param friend The friend to bind.
         */
        public void bind(Person friend) {
            friendName.setText(friend.getName());
            friendCheckBox.setChecked(selectedFriends.contains(friend));

            itemView.setOnClickListener(v -> toggleSelection(friend));
            friendCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> toggleSelection(friend, isChecked));
        }

        /**
         * Toggles the selection state of a friend.
         *
         * @param friend The friend to toggle.
         */
        private void toggleSelection(Person friend) {
            boolean isSelected = selectedFriends.contains(friend);
            if (isSelected) {
                selectedFriends.remove(friend);
            } else {
                selectedFriends.add(friend);
            }
            notifyItemChanged(getAdapterPosition());
        }

        /**
         * Toggles the selection state of a friend based on the checkbox state.
         *
         * @param friend The friend to toggle.
         * @param isChecked The new checked state of the checkbox.
         */
        private void toggleSelection(Person friend, boolean isChecked) {
            if (isChecked && !selectedFriends.contains(friend)) {
                selectedFriends.add(friend);
            } else if (!isChecked) {
                selectedFriends.remove(friend);
            }
        }
    }
}
