package com.example.bananasplit.dataModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonInDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Person person);

    @Update
    void update(Person person);

    @Delete
    void delete(Person person);

    @Transaction
    @Query("SELECT * FROM Person WHERE personID = :id")
    LiveData<List<PersonWithJourneys>> getPersonWithJourneys(int id);

    @Transaction
    @Query("SELECT * FROM Person WHERE Person.name LIKE :name")
    LiveData<List<PersonWithJourneys>> getPersonWithJourneysByName(String name);
}
