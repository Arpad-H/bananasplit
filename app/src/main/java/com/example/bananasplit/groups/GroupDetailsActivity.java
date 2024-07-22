package com.example.bananasplit.groups;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
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
import com.example.bananasplit.databinding.ActivityAddExpenseBinding;
import com.example.bananasplit.databinding.ActivityCreateGroupBinding;
import com.example.bananasplit.databinding.ActivityGroupDetailsBinding;
import com.example.bananasplit.expense.CreateExpenseActivity;
import com.example.bananasplit.expense.ExpenseAdapter;
import com.example.bananasplit.expense.ExpenseViewModel;
import com.example.bananasplit.friends.MemberView;
import com.example.bananasplit.util.ExpenseCalculator;
import com.example.bananasplit.settleUp.SettleUpActivity;
import com.example.bananasplit.util.ImageUtils;
import com.example.bananasplit.util.UserSessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GroupDetailsActivity extends BaseActivity {

    private ActivityGroupDetailsBinding binding;
    private ExpenseViewModel expenseViewModel;
    private GroupViewModel groupViewModel;
    private Group group;
    TextView balanceTextView;

    TextView owedAmountTextView;

    TextView youOweTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_group_details, getContentContainer(), false);
        getContentContainer().addView(contentView);

        binding = ActivityGroupDetailsBinding.bind(contentView);

        TextView groupNameTextView = binding.groupNameTextView;
        ImageView groupCoverImageView = binding.groupCoverImageView;

        group = getIntent().getParcelableExtra("group");
        groupNameTextView.setText(group.getName());
        groupCoverImageView.setImageDrawable(ImageUtils.getDrawableFromUri(this, Uri.parse(group.getImageUri())));

        RecyclerView recyclerView = binding.recyclerViewExpenses;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ExpenseAdapter adapter = new ExpenseAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getExpensesByGroupId(group.getGroupID()).observe(this, adapter::updateExpenses);

        MemberView memberView = binding.memberView; // Assuming memberView is a custom view
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.getMembersByGroupId(group.getGroupID()).observe(this, memberView::setMembers);

        binding.btnSettleUp.setOnClickListener(v -> { // Use binding for button click listener
            Intent intent = new Intent(this, SettleUpActivity.class);
            intent.putExtra("group", (Parcelable) group);
            startActivity(intent);
        });

        populateBalanceOverview();

        binding.btnAddExpense.setOnClickListener(v -> { // Use binding for floating action button click listener
            Intent intent = new Intent(this, CreateExpenseActivity.class);
            intent.putExtra("group", (Parcelable) group);
            startActivity(intent);
        });
    }

    private void populateBalanceOverview() {
         youOweTextView = binding.txtYouOweAmount;
         owedAmountTextView = binding.txtOwedAmount;
         balanceTextView = binding.txtTotalBalance;

        AppDatabase database = DatabaseModule.getInstance(this);

        ExpenseInDao expenseInDao = database.expenseInDao();

        UserSessionManager userSessionManager = new UserSessionManager(this);

        observeExpenseData(group.getGroupID());
    }

    private void observeExpenseData(int groupId) {
        ExpenseCalculator expenseCalculator = new ExpenseCalculator(this);
        expenseCalculator.getTotalAmountOwedByCurrentUserInGroup(groupId).observe(this, youOwe -> {
            if (youOwe != null) {

                runOnUiThread(() -> youOweTextView.setText(String.format("%.2f", youOwe)));
            } else {
                runOnUiThread(() -> youOweTextView.setText("0.00"));
            }
        });

        // Observe the total amount paid
        expenseCalculator.getTotalAmountPaidByCurrentInGroup(groupId).observe(this, owedAmount -> {
            if (owedAmount != null) {
                // Update UI with the amount paid
                runOnUiThread(() -> owedAmountTextView.setText(String.format("%.2f", owedAmount)));
            } else {
                runOnUiThread(() -> owedAmountTextView.setText("0.00"));
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_group_details;
    }

}
