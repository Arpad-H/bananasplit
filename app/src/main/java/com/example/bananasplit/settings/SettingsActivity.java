package com.example.bananasplit.settings;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.AppDatabase;
import com.example.bananasplit.dataModel.Currency;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Language;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.dataModel.PersonInDao;
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
        darkMode.setChecked((userSessionManager.getDarkMode()));
        darkMode.setOnClickListener(v->{
            userSessionManager.setDarkMode(darkMode.isChecked());
        });

        Spinner language = binding.settingsSpinnerLanguage;
        setupLanguageSpinner(language);

        Spinner defaultCurrency = binding.settingsSpinnerCurrency;
        setupCurrencySpinner(defaultCurrency);

        Person currentUser = repository.getPersonForID(userSessionManager.getCurrentUserId()).getValue();
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
     * Sets up the spinner for selecting the default Currency
     * @author Arpad Horvath
     */
    private void setupCurrencySpinner(Spinner spinner) {
        Currency[] currencies = Currency.values();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getCurrencySymbols(currencies));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Gets the currency symbols
     *
     * @param currencies Array of currencies
     * @return Array of currency symbols
     * @author Arpad Horvath
     */
    private String[] getCurrencySymbols(Currency[] currencies) {
        String[] currencySymbols = new String[currencies.length];
        for (int i = 0; i < currencies.length; i++) {
            currencySymbols[i] = currencies[i].getCurrencySymbol();
        }
        return currencySymbols;
    }

    /**
     * Sets up the spinner for selecting the default Language
     * @author Arpad Horvath, Dennis Brockmeyer
     */
    private void setupLanguageSpinner(Spinner spinner) {
        Language[] languages = Language.values();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getLanguageNames(languages));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Gets the Languages as strings
     *
     * @param languages Array of languages
     * @return Array of language strings
     * @author Arpad Horvath, Dennis Brockmeyer
     */
    private String[] getLanguageNames(Language[] languages) {
        String[] languageNames = new String[languages.length];
        for (int i = 0; i < languages.length; i++) {
            languageNames[i] = languages[i].getName();
        }
        return languageNames;

    }
}
