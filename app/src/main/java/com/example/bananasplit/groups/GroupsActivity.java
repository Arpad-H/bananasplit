package com.example.bananasplit.groups;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.ListItemHolder;
import com.example.bananasplit.R;
import com.example.bananasplit.databinding.ActivityFriendsBinding;
import com.example.bananasplit.databinding.ActivityGroupsBinding;
import com.example.bananasplit.dataModel.Group;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Activity to display and manage user groups.
 *
 * @Author Arpad Horvath
 */
public class GroupsActivity extends BaseActivity implements ListItemHolder {
    private static final int REQUEST_CREATE_GROUP = 1;
    private static final String TAG = "GroupsActivity";
    private ActivityGroupsBinding binding;
    private GroupAdapter adapter;
    private GroupViewModel groupViewModel;

    /**
     * Called when the activity is first created.
     * Initializes the activity and sets up the view components.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createBinding();
        setupRecyclerView();
        setupViewModel();
        setupFloatingActionButton();
        setupToggleButtons();
    }

    /**
     * Initializes the view binding for this activity.
     */
    private void createBinding() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_groups, getContentContainer(), false);
        getContentContainer().addView(contentView);
        binding = ActivityGroupsBinding.bind(contentView);
    }

    /**
     * Sets up the RecyclerView with a LinearLayoutManager and attaches the adapter.
     */
    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GroupAdapter(new ArrayList<>(), this);
        binding.recyclerView.setAdapter(adapter);
    }

    /**
     * Initializes the ViewModel and sets up the observer for group data.
     */
    private void setupViewModel() {
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.getAllGroups().observe(this, groups -> adapter.updateGroups(groups));
    }

    /**
     * Sets up the Floating Action Button for adding a new group.
     */
    private void setupFloatingActionButton() {
        binding.btnAddGroup.setOnClickListener(v -> startActivity(new Intent(GroupsActivity.this, CreateGroupActivity.class)));
    }

    /**
     * Sets up the toggle button to switch between edit states in the group adapter.
     */
    private void setupToggleButtons() {
        binding.btnToggleGrpEditState.setOnClickListener(v -> adapter.toggleButtonsVisibility());
    }

    /**
     * Handles the result from activities started for a result.
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult(), allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param data        An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CREATE_GROUP && resultCode == RESULT_OK && data != null) {
            handleCreateGroupResult(data);
        }
    }

    /**
     * Handles the result from the CreateGroupActivity.
     *
     * @param data The intent data returned from the CreateGroupActivity.
     */
    private void handleCreateGroupResult(@NonNull Intent data) {
        String name = data.getStringExtra("name");
        String imageUri = data.getStringExtra("imageUri");

        if (name != null && imageUri != null) {
            Group newGroup = new Group.GroupBuilder()
                    .name(name)
                    .imageURI(imageUri)
                    .build();

            groupViewModel.insert(newGroup, new ArrayList<>());
            Log.d(TAG, "New group created: " + name);
        } else {
            Log.e(TAG, "Error creating group: name or imageUri is null");
            Toast.makeText(this, "Failed to create group", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Handles item click events from the adapter.
     * ItemListHolder interface implementation.
     *
     * @param position The position of the item clicked.
     */
    @Override
    public void onItemClicked(int position) {
        Group groupToView = adapter.getGroupAt(position);
        Intent intent = new Intent(GroupsActivity.this, GroupDetailsActivity.class);
        intent.putExtra("group", (Parcelable) groupToView);
        startActivity(intent);
    }

    /**
     * Handles item delete events from the adapter.
     * ItemListHolder interface implementation.
     *
     * @param position The position of the item to be deleted.
     */
    @Override
    public void onDelete(int position) {
        Group groupToDelete = adapter.getGroupAt(position);
        groupViewModel.delete(groupToDelete);
    }

    /**
     * Handles item edit events from the adapter.
     * ItemListHolder interface implementation.
     *
     * @param position The position of the item to be edited.
     */
    @Override
    public void onEdit(int position) {
        Group groupToEdit = adapter.getGroupAt(position);
        Intent intent = new Intent(GroupsActivity.this, CreateGroupActivity.class);
        intent.putExtra("group", (Parcelable) groupToEdit);
        startActivity(intent);
    }

    /**
     * Returns the layout resource ID for this activity. Used by the BaseActivity.
     *
     * @return The layout resource ID.
     */
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_groups;
    }
}
