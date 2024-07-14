package com.example.bananasplit.dataModel;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters(Converters.class)
@Database(entities = {Group.class, Expense.class, Person.class, PersonGroupCrossRef.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GroupInDao groupInDao();
    public abstract ExpenseInDao expenseInDao();
    public abstract PersonInDao personInDao();
}
