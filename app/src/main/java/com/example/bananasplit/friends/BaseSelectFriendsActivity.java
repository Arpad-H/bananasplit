package com.example.bananasplit.friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.groups.SelectFriendsActivity;
import com.example.bananasplit.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSelectFriendsActivity extends BaseActivity {

    protected List<Person> selectedFriends = new ArrayList<>();
    private ViewGroup selectedFriendsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedFriendsContainer = getSelectedFriendsContainer();
        setupSelectFriendsLauncher();
    }
    protected abstract ViewGroup getSelectedFriendsContainer();
    private void setupSelectFriendsLauncher() {
        selectFriendsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        List<Person> friends = result.getData().getParcelableArrayListExtra("selectedFriends", Person.class);
                        if (friends != null) {
                            selectedFriends = friends;
                            displaySelectedFriends();
                        }
                    }
                }
        );
    }

    protected ActivityResultLauncher<Intent> selectFriendsLauncher;

    protected void launchSelectFriendsActivity() {
        Intent intent = new Intent(this, SelectFriendsActivity.class);
        selectFriendsLauncher.launch(intent);
    }

    protected void displaySelectedFriends() {
        selectedFriendsContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Person friend : selectedFriends) {
            View friendView = inflater.inflate(R.layout.friend_with_picture_list_item, selectedFriendsContainer, false);

            TextView friendNameTextView = friendView.findViewById(R.id.person_name);
            ImageView friendImageView = friendView.findViewById(R.id.friend_profile_picture);

            friendNameTextView.setText(friend.getName());
            ImageUtils.setProfileImage(friendImageView, friend.getName());

            selectedFriendsContainer.addView(friendView);
        }
    }
}
