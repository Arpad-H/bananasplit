package com.example.bananasplit.groups;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Currency;
import com.example.bananasplit.dataModel.Group;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.databinding.ActivityCreateGroupBinding;
import com.example.bananasplit.databinding.ActivityGroupsBinding;
import com.example.bananasplit.friends.BaseSelectFriendsActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class CreateGroupActivity extends BaseSelectFriendsActivity {

    private ActivityCreateGroupBinding binding;
    private GroupViewModel groupViewModel;
    private Uri imageUri = Uri.parse("android.resource://com.example.bananasplit/drawable/logo");

    private ImageView groupCoverImageView;
    private Spinner changeCurrencySpinner;
//    private List<Person> selectedFriends = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_create_group, getContentContainer(), false);
        getContentContainer().addView(contentView);

        binding = ActivityCreateGroupBinding.bind(contentView);

        setSelectedFriendsContainer();

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);


        setupChangeCurrencySpinner();

        setupActivity();
        setupListeners();
    }

    private void setupActivity() {
        Intent currentIntent = getIntent();
        Group group = currentIntent.getParcelableExtra("group");
        if (group != null) {
            setTitle("Edit Group");
            binding.groupNameEditText.setText(group.getName());
            imageUri = Uri.parse(group.getImageUri());
            binding.groupCoverImageView.setImageURI(imageUri);
            binding.createButton.setText(R.string.update);
        } else {
            setTitle("Create Group");
        }
    }

    private void setupListeners() {
        binding.pickImageButton.setOnClickListener(v -> {
            ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
        });

        binding.btnAddFriendsToGroup.setOnClickListener(v -> {
            launchSelectFriendsActivity();
        });

        binding.createButton.setOnClickListener(v -> {
            saveGroup();
            finish();
        });
    }

    private void saveGroup() {
        String name = binding.groupNameEditText.getText().toString();
        if (imageUri == null) {
            imageUri = Uri.parse("android.resource://com.example.bananasplit/drawable/logo");
        }
        String imageUriString = imageUri.toString();
        Currency selectedCurrency = Currency.fromString((String) changeCurrencySpinner.getSelectedItem());

        Group newGroup = new Group.GroupBuilder()
                .name(name)
                .imageURI(imageUriString)
                .currency(selectedCurrency)
                .build();

        Intent currentIntent = getIntent();
        Group existingGroup = currentIntent.getParcelableExtra("group");
        if (existingGroup != null) {
            newGroup.setId(existingGroup.getGroupID());
            groupViewModel.update(newGroup);
        } else {
            groupViewModel.insert(newGroup, selectedFriends);
        }
    }

    private void setupChangeCurrencySpinner() {
        changeCurrencySpinner = binding.spinnerGroupCurrency;
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
        return binding.selectedFriendsLayout;
    }

    @Override
    protected void handleAdditionalElements(View friendView, Person friend) {
        //not needed
    }

    @Override
    protected int getListItemLayoutResId() {
        return R.layout.friend_with_picture_list_item;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_group;
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
