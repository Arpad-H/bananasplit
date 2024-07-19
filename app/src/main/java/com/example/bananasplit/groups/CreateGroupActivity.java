package com.example.bananasplit.groups;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Group;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.util.ImageUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateGroupActivity extends BaseActivity {

    private EditText nameEditText;
//    private EditText dateEditText;
//    private EditText durationEditText;
    private GroupViewModel groupViewModel;
    private LinearLayout selectedFriendsContainer;
    private Button pickImageButton;
    private Uri imageUri = Uri.parse("android.resource://com.example.bananasplit/drawable/logo");
    private ImageView groupCoverImageView;
    private List<Person> selectedFriends = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        nameEditText = findViewById(R.id.nameEditText);
//        dateEditText = findViewById(R.id.dateEditText);
//        durationEditText = findViewById(R.id.durationEditText);
        Button createButton = findViewById(R.id.createButton);
        pickImageButton = findViewById(R.id.pickImageButton);
        groupCoverImageView = findViewById(R.id.groupCoverImageView);
//TODO Stringeingaben crshed die App
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        selectedFriendsContainer = findViewById(R.id.selected_friends_layout);

        Intent currentIntent = getIntent();
        if (currentIntent.getParcelableExtra("group", Group.class) != null) {
            setTitle("Edit Group");
            Group group = currentIntent.getParcelableExtra("group", Group.class);
            nameEditText.setText(group.getName());
//            dateEditText.setText(group.getDate());
//            durationEditText.setText(String.valueOf(group.getDuration()));
            imageUri = Uri.parse(group.getImageUri());
            createButton.setText(R.string.update);
        } else {
            setTitle("Create Group");
        }

        pickImageButton.setOnClickListener(v -> {
            ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
        });

        Button addFriendsButton = findViewById(R.id.btn_add_friends_to_group);
        addFriendsButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(CreateGroupActivity.this, SelectFriendsActivity.class);
            selectFriendsLauncher.launch(newIntent);
        });

        createButton.setOnClickListener(v -> {
            // Get the input data
            String name = nameEditText.getText().toString();
//            String date = dateEditText.getText().toString();
//            int duration = Integer.parseInt(durationEditText.getText().toString());
            if (imageUri == null) {
                imageUri = Uri.parse("android.resource://com.example.bananasplit/drawable/logo");
            }
            String imageUriString = imageUri.toString();


            Group newGroup = new Group(name, imageUriString);
//            Group newGroup = new Group(name, date, duration, imageUriString);

//            newGroup.setId(intent.getIntExtra(String.valueOf(id), -1));
            if (currentIntent.getParcelableExtra("group", Group.class) != null) {
                int id = Objects.requireNonNull(currentIntent.getParcelableExtra("group", Group.class)).getGroupID();
                newGroup.setId(currentIntent.getIntExtra(String.valueOf(id), -1));
                groupViewModel.update(newGroup);
            } else {
                groupViewModel.insert(newGroup, selectedFriends); //TODO falsche grp id. immer 0
            }
            finish();
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_group;
    }

    private ActivityResultLauncher<Intent> selectFriendsLauncher = registerForActivityResult(
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
    private void displaySelectedFriends() {
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            groupCoverImageView.setImageURI(imageUri);
        }
    }
}
