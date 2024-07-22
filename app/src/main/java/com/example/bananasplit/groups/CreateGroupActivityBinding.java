package com.example.bananasplit.groups;

import android.widget.EditText;

import com.example.bananasplit.databinding.ActivityCreateGroupBinding;

public class CreateGroupActivityBinding {
    private final ActivityCreateGroupBinding binding;

    public CreateGroupActivityBinding(ActivityCreateGroupBinding binding) {
        this.binding = binding;
    }

    public EditText getGroupNameEditText() {
        return binding.groupNameEditText;
    }

    // Add other binding methods for your views
}