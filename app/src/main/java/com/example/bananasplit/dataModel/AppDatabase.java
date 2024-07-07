package com.example.bananasplit.dataModel;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters(Converters.class)
@Database(entities = {Journey.class, Expense.class, Person.class,PersonJourneyCrossRef.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract JourneyInDao journeyInDao();
    public abstract ExpenseInDao expenseInDao();
    public abstract PersonInDao personInDao();
}
