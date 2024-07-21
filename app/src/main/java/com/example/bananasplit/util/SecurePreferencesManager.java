package com.example.bananasplit.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;

public class SecurePreferencesManager {
    private static final String SHARED_PREFS_FILE = "secure_prefs";
    private static final String PAYPAL_CLIENT_ID = "api_key";

    private SharedPreferences sharedPreferences;

    public SecurePreferencesManager(Context context) {
        try {
            // Generate a master key for encryption
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            // Create EncryptedSharedPreferences
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

    public void saveClientIDKey(String apiKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PAYPAL_CLIENT_ID, apiKey);
        editor.apply();
    }

    public String getApiKey() {
        return sharedPreferences.getString(PAYPAL_CLIENT_ID, null);
    }
}
