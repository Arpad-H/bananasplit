package com.example.bananasplit.groups;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Currency;
import com.example.bananasplit.dataModel.Group;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.friends.BaseSelectFriendsActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class CreateGroupActivity extends  BaseSelectFriendsActivity {

    private EditText nameEditText;
//    private EditText dateEditText;
//    private EditText durationEditText;
    private GroupViewModel groupViewModel;
    private LinearLayout selectedFriendsContainer;
    private Button pickImageButton;
    private Uri imageUri = Uri.parse("android.resource://com.example.bananasplit/drawable/logo");
    private ImageView groupCoverImageView;
    private Spinner changeCurrencySpinner;
//    private List<Person> selectedFriends = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        nameEditText = findViewById(R.id.group_name_edit_text);

        Button createButton = findViewById(R.id.createButton);
        pickImageButton = findViewById(R.id.pickImageButton);
        groupCoverImageView = findViewById(R.id.GroupCoverImageView);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        selectedFriendsContainer = findViewById(R.id.selected_friends_layout);
        changeCurrencySpinner = findViewById(R.id.spinner_group_currency);

        setupChangeCurrencySpinner();

        Intent currentIntent = getIntent();
        if (currentIntent.getParcelableExtra("group") != null) {
            setTitle("Edit Group");
            Group group = currentIntent.getParcelableExtra("group");
            nameEditText.setText(group.getName());
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
            if (imageUri == null) {
                imageUri = Uri.parse("android.resource://com.example.bananasplit/drawable/logo"); //default image spaeter ein dediziertes bild
            }
            String imageUriString = imageUri.toString();
            Currency selectedCurrency = Currency.fromString((String) changeCurrencySpinner.getSelectedItem());


//            Group newGroup = new Group(name, imageUriString);
            Group newGroup = new Group.GroupBuilder()
                    .name(name)
                    .imageURI(imageUriString)
                    .currency(selectedCurrency)
                    .build();

            if (currentIntent.getParcelableExtra("group") != null) {
                Group group = currentIntent.getParcelableExtra("group");
                int id = group.getGroupID();
                newGroup.setId(currentIntent.getIntExtra(String.valueOf(id), -1));
                groupViewModel.update(newGroup);
            } else {
                groupViewModel.insert(newGroup, selectedFriends);
            }
            finish();
        });
    }

    private void setupChangeCurrencySpinner() {
        Currency[] categories = Currency.values();

        String[] currencySymbol = new String[categories.length];
        for (int i = 0; i < categories.length; i++) {
            currencySymbol[i] = categories[i].getCurrencySymbol();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencySymbol);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeCurrencySpinner.setAdapter(adapter);
    }


    @Override
    protected ViewGroup getSelectedFriendsContainer() {
        return findViewById(R.id.selected_friends_layout);
    }

    @Override
    protected void handleAdditionalElements(View friendView, Person friend) {

    }

    @Override
    protected int getListItemLayoutResId() {
        return R.layout.friend_with_picture_list_item;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_group;
    }

//    private ActivityResultLauncher<Intent> selectFriendsLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            result -> {
//                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                    List<Person> friends = result.getData().getParcelableArrayListExtra("selectedFriends", Person.class);
//                    if (friends != null) {
//                        selectedFriends = friends;
//                        displaySelectedFriends();
//                    }
//                }
//            }
//    );
//    private void displaySelectedFriends() {
//        selectedFriendsContainer.removeAllViews();
//        LayoutInflater inflater = LayoutInflater.from(this);
//
//        for (Person friend : selectedFriends) {
//            View friendView = inflater.inflate(R.layout.friend_with_picture_list_item, selectedFriendsContainer, false);
//
//            TextView friendNameTextView = friendView.findViewById(R.id.person_name);
//            ImageView friendImageView = friendView.findViewById(R.id.friend_profile_picture);
//
//            friendNameTextView.setText(friend.getName());
//            ImageUtils.setProfileImage(friendImageView, friend.getName());
//
//            selectedFriendsContainer.addView(friendView);
//        }
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            groupCoverImageView.setImageURI(imageUri);
        }
    }
}
