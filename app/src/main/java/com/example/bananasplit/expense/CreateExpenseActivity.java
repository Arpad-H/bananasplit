package com.example.bananasplit.expense;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Currency;
import com.example.bananasplit.dataModel.DatabaseModule;
import com.example.bananasplit.dataModel.Expense;
import com.example.bananasplit.dataModel.Group;
import com.example.bananasplit.dataModel.GroupInDao;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.groups.GroupViewModel;

import java.util.List;

public class CreateExpenseActivity extends BaseActivity {
    private EditText nameEditText;
    private EditText amountEditText;
    ExpenseViewModel expenseViewModel;
    private Spinner personWhoPaidSpinner;
    private Button changeSplitRatioButton;
    GroupViewModel groupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Group group = getIntent().getParcelableExtra("group", Group.class);

        nameEditText = findViewById(R.id.edit_text_name);
        amountEditText = findViewById(R.id.edit_text_amount);
        personWhoPaidSpinner = findViewById(R.id.spinner_change_person_who_paid);


        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        changeSplitRatioButton = findViewById(R.id.btn_change_split_ratio);

        groupViewModel.getMembersByGroupId(group.getGroupID()).observe(this, members -> {
            PersonSpinnerAdapter adapter = new PersonSpinnerAdapter(this, R.layout.person_spinner_item, members);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            personWhoPaidSpinner.setAdapter(adapter);
        });


        changeSplitRatioButton.setOnClickListener(v -> {
            showSplitRatioDialog();
        });


        bindCreateExpenseButton(group);


    }
    private void showSplitRatioDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_split_ratio);

        CheckBox equalSplitCheckBox = dialog.findViewById(R.id.checkbox_equal_split);
        Button confirmButton = dialog.findViewById(R.id.btn_confirm_split);

        confirmButton.setOnClickListener(v -> {
            // Handle the logic for splitting the bill
            if (equalSplitCheckBox.isChecked()) {
                // Implement the equal split logic
                //TODO
            }
            // Implement other split logics if any

            dialog.dismiss();
        });

        dialog.show();
    }

    private void bindCreateExpenseButton(Group group) {
        Button createButton = findViewById(R.id.create_expense_button);
        createButton.setOnClickListener(v -> {

            String name = nameEditText.getText().toString();
            float amount = Float.parseFloat(amountEditText.getText().toString());
            Person person = (Person) personWhoPaidSpinner.getSelectedItem(); //TODO currently just get the first person in the group as a temp solution
            Expense newExpense = new Expense(name,person, group.getGroupID(), amount, Currency.EUR);
            expenseViewModel.insert(newExpense);

            finish();
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_expense;
    }
}
