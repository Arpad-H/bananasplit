package com.example.bananasplit.activities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.AppActivityTracker;
import com.example.bananasplit.dataModel.AppActivityTrackerInDao;
import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;

import java.util.List;

public class AppActivityViewModel extends AndroidViewModel {

    private final LiveData<List<AppActivityTracker>> allActivityTrackers;

    public AppActivityViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = DatabaseModule.getInstance(application);
        AppActivityTrackerInDao dao = database.appActivityTrackerInDao();
        allActivityTrackers = dao.getAllActivityTrackers();
    }

    public LiveData<List<AppActivityTracker>> getAllActivityTrackers() {
        return allActivityTrackers;
    }
}
