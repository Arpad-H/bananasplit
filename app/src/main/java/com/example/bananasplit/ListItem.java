package com.example.bananasplit;

public interface ListItem {
    void onItemClicked(int position);
    void onDelete(int position);
    void onEdit(int position);

}
