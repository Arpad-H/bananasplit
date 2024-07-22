package com.example.bananasplit.util;



import android.content.Context;

import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.DatabaseModule;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
@Module
@InstallIn(SingletonComponent.class)
public class LoggerModule {

    @Inject
    AppDatabase databaseModule;
    @Provides
    public ActivityLogger provideActivityLogger(@ApplicationContext Context context) {
        return new AppActivityLogger(databaseModule);
    }
}
