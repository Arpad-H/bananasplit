package com.example.bananasplit.friends;

import android.content.Intent;
import android.os.Bundle;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.expense.ExpenseAdapter;
import com.example.bananasplit.expense.ExpenseViewModel;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.settleUp.SettleUpDetailsActivity;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class FriendsDetailActivity extends BaseActivity {
    private ExpenseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView friendNameTV = findViewById(R.id.person_name);
        TextView friendOwedTV = findViewById(R.id.friendOwedTV);
        TextView friendOweTV = findViewById(R.id.friendOweTV);
        TextView balance = findViewById(R.id.friendBalanceTV);
        Button settleUp = findViewById(R.id.friends_detail_btn_settle);

        Person friend = getIntent().getParcelableExtra("friend");

        if (friend != null) {
            friendNameTV.setText(friend.getName());

            RecyclerView recyclerView = findViewById(R.id.recyclerViewExpensesFriend);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ExpenseAdapter();
            recyclerView.setAdapter(adapter);

            ExpenseViewModel expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
            expenseViewModel.getExpensesByFriendId(1, friend.getPersonID()).observe(this, expenses -> adapter.updateExpenses(expenses));
            settleUp.setOnClickListener(v -> {
                Intent intent = new Intent(FriendsDetailActivity.this, SettleUpDetailsActivity.class);
                intent.putExtra("friend", (Parcelable) friend);
                startActivity(intent);
            });
        }
    }



    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_friends_detail;
    }

}