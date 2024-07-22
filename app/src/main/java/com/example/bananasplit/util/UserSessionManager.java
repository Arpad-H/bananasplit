package com.example.bananasplit.util;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_CURRENT_USER_ID = "currentUserId";
    private static final String KEY_DARK_MODE = "darkMode";
    private static final String KEY_CURRENCY = "defaultCurrency";
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public UserSessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setCurrentUserId(int userId) {
        editor.putInt(KEY_CURRENT_USER_ID, userId);
        editor.apply();
    }

    public int getCurrentUserId() {
        return prefs.getInt(KEY_CURRENT_USER_ID, -1);
    }

    public boolean getDarkMode() {
        return prefs.getBoolean(KEY_DARK_MODE, false);
    }

    public void setDarkMode(boolean darkMode) {
        editor.putBoolean(KEY_DARK_MODE, darkMode);
    }

    public String getCurrency() {
        return prefs.getString(KEY_CURRENCY, "€");
    }

    public void setCurrency(String currency) {
        editor.putString(KEY_CURRENCY, currency);
    }


    public void clearAllPreferences() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
