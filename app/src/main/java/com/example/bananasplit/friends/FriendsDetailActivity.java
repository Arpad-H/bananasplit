package com.example.bananasplit.friends;

import android.content.Intent;
import android.os.Bundle;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.databinding.ActivityFriendsDetailBinding;
import com.example.bananasplit.expense.ExpenseAdapter;
import com.example.bananasplit.expense.ExpenseViewModel;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.settleUp.SettleUpDetailsActivity;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FriendsDetailActivity extends BaseActivity {
    private ExpenseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityFriendsDetailBinding binding = createBinding();

        TextView friendNameTV = binding.personName;
        TextView friendOwedTV = binding.friendOwedTV;
        TextView friendOweTV = binding.friendOweTV;
        TextView balance = binding.friendBalanceTV;
        Button settleUp = binding.friendsDetailBtnSettle;

        Person friend = getIntent().getParcelableExtra("friend");

        if (friend != null) {
            friendNameTV.setText(friend.getName());

            RecyclerView recyclerView = binding.recyclerViewExpensesFriend;
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

    private @NonNull ActivityFriendsDetailBinding createBinding() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_friends_detail, getContentContainer(), false);
        getContentContainer().addView(contentView);
        return ActivityFriendsDetailBinding.bind(contentView);
    }

    /**
     * Returns the layout resource ID for this activity. Used by the BaseActivity.
     *
     * @return The layout resource ID.
     */
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_friends_detail;
    }

}