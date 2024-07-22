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

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {
    private List<Group> groups;
    private final ListItemHolder itemHolder;
    private boolean areButtonsVisible = false;

    public GroupAdapter(List<Group> groups, ListItemHolder itemHolder) {
        this.groups = groups;
        this.itemHolder = itemHolder;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_list_item, parent, false);
        return new GroupViewHolder(view, itemHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.bind(groups.get(position), areButtonsVisible);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public void updateGroups(List<Group> newGroups) {
        this.groups = newGroups;
        notifyDataSetChanged();
    }

    public Group getGroupAt(int position) {
        return groups.get(position);
    }

    public void toggleButtonsVisibility() {
        areButtonsVisible = !areButtonsVisible;
        notifyDataSetChanged();
    }
}
