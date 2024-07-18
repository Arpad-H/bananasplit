package com.example.bananasplit.expense;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

public class CreateExpenseActivity  extends BaseActivity {
    private EditText nameEditText;
    private EditText amountEditText;
    ExpenseViewModel expenseViewModel;
    GroupViewModel groupViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Group group = getIntent().getParcelableExtra("group", Group.class);

        nameEditText = findViewById(R.id.edit_text_name);
        amountEditText = findViewById(R.id.edit_text_amount);

        Button createButton = findViewById(R.id.create_expense_button);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        createButton.setOnClickListener(v -> {

            String name = nameEditText.getText().toString();
            float amount = Float.parseFloat(amountEditText.getText().toString());
            Person person = groupViewModel.getMembersByGroupId(group.getGroupID()).getValue().get(0); //TODO currently just get the first person in the group as a temp solution
            Expense newExpense = new Expense(name,person.getPersonID(), group.getGroupID(), amount, Currency.EUR);
            expenseViewModel.insert(newExpense);

            finish();
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_expense;
    }
}
