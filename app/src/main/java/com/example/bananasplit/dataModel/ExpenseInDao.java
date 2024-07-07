package com.example.bananasplit.dataModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface ExpenseInDao {

    @Transaction
    @Query("SELECT * FROM Expense WHERE id = :id")
    LiveData<Expense> getExpenseByID(int id);
}
