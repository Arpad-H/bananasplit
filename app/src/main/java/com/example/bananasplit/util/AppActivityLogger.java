package com.example.bananasplit.util;

import com.example.bananasplit.dataModel.AppActivityTracker;
import com.example.bananasplit.dataModel.AppActivityTrackerInDao;
import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Person;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.AndroidEntryPoint;

public class AppActivityLogger implements ActivityLogger {
    private final AppActivityTrackerInDao appActivityTrackerInDao;




    public AppActivityLogger(AppDatabase database) {
        appActivityTrackerInDao = database.appActivityTrackerInDao();
    }
    @Override
    public void logActivity(String initiator, String activityDetails, String activityLocation) {
        AppActivityTracker activityTracker = new AppActivityTracker.Builder()
                .setInitiator(initiator)
                .setActivityDetails(activityDetails)
                .setActivityLocation(activityLocation)
                .build();

        insertActivity(activityTracker);
    }

    private void insertActivity(AppActivityTracker activityTracker) {
        new Thread(() -> {
            appActivityTrackerInDao.insert(activityTracker);
        }).start();
    }
}
