package com.example.bananasplit.settings;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.os.LocaleListCompat;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Currency;
import com.example.bananasplit.dataModel.Language;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.dataModel.repository.PersonRepository;
import com.example.bananasplit.databinding.ActivitySettingsBinding;
import com.example.bananasplit.util.UserSessionManager;

/**
 * Activity to show and change AppSettings
 *
 * @author Dennis Brockmeyer, Arpad Horvath (where specified)
 */
public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PersonRepository repository = new PersonRepository(getApplication());

        ActivitySettingsBinding binding = createBinding();

        UserSessionManager userSessionManager = new UserSessionManager(getApplication());
        SwitchCompat darkMode = binding.switchDarkmode;
        darkMode.setChecked(userSessionManager.getDarkMode());

        Spinner language = binding.settingsSpinnerLanguage;
        setupSpinner(language, Language.getLanguageNames());

        Spinner defaultCurrency = binding.settingsSpinnerCurrency;
        setupSpinner(defaultCurrency, Currency.getCurrencySymbols());

        setupUserEditBtn(repository, userSessionManager, binding);

        ImageButton saveSettings = binding.settingsSave;
        saveSettings.setOnClickListener(v -> {
            if (!language.getSelectedItem().toString().equals(userSessionManager.getLanguage())) {
                userSessionManager.setLanguage(language.getSelectedItem().toString());
                super.setLanguage(Language.from(userSessionManager.getLanguage()).getLanguageCode());
            }
            if (darkMode.isChecked() != userSessionManager.getDarkMode()) {
                userSessionManager.setDarkMode(darkMode.isChecked());
                super.setDarkMode(darkMode.isChecked());
            }
            Toast.makeText(this, "Settings saved", Toast.LENGTH_LONG).show();
        });
    }

    private void setupUserEditBtn(PersonRepository repository, UserSessionManager userSessionManager, ActivitySettingsBinding binding) {
        Person currentUser = repository.getCurrentUser(userSessionManager.getCurrentUserId());
        Button setupUser = binding.btnSetupUser;
        setupUser.setOnClickListener(v->{
            Intent intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
            if (currentUser != null) {
                intent.putExtra("editUser", (Parcelable) currentUser);
            }
            startActivity(intent);
        });
    }

    /**
     * Creates a Binding to use in further Code
     * @return the binding
     */
    private @NonNull ActivitySettingsBinding createBinding() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_settings, getContentContainer(), false);
        getContentContainer().addView(contentView);
        return ActivitySettingsBinding.bind(contentView);
    }

    /**
     * Returns the layout resource ID for this activity. Used by the BaseActivity.
     *
     * @return The layout resource ID.
     */
    @Override
    protected int getLayoutResourceId() {
       return R.layout.activity_settings;
    }

    /**
     * Sets up the spinner with the options in the StringArray
     * @author Dennis Brockmeyer
     */
    private void setupSpinner(Spinner spinner, String[] stringArray) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
