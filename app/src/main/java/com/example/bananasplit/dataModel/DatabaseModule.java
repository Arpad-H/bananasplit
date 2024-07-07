package com.example.bananasplit.dataModel;

import android.content.Context;

import androidx.room.Room;

//@Module
//@InstallIn(SingletonComponent.class)
public class DatabaseModule {
    static AppDatabase appDatabase;
    // @Provides
    //  @Singleton
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app-database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
