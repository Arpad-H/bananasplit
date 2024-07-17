package com.example.bananasplit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.dataModel.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FriendsActivity extends BaseActivity implements ListItemHolder {
    private RecyclerView recyclerView;
    private FriendsAdapter adapter;
    private FriendViewModel friendViewmodel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        recyclerView = findViewById(R.id.recyclerFriends);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FriendsAdapter(new ArrayList<>(), this);
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
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_friends;
    }

    @Override
    public void onItemClicked(int position) {
        // TODO: handle on ItemClicked
        Person personDetails = adapter.getPersonAt(position);
        Intent intent = new Intent(FriendsActivity.this, FriendsDetailActivity.class);
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
        // TODO: handle on ItemEdit

    }
}