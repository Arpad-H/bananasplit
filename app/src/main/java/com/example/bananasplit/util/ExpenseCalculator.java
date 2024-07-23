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

/**
 * Utility class for calculating the total amount paid by and owed to the current user in a group.
 * @author Arpad Horvath
 */
public class ExpenseCalculator {
    private final ExpenseInDao expenseInDao;
    private final UserSessionManager userSessionManager;
    @Inject
    AppDatabase appDatabase;

    /**
     * Creates a new ExpenseCalculator instance.
     *
     * @param application The application context
     */
    public ExpenseCalculator(Application application) {
        appDatabase = DatabaseModule.getInstance(application);
        expenseInDao = appDatabase.expenseInDao();
        userSessionManager = new UserSessionManager(application);
    }

    /**
     * Returns the total amount paid by the current user in the specified group.
     *
     * @param groupId The ID of the group
     * @return A LiveData object containing the total amount paid by the current user
     */
    public LiveData<Double> getTotalAmountPaidByCurrentUserInGroup(int groupId) {
        return getTotalAmountForGroup(groupId, true);
    }

    /**
     * Returns the total amount owed to the current user in the specified group.
     *
     * @param groupId The ID of the group
     * @return A LiveData object containing the total amount owed to the current user
     */
    public LiveData<Double> getTotalAmountOwedByCurrentUserInGroup(int groupId) {
        return getTotalAmountForGroup(groupId, false);
    }

    /**
     * Returns the total amount paid by the current user in the specified group.
     *
     * @param groupId The ID of the group
     * @param isPaid  True if the amount is paid, false if it is owed
     * @return A LiveData object containing the total amount paid by the current user
     */
    private LiveData<Double> getTotalAmountForGroup(int groupId, boolean isPaid) {
        MediatorLiveData<Double> result = new MediatorLiveData<>();
        int currentUserId = userSessionManager.getCurrentUserId();
        LiveData<Double> liveDataFromDao = isPaid
                ? expenseInDao.getTotalAmountPaidByUserInGroup(currentUserId, groupId)
                : expenseInDao.getTotalAmountOwedByUserInGroup(currentUserId, groupId);

        result.addSource(liveDataFromDao, value -> result.setValue(value != null ? value : 0.0));
        return result;
    }
}
