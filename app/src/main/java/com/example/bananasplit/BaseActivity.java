package com.example.bananasplit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bananasplit.friends.FriendsActivity;
import com.example.bananasplit.groups.GroupsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());


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
            }
            return false;
        });

        // Set the current selected item
        if (this instanceof GroupsActivity) {
            bottomNavigationView.setSelectedItemId(R.id.nav_groups);
        } else if (this instanceof FriendsActivity) {
            bottomNavigationView.setSelectedItemId(R.id.nav_friends);
        } else if (this instanceof ActivitiesActivity) {
            bottomNavigationView.setSelectedItemId(R.id.nav_activities);
        }else{
           bottomNavigationView.setSelectedItemId(R.id.nav_ghost);
        }
        // TODO: update for FirendsDetailActivity and GroupDetailsActivity

    }
//    @SuppressLint("RestrictedApi")
//    public static void deselectAllItems(BottomNavigationView bottomNavigationView) {
//        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
//        try {
//            Field selectedItemField = menuView.getClass().getDeclaredField("selectedItem");
//            selectedItemField.setAccessible(true);
//            selectedItemField.setInt(menuView, -1);
//            selectedItemField.setAccessible(false);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        // Force update to reflect the deselection in the UI
//        for (int i = 0; i < menuView.getChildCount(); i++) {
//            menuView.getChildAt(i).setSelected(false);
//        }
//    }

    protected abstract int getLayoutResourceId();
}