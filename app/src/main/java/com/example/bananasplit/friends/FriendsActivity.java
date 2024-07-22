package com.example.bananasplit.friends;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.ListItemHolder;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.databinding.ActivityCreateGroupBinding;
import com.example.bananasplit.databinding.ActivityFriendsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FriendsActivity extends BaseActivity implements ListItemHolder {
    private FriendsAdapter adapter;
    private FriendViewModel friendViewmodel;
private ActivityFriendsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentViewForActivity(getLayoutResourceId());

        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_friends, getContentContainer(), false);
        getContentContainer().addView(contentView);

        binding = ActivityFriendsBinding.bind(contentView);



        RecyclerView recyclerView = findViewById(R.id.recyclerFriends);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FriendsAdapter(new ArrayList<>(),  this);
        recyclerView.setAdapter(adapter);

        friendViewmodel = new ViewModelProvider(this).get(FriendViewModel.class);
        friendViewmodel.getAllFriends().observe(this, friends -> {
            adapter.updateFriends(friends);
        });
        FloatingActionButton addFriend = findViewById(R.id.btn_add_friend);
        addFriend.setOnClickListener(v -> {
            Intent intent = new Intent(FriendsActivity.this, CreateFriendActivity.class);
            startActivity(intent);
        });
        ImageButton toggleButton = findViewById(R.id.btn_toggle_friends_edit_state);
        toggleButton.setOnClickListener(v -> {
            adapter.toggleButtonVisibility();
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_friends;
    }

    @Override
    public void onItemClicked(int position) {
        Person personDetails = adapter.getPersonAt(position);
        Intent intent = new Intent(this, FriendsDetailActivity.class);
        intent.putExtra("friend", (Parcelable) personDetails);
        startActivity(intent);
    }

    @Override
    public void onDelete(int position) {
        Person personDelete = adapter.getPersonAt(position);
        friendViewmodel.delete(personDelete);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onEdit(int position) {
        Person personDetails = adapter.getPersonAt(position);
        Intent intent = new Intent(FriendsActivity.this, CreateFriendActivity.class);
        intent.putExtra("friend", (Parcelable) personDetails);
        startActivity(intent);

    }
}