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

/**
 * ViewModel for managing Group-related data and operations.
 * @author Arpad Horvath
 */
public class GroupViewModel extends AndroidViewModel {
    private final GroupRepository groupRepository;
    private final LiveData<List<Group>> allGroups;
    private final ExecutorService executorService;

    /**
     * Constructor for GroupViewModel.
     *
     * @param application The application context.
     */
    public GroupViewModel(@NonNull Application application) {
        super(application);
        groupRepository = new GroupRepository(application);
        allGroups = groupRepository.getAllGroups();
        executorService = Executors.newSingleThreadExecutor();
    }
    /**
     * Returns a LiveData list of all groups.
     *
     * @return A LiveData list of all groups.
     */
    public LiveData<List<Group>> getAllGroups() {
        return allGroups;
    }
    /**
     * Inserts a group and its associated persons into the repository.
     *
     * @param group   The group to insert.
     * @param persons The list of persons associated with the group.
     */
    public void insert(Group group, List<Person> persons) {
        executorService.execute(() -> groupRepository.insertGroupWithPersons(group, persons));
    }
    /**
     * Updates a group in the repository.
     *
     * @param group The group to update.
     */
    public void update(Group group) {
        executorService.execute(() -> groupRepository.updateGroup(group));
    }
    /**
     * Deletes a group from the repository.
     *
     * @param group The group to delete.
     */
    public void delete(Group group) {
        executorService.execute(() -> groupRepository.deleteGroup(group));
    }
    /**
     * Returns a LiveData list of members by group ID.
     *
     * @param groupId The ID of the group.
     * @return A LiveData list of members by group ID.
     */
    public LiveData<List<Person>> getMembersByGroupId(int groupId) {
        return groupRepository.getMembersByGroupId(groupId);
    }
    /**
     * Called when the ViewModel is destroyed.
     * Ensures the ExecutorService is properly shut down.
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}
