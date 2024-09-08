package com.example.bananasplit.friends;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.ListItemHolder;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.databinding.ActivityFriendsBinding;
import com.example.bananasplit.databinding.ActivityFriendsDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Activity to display and edit Friends
 *
 * @author Dennis Brockmeyer
 */
public class FriendsActivity extends BaseActivity implements ListItemHolder {
    private FriendsAdapter adapter;
    private FriendViewModel friendViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityFriendsBinding binding = createBinding();
        setupRecyclerView(binding);
        setupViewModel();
        setupFloatingButton(binding);
        setupToggleButton(binding);
    }

    /**
     * sets up the ToggleButton used to hide/display the edit and delete buttons
     * @param binding the view binding for this class
     */
    private void setupToggleButton(ActivityFriendsBinding binding) {
        ImageButton toggleButton = binding.btnToggleFriendsEditState;
        toggleButton.setOnClickListener(v -> {
            adapter.toggleButtonVisibility();
        });
    }

    /**
     * sets up the FloatingActionButton used to create a Friend
     * @param binding the view binding for this class
     */
    private void setupFloatingButton(ActivityFriendsBinding binding) {
        FloatingActionButton addFriend = binding.btnAddFriend;
        addFriend.setOnClickListener(v -> {
            Intent intent = new Intent(FriendsActivity.this, CreateFriendActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Initializes the ViewModel
     */
    private void setupViewModel() {
        friendViewmodel = new ViewModelProvider(this).get(FriendViewModel.class);
        friendViewmodel.getAllFriends().observe(this, friends -> {
            adapter.updateFriends(friends);
        });
    }

    /**
     * sets up the RecyclerView used to display all Friends
     * @param binding the view binding for this class
     */
    private void setupRecyclerView(ActivityFriendsBinding binding) {
        RecyclerView recyclerView = binding.recyclerFriends;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FriendsAdapter(new ArrayList<>(),  this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Initializes the view binding for this activity.
     * @return the view binding
     */
    private @NonNull ActivityFriendsBinding createBinding() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_friends, getContentContainer(), false);
        getContentContainer().addView(contentView);
        return ActivityFriendsBinding.bind(contentView);
    }

    /**
     * Returns the layout resource ID for this activity. Used by the BaseActivity.
     *
     * @return The layout resource ID.
     */
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_friends;
    }

    /**
     * Shows details for the clicked friend
     * ItemListHolder interface implementation.
     * @param position The position of the clicked item.
     */
    @Override
    public void onItemClicked(int position) {
        Person personDetails = adapter.getPersonAt(position);
        Intent intent = new Intent(FriendsActivity.this, FriendsDetailActivity.class);
        intent.putExtra("friend", (Parcelable) personDetails);
        startActivity(intent);
    }

    /**
     * Deletes the clicked friend
     * ItemListHolder interface implementation.
     * @param position The position of the clicked item.
     */
    @Override
    public void onDelete(int position) {
        Person personDelete = adapter.getPersonAt(position);
        friendViewmodel.delete(personDelete);
        adapter.notifyItemRemoved(position);
    }

    /**
     * Edits the clicked friend
     * ItemListHolder interface implementation.
     * @param position The position of the clicked item.
     */
    @Override
    public void onEdit(int position) {
        Person personDetails = adapter.getPersonAt(position);
        Intent intent = new Intent(FriendsActivity.this, CreateFriendActivity.class);
        intent.putExtra("friend", (Parcelable) personDetails);
        startActivity(intent);

    }
}