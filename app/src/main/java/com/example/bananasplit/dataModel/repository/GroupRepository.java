package com.example.bananasplit.dataModel.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Group;
import com.example.bananasplit.dataModel.GroupInDao;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.util.Logging.ActivityLogger;
import com.example.bananasplit.util.Logging.AppActivityLogger;
import com.example.bananasplit.util.UserSessionManager;

import java.util.List;

/**
 * Repository for the Group entity. This class is responsible for handling data operations for the Group entity.
 * @author Arpad Horvath
 */
public class GroupRepository {
    private final GroupInDao groupInDao;
    private final ActivityLogger activityLogger;
    private final UserSessionManager userSessionManager;

    /**
     * Constructor for the GroupRepository class.
     *
     * @param application The application context.
     */
    public GroupRepository(Application application) {
        AppDatabase database = DatabaseModule.getInstance(application);
        userSessionManager = new UserSessionManager(application);
        activityLogger = new AppActivityLogger(database);
        groupInDao = database.groupInDao();
    }

    /**
     * Returns a LiveData object containing a list of all groups.
     *
     * @return LiveData object containing a list of all groups.
     */
    public LiveData<List<Group>> getAllGroups() {
        return groupInDao.getAllGroups();
    }

    /**
     * inserts a group into the database.
     * @param group The group to be inserted.
     * @param persons The lost of persons to be inserted into the group.
     */
    public void insertGroupWithPersons(Group group, List<Person> persons) {
        groupInDao.insertGroupWithPersons(group, persons);
        logGroupCreated(group);
    }

    /**
     * Updates a group in the database.
     * @param group The group to be updated.
     */
    public void updateGroup(Group group) {
        groupInDao.update(group);
    }

    /**
     * Deletes a group from the database.
     * @param group
     */
    public void deleteGroup(Group group) {
        groupInDao.delete(group);
        groupInDao.deletePersonGroupCrossRefsForGroup(group.getGroupID());
        logGroupDeleted(group);
    }

    /**
     * Returns a LiveData object containing a list of persons in a group.
     * @param groupId The ID of the group.
     * @return LiveData object containing a list of persons in a group.
     */
    public LiveData<List<Person>> getMembersByGroupId(int groupId) {
        return groupInDao.getMembersByGroupId(groupId);
    }

    /**
     * Logs the creation of a group.
     * @param group The group that was created.
     */
    private void logGroupCreated(Group group) {
        activityLogger.logActivity(userSessionManager.getCurrentUserName(), "created group " + group.getName(), "Groups");
    }

    /**
     * Logs the deletion of a group.
     * @param group The group that was deleted.
     */
    private void logGroupDeleted(Group group) {
        activityLogger.logActivity(userSessionManager.getCurrentUserName(), "deleted group " + group.getName(), "Groups");
    }
}
