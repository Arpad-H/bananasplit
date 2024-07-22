package com.example.bananasplit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivitiesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentViewForActivity(getLayoutResourceId());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_activities;
    }
}