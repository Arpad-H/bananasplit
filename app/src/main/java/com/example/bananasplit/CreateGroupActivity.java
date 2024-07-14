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

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input data
                String name = nameEditText.getText().toString();
                String date = dateEditText.getText().toString();
                int duration = Integer.parseInt(durationEditText.getText().toString());


                Group newGroup = new Group(name, date, duration);
                groupViewModel.insert(newGroup);

                finish();
            }
        });
    }
}
