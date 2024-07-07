package com.example.bananasplit.dataModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface JourneyInDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Journey journey);

    @Delete
    void delete(Journey journey);

    @Transaction
    @Query("SELECT * FROM Journey WHERE journeyID = :ID")
    LiveData<List<JourneyWithExpensesAndPersons>> getJourneyWithExpensesAndPersonsByID(int ID);

    @Query("SELECT * FROM Journey")
    LiveData<List<Journey>> getAllJourneys();
}
