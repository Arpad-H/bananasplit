package com.example.bananasplit.settings;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.widget.SwitchCompat;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.dataModel.PersonInDao;
import com.example.bananasplit.util.UserSessionManager;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_settings, getContentContainer(), false);
        getContentContainer().addView(contentView);

        UserSessionManager userSessionManager = new UserSessionManager(getApplication());
        SwitchCompat darkmode = findViewById(R.id.switch_darkmode);
        darkmode.setChecked((userSessionManager.getDarkMode()));
        AppDatabase database = DatabaseModule.getInstance(this);
        PersonInDao personInDao = database.personInDao();
        Person currentUser = personInDao.getPersonForID(userSessionManager.getCurrentUserId()).getValue();
        Log.d("SettingsActivity", "Current User:\nuserSessionManagerId:\t" + userSessionManager.getCurrentUserId() + "\tPerson:" + currentUser);
        Button setupUser = findViewById(R.id.btn_setup_user);
        setupUser.setOnClickListener(v->{
            Intent intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
            if (currentUser != null) {
                intent.putExtra("editUser", (Parcelable) currentUser);
            }
            startActivity(intent);
        });
    }
    @Override
    protected int getLayoutResourceId() {
       return R.layout.activity_settings;
    }


}
