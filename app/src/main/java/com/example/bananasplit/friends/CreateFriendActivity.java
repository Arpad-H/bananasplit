package com.example.bananasplit.friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.databinding.ActivityCreateFriendBinding;

import java.util.Objects;

public class CreateFriendActivity extends BaseActivity {

    private FriendViewModel friendViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCreateFriendBinding binding = createBinding();

        friendViewModel = new ViewModelProvider(this).get(FriendViewModel.class);

        TextView friendName = binding.nameFriendEdit;
        TextView friendEmail = binding.emailFriendEdit;
        TextView friendHeadline = binding.createExpenseHeadline;
        ImageButton createFriend = binding.createFriend;

        Intent intent = getIntent();
        boolean update = intent.getParcelableExtra("friend") != null;
        if (update) {
            Person friend = intent.getParcelableExtra("friend");
            if (friend != null) {
                friendHeadline.setText("Update " + friend.getName());
                friendName.setText(friend.getName());
                friendEmail.setText(friend.getEmail());
            }
        }

        createFriend.setOnClickListener(v -> {
            String name = Objects.requireNonNull(friendName.getText()).toString();
            String email = Objects.requireNonNull(friendEmail.getText()).toString();
            Person friend = new Person.PersonBuilder()
                    .name(name)
                    .email(email)
                    .build();

            if (update) {
                friend.setPersonID(Objects.requireNonNull((Person) intent.getParcelableExtra("friend")).getPersonID());
                friendViewModel.update(friend);
            } else {
                friendViewModel.insert(friend);

            }
            finish();
        });
    }

    private @NonNull ActivityCreateFriendBinding createBinding() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_create_friend, getContentContainer(), false);
        getContentContainer().addView(contentView);

        return ActivityCreateFriendBinding.bind(contentView);
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_group;
    }
}

