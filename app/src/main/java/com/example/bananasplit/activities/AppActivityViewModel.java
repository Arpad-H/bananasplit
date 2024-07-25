package com.example.bananasplit.activities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.AppActivityTrackerFootprint;
import com.example.bananasplit.dataModel.AppActivityTrackerInDao;
import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;

import java.util.List;

/**
 * This class is a ViewModel for the AppActivityTrackerFootprint entity.
 * @author Arpad Horvath
 */

public class AppActivityViewModel extends AndroidViewModel {

    private final LiveData<List<AppActivityTrackerFootprint>> allActivityTrackers;

    public AppActivityViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = DatabaseModule.getInstance(application);
        AppActivityTrackerInDao dao = database.appActivityTrackerInDao();
        allActivityTrackers = dao.getAllActivityTrackers();
    }

    /**
     * returns all the activity trackers.
     * @return allActivityTrackers
     */
    public LiveData<List<AppActivityTrackerFootprint>> getAllActivityTrackers() {
        return allActivityTrackers;
    }
}
