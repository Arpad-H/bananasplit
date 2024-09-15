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

/**
 * Activity for creating and editing a group.
 *
 * @author Arpad Horvath, Dennis Brockmeyer (where specified)
 */
public class CreateGroupActivity extends BaseSelectFriendsActivity {

    private ActivityCreateGroupBinding binding;
    private GroupViewModel groupViewModel;
    private Uri imageUri;
    private Spinner changeCurrencySpinner;

    /**
     * Called when the activity is first created.
     * Initializes the activity and sets up the view components.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently supplied.
     * @Author Arpad Horvath
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        initializeViewModel();


        setSelectedFriendsContainer();


        setupChangeCurrencySpinner();

        setupActivity();
        setupListeners();
    }

    /**
     * Initializes view binding.
     *
     * @Author Arpad Horvath
     */
    private void initBinding() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_create_group, getContentContainer(), false);
        getContentContainer().addView(contentView);
        binding = ActivityCreateGroupBinding.bind(contentView);
    }

    /**
     * Initializes the ViewModel.
     *
     * @Author Arpad Horvath
     */
    private void initializeViewModel() {
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
    }

    /**
     * Configures the activity based on the intent data.
     *
     * @Author Arpad Horvath
     */
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
            imageUri = Uri.parse("android.resource://com.example.bananasplit/drawable/logo"); // default image
        }
    }

    /**
     * Sets up listeners for various UI elements.
     *
     * @Author Arpad Horvath
     */
    private void setupListeners() {
        binding.pickImageButton.setOnClickListener(v -> openImagePicker());

        binding.btnAddFriendsToGroup.setOnClickListener(v -> launchSelectFriendsActivity());

        binding.createButton.setOnClickListener(v -> saveGroup());
    }

    /**
     * Opens the image picker to select an image.
     *
     * @Author Arpad Horvath
     */
    private void openImagePicker() {
        ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }

    /**
     * Saves the group data to the ViewModel.
     *
     * @Author Arpad Horvath
     */
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
            groupViewModel.addMembers(newGroup, selectedFriends);
        } else {
            groupViewModel.insert(newGroup, selectedFriends);
        }
        finish();
    }

    /**
     * Sets up the spinner for currency selection.
     *
     * @Author Dennis Brockmeyer
     */
    private void setupChangeCurrencySpinner() {
        changeCurrencySpinner = binding.spinnerGroupCurrency;

        Currency[] currencies = Currency.values();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getCurrencySymbols(currencies));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeCurrencySpinner.setAdapter(adapter);
    }

    /**
     * Converts currency values to their symbols.
     *
     * @param currencies The array of currencies.
     * @return An array of currency symbols.
     * @Author Arpad Horvath
     */
    private String[] getCurrencySymbols(Currency[] currencies) {
        String[] symbols = new String[currencies.length];
        for (int i = 0; i < currencies.length; i++) {
            symbols[i] = currencies[i].getCurrencySymbol();
        }
        return symbols;
    }

    /**
     * Lets the BaseSelectFriendsActivity know where to put the selected friends
     *
     * @return The layout container for the selected friends
     * @Author Arpad Horvath
     */
    @Override
    protected ViewGroup getSelectedFriendsContainer() {
        return binding.selectedFriendsLayout;
    }


    @Override
    protected void handleAdditionalElements(View friendView, Person friend) {
        //not needed
    }

    /**
     * Returns the layout resource ID for the list item
     *
     * @return The layout resource ID
     * @Author Arpad Horvath
     */
    @Override
    protected int getListItemLayoutResId() {
        return R.layout.friend_with_picture_list_item;
    }

    /**
     * Returns the layout resource ID for the Activity
     *
     * @return The layout resource ID
     * @Author Arpad Horvath
     */
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_group;
    }

    /**
     * Result handler for the image picker.
     * <a href="https://github.com/Dhaval2404/ImagePicker">...</a>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @Author Arpad Horvath
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            binding.groupCoverImageView.setImageURI(imageUri);
        }
    }
}
