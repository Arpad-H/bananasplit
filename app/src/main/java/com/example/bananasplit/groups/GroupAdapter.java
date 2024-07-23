package com.example.bananasplit.groups;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.ListItemHolder;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Group;

import java.util.List;


/**
 * Adapter for displaying groups in a RecyclerView.
 *
 * @author Arpad Horvath
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {
    private List<Group> groups;
    private final ListItemHolder itemHolder;
    private boolean areButtonsVisible = false;

    /**
     * Constructor for the GroupAdapter
     *
     * @param itemHolder The item holder interface for handling item events
     * @param groups     The list of groups to display
     */
    public GroupAdapter(List<Group> groups, ListItemHolder itemHolder) {
        this.groups = groups;
        this.itemHolder = itemHolder;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position
     * @param viewType The view type of the new View
     * @return A new GroupViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_list_item, parent, false);
        return new GroupViewHolder(view, itemHolder);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapters data set
     */
    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.bind(groups.get(position), areButtonsVisible);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return groups.size();
    }

    /**
     * Updates the adapter's data set with a new list of groups.
     *
     * @param newGroups The new list of groups to display.
     */
    public void updateGroups(List<Group> newGroups) {
        this.groups = newGroups;
        notifyDataSetChanged();
    }

    /**
     * Returns the group at the specified position in the data set.
     *
     * @param position The position of the group to return.
     * @return The group at the specified position.
     */
    public Group getGroupAt(int position) {
        return groups.get(position);
    }
    /**
     * Toggles the visibility of buttons in each item view and refreshes the list.
     */
    public void toggleButtonsVisibility() {
        areButtonsVisible = !areButtonsVisible;
        notifyDataSetChanged();
    }
}
