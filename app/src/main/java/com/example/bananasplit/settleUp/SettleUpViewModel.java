package com.example.bananasplit.settleUp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.ExpenseInDao;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.dataModel.PersonInDao;

import java.util.List;

import javax.inject.Inject;

public class SettleUpViewModel extends AndroidViewModel {
    private final LiveData<List<Person>> allFriends;
    private final PersonInDao personInDao;
    private final ExpenseInDao expenseInDao;
    private Person person;
    @Inject
    AppDatabase appDatabase;

    public SettleUpViewModel(@NonNull Application application) {
        super(application);

        personInDao = appDatabase.personInDao();
        expenseInDao = appDatabase.expenseInDao();
        allFriends = personInDao.getFriends();
    }

    public LiveData<List<Person>> getAllFriends() {
        return allFriends;
    }
}
