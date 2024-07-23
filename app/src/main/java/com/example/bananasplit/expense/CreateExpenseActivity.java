package com.example.bananasplit.expense;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.ViewModelProvider;

import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Currency;
import com.example.bananasplit.dataModel.Expense;
import com.example.bananasplit.dataModel.ExpenseCategory;
import com.example.bananasplit.dataModel.Group;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.databinding.ActivityAddExpenseBinding;
import com.example.bananasplit.friends.BaseSelectFriendsActivity;
import com.example.bananasplit.groups.GroupViewModel;
import com.example.bananasplit.groups.SelectFriendsActivity;
import com.example.bananasplit.scanner.ScannerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Activity for creating a new expense entry.
 * @author Arpad Horvath
 */
public class CreateExpenseActivity extends BaseSelectFriendsActivity {
    private ActivityAddExpenseBinding binding;
    private ExpenseViewModel expenseViewModel;
    private GroupViewModel groupViewModel;
    private final List<Person> participantsInExpense = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();
        initializeViewModels();
        setupSpinners();
        setupButtons();
    }

    /**
     * Initializes the view
     */
    private void initializeView() {
        binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    /**
     * Initializes the view models
     */
    private void initializeViewModels() {
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        Group group = getIntent().getParcelableExtra("group");
        groupViewModel.getMembersByGroupId(group.getGroupID()).observe(this, this::setupPersonSpinner);
    }

    /**
     * Sets up the spinner for selecting the person who paid for the expense
     *
     * @param members List of members in the group
     */
    private void setupPersonSpinner(List<Person> members) {
        PersonSpinnerAdapter adapter = new PersonSpinnerAdapter(this, R.layout.person_spinner_item, members);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerChangePersonWhoPaid.setAdapter(adapter);
    }

    /**
     * Sets up the spinners for selecting the category and currency of the expense
     */
    private void setupSpinners() {
        setupCategorySpinner();
        setupCurrencySpinner();
    }

    /**
     * Sets up the spinner for selecting the category of the expense
     */
    private void setupCategorySpinner() {
        ExpenseCategory[] categories = ExpenseCategory.values();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getCategoryNames(categories));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerChangeCategory.setAdapter(adapter);
    }

    /**
     * Gets the names of the expense categories
     *
     * @param categories Array of expense categories
     * @return Array of category names
     */
    private String[] getCategoryNames(ExpenseCategory[] categories) {
        String[] categoryNames = new String[categories.length];
        for (int i = 0; i < categories.length; i++) {
            categoryNames[i] = categories[i].getCategory();
        }
        return categoryNames;
    }

    /**
     * Sets up the spinner for selecting the currency of the expense
     */
    private void setupCurrencySpinner() {
        Currency[] currencies = Currency.values();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getCurrencySymbols(currencies));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerChangeCurrency.setAdapter(adapter);
    }

    /**
     * Gets the currency symbols
     *
     * @param currencies Array of currencies
     * @return Array of currency symbols
     */
    private String[] getCurrencySymbols(Currency[] currencies) {
        String[] currencySymbols = new String[currencies.length];
        for (int i = 0; i < currencies.length; i++) {
            currencySymbols[i] = currencies[i].getCurrencySymbol();
        }
        return currencySymbols;
    }

    /**
     * Sets up the buttons for changing the split ratio, scanning the receipt, and creating the expense
     */
    private void setupButtons() {
        binding.btnChangeSplitRatio.setOnClickListener(v -> showSplitRatioDialog());
        binding.btnScan.setOnClickListener(v -> startScannerActivity());
        binding.createExpenseButton.setOnClickListener(v -> createExpense());
    }

    /**
     * Starts the scanner activity
     */
    private void startScannerActivity() {
        Intent intent = new Intent(this, ScannerActivity.class);
        startActivity(intent);
    }

    /**
     * Shows the split ratio dialog
     */
    @SuppressLint("SetTextI18n")
    private void showSplitRatioDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_split_ratio);
        setupSplitRatioDialog(dialog);
        dialog.show();
    }

    /**
     * Sets up the split ratio dialog
     *
     * @param dialog Dialog for the split ratio
     */
    private void setupSplitRatioDialog(Dialog dialog) {
        CheckBox equalSplitCheckBox = dialog.findViewById(R.id.checkbox_equal_split);
        Button confirmButton = dialog.findViewById(R.id.btn_confirm_split);
        Button selectParticipantsButton = dialog.findViewById(R.id.btn_select_expense_participating_people);
        selectParticipantsButton.setOnClickListener(v -> selectParticipants());

        confirmButton.setOnClickListener(v -> {
            handleSplitRatioDialog(dialog, equalSplitCheckBox);
        });
    }

    /**
     * Selects the participants for the expense
     */
    private void selectParticipants() {
        Intent intent = new Intent(this, SelectFriendsActivity.class);
        selectFriendsLauncher.launch(intent);
    }

    /**
     * Handles the split ratio dialog
     *
     * @param dialog              Dialog for the split ratio
     * @param equalSplitCheckBox  Checkbox for equal split
     */
    private void handleSplitRatioDialog(Dialog dialog, CheckBox equalSplitCheckBox) {
        if (equalSplitCheckBox.isChecked()) {
            binding.btnChangeSplitRatio.setText(R.string.equal);
            // TODO: Handle equal split logic for database
        } else if (!selectedFriends.isEmpty()) {
            binding.btnChangeSplitRatio.setText("Custom");
            // TODO: Handle custom split logic for database
        }
        dialog.dismiss();
    }

    /**
     * Creates the expense
     */
    private void createExpense() {
        Map<Person, Float> personExpenseRatio = extractDataFromEditTexts(R.id.edit_expense_ratio); //currently just equal split works correctly
        String name = binding.editTextName.getText().toString();
        float amount = Float.parseFloat(binding.editTextAmount.getText().toString());
        Person person = (Person) binding.spinnerChangePersonWhoPaid.getSelectedItem();
        ExpenseCategory selectedCategory = ExpenseCategory.fromString((String) binding.spinnerChangeCategory.getSelectedItem());
        Currency selectedCurrency = Currency.fromString((String) binding.spinnerChangeCurrency.getSelectedItem());

        Expense newExpense = new Expense(name, person, getGroupId(), amount, selectedCurrency, selectedCategory);
        expenseViewModel.insertExpenseWithPersonsAndAmount(newExpense, personExpenseRatio);
        finish();
    }

    /**
     * Gets the group ID
     * @return
     */
    private int getGroupId() {
        Group group = getIntent().getParcelableExtra("group");
        return group.getGroupID();
    }

    @Override
    protected ViewGroup getSelectedFriendsContainer() {
        return binding.selectedFriendsLayout;
    }

    @Override
    protected void handleAdditionalElements(View friendView, Person friend) {
        friendView.findViewById(R.id.edit_expense_ratio);
    }

    @Override
    protected int getListItemLayoutResId() {
        return R.layout.expense_participant_custom_ratio_list_item;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_expense;
    }
}
