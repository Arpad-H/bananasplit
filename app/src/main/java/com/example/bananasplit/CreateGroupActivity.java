package com.example.bananasplit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bananasplit.dataModel.Group;

public class CreateGroupActivity extends AppCompatActivity {
    public static final String EXTRA_GROUP_ID = "com.example.bananasplit.EXTRA_GROUP_ID";
    public static final String EXTRA_GROUP_NAME = "com.example.bananasplit.EXTRA_GROUP_NAME";
    public static final String EXTRA_GROUP_DATE = "com.example.bananasplit.EXTRA_GROUP_DATE";
    public static final String EXTRA_GROUP_DURATION = "com.example.bananasplit.EXTRA_GROUP_DURATION";
    private EditText nameEditText;
    private EditText dateEditText;
    private EditText durationEditText;
    private GroupViewModel groupViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        nameEditText = findViewById(R.id.nameEditText);
        dateEditText = findViewById(R.id.dateEditText);
        durationEditText = findViewById(R.id.durationEditText);
        Button createButton = findViewById(R.id.createButton);
//TODO Stringeingaben crshed die App
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);


        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_GROUP_ID)) {
            setTitle("Edit Group");
            nameEditText.setText(intent.getStringExtra(EXTRA_GROUP_NAME));
            dateEditText.setText(intent.getStringExtra(EXTRA_GROUP_DATE));
            durationEditText.setText(String.valueOf(intent.getIntExtra(EXTRA_GROUP_DURATION, 0)));
            createButton.setText("Update");
        } else {
            setTitle("Create Group");
        }


        createButton.setOnClickListener(v -> {
            // Get the input data
            String name = nameEditText.getText().toString();
            String date = dateEditText.getText().toString();
            int duration = Integer.parseInt(durationEditText.getText().toString());


            Group newGroup = new Group(name, date, duration);
            newGroup.setId(intent.getIntExtra(EXTRA_GROUP_ID, -1));
            if (intent.hasExtra(EXTRA_GROUP_ID)) {
                groupViewModel.update(newGroup);
            } else {
                groupViewModel.insert(newGroup);
            }

            finish();
        });
    }
}
