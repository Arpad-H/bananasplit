package com.example.bananasplit;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.Manifest;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.bananasplit.dataModel.AppDatabase;
//import com.example.bananasplit.dataModel.CurrentUserInDao;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.dataModel.PersonInDao;
import com.example.bananasplit.groups.GroupsActivity;
import com.example.bananasplit.scanner.ScannerActivity;
import com.example.bananasplit.settings.EditProfileActivity;
import com.example.bananasplit.util.UserSessionManager;

public class MainActivity extends BaseActivity {
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private UserSessionManager userSessionManager;
    private ActivityResultLauncher<Intent> editProfileLauncher;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button testButton = findViewById(R.id.btn_camtest);
        testButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ScannerActivity.class);
            startActivity(intent);
        });
        requestCameraPermission();

        userSessionManager = new UserSessionManager(this);
        database = DatabaseModule.getInstance(this);

        editProfileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        launchGroupsActivity();
                    }
                }
        );

        if (userSessionManager.getCurrentUserId() == -1) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            editProfileLauncher.launch(intent);
        } else {
            launchGroupsActivity();
        }
    }

    private void launchGroupsActivity() {
        Intent intent = new Intent(this, GroupsActivity.class);
        startActivity(intent);
        finish(); //we dont want main in the back stack
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            //TODO explain why the app needs this permission
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {

        } else {

        }
    });

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
        } else {
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
