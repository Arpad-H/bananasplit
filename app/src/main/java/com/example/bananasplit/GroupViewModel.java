package com.example.bananasplit;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Group;
import com.example.bananasplit.dataModel.GroupInDao;
import com.example.bananasplit.dataModel.AppDatabase;
import java.util.List;

public class GroupViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private LiveData<List<Group>> allGroups;
private GroupInDao groupInDao;
    public GroupViewModel(@NonNull Application application) {
        super(application);

        appDatabase = DatabaseModule.getInstance(application);
        groupInDao = appDatabase.groupInDao();
        updateAllGroups();
    }

    private void updateAllGroups() {
        allGroups = groupInDao.getAllGroups();
    }

    //TODO insert, update, delete
    public void insert(Group group) {
        new Thread(() -> {
            groupInDao.insert(group);
        }).start();
    }

    public void update(Group group) {
        new Thread(() -> groupInDao.update(group)).start();

    }

    public void delete(Group group) {
        new Thread(() -> groupInDao.delete(group)).start();
    }

    public LiveData<List<Group>> getAllGroups() {
        return allGroups;
    }
}
