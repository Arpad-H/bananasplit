package com.example.bananasplit.util;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.ExpenseInDao;
import com.example.bananasplit.dataModel.ExpenseInDao_Impl;

public class ExpenseCalculator {
    private ExpenseInDao expenseInDao; // Assuming you have a DAO for accessing expenses
    private UserSessionManager userSessionManager;

    public ExpenseCalculator(Context context) {
        expenseInDao = DatabaseModule.getInstance(context).expenseInDao(); // Assuming you have a database setup
        userSessionManager = new UserSessionManager(context);
    }

    public LiveData<Double> getTotalAmountPaidByCurrentUser() {
        int currentUserId = userSessionManager.getCurrentUserId();
        return expenseInDao.getTotalAmountPaidByUser(currentUserId);
    }

    public LiveData<Double> getTotalAmountOwedByCurrentUser() {
        int currentUserId = userSessionManager.getCurrentUserId();
        return expenseInDao.getTotalAmountOwedByUser(currentUserId);
    }
}
