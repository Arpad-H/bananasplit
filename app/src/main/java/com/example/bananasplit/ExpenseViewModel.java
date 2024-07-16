package com.example.bananasplit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Expense;
import com.example.bananasplit.dataModel.ExpenseInDao;
import com.example.bananasplit.dataModel.AppDatabase;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private ExpenseInDao expenseInDao;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = DatabaseModule.getInstance(application);
        expenseInDao = database.expenseInDao();
    }

    public LiveData<List<Expense>> getExpensesByGroupId(int groupId) {
        return expenseInDao.getExpensesByGroupId(groupId);
    }

    public void insert(Expense expense) {
        new Thread(() -> expenseInDao.insert(expense)).start();
    }

    public void delete(Expense expense) {
        new Thread(() -> expenseInDao.delete(expense)).start();
    }

    public void update(Expense expense) {
        new Thread(() -> expenseInDao.update(expense)).start();
    }
}
