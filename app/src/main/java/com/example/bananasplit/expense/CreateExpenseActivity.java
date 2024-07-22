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
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.lifecycle.ViewModelProvider;

import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Currency;
import com.example.bananasplit.dataModel.Expense;
import com.example.bananasplit.dataModel.ExpenseCategory;
import com.example.bananasplit.dataModel.Group;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.databinding.ActivityAddExpenseBinding;
import com.example.bananasplit.databinding.ActivitySelectFriendsBinding;
import com.example.bananasplit.friends.BaseSelectFriendsActivity;
import com.example.bananasplit.groups.GroupViewModel;
import com.example.bananasplit.groups.SelectFriendsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateExpenseActivity extends BaseSelectFriendsActivity {
    private ActivityAddExpenseBinding binding;
    ExpenseViewModel expenseViewModel;
    GroupViewModel groupViewModel;
    private final List<Person> participantsInExpense = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_add_expense, getContentContainer(), false);
        getContentContainer().addView(contentView);

        binding = ActivityAddExpenseBinding.bind(contentView);

        Group group = getIntent().getParcelableExtra("group");

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        groupViewModel.getMembersByGroupId(group.getGroupID()).observe(this, members -> {
            PersonSpinnerAdapter adapter = new PersonSpinnerAdapter(this, R.layout.person_spinner_item, members);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinnerChangePersonWhoPaid.setAdapter(adapter);
        });

        binding.btnChangeSplitRatio.setOnClickListener(v -> {
            showSplitRatioDialog();
        });

        setupChangeCategorySpinner();
        setupChangeCurrencySpinner();

        bindCreateExpenseButton(group);
    }

    @Override
    protected ViewGroup getSelectedFriendsContainer() {
        return binding.selectedFriendsLayout;
    }

    @Override
    protected void handleAdditionalElements(View friendView, Person friend) {
        EditText ratioEditText = friendView.findViewById(R.id.edit_expense_ratio);
    }

    @Override
    protected int getListItemLayoutResId() {
        return R.layout.expense_participant_custom_ratio_list_item;
    }

    private void setupChangeCategorySpinner() {
        ExpenseCategory[] categories = ExpenseCategory.values();
        String[] categoryNames = new String[categories.length];
        for (int i = 0; i < categories.length; i++) {
            categoryNames[i] = categories[i].getCategory();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerChangeCategory.setAdapter(adapter);
    }

    private void setupChangeCurrencySpinner() {
        Currency[] categories = Currency.values();
        String[] currencySymbol = new String[categories.length];
        for (int i = 0; i < categories.length; i++) {
            currencySymbol[i] = categories[i].getCurrencySymbol();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencySymbol);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerChangeCurrency.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void showSplitRatioDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_split_ratio);

        CheckBox equalSplitCheckBox = dialog.findViewById(R.id.checkbox_equal_split);
        Button confirmButton = dialog.findViewById(R.id.btn_confirm_split);
        Button selectParticipantsButton = dialog.findViewById(R.id.btn_select_expense_participating_people);
        selectParticipantsButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(this, SelectFriendsActivity.class);
            selectFriendsLauncher.launch(newIntent);
        });
        confirmButton.setOnClickListener(v -> {
            // Handle the logic for splitting the bill
            if (equalSplitCheckBox.isChecked()) {
                binding.btnChangeSplitRatio.setText(R.string.equal);
                //TODO
            } else if (!selectedFriends.isEmpty()) {
                binding.btnChangeSplitRatio.setText("Custom");
            }

            dialog.dismiss();
        });

        dialog.show();
    }

    private void bindCreateExpenseButton(Group group) {
        binding.createExpenseButton.setOnClickListener(v -> {
            Map<Person, Float> personExpenseRatio = extractDataFromEditTexts(R.id.edit_expense_ratio);
            String name = binding.editTextName.getText().toString();
            float amount = Float.parseFloat(binding.editTextAmount.getText().toString());
            Person person = (Person) binding.spinnerChangePersonWhoPaid.getSelectedItem();
            ExpenseCategory selectedCategory = ExpenseCategory.fromString((String) binding.spinnerChangeCategory.getSelectedItem());
            Currency selectedCurrency = Currency.fromString((String) binding.spinnerChangeCurrency.getSelectedItem());

            Expense newExpense = new Expense(name, person, group.getGroupID(), amount, selectedCurrency, selectedCategory);
            expenseViewModel.insertExpenseWithPersonsAndAmount(newExpense, personExpenseRatio);
            finish();
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_expense;
    }
}
