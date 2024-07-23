package com.example.bananasplit.scanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.databinding.ActivityAddExpenseFromScannerBinding;
import com.example.bananasplit.databinding.ActivityCreateGroupBinding;
import com.example.bananasplit.groups.GroupViewModel;

import java.util.List;

public class AddExpenseFromScannerActivity extends BaseActivity{
    private ActivityAddExpenseFromScannerBinding    binding;
    private List<ScanEntry> scanEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_add_expense_from_scanner, getContentContainer(), false);
        getContentContainer().addView(contentView);

        binding = ActivityAddExpenseFromScannerBinding.bind(contentView);
        scanEntries = getIntent().getParcelableArrayListExtra("scanEntries");


        setupRecyclerView();
        //TODO implement the rest of the activity like database operations
    }

    private void setupRecyclerView() {

        binding.ScannedEntriesRV.setLayoutManager(new LinearLayoutManager(this));
        ScanEntryAdapter adapter = new ScanEntryAdapter(scanEntries);
        binding.ScannedEntriesRV.setAdapter(adapter);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_expense_from_scanner;
    }
}
