package com.example.bananasplit.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_CURRENT_USER_ID = "currentUserId";
    private static final String KEY_CURRENT_USER_NAME = "currentUserName";
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public UserSessionManager(Application application) {
        prefs = application.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setCurrentUserId(int userId) {
        editor.putInt(KEY_CURRENT_USER_ID, userId);
        editor.apply();
    }
    public void setCurrentUserName(String userName) {
        editor.putString(KEY_CURRENT_USER_NAME, userName);
        editor.apply();
    }
    public String getCurrentUserName() {
        return prefs.getString(KEY_CURRENT_USER_NAME, "");
    }


    public int getCurrentUserId() {
        return prefs.getInt(KEY_CURRENT_USER_ID, -1);
    }
    public void clearAllPreferences() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
