package com.example.bananasplit.util;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.ExpenseInDao;
import com.example.bananasplit.dataModel.ExpenseInDao_Impl;

public class ExpenseCalculator {
    private final ExpenseInDao expenseInDao;
    private final UserSessionManager userSessionManager;

    public ExpenseCalculator(Context context) {
        expenseInDao = DatabaseModule.getInstance(context).expenseInDao();
        userSessionManager = new UserSessionManager(context);
    }

    public LiveData<Double> getTotalAmountPaidByCurrentInGroup(int groupId) {
        MediatorLiveData<Double> result = new MediatorLiveData<>();
        int currentUserId = userSessionManager.getCurrentUserId();
        LiveData<Double> liveDataFromDao = expenseInDao.getTotalAmountPaidByUserInGroup(currentUserId, groupId);
        result.addSource(liveDataFromDao, value -> {
            if (value == null) {
                result.setValue(0.0); // Default value when no expenses are registered
            } else {
                result.setValue(value);
            }
        });

        return result;
    }

    public LiveData<Double> getTotalAmountOwedByCurrentUserInGroup(int groupId) {
        MediatorLiveData<Double> result = new MediatorLiveData<>();
        int currentUserId = userSessionManager.getCurrentUserId();
        LiveData<Double> liveDataFromDao = expenseInDao.getTotalAmountOwedByUserInGroup(currentUserId, groupId);

        result.addSource(liveDataFromDao, value -> {
            if (value == null) {
                result.setValue(0.0); // Default value when no expenses are registered
            } else {
                result.setValue(value);
            }
        });

        return result;
    }
}
