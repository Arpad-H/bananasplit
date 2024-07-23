package com.example.bananasplit.dataModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

/**
 *  Defines the methods that will be used to interact with the database
 * @author Arpad Horvath
 */
@Dao
public interface AppActivityTrackerInDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(AppActivityTrackerFootprint appActivityTrackerSignature);
    @Transaction
    @Query("SELECT * FROM AppActivityTrackerFootprint")
    LiveData<List<AppActivityTrackerFootprint>> getAllActivityTrackers();
}
