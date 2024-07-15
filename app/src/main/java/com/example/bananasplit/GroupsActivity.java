package com.example.bananasplit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
            // Open the new activity or create a group
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

            Group newGroup = new Group(name, date, duration);
            groupViewModel.insert(newGroup);
        }
    }
    @Override
    public void onItemClicked(int position) {
        Log.d("GroupsActivity", "onItemClicked: " + position);
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
        intent.putExtra(CreateGroupActivity.EXTRA_GROUP_ID, groupToEdit.getGroupID());
        intent.putExtra(CreateGroupActivity.EXTRA_GROUP_NAME, groupToEdit.getName());
        intent.putExtra(CreateGroupActivity.EXTRA_GROUP_DATE, groupToEdit.getDate());
        intent.putExtra(CreateGroupActivity.EXTRA_GROUP_DURATION, groupToEdit.getDuration());
        startActivity(intent);
    }
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_groups;
    }
}