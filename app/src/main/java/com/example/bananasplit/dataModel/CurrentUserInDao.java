//package com.example.bananasplit.dataModel;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Insert;
//import androidx.room.OnConflictStrategy;
//import androidx.room.Query;
//import androidx.room.Update;
//
//@Dao
//public interface CurrentUserInDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insert(Person person, int id);
//    @Update
//    void update(Person person);
//
//    @Query("SELECT * FROM Person LIMIT 1")
//    LiveData<Person> getCurrentUser();
//
//}
