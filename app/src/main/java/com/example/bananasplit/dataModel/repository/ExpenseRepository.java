package com.example.bananasplit.dataModel.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Expense;
import com.example.bananasplit.dataModel.ExpenseInDao;
import com.example.bananasplit.dataModel.Group;
import com.example.bananasplit.dataModel.GroupInDao;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.util.Logging.ActivityLogger;
import com.example.bananasplit.util.Logging.AppActivityLogger;
import com.example.bananasplit.util.UserSessionManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository for the Expense entity.
 * Handles the data operations for the Expense entity.
 * @author Arpad Horvath
 */
public class ExpenseRepository {
    private final ExpenseInDao expenseInDao;
    private final GroupInDao groupInDao;
    private final ActivityLogger activityLogger;
    private final UserSessionManager userSessionManager;
    private final ExecutorService executorService;
    private final Handler mainHandler;

    /**
     * Constructor for the ExpenseRepository.
     * @param application the application
     */
    public ExpenseRepository(Application application) {
        AppDatabase database = DatabaseModule.getInstance(application);
        this.expenseInDao = database.expenseInDao();
        this.groupInDao = database.groupInDao();
        this.activityLogger = new AppActivityLogger(database);
        this.userSessionManager = new UserSessionManager(application);
        this.executorService = Executors.newFixedThreadPool(4);
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Returns all expenses in the database matching the given groupId.
     * @param groupId the ID of the group
     * @return LiveData<List<Expense>>
     */
    public LiveData<List<Expense>> getExpensesByGroupId(int groupId) {
        return expenseInDao.getExpensesByGroupId(groupId);
    }

    /**
     * Returns all expenses between the user with the given ownID and the user with the given friendID.
     * @param ownID
     * @param friendID
     * @return LiveData<List<Expense>>
     */
    public LiveData<List<Expense>> getExpensesByFriendId(int ownID, int friendID) {
        return expenseInDao.getExpensesByFriendId(ownID, friendID);
    }

    /**
     * Inserts an expense into the database.
     * @param expense the expense to insert
     */
    public void insert(Expense expense) {
        executorService.execute(() -> expenseInDao.insert(expense));
    }

    /**
     * Deletes an expense from the database.
     * @param expense the expense to delete
     */
    public void delete(Expense expense) {
        executorService.execute(() -> expenseInDao.delete(expense));
    }

    /**
     * Updates an expense in the database.
     * @param expense the expense to update
     */
    public void update(Expense expense) {
        executorService.execute(() -> expenseInDao.update(expense));
    }

    /**
     * Inserts an expense with persons and amounts into the database.
     * @param expense the expense to insert
     * @param personsWithAmounts a map of persons and their respective amounts
     */
    public void insertExpenseWithPersonsAndAmount(Expense expense, Map<Person, Float> personsWithAmounts) {
        executorService.execute(() -> {
            expenseInDao.insertExpenseWithPersonsAndAmount(expense, personsWithAmounts);
            mainHandler.post(() -> logAddExpense(expense)); // Post to the main thread
        });
    }

    /**
     * Logs the activity of adding an expense to a group.
     * @param expense the expense that was added
     */
    private void logAddExpense(Expense expense) {
        String username = userSessionManager.getCurrentUserName();
        LiveData<Group> groupLiveData = groupInDao.getGroupByID(expense.getGroupID());
        Observer<Group> groupObserver = new Observer<Group>() {
            @Override
            public void onChanged(Group group) {
                if (group != null) {
                    String action = "added expense: " + expense.getDescription() + ", in " + group.getName();
                    activityLogger.logActivity(username, action, group.getName());

                    groupLiveData.removeObserver(this);
                }
            }
        };
        groupLiveData.observeForever(groupObserver);
    }
}
