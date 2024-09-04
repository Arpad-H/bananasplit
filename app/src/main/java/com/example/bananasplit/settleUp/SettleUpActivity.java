package com.example.bananasplit.settleUp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

//import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.ListItemHolder;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.databinding.ActivityGroupsBinding;
import com.example.bananasplit.databinding.ActivitySettleUpBinding;
import com.example.bananasplit.friends.FriendsActivity;
import com.example.bananasplit.friends.FriendsDetailActivity;

import java.util.ArrayList;

public class SettleUpActivity extends BaseActivity implements ListItemHolder {
    private SettleUpAdapter adapter;
    private ActivitySettleUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBinding();
        setupRecyclerView();
        setupBackButton();
        setupViewModel();

    }

    private void setupViewModel() {
        SettleUpViewModel settleUpViewModel = new ViewModelProvider(this).get(SettleUpViewModel.class);
        settleUpViewModel.getAllFriends().observe(this, friends -> {
            adapter.updateFriends(friends);
        });
    }

    private void setupBackButton() {
        ImageButton back = binding.backButton;
        back.setOnClickListener(v-> finish());
    }

    private void setupRecyclerView() {
        //        RecyclerView recyclerView = findViewById(R.id.recyclerSettleUp);
        adapter = new SettleUpAdapter(new ArrayList<>(), this);
        binding.recyclerSettleUp.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerSettleUp.setAdapter(adapter);
    }

    private void setupBinding() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_settle_up, getContentContainer(), false);
        getContentContainer().addView(contentView);
        binding = ActivitySettleUpBinding.bind(contentView);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_settle_up;
    }

    @Override
    public void onItemClicked(int position) {
        Person personDetails = adapter.getPersonAt(position);
        Intent intent = new Intent(SettleUpActivity.this, SettleUpDetailsActivity.class);
        intent.putExtra("friend", (Parcelable) personDetails);
        startActivity(intent);
    }

    @Override
    public void onDelete(int position) {
    }

    @Override
    public void onEdit(int position) {
    }
}