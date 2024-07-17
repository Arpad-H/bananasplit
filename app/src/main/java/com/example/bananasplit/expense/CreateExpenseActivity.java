package com.example.bananasplit.expense;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Currency;
import com.example.bananasplit.dataModel.Expense;
import com.example.bananasplit.dataModel.Group;

public class CreateExpenseActivity  extends BaseActivity {
    private EditText nameEditText;
    private EditText amountEditText;
    ExpenseViewModel expenseViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Group group = getIntent().getParcelableExtra("group", Group.class);

        nameEditText = findViewById(R.id.edit_text_name);
        amountEditText = findViewById(R.id.edit_text_amount);

        Button createButton = findViewById(R.id.create_expense_button);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        createButton.setOnClickListener(v -> {

            String name = nameEditText.getText().toString();
            float amount = Float.parseFloat(amountEditText.getText().toString());

            Expense newExpense = new Expense(1, group.getGroupID(), amount, Currency.EUR);
            expenseViewModel.insert(newExpense);

            finish();
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_expense;
    }
}
