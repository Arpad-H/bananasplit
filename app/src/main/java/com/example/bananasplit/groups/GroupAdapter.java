package com.example.bananasplit.groups;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.ListItemHolder;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Group;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private List<Group> groups;
    private final ListItemHolder listener;
    private boolean areButtonsVisible = false;
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
        holder.nameTextView.setText(group.getName());
//        holder.dateTextView.setText(group.getDate());
//        holder.durationTextView.setText(String.valueOf(group.getDuration()));
        if (group.getImageUri() != null) {
            holder.groupCoverImageView.setImageURI(Uri.parse(group.getImageUri()));
        }
        else {
            holder.groupCoverImageView.setImageResource(R.drawable.logo);
        }
        holder.editButton.setVisibility(areButtonsVisible ? View.VISIBLE : View.GONE);
        holder.deleteButton.setVisibility(areButtonsVisible ? View.VISIBLE : View.GONE);

        holder.editButton.setOnClickListener(v -> listener.onEdit(position));
        holder.deleteButton.setOnClickListener(v -> listener.onDelete(position));
    }
    public void toggleButtonsVisibility() {
        areButtonsVisible = !areButtonsVisible;
        notifyDataSetChanged();
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
        Button editButton ;
        Button deleteButton ;
        ImageView groupCoverImageView;
        private final LinearLayout linearLayout;
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
//            dateTextView = itemView.findViewById(R.id.dateTextView);
//            durationTextView = itemView.findViewById(R.id.durationTextView);
            groupCoverImageView = itemView.findViewById(R.id.group_cover_image_view);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            linearLayout = itemView.findViewById(R.id.buttonHolderLayout);

            itemView.setOnClickListener(this);
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
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
        private void toggleButtons(boolean isVisible) {
            linearLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
        }
    }
}

