package com.example.bananasplit.dataModel.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Group;
import com.example.bananasplit.dataModel.GroupInDao;
import com.example.bananasplit.dataModel.Person;

import java.util.List;

public class GroupRepository {
    private final GroupInDao groupInDao;

    public GroupRepository(Application application) {
        AppDatabase database = DatabaseModule.getInstance(application);
        groupInDao = database.groupInDao();
    }

    public LiveData<List<Group>> getAllGroups() {
        return groupInDao.getAllGroups();
    }

    public void insertGroupWithPersons(Group group, List<Person> persons) {
        groupInDao.insertGroupWithPersons(group, persons);
    }

    public void updateGroup(Group group) {
        groupInDao.update(group);
    }

    public void deleteGroup(Group group) {
        groupInDao.delete(group);
        groupInDao.deletePersonGroupCrossRefsForGroup(group.getGroupID());
    }

    public LiveData<List<Person>> getMembersByGroupId(int groupId) {
        return groupInDao.getMembersByGroupId(groupId);
    }
}
