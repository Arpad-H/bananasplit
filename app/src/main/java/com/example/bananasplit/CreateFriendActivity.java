package com.example.bananasplit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bananasplit.dataModel.Person;

import java.util.Objects;

public class CreateFriendActivity extends AppCompatActivity {
    private EditText friendName;
    private FriendViewModel friendViewmodel;
    private Button createFriend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_friend);

        friendName = findViewById(R.id.nameFriendEdit);
        friendViewmodel = new ViewModelProvider(this).get(FriendViewModel.class);
        createFriend = findViewById(R.id.createFriend);

        Intent intent = getIntent();
        boolean update = intent.getParcelableExtra("friend", Person.class) != null;
        if (update) {
            Person friend = intent.getParcelableExtra("friend", Person.class);
            createFriend.setText("Update");
            if (friend != null) {
                friendName.setText(friend.name);
            }

        }

        createFriend.setOnClickListener(v -> {
            String name = friendName.getText().toString();
            Person friend = new Person(name);

            if (update) {
                friend.personID = Objects.requireNonNull(intent.getParcelableExtra("friend", Person.class)).personID;
                friendViewmodel.update(friend);
            } else {
                friendViewmodel.insert(friend);
            }
            finish();
        });
    }
}
