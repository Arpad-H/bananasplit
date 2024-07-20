package com.example.bananasplit.groups;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Group;
import com.example.bananasplit.expense.CreateExpenseActivity;
import com.example.bananasplit.expense.ExpenseAdapter;
import com.example.bananasplit.expense.ExpenseViewModel;
import com.example.bananasplit.friends.MemberView;
import com.example.bananasplit.util.ImageUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GroupDetailsActivity extends BaseActivity {
    private RecyclerView recyclerView;
    ExpenseAdapter adapter;
    ExpenseViewModel expenseViewModel;

    GroupViewModel groupViewModel;
    MemberView memberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView groupNameTextView = findViewById(R.id.groupNameTextView);
        ImageView groupCoverImageView = findViewById(R.id.groupCoverImageView);
//        TextView groupDateTextView = findViewById(R.id.groupDateTextView);
//        TextView groupDurationTextView = findViewById(R.id.groupDurationTextView);

        Group group = getIntent().getParcelableExtra("group", Group.class);

        if (group != null) {
            groupNameTextView.setText(group.getName());
//            groupDateTextView.setText(group.getDate());
//            groupDurationTextView.setText(String.valueOf(group.getDuration()));
            groupCoverImageView.setImageDrawable(ImageUtils.getDrawableFromUri(this, Uri.parse(group.getImageUri())));
        }

        recyclerView = findViewById(R.id.recyclerViewExpenses);
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


        FloatingActionButton fab = findViewById(R.id.btn_add_expense);
        fab.setOnClickListener(v -> {

            Intent intent = new Intent(this, CreateExpenseActivity.class);
            intent.putExtra("group", (Parcelable) group);
            startActivity(intent);
        });
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_group_details;
    }
}
