package com.example.bananasplit;

import android.os.Bundle;

import com.example.bananasplit.dataModel.Person;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class FriendsDetailActivity extends BaseActivity {
    private RecyclerView recyclerView;
    ExpenseAdapter adapter;
    ExpenseViewModel expenseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView friendNameTV = findViewById(R.id.friendNameTV);
        TextView friendOwedTV = findViewById(R.id.friendOwedTV);
        TextView friendOweTV = findViewById(R.id.friendOweTV);

        Person friend = getIntent().getParcelableExtra("friend", Person.class);

        if (friend != null) {
            friendNameTV.setText(friend.name);
        }

        recyclerView = findViewById(R.id.recyclerFriends);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpenseAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);


    }



    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_friends_detail;
    }

}