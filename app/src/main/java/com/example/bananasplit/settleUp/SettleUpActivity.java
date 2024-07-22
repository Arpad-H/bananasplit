package com.example.bananasplit.settleUp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

//import androidx.activity.EdgeToEdge;
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

        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.activity_settle_up, getContentContainer(), false);
        getContentContainer().addView(contentView);

        binding = ActivitySettleUpBinding.bind(contentView);

        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(v-> finish());
        RecyclerView recyclerView = findViewById(R.id.recyclerSettleUp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SettleUpAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        SettleUpViewModel settleUpViewModel = new ViewModelProvider(this).get(SettleUpViewModel.class);
        settleUpViewModel.getAllFriends().observe(this, friends -> {
            adapter.updateFriends(friends);
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_settle_up;
    }

    @Override
    public void onItemClicked(int position) {
        // TODO: handle onItemClicked
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