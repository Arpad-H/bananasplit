package com.example.bananasplit.groups;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.bananasplit.friends.FriendViewModel;
import com.example.bananasplit.friends.FriendsAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectFriendsActivity extends BaseActivity {
    private SelectFriendsAdapter friendsAdapter;
    private FriendViewModel friendViewModel;
    private List<Person> selectedFriends = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        RecyclerView recyclerView = findViewById(R.id.recyclerView_select_friends);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        friendsAdapter = new SelectFriendsAdapter(new ArrayList<>());

        recyclerView.setAdapter(friendsAdapter);

        friendViewModel = new ViewModelProvider(this).get(FriendViewModel.class);
        friendViewModel.getAllFriends().observe(this, friends -> friendsAdapter.updateFriends(friends));

        Button doneButton = findViewById(R.id.btn_select_friends);
        doneButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putParcelableArrayListExtra("selectedFriends", new ArrayList<>(friendsAdapter.getSelectedFriends()));
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_select_friends;
    }
}
