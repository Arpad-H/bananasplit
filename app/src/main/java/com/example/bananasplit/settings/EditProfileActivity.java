package com.example.bananasplit.settings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.dataModel.PersonInDao;
import com.example.bananasplit.groups.GroupsActivity;
import com.example.bananasplit.util.UserSessionManager;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class EditProfileActivity extends AppCompatActivity {
    private Button pickImageButton;
    private ImageView profilePictureView;
    private Uri imageUri= Uri.parse("android.resource://com.example.bananasplit/drawable/logo");
    private EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        Person edit = getIntent().hasExtra("editUser") ? getIntent().getParcelableExtra("editUser") : null;
        nameEditText = findViewById(R.id.edit_name);
        if (edit != null) {
            nameEditText.setText(edit.getName());
        } else {
            Log.d("EditProfileActivity", "No Extras");
        }
        Button confirmButton = findViewById(R.id.btn_confirm_profile);
        pickImageButton = findViewById(R.id.pickImageButton);
        profilePictureView = findViewById(R.id.profile_picture_image_view);



        pickImageButton.setOnClickListener(v -> {
            ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
        });

        confirmButton.setOnClickListener(v -> {
            UserSessionManager userSessionManager = new UserSessionManager(getApplication());
            AppDatabase database = DatabaseModule.getInstance(this);
            PersonInDao personInDao = database.personInDao();
            Person currentUser = personInDao.getCurrentUser(userSessionManager.getCurrentUserId());
            if (currentUser == null) {
            new Thread(() -> {
                int id = (int) personInDao.insert(new Person.PersonBuilder().name(nameEditText.getText().toString()).imageURI(imageUri.toString()).build());
                userSessionManager.setCurrentUserId(id);
                userSessionManager.setCurrentUserName(nameEditText.getText().toString());
            }).start();
            } else {
                new Thread(() -> {
                    personInDao.update(new Person.PersonBuilder().personID(userSessionManager.getCurrentUserId()).name(nameEditText.getText().toString()).imageURI(imageUri.toString()).build());
                }).start();
            }
            setResult(RESULT_OK);
            finish();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            profilePictureView.setImageURI(imageUri);
        }
    }

}
