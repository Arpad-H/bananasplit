package com.example.bananasplit.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;

/**
 * Manages secure storage and retrieval of sensitive data using encrypted SharedPreferences.
 * @author Arpad Horvath
 * @see <a href="https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences">EncryptedSharedPreferences</a>
 */
public class SecurePreferencesManager {
    private static final String SHARED_PREFS_FILE = "secure_prefs";
    private static final String PAYPAL_CLIENT_ID = "api_key";

    private SharedPreferences sharedPreferences;

    /**
     * Constructs a SecurePreferencesManager instance.
     * Initializes encrypted SharedPreferences for secure data storage.
     *
     * @param context The application context used to access SharedPreferences.
     */
    public SecurePreferencesManager(Context context) {
        try {

            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();


            sharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    SHARED_PREFS_FILE,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the PayPal client ID securely in SharedPreferences.
     *
     * @param apiKey The PayPal client ID to be saved.
     */
    public void saveClientIDKey(String apiKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PAYPAL_CLIENT_ID, apiKey);
        editor.apply();
    }
    /**
     * Retrieves the PayPal client ID from SharedPreferences.
     *
     * @return The stored PayPal client ID, or null if not found.
     */
    public String getApiKey() {
        return sharedPreferences.getString(PAYPAL_CLIENT_ID, null);
    }
}
