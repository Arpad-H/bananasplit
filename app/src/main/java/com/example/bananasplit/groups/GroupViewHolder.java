package com.example.bananasplit.groups;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bananasplit.ListItemHolder;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Group;

public class GroupViewHolder extends RecyclerView.ViewHolder {
    private final TextView groupNameTextView;
    private final Button editButton;
    private final Button deleteButton;

    public GroupViewHolder(@NonNull View itemView, ListItemHolder itemHolder) {
        super(itemView);
        groupNameTextView = itemView.findViewById(R.id.txt_group_name);
        editButton = itemView.findViewById(R.id.btn_edit_group);
        deleteButton = itemView.findViewById(R.id.btn_delete_group);

        itemView.setOnClickListener(v -> itemHolder.onItemClicked(getAdapterPosition()));
        editButton.setOnClickListener(v -> itemHolder.onEdit(getAdapterPosition()));
        deleteButton.setOnClickListener(v -> itemHolder.onDelete(getAdapterPosition()));
    }

    public void bind(Group group, boolean areButtonsVisible) {
        groupNameTextView.setText(group.getName());
        editButton.setVisibility(areButtonsVisible ? View.VISIBLE : View.GONE);
        deleteButton.setVisibility(areButtonsVisible ? View.VISIBLE : View.GONE);
    }
}
