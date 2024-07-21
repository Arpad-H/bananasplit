package com.example.bananasplit.groups;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Group;
import com.example.bananasplit.dataModel.GroupInDao;
import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.Person;

import java.util.List;

public class GroupViewModel extends AndroidViewModel {
    private final AppDatabase appDatabase;
    private LiveData<List<Group>> allGroups;
//    private LiveData<List<Person>> allPersons;
private final GroupInDao groupInDao;
    public GroupViewModel(@NonNull Application application) {
        super(application);

        appDatabase = DatabaseModule.getInstance(application);
        groupInDao = appDatabase.groupInDao();
        updateAllGroups();
//        updateAllPersons();
    }

    private void updateAllGroups() {
        allGroups = groupInDao.getAllGroups();
    }
//    private void updateAllPersons() {
//        allPersons = groupInDao.getPersonIDsForGroup();
//    }

    //TODO insert, update, delete
    public void insert(Group group, List<Person> persons) {
        new Thread(() -> {
            groupInDao.insertGroupWithPersons(group, persons);
        }).start();
    }
//    public void insert(Group group) {
//        new Thread(() -> {
//            groupInDao.insert(group);
//        }).start();
//    }

    public void update(Group group) {
        new Thread(() -> groupInDao.update(group)).start();

    }

    public void delete(Group group) {
        new Thread(() -> groupInDao.delete(group)).start();
        new Thread(() -> groupInDao.deletePersonGroupCrossRefsForGroup(group.getGroupID())).start();

    }

    public LiveData<List<Person>> getMembersByGroupId(int groupId) {
        return groupInDao.getMembersByGroupId(groupId);
    }
    public LiveData<List<Group>> getAllGroups() {
        return allGroups;
    }
}
