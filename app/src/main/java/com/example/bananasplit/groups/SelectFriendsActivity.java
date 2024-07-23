package com.example.bananasplit.groups;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.ListItemHolder;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.databinding.ActivityFriendsBinding;
import com.example.bananasplit.databinding.ActivityGroupsBinding;
import com.example.bananasplit.databinding.ActivitySelectFriendsBinding;
import com.example.bananasplit.friends.FriendViewModel;
import com.example.bananasplit.friends.FriendsAdapter;

import java.util.ArrayList;
import java.util.List;
/**
 * Activity for selecting friends to add to a group or for other purposes.
 * @author Arpad Horvath
 */
public class SelectFriendsActivity extends BaseActivity {
    private SelectFriendsAdapter friendsAdapter;
    private FriendViewModel friendViewModel;
    private final List<Person> selectedFriends = new ArrayList<>();
    private ActivitySelectFriendsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        setupRecyclerView();
        setupViewModel();
        setupDoneButton();
    }
    /**
     * Sets up the view binding for this activity.
     */
    private void initBinding() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_select_friends, getContentContainer(), false);
        getContentContainer().addView(contentView);

        binding = ActivitySelectFriendsBinding.bind(contentView);
    }
    /**
     * Configures the RecyclerView with an adapter and layout manager.
     */
    private void setupRecyclerView() {
        RecyclerView recyclerView = binding.recyclerViewSelectFriends;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        friendsAdapter = new SelectFriendsAdapter(new ArrayList<>());
        recyclerView.setAdapter(friendsAdapter);
    }
    /**
     * Initializes the ViewModel and observes the list of friends.
     */
    private void setupViewModel() {
        friendViewModel = new ViewModelProvider(this).get(FriendViewModel.class);
        friendViewModel.getAllFriends().observe(this, this::updateFriendsList);
    }
    /**
     * Updates the friends list in the adapter.
     *
     * @param friends The list of friends to update in the adapter.
     */
    private void updateFriendsList(List<Person> friends) {
        friendsAdapter.updateFriends(friends);
    }
    /**
     * Configures the Done button click listener.
     */
    private void setupDoneButton() {
        Button doneButton = binding.btnSelectFriends;
        doneButton.setOnClickListener(v -> {
            returnSelectedFriends();
        });
    }
    /**
     * Returns the selected friends to the calling activity.
     */
    private void returnSelectedFriends() {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("selectedFriends", new ArrayList<>(friendsAdapter.getSelectedFriends()));
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_select_friends;
    }
}
