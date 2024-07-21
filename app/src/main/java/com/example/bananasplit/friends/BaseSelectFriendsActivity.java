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
import androidx.appcompat.app.AppCompatActivity;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.groups.SelectFriendsActivity;
import com.example.bananasplit.util.ImageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseSelectFriendsActivity extends BaseActivity {

    protected List<Person> selectedFriends = new ArrayList<>();
    private ViewGroup selectedFriendsContainer;
    private final Map<Person, View> personViewMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedFriendsContainer = getSelectedFriendsContainer();
        setupSelectFriendsLauncher();
    }
    protected abstract ViewGroup getSelectedFriendsContainer();
//    protected abstract void getListItemForPerson();
    private void setupSelectFriendsLauncher() {
        selectFriendsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        List<Person> friends = result.getData().getParcelableArrayListExtra("selectedFriends");
                        if (friends != null) {
                            selectedFriends = friends;
                            displaySelectedFriends();
                        }
                    }
                }
        );
    }
    protected Map<Person, Float> extractDataFromEditTexts(int editTextId) {
        Map<Person, Float> dataMap = new HashMap<>();

        for (Map.Entry<Person, View> entry : personViewMap.entrySet()) {
            Person person = entry.getKey();
            View view = entry.getValue();
            EditText editText = view.findViewById(editTextId);
            if (editText != null) {
                float additionalInfo = Float.parseFloat(editText.getText().toString());
                dataMap.put(person, additionalInfo);
            }
        }

        return dataMap;
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
            View friendView = inflater.inflate(getListItemLayoutResId(), selectedFriendsContainer, false);

            TextView friendNameTextView = friendView.findViewById(R.id.person_name);
            ImageView friendImageView = friendView.findViewById(R.id.friend_profile_picture);

            friendNameTextView.setText(friend.getName());
            ImageUtils.setProfileImage(friendImageView, friend.getName());
            handleAdditionalElements(friendView, friend);
            selectedFriendsContainer.addView(friendView);
            personViewMap.put(friend, friendView);
        }
    }
    protected abstract void handleAdditionalElements(View friendView, Person friend);
    protected abstract int getListItemLayoutResId();
}
