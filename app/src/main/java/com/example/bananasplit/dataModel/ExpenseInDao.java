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
import java.util.Map;

@Dao
public interface ExpenseInDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Expense expense);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExpensePersonCrossRef(ExpensePersonCrossRef crossRef);
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

    @Transaction
    @Query("SELECT * FROM Expense WHERE SpenderID = :ownID OR SpenderID = :friendID")
    LiveData<List<Expense>> getExpensesByFriendId(int ownID, int friendID);

    @Transaction
    default void insertExpenseWithPersonsAndAmount(Expense expense, Map<Person,Float> personsWithAmounts) {
        int expenseID = (int) insert(expense);
        for (Map.Entry<Person,Float> pair : personsWithAmounts.entrySet()) {
            insertExpensePersonCrossRef(new ExpensePersonCrossRef(pair.getKey().getPersonID(), expenseID, pair.getValue()));
        }
    }
    @Query("SELECT SUM(amount) FROM Expense WHERE payerID = :userId")
    LiveData<Double> getTotalAmountPaidByUser(int userId);

    @Query("SELECT SUM(amount) / COUNT(participants) FROM Expense WHERE :userId IN (participants)")
    LiveData<Double> getTotalAmountOwedByUser(int userId);
}
