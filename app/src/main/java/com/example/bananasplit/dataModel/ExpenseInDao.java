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
public interface ExpenseInDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Expense expense);
    @Update
    void update(Expense expense);

    @Delete
    void delete(Expense expense);


    @Transaction
    @Query("SELECT * FROM Expense WHERE id = :id")
    LiveData<Expense> getExpenseByID(int id);

    @Transaction
    @Query("SELECT * FROM Expense WHERE groupID = :groupID")
    LiveData<List<Expense>> getExpensesByGroupId(int groupID);
}
