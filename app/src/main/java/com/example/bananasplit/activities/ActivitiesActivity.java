package com.example.bananasplit.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.AppActivityTrackerFootprint;
import com.example.bananasplit.databinding.ActivityActivitiesBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity displays a list of all activities that have been noted. It represents a kind of news feed on the latest Activities.
 * @author Arpad Horvath
 */
public class ActivitiesActivity extends BaseActivity {
    private ActivityActivitiesBinding binding;
    private AppActivityViewModel viewModel;
    private AppActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_activities, getContentContainer(), false);
        getContentContainer().addView(contentView);
        binding = ActivityActivitiesBinding.bind(contentView);


        setupRecyclerView();
        setupViewModel();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_activities;
    }

    /**
     * Sets up the recycler view for the activity list.
     */
    private void setupRecyclerView() {
        binding.recyclerViewActivity.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppActivityAdapter(new ArrayList<>());
        binding.recyclerViewActivity.setAdapter(adapter);
    }
    /**
     * Sets up the view model for the activity list.
     */
    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(AppActivityViewModel.class);
        viewModel.getAllActivityTrackers().observe(this, this::updateUI);
    }

    /**
     * Updates the UI with the given list of activity trackers.
     * @param activityTrackers the list of activity trackers to display
     */
    private void updateUI(List<AppActivityTrackerFootprint> activityTrackers) {
        adapter.updateEntries(activityTrackers);
    }
}