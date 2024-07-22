package com.example.bananasplit.groups;

import android.app.Activity;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bananasplit.dataModel.AppActivityTracker;
import com.example.bananasplit.dataModel.Group;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.dataModel.repository.GroupRepository;
import com.example.bananasplit.util.ActivityLogger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GroupViewModel extends AndroidViewModel {
    private final GroupRepository groupRepository;
    private final LiveData<List<Group>> allGroups;
    private final ExecutorService executorService;


    public GroupViewModel(@NonNull Application application) {
        super(application);
        groupRepository = new GroupRepository(application);
        allGroups = groupRepository.getAllGroups();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Group>> getAllGroups() {
        return allGroups;
    }

    public void insert(Group group, List<Person> persons) {
        executorService.execute(() -> groupRepository.insertGroupWithPersons(group, persons));
    }

    public void update(Group group) {
        executorService.execute(() -> groupRepository.updateGroup(group));
    }

    public void delete(Group group) {
        executorService.execute(() -> groupRepository.deleteGroup(group));
    }

    public LiveData<List<Person>> getMembersByGroupId(int groupId) {
        return groupRepository.getMembersByGroupId(groupId);
    }
}
