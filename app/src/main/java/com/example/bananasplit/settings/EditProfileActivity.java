package com.example.bananasplit.settings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Language;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.dataModel.PersonInDao;
import com.example.bananasplit.dataModel.repository.PersonRepository;
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
        UserSessionManager userSessionManager = new UserSessionManager(getApplication());
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
        Spinner languageSpinner = findViewById(R.id.language_spinner);
        setupSpinner(languageSpinner, Language.getLanguageNames());


        pickImageButton.setOnClickListener(v -> {
            ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
        });

        confirmButton.setOnClickListener(v -> {
            PersonRepository repository = new PersonRepository(getApplication());
            Person currentUser = repository.getCurrentUser(userSessionManager.getCurrentUserId());
            String language = languageSpinner.getSelectedItem().toString();
            if (currentUser == null) {
            new Thread(() -> {
                int id = (int) repository.insert(new Person.PersonBuilder().name(nameEditText.getText().toString()).imageURI(imageUri.toString()).build());
                userSessionManager.setCurrentUserId(id);
                userSessionManager.setCurrentUserName(nameEditText.getText().toString());
                userSessionManager.setLanguage(language);
            }).start();
            } else {
                new Thread(() -> {
                    repository.update(new Person.PersonBuilder().personID(userSessionManager.getCurrentUserId()).name(nameEditText.getText().toString()).imageURI(imageUri.toString()).build());
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
    /**
     * Sets up the spinner with the options in the StringArray
     * @author Dennis Brockmeyer
     */
    private void setupSpinner(Spinner spinner, String[] stringArray) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
