package com.example.bananasplit.groups;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.ExpenseInDao;
import com.example.bananasplit.dataModel.Group;
import com.example.bananasplit.expense.CreateExpenseActivity;
import com.example.bananasplit.expense.ExpenseAdapter;
import com.example.bananasplit.expense.ExpenseViewModel;
import com.example.bananasplit.friends.MemberView;
import com.example.bananasplit.util.ExpenseCalculator;
import com.example.bananasplit.settleUp.SettleUpActivity;
import com.example.bananasplit.settleUp.SettleUpDetailsActivity;
import com.example.bananasplit.util.ImageUtils;
import com.example.bananasplit.util.UserSessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GroupDetailsActivity extends BaseActivity {
    ExpenseAdapter adapter;
    ExpenseViewModel expenseViewModel;

    GroupViewModel groupViewModel;
    MemberView memberView;
    TextView balanceTextView;
    TextView owedAmountTextView;
    TextView youOweTextView;
    Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView groupNameTextView = findViewById(R.id.groupNameTextView);
        ImageView groupCoverImageView = findViewById(R.id.GroupCoverImageView);
//        TextView groupDateTextView = findViewById(R.id.groupDateTextView);
//        TextView groupDurationTextView = findViewById(R.id.groupDurationTextView);

        group = getIntent().getParcelableExtra("group");
        groupNameTextView.setText(group.getName());
        groupCoverImageView.setImageDrawable(ImageUtils.getDrawableFromUri(this, Uri.parse(group.getImageUri())));


        RecyclerView recyclerView = findViewById(R.id.recyclerViewExpenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpenseAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getExpensesByGroupId(group.getGroupID()).observe(this, expenses -> {
            adapter.updateExpenses(expenses);
        });

        memberView = findViewById(R.id.memberView);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.getMembersByGroupId(group.getGroupID()).observe(this, members -> {
            memberView.setMembers(members);
        });

        Button settleUp = findViewById(R.id.btn_settle_up);
        settleUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettleUpActivity.class);
            intent.putExtra("group", (Parcelable) group);
            startActivity(intent);
        });

        populateBalanceOverview();
        FloatingActionButton fab = findViewById(R.id.btn_add_expense);
        fab.setOnClickListener(v -> {

            Intent intent = new Intent(this, CreateExpenseActivity.class);
            intent.putExtra("group", (Parcelable) group);
            startActivity(intent);
        });
    }

    private void populateBalanceOverview() {
        youOweTextView = findViewById(R.id.txt_you_owe_amount);
        owedAmountTextView = findViewById(R.id.txt_owed_amount);
        balanceTextView = findViewById(R.id.txt_total_balance);
        AppDatabase database = DatabaseModule.getInstance(this);
        ExpenseInDao expenseInDao = database.expenseInDao();
        UserSessionManager userSessionManager = new UserSessionManager(this);
        observeExpenseData(group.getGroupID());

    }

    private void observeExpenseData(int groupId) {
        ExpenseCalculator expenseCalculator = new ExpenseCalculator(this);
        expenseCalculator.getTotalAmountOwedByCurrentUserInGroup(groupId).observe(this, youOwe -> {
            if (youOwe != null) {
                // Update UI with the amount owed
                runOnUiThread(() -> youOweTextView.setText(String.format("%.2f", youOwe) + group.getCurrency().getCurrencySymbol()));
            } else {

                runOnUiThread(() -> youOweTextView.setText("0.00" + group.getCurrency().getCurrencySymbol()));
            }
        });

// Observe the total amount paid
        expenseCalculator.getTotalAmountPaidByCurrentInGroup(groupId).observe(this, owedAmount -> {
            if (owedAmount != null) {
                // Update UI with the amount paid
                runOnUiThread(() -> owedAmountTextView.setText(String.format("%.2f", owedAmount) + group.getCurrency().getCurrencySymbol()));
            } else {

                runOnUiThread(() -> owedAmountTextView.setText("0.00" + group.getCurrency().getCurrencySymbol()));
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_group_details;
    }
}
