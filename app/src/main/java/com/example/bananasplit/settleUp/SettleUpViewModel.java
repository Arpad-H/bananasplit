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
import com.example.bananasplit.dataModel.repository.PersonRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class SettleUpViewModel extends AndroidViewModel {
    private final LiveData<List<Person>> allFriends;
    private PersonRepository repository;
    private ExecutorService executorService;

    public SettleUpViewModel(@NonNull Application application) {
        super(application);
        repository = new PersonRepository(application);
        allFriends = repository.getFriends();
        executorService = Executors.newSingleThreadExecutor();
    }




    public LiveData<List<Person>> getAllFriends() {
        return allFriends;
    }
}
