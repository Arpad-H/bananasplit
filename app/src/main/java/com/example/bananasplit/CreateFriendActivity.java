package com.example.bananasplit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bananasplit.dataModel.Person;

public class CreateFriendActivity extends AppCompatActivity {
    private EditText friendName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_friend);

        friendName = findViewById(R.id.nameFriendEdit);
        Button createFriend = findViewById(R.id.createFriend);
        createFriend.setOnClickListener(v -> {
            String name = friendName.getText().toString();
            Person friend = new Person(name);
            finish();
        });
    }
}
