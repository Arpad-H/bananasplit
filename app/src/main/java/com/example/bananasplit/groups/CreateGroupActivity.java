package com.example.bananasplit.groups;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Group;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.Objects;

public class CreateGroupActivity extends AppCompatActivity {
    //    public static final String EXTRA_GROUP_ID = "com.example.bananasplit.EXTRA_GROUP_ID";
//    public static final String EXTRA_GROUP_NAME = "com.example.bananasplit.EXTRA_GROUP_NAME";
//    public static final String EXTRA_GROUP_DATE = "com.example.bananasplit.EXTRA_GROUP_DATE";
//    public static final String EXTRA_GROUP_DURATION = "com.example.bananasplit.EXTRA_GROUP_DURATION";
    private EditText nameEditText;
    private EditText dateEditText;
    private EditText durationEditText;
    private GroupViewModel groupViewModel;
    private Button pickImageButton;
    private Uri imageUri = Uri.parse("android.resource://com.example.bananasplit/drawable/logo");
    private ImageView groupCoverImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        nameEditText = findViewById(R.id.nameEditText);
        dateEditText = findViewById(R.id.dateEditText);
        durationEditText = findViewById(R.id.durationEditText);
        Button createButton = findViewById(R.id.createButton);
        pickImageButton = findViewById(R.id.pickImageButton);
        groupCoverImageView = findViewById(R.id.groupCoverImageView);
//TODO Stringeingaben crshed die App
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);


        Intent intent = getIntent();
        if (intent.getParcelableExtra("group", Group.class) != null){
            setTitle("Edit Group");
            Group group = intent.getParcelableExtra("group", Group.class);
            nameEditText.setText(group.getName());
            dateEditText.setText(group.getDate());
            durationEditText.setText(String.valueOf(group.getDuration()));
            imageUri = Uri.parse(group.getImageUri());
            createButton.setText("Update");
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

        createButton.setOnClickListener(v -> {
            // Get the input data
            String name = nameEditText.getText().toString();
            String date = dateEditText.getText().toString();
            int duration = Integer.parseInt(durationEditText.getText().toString());
            String imageUriString = imageUri.toString();

            Group newGroup = new Group(name, date, duration, imageUriString);

//            newGroup.setId(intent.getIntExtra(String.valueOf(id), -1));
            if (intent.getParcelableExtra("group", Group.class) != null) {
                int id = Objects.requireNonNull(intent.getParcelableExtra("group", Group.class)).groupID;
                newGroup.setId(intent.getIntExtra(String.valueOf(id), -1));
                groupViewModel.update(newGroup);
            } else {
                groupViewModel.insert(newGroup);
            }
            finish();
        });
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
