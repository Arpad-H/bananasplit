package com.example.bananasplit.groups;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.ListItemHolder;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Group;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GroupsActivity extends BaseActivity implements ListItemHolder {
    private RecyclerView recyclerView;
    private GroupAdapter adapter;
    private GroupViewModel groupViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new GroupAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.getAllGroups().observe(this, groups -> {
            adapter.updateGroups(groups);
        });


        FloatingActionButton fab = findViewById(R.id.btn_add_group);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(GroupsActivity.this, CreateGroupActivity.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Get the new group data from the Intent and add it to the list
            String name = data.getStringExtra("name");
            String date = data.getStringExtra("date");
            int duration = data.getIntExtra("duration", 0);
            String imageUri = data.getStringExtra("imageUri");

            Group newGroup = new Group(name, date, duration, imageUri);

            groupViewModel.insert(newGroup, new ArrayList<>()); //TODO: selectedFriends, currently just empty list as placeholder
        }
    }
    @Override
    public void onItemClicked(int position) {
        Group groupToView = adapter.getGroupAt(position);
        Intent intent = new Intent(GroupsActivity.this, GroupDetailsActivity.class);
        intent.putExtra("group", (Parcelable) groupToView);
        startActivity(intent);
    }

    @Override
    public void onDelete(int position) {
        Group groupToDelete = adapter.getGroupAt(position);
        groupViewModel.delete(groupToDelete);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onEdit(int position) {
        Group groupToEdit = adapter.getGroupAt(position);
        Intent intent = new Intent(GroupsActivity.this, CreateGroupActivity.class);
        intent.putExtra("group", (Parcelable) groupToEdit);
        startActivity(intent);
    }
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_groups;
    }
}