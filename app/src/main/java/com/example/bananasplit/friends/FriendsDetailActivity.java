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
import com.example.bananasplit.util.UserSessionManager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Displays Details for the Friend, shows Expenses and provides the ability to settle up with the friend
 * @author Dennis Brockmeyer
 */
public class FriendsDetailActivity extends BaseActivity {
    private ExpenseAdapter adapter;
    private UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSessionManager = new UserSessionManager(getApplication());

        ActivityFriendsDetailBinding binding = createBinding();

        TextView friendNameTV = binding.personName;
        TextView friendOwedTV = binding.friendOwedTV;
        TextView friendOweTV = binding.friendOweTV;
        TextView balance = binding.friendBalanceTV;
        Button settleUp = binding.friendsDetailBtnSettle;

        Person friend = getIntent().getParcelableExtra("friend");

        if (friend != null) {
            friendNameTV.setText(friend.getName());

            setupRecycler(binding);

            setupViewModel(friend);
            setupOnClickListener(settleUp, friend);
        }
    }

    /**
     * Sets up the onClickListener to settle up with a friend
     * @param settleUp the button to listen
     * @param friend to friend to settle up with
     */
    private void setupOnClickListener(Button settleUp, Parcelable friend) {
        settleUp.setOnClickListener(v -> {
            Intent intent = new Intent(FriendsDetailActivity.this, SettleUpDetailsActivity.class);
            intent.putExtra("friend", friend);
            startActivity(intent);
        });
    }

    /**
     * Initializes the ViewModel
     */
    private void setupViewModel(Person friend) {
        ExpenseViewModel expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getExpensesByFriendId(userSessionManager.getCurrentUserId(), friend.getPersonID()).observe(this, expenses -> adapter.updateExpenses(expenses));
    }

    /**
     * sets up the RecyclerView used to display all Expenses with this Friends
     * @param binding the view binding for this class
     */
    private void setupRecycler(ActivityFriendsDetailBinding binding) {
        RecyclerView recyclerView = binding.recyclerViewExpensesFriend;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);
    }

    /**
     * Initializes the view binding for this activity.
     * @return the view binding
     */
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