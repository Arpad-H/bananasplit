package com.example.bananasplit.util.Logging;



import android.content.Context;

import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.util.Logging.ActivityLogger;
import com.example.bananasplit.util.Logging.AppActivityLogger;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
/**
 * This module provides the ActivityLogger object to the application.
 * Dependency Incetion not yet working
 * @author Arpad Horvath
 */
@Module
@InstallIn(SingletonComponent.class)
public class LoggerModule {

    @Inject
    AppDatabase databaseModule;

    /**
     * Provides the ActivityLogger object to the application.
     * @param context The context of the application.
     * @return The ActivityLogger object.
     */
    @Provides
    public ActivityLogger provideActivityLogger(@ApplicationContext Context context) {
        return new AppActivityLogger(databaseModule);
    }
}
