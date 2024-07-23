package com.example.bananasplit.groups;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
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
import com.example.bananasplit.databinding.ActivityGroupDetailsBinding;
import com.example.bananasplit.expense.CreateExpenseActivity;
import com.example.bananasplit.expense.ExpenseAdapter;
import com.example.bananasplit.expense.ExpenseViewModel;
import com.example.bananasplit.friends.MemberView;
import com.example.bananasplit.util.ExpenseCalculator;
import com.example.bananasplit.settleUp.SettleUpActivity;
import com.example.bananasplit.util.ImageUtils;
import com.example.bananasplit.util.UserSessionManager;

import java.util.ArrayList;

/**
 * Activity for displaying the details of a group, including expenses and group members.
 *
 * @author Arpad Horvath, Dennis Brockmeyer
 */
public class GroupDetailsActivity extends BaseActivity {

    private ActivityGroupDetailsBinding binding;
    private ExpenseViewModel expenseViewModel;
    private GroupViewModel groupViewModel;
    private Group group;
    private TextView balanceTextView;
    private TextView owedAmountTextView;
    private TextView youOweTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupLayout();
        initializeViewModels();
        configureUI();
        setupListeners();
    }

    /**
     * Sets up the layout for the activity.
     *
     * @Author Arpad Horvath
     */
    private void setupLayout() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_group_details, getContentContainer(), false);
        getContentContainer().addView(contentView);
        binding = ActivityGroupDetailsBinding.bind(contentView);
    }

    /**
     * Initializes the ViewModels used in this activity.
     *
     * @Author Arpad Horvath
     */
    private void initializeViewModels() {
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
    }

    /**
     * Configures the UI elements, including setting up the group details and RecyclerView.
     *
     * @Author Arpad Horvath
     */
    private void configureUI() {
        group = getIntent().getParcelableExtra("group");
        if (group != null) {
            setupGroupDetails();
            setupRecyclerView();
            setupMemberView();
            populateBalanceOverview();
        }
    }

    /**
     * Sets up the group details such as name and image.
     *
     * @Author Arpad Horvath
     */
    private void setupGroupDetails() {
        binding.groupNameTextView.setText(group.getName());
        binding.groupCoverImageView.setImageDrawable(ImageUtils.getDrawableFromUri(this, Uri.parse(group.getImageUri())));
    }

    /**
     * Sets up the RecyclerView with an adapter and observes expenses.
     *
     * @Author Arpad Horvath
     */
    private void setupRecyclerView() {
        RecyclerView recyclerView = binding.recyclerViewExpenses;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ExpenseAdapter adapter = new ExpenseAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        expenseViewModel.getExpensesByGroupId(group.getGroupID()).observe(this, adapter::updateExpenses);
    }

    /**
     * Sets up the MemberView and observes group members.
     *
     * @Author Arpad Horvath
     */
    private void setupMemberView() {
        MemberView memberView = binding.memberView;
        groupViewModel.getMembersByGroupId(group.getGroupID()).observe(this, memberView::setMembers);
    }

    /**
     * Populates the balance overview by observing expense data.
     *
     * @Author Arpad Horvath
     */
    private void populateBalanceOverview() {
        youOweTextView = binding.txtYouOweAmount;
        owedAmountTextView = binding.txtOwedAmount;
        balanceTextView = binding.txtTotalBalance; //TODO implement balance calculation

        AppDatabase database = DatabaseModule.getInstance(this);
        ExpenseInDao expenseInDao = database.expenseInDao();
        UserSessionManager userSessionManager = new UserSessionManager(getApplication());

        observeExpenseData(group.getGroupID());
    }

    /**
     * Observes and updates the UI with expense data.
     *
     * @param groupId The ID of the group whose expenses are being observed.
     * @Author Dennis Brockmeyer , Arpad Horvath
     */
    private void observeExpenseData(int groupId) {
        ExpenseCalculator expenseCalculator = new ExpenseCalculator(getApplication());

        expenseCalculator.getTotalAmountOwedByCurrentUserInGroup(groupId).observe(this, youOwe -> {
            updateTextView(youOweTextView, youOwe.floatValue());
        });

        expenseCalculator.getTotalAmountPaidByCurrentInGroup(groupId).observe(this, owedAmount -> {
            updateTextView(owedAmountTextView, owedAmount.floatValue());
        });
    }

    /**
     * Updates a TextView with the provided amount and currency symbol.
     *
     * @param textView The TextView to update.
     * @param amount   The amount to display.
     * @Author Dennis Brockmeyer , Arpad Horvath
     */
    private void updateTextView(TextView textView, Float amount) {
        String formattedAmount = String.format("%.2f", amount != null ? amount : 0.0);
        textView.setText(formattedAmount + group.getCurrency().getCurrencySymbol());
    }

    /**
     * Sets up click listeners for buttons.
     * @Author Arpad Horvath
     */
    private void setupListeners() {
        binding.btnSettleUp.setOnClickListener(v -> navigateToSettleUp());
        binding.btnAddExpense.setOnClickListener(v -> navigateToCreateExpense());
    }

    /**
     * Navigates to the SettleUpActivity.
     * @Author Dennis Brockmeyer
     */
    private void navigateToSettleUp() {
        Intent intent = new Intent(this, SettleUpActivity.class);
        intent.putExtra("group", (Parcelable) group);
        startActivity(intent);
    }

    /**
     * Navigates to the CreateExpenseActivity.
     * @Author Arpad Horvath
     */
    private void navigateToCreateExpense() {
        Intent intent = new Intent(this, CreateExpenseActivity.class);
        intent.putExtra("group", (Parcelable) group);
        startActivity(intent);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_group_details;
    }
}
