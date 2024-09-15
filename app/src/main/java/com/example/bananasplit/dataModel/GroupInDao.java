package com.example.bananasplit.dataModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

/**
 * Data Access Object for the Group class.
 * @author Dennis Brockmeyer, Arpad Horvath
 */
@Dao
public interface GroupInDao {

    /**
     * Inserts a new group into the database.
     * @param group The group to be inserted.
     * @return The ID of the inserted group.
     * @author Arpad Horvath
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Group group);

    /**
     * Inserts a new PersonGroupCrossRef into the database.
     * @param crossRef The PersonGroupCrossRef to be inserted.
     * @author Arpad Horvath
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPersonGroupCrossRef(PersonGroupCrossRef crossRef);

    /**
     * Deletes a group from the database.
     * @param group The group to be deleted.
     * @author Arpad Horvath
     */
    @Delete
    void delete(Group group);

    /**
     * Deletes a PersonGroupCrossRef from the database with the given groupID.
     * @param groupId The ID of the group whose PersonGroupCrossRefs are to be deleted.
     * @author Arpad Horvath
     */
    @Query("DELETE FROM PersonGroupCrossRef WHERE groupID = :groupId")
    void deletePersonGroupCrossRefsForGroup(int groupId);

    /**
     * Updates a group in the database.
     * @param group The group to be updated.
     * @author Arpad Horvath
     */
    @Update
    void update(Group group);

    /**
     * gets the expenses and persons for the group with the given ID.
     * @param ID The ID of the group.
     * @author Arpad Horvath
     */
    @Transaction
    @Query("SELECT * FROM `Group` WHERE groupID = :ID")
    LiveData<List<GroupWithExpensesAndPersons>> getGroupWithExpensesAndPersonsByID(int ID);

    /**
     * gets all groups from the database.
     * @return A list of all groups in the database.
     * @author Arpad Horvath
     */
    @Transaction
    @Query("SELECT * FROM `Group`")
    LiveData<List<Group>> getAllGroups();

    /**
     * gets all members for a group from the database.
     * @return A list of all members in the group.
     * @author Arpad Horvath
     */
    @Transaction
    @Query("SELECT * FROM Person INNER JOIN PersonGroupCrossRef ON Person.personID = PersonGroupCrossRef.personID WHERE PersonGroupCrossRef.groupID = :groupId")
    LiveData<List<Person>> getMembersByGroupId(int groupId);

    /**
     * Inserts a group with a list of persons into the database.
     * @param group The group to be inserted.
     * @param persons The list of persons to be inserted.
     * @author Arpad Horvath
     */
    @Transaction
    default void insertGroupWithPersons(Group group, List<Person> persons) {
        int grpID = (int) insert(group);
        for (Person person : persons) {
            insertPersonGroupCrossRef(new PersonGroupCrossRef(person.getPersonID(), grpID));
        }
//        return grpID;
    }

    /**
     * Adds new Members to an existing Group
     * @param grpID the group to be update
     * @param persons The list of persons to be inserted
     */
    @Transaction
    default void addMembers(int grpID, List<Person> persons) {
        for (Person person : persons) {
            insertPersonGroupCrossRef(new PersonGroupCrossRef(person.getPersonID(), grpID));
        }
    }


    /**
     * gets a group from the database with the given ID.
     * @param ID The ID of the group.
     * @return The group with the given ID.
     * @author Arpad Horvath
     */
    @Transaction
    @Query("SELECT * FROM `Group` WHERE groupID = :ID")
    LiveData<Group> getGroupByID(int ID);
}
