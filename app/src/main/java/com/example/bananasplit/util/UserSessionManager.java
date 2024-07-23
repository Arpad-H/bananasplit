package com.example.bananasplit.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
/**
 * Manages user session data, including user ID, name, dark mode preference, and default currency.
 * Stores data in SharedPreferences.
 * @author Arpad Horvath, Dennis Brockmeyer
 */
public class UserSessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_CURRENT_USER_ID = "currentUserId";
    private static final String KEY_DARK_MODE = "darkMode";
    private static final String KEY_CURRENCY = "defaultCurrency";
    private static final String KEY_CURRENT_USER_NAME = "currentUserName";
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    /**
     * Constructs a UserSessionManager instance.
     * Initializes SharedPreferences and Editor.
     *
     * @param application The application context used to access SharedPreferences.
     * @author Arpad Horvath
     */
    public UserSessionManager(Application application) {
        prefs = application.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }
    /**
     * Sets the current user ID.
     *
     * @param userId The user ID to set.
     * @author Arpad Horvath
     */
    public void setCurrentUserId(int userId) {
        editor.putInt(KEY_CURRENT_USER_ID, userId);
        editor.apply();
    }
    /**
     * Sets the current user name.
     *
     * @param userName The user name to set.
     * @author Arpad Horvath
     */
    public void setCurrentUserName(String userName) {
        editor.putString(KEY_CURRENT_USER_NAME, userName);
        editor.apply();
    }
    /**
     * Retrieves the current user name.
     *
     * @return The current user name. Empty String by default.
     * @author Arpad Horvath
     */
    public String getCurrentUserName() {
        return prefs.getString(KEY_CURRENT_USER_NAME, "");
    }


    /**
     * Retrieves the current user ID.
     *
     * @return The current user ID, or -1 if not set.
     * @author Arpad Horvath
     */
    public int getCurrentUserId() {
        return prefs.getInt(KEY_CURRENT_USER_ID, -1);
    }
    /**
     * Checks if dark mode is enabled.
     *
     * @return True if dark mode is enabled, otherwise false.
     * @Author Dennis Brockmeyer
     */
    public boolean getDarkMode() {
        return prefs.getBoolean(KEY_DARK_MODE, false);
    }
    /**
     * Sets the dark mode preference.
     *
     * @param darkMode True to enable dark mode, otherwise false.
     *                 @Author Dennis Brockmeyer
     */
    public void setDarkMode(boolean darkMode) {
        editor.putBoolean(KEY_DARK_MODE, darkMode);
    }
    /**
     * Retrieves the default currency.
     *
     * @return The default currency, or "€" if not set.
     * @Author Dennis Brockmeyer
     */
    public String getCurrency() {
        return prefs.getString(KEY_CURRENCY, "€");
    }
    /**
     * Sets the default currency.
     *
     * @param currency The currency to set.
     *                 @Author Dennis Brockmeyer

     */
    public void setCurrency(String currency) {
        editor.putString(KEY_CURRENCY, currency);
    }

    /**
     * Clears all preferences.
     * @Author Arpad Horvath
     */
    public void clearAllPreferences() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
