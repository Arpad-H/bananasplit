package com.example.bananasplit.friends;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.dataModel.PersonInDao;
import com.example.bananasplit.dataModel.repository.PersonRepository;
import com.example.bananasplit.util.Logging.ActivityLogger;
import com.example.bananasplit.util.Logging.AppActivityLogger;
import com.example.bananasplit.util.UserSessionManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The ViewModel for the FriendsFragment
* @author Dennis Brockmeyer, Arpad Horvath(where specified)
 */
public class FriendViewModel extends AndroidViewModel {
    private final PersonRepository repository;
    private final LiveData<List<Person>> allFriends;
    private final ExecutorService executorService;

    public FriendViewModel(@NonNull Application application) {
        super(application);
        repository = new PersonRepository(application);
        allFriends = repository.getFriends();
        executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Inserts a new Person into the database
     * @param person the new person to be inserted
     */
    public void insert(Person person) {
        executorService.execute(() -> repository.insert(person));
    }

    /**
     * Deletes a Person from the database
     * @param person the person to be deleted
     */
    public void delete(Person person) {
        executorService.execute(() -> repository.delete(person));
    }

    /**
     * Updates a Person from the database
     * @param person the person to be updated
     */
    public void update(Person person) {
        executorService.execute(() -> repository.update(person));
    }

    public LiveData<List<Person>> getAllFriends() {
        return allFriends;
    }

    /**
     * Called when the ViewModel is destroyed.
     * Ensures the ExecutorService is properly shut down.
     * @author Arpad Horvath
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}
