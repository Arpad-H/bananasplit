package com.example.bananasplit.friends;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;

import java.util.Objects;

public class CreateFriendActivity extends AppCompatActivity {
    private EditText friendName;
    private EditText friendEmail;
    private FriendViewModel friendViewmodel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_friend);

        friendName = findViewById(R.id.nameFriendEdit);
        friendEmail = findViewById(R.id.emailFriendEdit);
        friendViewmodel = new ViewModelProvider(this).get(FriendViewModel.class);
        TextView friendHeadline = findViewById(R.id.createFriendHeadline);
        ImageButton createFriend = findViewById(R.id.createFriend);

        Intent intent = getIntent();
        boolean update = intent.getParcelableExtra("friend", Person.class) != null;
        if (update) {
            Person friend = intent.getParcelableExtra("friend", Person.class);
            if (friend != null) {
            friendHeadline.setText("Update " + friend.getName());
                friendName.setText(friend.getName());
                friendEmail.setText(friend.getEmail());
            }

        }

        createFriend.setOnClickListener(v -> {
            String name = friendName.getText().toString();
            String email = friendEmail.getText().toString();
            Person friend = new Person(name, email);

            if (update) {
                friend.setPersonID(Objects.requireNonNull(intent.getParcelableExtra("friend", Person.class)).getPersonID());
                friendViewmodel.update(friend);
            } else {
                friendViewmodel.insert(friend);
            }
            finish();
        });
    }
}
