package com.example.bananasplit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.dataModel.Group;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private List<Group> groups;
    private ListItemHolder listener;

    public GroupAdapter(List<Group> groups, ListItemHolder listener) {
        this.groups = groups;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_list_item, parent, false);
        return new GroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.nameTextView.setText(group.name);
        holder.dateTextView.setText(group.date);
        holder.durationTextView.setText(String.valueOf(group.duration));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
    public Group getGroupAt(int position) {
        return groups.get(position);
    }

    public void updateGroups(List<Group> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }
    class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView;
        TextView dateTextView;
        TextView durationTextView;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.editButton).setOnClickListener(this);
            itemView.findViewById(R.id.deleteButton).setOnClickListener(this);
        }

        public Group getGroupAt(int position) {
            return groups.get(position);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                int id = v.getId();
                if (id == R.id.editButton) {
                    listener.onEdit(position);
                } else if (id == R.id.deleteButton) {
                    listener.onDelete(position);
                } else {
                    listener.onItemClicked(position);
                }
            }
        }
    }
}

