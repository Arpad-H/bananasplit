package com.example.bananasplit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.databinding.ActivityMainBinding;
import com.example.bananasplit.groups.GroupsActivity;
import com.example.bananasplit.scanner.ScannerActivity;
import com.example.bananasplit.settings.EditProfileActivity;
import com.example.bananasplit.util.SecurePreferencesManager;
import com.example.bananasplit.util.UserSessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

import javax.inject.Inject;

/**
 * The MainActivity class handles the intitial Setup of the Application on launch. Managing user sessions,
 * initializing necessary components, and handling permissions.
 * @author Arpad Horvath
 */
public class MainActivity extends BaseActivity {
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private UserSessionManager userSessionManager;
    @Inject
    private AppDatabase database;
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> editProfileLauncher;
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            this::handleCameraPermissionResult
    );

    /**
     * Called when the activity is first created. Initializes components and checks user session.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeComponents();
        setupPayPalCheckout();
        checkUserSession();
    }

    /**
     * Initializes the components of the activity.
     */
    private void initializeComponents() {
        setupTestButton();
        requestCameraPermission();
        initializeUserSessionManager();
        initializeSecurePreferences();
        setupEditProfileLauncher();
    }
//TODO Remove
    private void setupTestButton() {
        binding.btnCamtest.setOnClickListener(view -> {
            Intent intent = new Intent(this, ScannerActivity.class);
            startActivity(intent);
        });
    }
    /**
     * Requests camera permission from the user.
     */
    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }
    /**
     * Initializes the UserSessionManager.
     */
    private void initializeUserSessionManager() {
        userSessionManager = new UserSessionManager(getApplication());
    }

    /**
     * Initializes SecurePreferencesManager and saves the PayPal client ID.
     */
    private void initializeSecurePreferences() {
        SecurePreferencesManager securePreferences = new SecurePreferencesManager(this);
        securePreferences.saveClientIDKey("AY-yuDyocmOSSWMxaG_lRLp_d2aTkCHxeP7o13U0kTAG-M_iSwAoF1u4o10l8OwQ2S6q3Gm4VCJuhbWi");
    }
    /**
     * Sets up the ActivityResultLauncher for the EditProfileActivity.
     */
    private void setupEditProfileLauncher() {
        editProfileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        launchGroupsActivity();
                    }
                }
        );
    }
    /**
     * Checks the user session and launches the appropriate activity.
     */
    private void checkUserSession() {
        if (userSessionManager.getCurrentUserId() == -1) {
            launchEditProfileActivity();
        } else {
            launchGroupsActivity();
        }
    }
    /**
     * Launches the EditProfileActivity.
     */
    private void launchEditProfileActivity() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        editProfileLauncher.launch(intent);
    }
    /**
     * Launches the GroupsActivity.
     */
    private void launchGroupsActivity() {
        Intent intent = new Intent(this, GroupsActivity.class);
        startActivity(intent);
        finish(); // We don't want MainActivity in the back stack
    }
    /**
     * Sets up the PayPal checkout configuration.
     *
     */
    //TODO move
    private void setupPayPalCheckout() {
        SecurePreferencesManager securePreferences = new SecurePreferencesManager(this);
        String sec = securePreferences.getApiKey();
        CheckoutConfig config = new CheckoutConfig(
                this.getApplication(),
                sec,
                Environment.SANDBOX,
                CurrencyCode.USD,
                UserAction.PAY_NOW,
                "nativexo://paypalpay"
        );
        PayPalCheckout.setConfig(config);
    }
    /**
     * Handles the result of the camera permission request.
     *
     * @param isGranted true if permission is granted, false otherwise.
     */
    private void handleCameraPermissionResult(boolean isGranted) {
        if (!isGranted) {
            Toast.makeText(this, R.string.camera_permission_denied, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Returns the layout resource ID for this activity. This is used by the BaseActivity class.
     *
     * @return The layout resource ID.
     */
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    /**
     * Requests storage permission from the user.
     */
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
        }
    }
    /**
     * Handles the result of permission requests.
     *
     * @param requestCode  The request code passed in requestPermissions(android.app.Activity, String[], int).
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions which is either PackageManager.PERMISSION_GRANTED or PackageManager.PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                Toast.makeText(this, R.string.storage_permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
