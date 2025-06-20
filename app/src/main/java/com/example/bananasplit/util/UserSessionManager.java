package com.example.bananasplit.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.bananasplit.dataModel.Currency;
import com.example.bananasplit.dataModel.Language;

import java.util.Locale;

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
    private static final String KEY_LANGUAGE = "language";
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
     * @author Dennis Brockmeyer
     */
    public boolean getDarkMode() {
        return prefs.getBoolean(KEY_DARK_MODE, false);
    }
    /**
     * Sets the dark mode preference.
     *
     * @param darkMode True to enable dark mode, otherwise false.
     * @author Dennis Brockmeyer
     */
    public void setDarkMode(boolean darkMode) {
        editor.putBoolean(KEY_DARK_MODE, darkMode);
        editor.apply();
    }
    /**
     * Retrieves the default currency.
     *
     * @return The default currency, or "€" if not set.
     * @author Dennis Brockmeyer
     */
    public String getCurrency() {
        return prefs.getString(KEY_CURRENCY, Currency.EUR.getCurrencySymbol());
    }
    /**
     * Sets the default currency.
     *
     * @param currency The currency to set.
     * @author Dennis Brockmeyer
     */
    public void setCurrency(String currency) {
        editor.putString(KEY_CURRENCY, currency);
        editor.apply();
    }

    /**
     * Sets the Language
     *
     * @param language the LanguageCode to be set
     * @author Dennis Brockmeyer
     */
    public void setLanguage(String language) {
        editor.putString(KEY_LANGUAGE, language);
        editor.apply();
    }

    /**
     * Returns the Name of the current Language
     * @return Name of the current Language
     * @author Dennis Brockmeyer
     */
    public String getLanguage() {
        return prefs.getString(KEY_LANGUAGE, Language.DE.name());
    }

    /**
     * Creates a Locale based on the current language setting.
     * @return The Locale for the current language.
     * @author Dennis Brockmeyer
     */
    public Locale getLocale() {
        return Locale.forLanguageTag(Language.from(getLanguage()).getLanguageCode());
    }


    /**
     * Clears all preferences.
     * @author Arpad Horvath
     */
    public void clearAllPreferences() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
