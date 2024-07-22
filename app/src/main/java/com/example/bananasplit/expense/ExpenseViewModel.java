package com.example.bananasplit.expense;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.AppActivityTracker;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Expense;
import com.example.bananasplit.dataModel.ExpenseInDao;
import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.GroupInDao;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.util.ActivityLogger;
import com.example.bananasplit.util.AppActivityLogger;
import com.example.bananasplit.util.UserSessionManager;

import java.util.List;
import java.util.Map;

public class ExpenseViewModel extends AndroidViewModel {
    private final ExpenseInDao expenseInDao;
    private final ActivityLogger activityLogger;
    private final UserSessionManager userSessionManager;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = DatabaseModule.getInstance(application);
        expenseInDao = database.expenseInDao();
        activityLogger = new AppActivityLogger(database);
        userSessionManager = new UserSessionManager(application);
    }

    public LiveData<List<Expense>> getExpensesByGroupId(int groupId) {
        return expenseInDao.getExpensesByGroupId(groupId);
    }

    public LiveData<List<Expense>> getExpensesByFriendId(int ownID, int friendID) {
        return expenseInDao.getExpensesByFriendId(ownID, friendID);
    }

    public void insert(Expense expense) {
        new Thread(() -> {
            expenseInDao.insert(expense);

        }).start();

    }

    public void delete(Expense expense) {
        new Thread(() -> expenseInDao.delete(expense)).start();
    }

    public void update(Expense expense) {
        new Thread(() -> expenseInDao.update(expense)).start();
    }

    public void insertExpenseWithPersonsAndAmount(Expense expense, Map<Person, Float> personsWithAmounts) {
        new Thread(() -> expenseInDao.insertExpenseWithPersonsAndAmount(expense, personsWithAmounts)).start();
        logAddExpense(expense);
    }
    private void logAddExpense(Expense expense) {
        String username = userSessionManager.getCurrentUserName();
        GroupInDao groupInDao = DatabaseModule.getInstance(getApplication()).groupInDao();
        groupInDao.getGroupByID(expense.getGroupID()).observeForever(group -> {
            String action = "added expense: " + expense.getDescription() + ", in " + group.getName();//TODO add to strings
            activityLogger.logActivity(username, action , group.getName());
        });
    }
}
