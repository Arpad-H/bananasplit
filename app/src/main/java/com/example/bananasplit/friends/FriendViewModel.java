package com.example.bananasplit.friends;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.dataModel.PersonInDao;

import java.util.List;

public class FriendViewModel extends AndroidViewModel {
    private final LiveData<List<Person>> allFriends;
    private final PersonInDao personInDao;

    public FriendViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = DatabaseModule.getInstance(application);
        personInDao = appDatabase.personInDao();
        allFriends = personInDao.getFriends();
    }

    public void insert(Person person) {
        new Thread(() -> {
            personInDao.insert(person);
        }).start();
    }

    public void delete(Person person) {
        new Thread(() -> {
            personInDao.delete(person);
        }).start();
    }

    public void update(Person person) {
        new Thread(() -> {
            personInDao.update(person);
        }).start();
    }


    public LiveData<List<Person>> getAllFriends() {
        return allFriends;
    }
}
