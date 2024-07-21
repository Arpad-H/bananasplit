package com.example.bananasplit.util;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_CURRENT_USER_ID = "currentUserId";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public UserSessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setCurrentUserId(int userId) {
        editor.putInt(KEY_CURRENT_USER_ID, userId);
        editor.apply();
    }

    public int getCurrentUserId() {
        return prefs.getInt(KEY_CURRENT_USER_ID, -1); // -1 indicates no user is set
    }
}
