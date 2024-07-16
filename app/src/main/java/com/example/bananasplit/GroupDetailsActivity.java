package com.example.bananasplit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bananasplit.dataModel.Group;

public class GroupDetailsActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView groupNameTextView = findViewById(R.id.groupNameTextView);
        TextView groupDateTextView = findViewById(R.id.groupDateTextView);
        TextView groupDurationTextView = findViewById(R.id.groupDurationTextView);

        Group group = getIntent().getParcelableExtra("group", Group.class);

        if (group != null) {
            groupNameTextView.setText(group.getName());
            groupDateTextView.setText(group.getDate());
            groupDurationTextView.setText(String.valueOf(group.getDuration()));
        }
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_group_details;
    }
}
