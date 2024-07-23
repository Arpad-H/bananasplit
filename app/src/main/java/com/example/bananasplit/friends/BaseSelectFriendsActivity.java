package com.example.bananasplit.friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.groups.SelectFriendsActivity;
import com.example.bananasplit.util.ImageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base activity class for selecting and displaying friends.
 * @Author Arpad Horvath
 */
public abstract class BaseSelectFriendsActivity extends BaseActivity {

    protected List<Person> selectedFriends = new ArrayList<>();
    private ViewGroup selectedFriendsContainer;
    private final Map<Person, View> personViewMap = new HashMap<>();
    protected ActivityResultLauncher<Intent> selectFriendsLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeSelectFriendsLauncher();
    }

    /**
     * Sets the container view for displaying selected friends.
     * Used in deriving Class after the layout has been inflated to avoid nullpointer exceptions.
     */
    protected void setSelectedFriendsContainer() {
        selectedFriendsContainer = getSelectedFriendsContainer();
    }

    /**
     * Gets the container view for displaying selected friends.
     *
     * @return The container view.
     */
    protected abstract ViewGroup getSelectedFriendsContainer();

    /**
     * Initializes the launcher for selecting friends.
     */
    private void initializeSelectFriendsLauncher() {
        selectFriendsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        List<Person> friends = result.getData().getParcelableArrayListExtra("selectedFriends");
                        if (friends != null) {
                            selectedFriends = friends;
                            updateSelectedFriendsDisplay();
                        }
                    }
                }
        );
    }

    /**
     * Launches the activity for selecting friends.
     */
    protected void launchSelectFriendsActivity() {
        Intent intent = new Intent(this, SelectFriendsActivity.class);
        selectFriendsLauncher.launch(intent);
    }

    /**
     * Updates the display of selected friends in the container view.
     */
    protected void updateSelectedFriendsDisplay() {
        selectedFriendsContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Person friend : selectedFriends) {
            View friendView = inflater.inflate(getListItemLayoutResId(), selectedFriendsContainer, false);

            populateFriendView(friendView, friend);
            selectedFriendsContainer.addView(friendView);
            personViewMap.put(friend, friendView);
        }
    }

    /**
     * Populates a view with the friend's data.
     *
     * @param friendView The view to be populated.
     * @param friend     The friend data.
     */
    private void populateFriendView(View friendView, Person friend) {
        TextView friendNameTextView = friendView.findViewById(R.id.person_name);
        ImageView friendImageView = friendView.findViewById(R.id.friend_profile_picture);

        friendNameTextView.setText(friend.getName());
        ImageUtils.setProfileImage(friendImageView, friend.getName());
        handleAdditionalElements(friendView, friend);
    }

    /**
     * Extracts data from EditTexts and maps it to the corresponding Person objects.
     *
     * @param editTextId The ID of the EditText fields to extract data from.
     * @return A map of Person objects and their associated data.
     */
    protected Map<Person, Float> extractDataFromEditTexts(int editTextId) {
        Map<Person, Float> dataMap = new HashMap<>();

        for (Map.Entry<Person, View> entry : personViewMap.entrySet()) {
            Person person = entry.getKey();
            View view = entry.getValue();
            EditText editText = view.findViewById(editTextId);

            if (editText != null) {
                try {
                    float additionalInfo = Float.parseFloat(editText.getText().toString());
                    dataMap.put(person, additionalInfo);
                } catch (NumberFormatException e) {
                    // Handle the case where the edit text is not a valid float
                    dataMap.put(person, 0.0f);
                }
            }
        }

        return dataMap;
    }

    /**
     * Handles additional elements within the friend view.
     *
     * @param friendView The view representing the friend.
     * @param friend     The friend data.
     */
    protected abstract void handleAdditionalElements(View friendView, Person friend);

    /**
     * Gets the resource ID for the list item layout.
     *
     * @return The layout resource ID.
     */
    protected abstract int getListItemLayoutResId();
}
