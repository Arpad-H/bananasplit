package com.example.bananasplit;
/**
 * Interface for handling actions related to list items, such as clicks, deletions, and edits.
 * @author Dennis Brockmeyer. Arpad Horvath
 */
public interface ListItemHolder {
    /**
     * Called when an item is clicked.
     *
     * @param position The position of the clicked item.
     */
    void onItemClicked(int position);
    /**
     * Called when an item is deleted.
     *
     * @param position The position of the item to be deleted.
     */
    void onDelete(int position);
    /**
     * Called when an item is edited.
     *
     * @param position The position of the item to be edited.
     */
    void onEdit(int position);

}
