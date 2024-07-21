package com.example.bananasplit.settings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bananasplit.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class EditProfileActivity extends AppCompatActivity {
    private Button pickImageButton;
//    private Uri imageUri = Uri.parse("android.resource://com.example.bananasplit/drawable/logo");
    private ImageView profilePictureView;
    private Uri imageUri= Uri.parse("android.resource://com.example.bananasplit/drawable/logo");
    private EditText nameEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        nameEditText = findViewById(R.id.edit_name);
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
            //TODO save the profile
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
