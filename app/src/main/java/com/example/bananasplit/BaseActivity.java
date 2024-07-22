package com.example.bananasplit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bananasplit.Activities.ActivitiesActivity;
import com.example.bananasplit.settings.SettingsActivity;
import com.example.bananasplit.friends.FriendsActivity;
import com.example.bananasplit.groups.GroupsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity {
    private FrameLayout contentContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        contentContainer = findViewById(R.id.content_container);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_groups) {
                Log.d("NAV-GROUPS", "Wechsel zu Groups");
                if (!(BaseActivity.this instanceof GroupsActivity)) {
                    startActivity(new Intent(this, GroupsActivity.class));
                    finish();
                }
                return true;
            } else if (itemId == R.id.nav_friends) {
                Log.d("NAV-FRIENDS", "Wechsel zu Friends");
                if (!(BaseActivity.this instanceof FriendsActivity)) {
                    startActivity(new Intent(this, FriendsActivity.class));
                    finish();
                }
                return true;
            } else if (itemId == R.id.nav_activities) {
                Log.d("NAV-ACTIVITIES", "Wechsel zu Activities");
                if (!(BaseActivity.this instanceof ActivitiesActivity)) {
                    startActivity(new Intent(this, ActivitiesActivity.class));
                    finish();
                }
                return true;
            } else if (itemId == R.id.nav_settings) {
                Log.d("NAV-SETTINGS", "Wechsel zu Settings");
                if (!(BaseActivity.this instanceof SettingsActivity)) {
                    startActivity(new Intent(this, SettingsActivity.class));
                    finish();
                }
                return true;
            }
            return false;
        });

        // Set the current selected item
        updateSelectedItem(bottomNavigationView);
    }

    private void updateSelectedItem(BottomNavigationView bottomNavigationView) {
        if (this instanceof GroupsActivity) {
            bottomNavigationView.setSelectedItemId(R.id.nav_groups);
        } else if (this instanceof FriendsActivity) {
            bottomNavigationView.setSelectedItemId(R.id.nav_friends);
        } else if (this instanceof ActivitiesActivity) {
            bottomNavigationView.setSelectedItemId(R.id.nav_activities);
        } else if (this instanceof SettingsActivity) {
            bottomNavigationView.setSelectedItemId(R.id.nav_settings);
        } else {
            bottomNavigationView.setSelectedItemId(R.id.nav_ghost);
        }
    }

    @LayoutRes
    protected abstract int getLayoutResourceId();

//    protected void setContentViewForActivity(int layoutResId) {
//        FrameLayout contentContainer = findViewById(R.id.content_container);
//        if (contentContainer != null) {
//            contentContainer.removeAllViews();
//            getLayoutInflater().inflate(layoutResId, contentContainer, true);
//        } else {
//            Log.e("BaseActivity", "content_container is null");
//        }
//    }
public FrameLayout getContentContainer() {
    return contentContainer;
}
}
