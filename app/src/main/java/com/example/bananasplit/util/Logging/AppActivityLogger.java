package com.example.bananasplit.util.Logging;

import com.example.bananasplit.dataModel.AppActivityTrackerFootprint;
import com.example.bananasplit.dataModel.AppActivityTrackerInDao;
import com.example.bananasplit.dataModel.AppDatabase;

/**
 * This class is responsible for logging activities.
 * Logged Activities are stored in the database and displayed in the activity screen to the user
 * It implements the ActivityLogger interface.
 * @author Arpad Horvath
 */
public class AppActivityLogger implements ActivityLogger {
    private final AppActivityTrackerInDao appActivityTrackerInDao;

    /**
     * Constructor for the AppActivityLogger class
     * @param database The database object
     */
    public AppActivityLogger(AppDatabase database) {
        appActivityTrackerInDao = database.appActivityTrackerInDao();
    }
    @Override
    public void logActivity(String initiator, String activityDetails, String activityLocation) {
        AppActivityTrackerFootprint activityTracker = new AppActivityTrackerFootprint.Builder()
                .setInitiator(initiator)
                .setActivityDetails(activityDetails)
                .setActivityLocation(activityLocation)
                .build();

        insertActivity(activityTracker);
    }

    /**
     * Inserts the activity into the database
     * @param activityTracker The activity to be inserted
     */
    private void insertActivity(AppActivityTrackerFootprint activityTracker) {
        new Thread(() -> {
            appActivityTrackerInDao.insert(activityTracker);
        }).start();
    }
}
