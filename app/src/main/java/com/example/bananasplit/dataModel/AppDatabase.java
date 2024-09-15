package com.example.bananasplit.dataModel;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Abstract Class to define which other Classes should be stored in the Database
 * @author Dennis Brockmeyer
 */
@TypeConverters(Converters.class)
@Database(entities = {Group.class, Expense.class, Person.class, PersonGroupCrossRef.class, ExpensePersonCrossRef.class, AppActivityTrackerFootprint.class}, version = 22)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GroupInDao groupInDao();
    public abstract ExpenseInDao expenseInDao();
    public abstract PersonInDao personInDao();
    public abstract AppActivityTrackerInDao appActivityTrackerInDao();

}
