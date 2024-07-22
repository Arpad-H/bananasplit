package com.example.bananasplit.dataModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface AppActivityTrackerInDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(AppActivityTracker appActivityTracker);
    @Transaction
    @Query("SELECT * FROM `AppActivityTracker`")
    LiveData<List<AppActivityTracker>> getAllActivityTrackers();
}
