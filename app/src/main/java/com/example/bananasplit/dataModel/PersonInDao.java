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
 * Data Access Object for the Person class
 * @author Dennis Brockmeyer
 */
@Dao
public interface PersonInDao {

    /**
     * Inserts a new person into the database.
     *
     * @param person The person to be inserted.
     * @return The ID of the inserted person.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Person person);

    /**
     * Updates a person in the database.
     *
     * @param person The person to be updated.
     */
    @Update(entity = Person.class)
    void update(Person person);

    /**
     * Deletes a person in the database.
     *
     * @param person The person to be deleted.
     */
    @Delete
    void delete(Person person);

    /**
     * Returns the person and all groups that the person is in.
     * Queried by ID
     *
     * @param id the ID of the person.
     * @return Livedata of the Person and a List of all Groups
     */
    @Transaction
    @Query("SELECT * FROM Person WHERE personID = :id")
    LiveData<List<PersonWithGroups>> getPersonWithGroupsByID(int id);

    /**
     * Returns the person and all groups that the person is in.
     * Queried by Name
     *
     * @param name the ID of the person.
     * @return Livedata of the Person and a List of all Groups
     */
    @Transaction
    @Query("SELECT * FROM Person WHERE Person.name LIKE :name")
    LiveData<List<PersonWithGroups>> getPersonWithGroupsByName(String name);

    /**
     * Returns the Livedata of the person for the ID
     *
     * @param personID the ID of the person.
     * @return Livedata of the Person
     */
    @Transaction
    @Query("SELECT * FROM Person WHERE personID = :personID")
    LiveData<Person> getPersonForID(int personID);

    /**
     * Returns the current User
     *
     * @param personID the ID of the person.
     * @return the Person
     */
    @Query("SELECT * FROM Person WHERE personID = :personID")
    Person getCurrentUser(int personID);


    /**
     * Returns all Friends
     *
     * @return all Friends
     */
    @Query("SELECT * FROM Person")
    LiveData<List<Person>> getFriends();
}
