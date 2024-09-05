package com.example.bananasplit.dataModel.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.dataModel.PersonInDao;
import com.example.bananasplit.util.Logging.ActivityLogger;
import com.example.bananasplit.util.Logging.AppActivityLogger;
import com.example.bananasplit.util.UserSessionManager;

import java.util.List;

/**
 * Repository for the Person entity. This class is responsible for handling data operations for the Person entity.
 * @author Dennis Brockmeyer
 */
public class PersonRepository {
    private final PersonInDao personInDao;
    private final ActivityLogger activityLogger;
    private final UserSessionManager userSessionManager;


    /**
     * Constructor for the FriendRepository class.
     *
     * @param application The application context.
     */
    public PersonRepository(Application application) {
        AppDatabase database = DatabaseModule.getInstance(application);
        this.personInDao = database.personInDao();
        this.userSessionManager = new UserSessionManager(application);
        this.activityLogger = new AppActivityLogger(database);
    }

    /**
     * Inserts a new Friend into the database
     * @param friend The friend that is created.
     */
    public long insert(Person friend) {
        long id = personInDao.insert(friend);
        logFriendCreated(friend);
        return id;
    }

    /**
     * Deletes a Friend from the database
     * @param friend The friend that is deleted.
     */
    public void delete(Person friend) {
        personInDao.delete(friend);
        logFriendDeleted(friend);
    }

    /**
     * Updates a Friend in the database
     * @param friend The friend that is updated.
     */
    public void update(Person friend) {
        personInDao.update(friend);
    }

    /**
     * returns the Livedata of all friends as a List from the database
     * @return all friends
     */
    public LiveData<List<Person>> getFriends() {
        return personInDao.getFriends();
    }

    /**
     * returns the Livedata of the Person for the personID from the Database
     * @param personID personID to be queried
     * @return the Livedata for the Person
     */
    public LiveData<Person> getPersonForID(int personID) {
        return personInDao.getPersonForID(personID);
    }

    /**
     * Logs the creation of a friend.
     * @param friend The friend that was created.
     * @author Arpad Horvath
     */
    private void logFriendCreated(Person friend) {
        activityLogger.logActivity(userSessionManager.getCurrentUserName(), "created friend " + friend.getName(), "Friends");
    }

    /**
     * Logs the deletion of a friend.
     * @param friend The friend that was deleted.
     * @author Arpad Horvath
     */
    private void logFriendDeleted(Person friend) {
        activityLogger.logActivity(userSessionManager.getCurrentUserName(), "deleted friend " + friend.getName(), "Friends");
    }

    public Person getCurrentUser(int currentUserId) {
        return personInDao.getCurrentUser(currentUserId);
    }
}
