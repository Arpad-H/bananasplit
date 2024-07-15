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
public interface GroupInDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Group group);

    @Delete
    void delete(Group group);

    @Update
    void update(Group group);

    @Transaction
    @Query("SELECT * FROM `Group` WHERE groupID = :ID")
    LiveData<List<GroupWithExpensesAndPersons>> getGroupWithExpensesAndPersonsByID(int ID);

    @Transaction
    @Query("SELECT * FROM `Group`")
    LiveData<List<Group>> getAllGroups();



}
