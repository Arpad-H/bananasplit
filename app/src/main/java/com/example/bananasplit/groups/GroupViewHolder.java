package com.example.bananasplit.groups;

import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bananasplit.ListItemHolder;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Group;

/**
 * ViewHolder for displaying group information in a RecyclerView.
 * @author Arpad Horvath
 */
public class GroupViewHolder extends RecyclerView.ViewHolder {
    private  TextView groupNameTextView;
    private  Button editButton;
    private  Button deleteButton;
    private ImageView groupImage;

    /**
     * Constructor for GroupViewHolder.
     *
     * @param itemView   The view of the item.
     * @param itemHolder The item holder interface for handling item events.
     */
    public GroupViewHolder(@NonNull View itemView, ListItemHolder itemHolder) {
        super(itemView);

        extractUIelements(itemView);
        setUpListeners(itemView, itemHolder);

    }

    /**
     * Sets up the listeners for the item.
     *
     * @param itemView   The view of the item.
     * @param itemHolder The item holder interface for handling item events.
     */
    private void setUpListeners(@NonNull View itemView, ListItemHolder itemHolder) {
        itemView.setOnClickListener(v -> itemHolder.onItemClicked(getAdapterPosition()));
        editButton.setOnClickListener(v -> itemHolder.onEdit(getAdapterPosition()));
        deleteButton.setOnClickListener(v -> itemHolder.onDelete(getAdapterPosition()));
    }

    /**
     * Extracts the UI elements from the view.
     * @param itemView
     */
    private void extractUIelements(@NonNull View itemView) {
        groupNameTextView = itemView.findViewById(R.id.txt_group_name);
        editButton = itemView.findViewById(R.id.btn_edit_group);
        deleteButton = itemView.findViewById(R.id.btn_delete_group);
        groupImage = itemView.findViewById(R.id.group_cover_image_view);
    }
    /**
     * Binds the group data to the view components.
     *
     * @param group            The group data to bind.
     * @param areButtonsVisible A flag to toggle the visibility of edit and delete buttons.
     */
    public void bind(Group group, boolean areButtonsVisible) {
        groupNameTextView.setText(group.getName());
        editButton.setVisibility(areButtonsVisible ? View.VISIBLE : View.GONE);
        deleteButton.setVisibility(areButtonsVisible ? View.VISIBLE : View.GONE);
        groupImage.setImageURI(Uri.parse(group.getImageUri()));
    }
}
