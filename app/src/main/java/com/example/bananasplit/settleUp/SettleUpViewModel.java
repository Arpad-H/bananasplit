package com.example.bananasplit.settleUp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Expense;
import com.example.bananasplit.dataModel.ExpenseCategory;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.dataModel.repository.ExpenseRepository;
import com.example.bananasplit.dataModel.repository.PersonRepository;

import com.example.bananasplit.dataModel.Currency;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettleUpViewModel extends AndroidViewModel {
    private final LiveData<List<Person>> allFriends;
    private PersonRepository personRepository;
    private ExpenseRepository expenseRepository;
    private ExecutorService executorService;

    public SettleUpViewModel(@NonNull Application application) {
        super(application);
        personRepository = new PersonRepository(application);
        expenseRepository = new ExpenseRepository(application);
        allFriends = personRepository.getFriends();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void settleUpFriends(Person ownID, Person friendID, float amount, Currency currency) {
        String name = "settleUp";
        Expense newExpense = new Expense(name, ownID, 1, amount, currency, ExpenseCategory.SETTLEMENT);
    }


    public LiveData<List<Person>> getAllFriends() {
        return allFriends;
    }

    /**
     * Called when the ViewModel is destroyed.
     * Ensures the ExecutorService is properly shut down.
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}
