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

@Dao
public interface GroupInDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Group group);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPersonGroupCrossRef(PersonGroupCrossRef crossRef);
    @Delete
    void delete(Group group);

    @Query("DELETE FROM PersonGroupCrossRef WHERE groupID = :groupId")
    void deletePersonGroupCrossRefsForGroup(int groupId);
    @Update
    void update(Group group);

    @Transaction
    @Query("SELECT * FROM `Group` WHERE groupID = :ID")
    LiveData<List<GroupWithExpensesAndPersons>> getGroupWithExpensesAndPersonsByID(int ID);

    @Transaction
    @Query("SELECT * FROM `Group`")
    LiveData<List<Group>> getAllGroups();
    @Transaction
    @Query("SELECT * FROM Person INNER JOIN PersonGroupCrossRef ON Person.personID = PersonGroupCrossRef.personID WHERE PersonGroupCrossRef.groupID = :groupId")
    LiveData<List<Person>> getMembersByGroupId(int groupId);

    @Transaction
    default void insertGroupWithPersons(Group group, List<Person> persons) {
        int grpID = (int) insert(group);
        for (Person person : persons) {
            insertPersonGroupCrossRef(new PersonGroupCrossRef(person.getPersonID(), grpID));
        }
//        return grpID;
    }

}
