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

public class GroupRepository {
    private final GroupInDao groupInDao;
    private final ActivityLogger activityLogger;
    private final UserSessionManager userSessionManager;
    public GroupRepository(Application application) {
        AppDatabase database = DatabaseModule.getInstance(application);
        userSessionManager = new UserSessionManager(application);
        activityLogger = new AppActivityLogger(database);
        groupInDao = database.groupInDao();
    }

    public LiveData<List<Group>> getAllGroups() {
        return groupInDao.getAllGroups();
    }

    public void insertGroupWithPersons(Group group, List<Person> persons) {
        groupInDao.insertGroupWithPersons(group, persons);
        logGroupCreated(group);
    }

    public void updateGroup(Group group) {
        groupInDao.update(group);
    }

    public void deleteGroup(Group group) {
        groupInDao.delete(group);
        groupInDao.deletePersonGroupCrossRefsForGroup(group.getGroupID());
        logGroupDeleted(group);
    }

    public LiveData<List<Person>> getMembersByGroupId(int groupId) {
        return groupInDao.getMembersByGroupId(groupId);
    }
    private void logGroupCreated(Group group) {
        activityLogger.logActivity(userSessionManager.getCurrentUserName(), "created group " + group.getName(), "Groups");
    }
    private void logGroupDeleted(Group group) {
        activityLogger.logActivity(userSessionManager.getCurrentUserName(), "deleted group " + group.getName(), "Groups");
    }
}
