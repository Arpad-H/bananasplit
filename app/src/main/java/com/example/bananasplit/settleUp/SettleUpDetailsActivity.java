package com.example.bananasplit.settleUp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

//import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;

public class SettleUpDetailsActivity extends BaseActivity {
    private SettleUpAdapter adapter;
    private SettleUpViewModel settleUpViewModel;
    private Button transfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(v-> finish());

        transfer = findViewById(R.id.btnTransfer);

        RadioGroup radioGroup = findViewById(R.id.paymentRadios);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> transfer.setEnabled(group.getCheckedRadioButtonId() != -1));



    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_settle_up_details;
    }
}