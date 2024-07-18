package com.example.bananasplit.friends;

import android.os.Bundle;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.expense.ExpenseAdapter;
import com.example.bananasplit.expense.ExpenseViewModel;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;

import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class FriendsDetailActivity extends BaseActivity {
    ExpenseAdapter adapter;
    ExpenseViewModel expenseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView friendNameTV = findViewById(R.id.friend_name);
        TextView friendOwedTV = findViewById(R.id.friendOwedTV);
        TextView friendOweTV = findViewById(R.id.friendOweTV);
        TextView balance = findViewById(R.id.friendBalanceTV);

        Person friend = getIntent().getParcelableExtra("friend", Person.class);

        if (friend != null) {
            friendNameTV.setText(friend.getName());

            RecyclerView recyclerView = findViewById(R.id.recyclerViewExpensesFriend);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ExpenseAdapter(new ArrayList<>());
            recyclerView.setAdapter(adapter);

            expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
            expenseViewModel.getExpensesByFriendId(1, friend.getPersonID()).observe(this, expenses -> {
                adapter.updateExpenses(expenses);
            });
        }
    }



    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_friends_detail;
    }

}