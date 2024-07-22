package com.example.bananasplit.util;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.ExpenseInDao;
import com.example.bananasplit.dataModel.ExpenseInDao_Impl;

import javax.inject.Inject;

public class ExpenseCalculator {
    private final ExpenseInDao expenseInDao;
    private final UserSessionManager userSessionManager;
    @Inject
    AppDatabase appDatabase;

    public ExpenseCalculator(Application application) {
        expenseInDao = appDatabase.expenseInDao();
        userSessionManager = new UserSessionManager(application);
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
