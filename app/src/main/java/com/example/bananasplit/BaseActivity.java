package com.example.bananasplit;

import android.content.Intent;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import com.example.bananasplit.activities.ActivitiesActivity;
import com.example.bananasplit.settings.SettingsActivity;
import com.example.bananasplit.friends.FriendsActivity;
import com.example.bananasplit.groups.GroupsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Base activity class providing common functionality for all activities in the app.
 * This includes the bottom navigation bar and the content container.
 *
 * @author Arpad Horvath, Dennis Brockmeyer (where specified)
 */
public abstract class BaseActivity extends AppCompatActivity {
    private FrameLayout contentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initializeUIComponents();
        setupBottomNavigation();
        updateSelectedItem();
    }

    /**
     * Sets up the bottom navigation and its item selection listener.
     */
    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_groups) {
                navigateToActivity(GroupsActivity.class, "NAV-GROUPS");
                return true;
            } else if (itemId == R.id.nav_friends) {
                navigateToActivity(FriendsActivity.class, "NAV-FRIENDS");
                return true;
            } else if (itemId == R.id.nav_activities) {
                navigateToActivity(ActivitiesActivity.class, "NAV-ACTIVITIES");
                return true;
            } else if (itemId == R.id.nav_settings) {
                navigateToActivity(SettingsActivity.class, "NAV-SETTINGS");
                return true;
            }
            return false;
        });
    }

    /**
     * Navigates to a specified activity and logs the action.
     *
     * @param activityClass The class of the activity to navigate to.
     * @param logTag        The tag to use for logging.
     */
    private void navigateToActivity(Class<? extends AppCompatActivity> activityClass, String logTag) {
        if (!getClass().equals(activityClass)) {
            Log.d(logTag, "Navigating to " + activityClass.getSimpleName());
            Intent intent = new Intent(this, activityClass);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Initializes UI components and binds them.
     */
    private void initializeUIComponents() {
        contentContainer = findViewById(R.id.content_container);
    }

    /**
     * Updates the selected item in the bottom navigation based on the current activity.
     */
    private void updateSelectedItem() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        int selectedItemId;

        if (this instanceof GroupsActivity) {
            selectedItemId = R.id.nav_groups;
        } else if (this instanceof FriendsActivity) {
            selectedItemId = R.id.nav_friends;
        } else if (this instanceof ActivitiesActivity) {
            selectedItemId = R.id.nav_activities;
        } else if (this instanceof SettingsActivity) {
            selectedItemId = R.id.nav_settings;
        } else {
            selectedItemId = R.id.nav_ghost;
        }

        bottomNavigationView.setSelectedItemId(selectedItemId);
    }

    /**
     * Not used anymore, but was used to inflate the layout inside the container.
     * Provides the layout resource ID for the activity. Must be implemented by subclasses.
     *
     * @return The layout resource ID.
     */
    @LayoutRes
    protected abstract int getLayoutResourceId();

    /**
     * Returns the content container for the activity.
     *
     * @return The content container.
     */
    public FrameLayout getContentContainer() {
        return contentContainer;
    }

    /**
     * changes the Language for all Activities
     * @param languageCode LanguageCode to change to
     * @author Dennis Brockmeyer
     */
    public void setLanguage(String languageCode) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode));
    }

    /**
     * Method to enable/disable the DarkMode
     * @param isDarkMode boolean Variable to enable/disable
     * @author Dennis Brockmeyer
     */
    public void setDarkMode(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
