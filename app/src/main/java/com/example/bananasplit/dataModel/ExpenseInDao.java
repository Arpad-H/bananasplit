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

/**
 * This interface is used to interact with the Expense table in the database.
 *
 * @author Dennis Brockmeyer, Arpad Horvath
 */

@Dao
public interface ExpenseInDao {
    /**
     * Inserts a new expense into the database.
     *
     * @param expense The expense to be inserted.
     * @return The ID of the inserted expense.
     * @author Dennis Brockmeyer
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Expense expense);

    /**
     * Inserts a new ExpensePersonCrossRef into the database.
     *
     * @param crossRef The ExpensePersonCrossRef to be inserted.
     * @author Dennis Brockmeyer
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExpensePersonCrossRef(ExpensePersonCrossRef crossRef);

    /**
     * Updates an expense in the database.
     *
     * @param expense The expense to be updated.
     * @author Dennis Brockmeyer
     */
    @Update
    void update(Expense expense);

    /**
     * Deletes an expense in the database.
     *
     * @param expense The expense to be deleted.
     * @author Dennis Brockmeyer
     */
    @Delete
    void delete(Expense expense);


    /**
     * Returns a single Expense by its ID
     * @param id the ID for the Expense
     * @return the expense of that ID
     * @author Dennis Brockmeyer
     */
    @Transaction
    @Query("SELECT * FROM Expense WHERE id = :id")
    LiveData<Expense> getExpenseByID(int id);

    /**
     * Returns all expenses of a group
     *
     * @param groupID the groupID of the group
     * @return a list of all expenses of the group
     * @author Arpad Horvath
     */
    @Transaction
    @Query("SELECT * FROM Expense WHERE groupID = :groupID")
    LiveData<List<Expense>> getExpensesByGroupId(int groupID);

    @Transaction
    @Query("SELECT * FROM Expense WHERE SpenderID = :ownID OR SpenderID = :friendID")
    LiveData<List<Expense>> getExpensesByFriendId(int ownID, int friendID);

    /**
     * insert an expense with a list of persons and their amounts, representing the split in the bill
     *
     * @param expense            the expense to be inserted
     * @param personsWithAmounts a map of persons and their amounts
     * @author Arpad Horvath
     */
    @Transaction
    default void insertExpenseWithPersonsAndAmount(Expense expense, Map<Person, Float> personsWithAmounts) {
        int expenseID = (int) insert(expense);
        for (Map.Entry<Person, Float> pair : personsWithAmounts.entrySet()) {
            insertExpensePersonCrossRef(new ExpensePersonCrossRef(pair.getKey().getPersonID(), expenseID, pair.getValue()));
        }
    }

    /**
     * Returns the total amount of money spent by a user in a group
     *
     * @param userId   the ID of the user
     * @param groupId  the ID of the group
     * @return the total amount of money spent by the user in the group
     * @author Arpad Horvath
     */
    @Query("SELECT COALESCE(SUM(amount), 0.0) FROM Expense WHERE spenderID = :userId and groupID = :groupId")
    LiveData<Double> getTotalAmountPaidByUserInGroup(int userId, int groupId);

    /**
     * Returns the total amount of money owed by a user in a group
     *
     * @param userId   the ID of the user
     * @param groupId  the ID of the group
     * @return the total amount of money owed by the user in the group
     * @author Arpad Horvath
     */
//    @Query("SELECT SUM(ep.amount) FROM ExpensePersonCrossRef ep WHERE ep.personID = :userId and ep.expenseID IN (SELECT e.id FROM Expense e WHERE e.groupID = :groupId)") //TODO: not working as intended
    @Query("SELECT SUM(ep.amount) FROM Expense e JOIN ExpensePersonCrossRef ep ON e.id = ep.expenseID JOIN Person p ON ep.personID = p.personID JOIN `Group` g ON e.groupID = g.groupID WHERE p.personID = :userId AND g.groupID = :groupId")
    LiveData<Double> getTotalAmountOwedByUserInGroup(int userId, int groupId);
}
