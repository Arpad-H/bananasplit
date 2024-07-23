package com.example.bananasplit.expense;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.Expense;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.dataModel.repository.ExpenseRepository;

import java.util.List;
import java.util.Map;

/**
 * ViewModel for the Expense entity.
 * @author Arpad Horvath, Dennis Brockmeyer (where specified)
 */
public class ExpenseViewModel extends AndroidViewModel {
    private final ExpenseRepository repository;

    /**
     * Constructor for the ExpenseViewModel.
     * @param application the application
     */
    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ExpenseRepository(application);
    }

    /**
     * Returns all expenses in the database matching the given groupID.
     * @param groupId the ID of the group
     * @return LiveData<List<Expense>>
     */
    public LiveData<List<Expense>> getExpensesByGroupId(int groupId) {
        return repository.getExpensesByGroupId(groupId);
    }

    /**
     * Returns all expenses between the user with the given ownID and the user with the given friendID.
     * @param ownID the ID of the user
     * @param friendID the ID of the friend
     * @return LiveData<List<Expense>>
     * @author Dennis Brockmeyer
     */
    public LiveData<List<Expense>> getExpensesByFriendId(int ownID, int friendID) {
        return repository.getExpensesByFriendId(ownID, friendID);
    }

    /**
     * Inserts an expense into the database.
     * @param expense the expense to be inserted
     */
    public void insert(Expense expense) {
        repository.insert(expense);
    }

    /**
     * Deletes an expense from the database.
     * @param expense
     */
    public void delete(Expense expense) {
        repository.delete(expense);
    }
    /**
     * Updates an expense in the database.
     * @param expense the expense to be updated
     */
    public void update(Expense expense) {
        repository.update(expense);
    }

    /**
     * Inserts an expense with persons and amounts into the database.
     * @param expense the expense to be inserted
     * @param personsWithAmounts the persons with their amounts
     */
    public void insertExpenseWithPersonsAndAmount(Expense expense, Map<Person, Float> personsWithAmounts) {
        repository.insertExpenseWithPersonsAndAmount(expense, personsWithAmounts);
    }
}
