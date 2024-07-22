package com.example.bananasplit.settleUp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.MainActivity;
import com.example.bananasplit.R;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.OrderRequest;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SettleUpDetailsActivity extends BaseActivity {
    SettleUpAdapter adapter;
    SettleUpViewModel settleUpViewModel;
    PaymentButtonContainer paymentButtonContainer;
    private ActivityResultLauncher<Intent> payPalLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(v -> finish());


        RadioGroup radioGroup = findViewById(R.id.paymentRadios);
        Button transferMoneyButton = findViewById(R.id.btnTransfer);
//        radioGroup.setOnCheckedChangeListener((group, checkedId) -> transferMoneyButton.setEnabled(group.getCheckedRadioButtonId() != -1));

        paymentButtonContainer = findViewById(R.id.paypal_payment_button);
//        setupPayPal();

        processPayPalPayment();
        transferMoneyButton.setOnClickListener(v -> {

            Toast.makeText(this, "Cash Payment recorded", Toast.LENGTH_SHORT).show();
            //TODO: handle Database query
            finish();

//            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
//
//            if (selectedRadioButtonId != -1) { // Check if a RadioButton is selected
//                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
//
//                if (selectedRadioButtonId == R.id.radioButtonPayPal) {
//                    processPayPalPayment();
//                } else if (selectedRadioButtonId == R.id.radioButtonCash) {
//                    Toast.makeText(this, "Settlement in Person Noted", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            } else {
//                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
//            }
        });

    }


    private void processPayPalPayment() {

        paymentButtonContainer.setup(
                createOrderActions -> {
                    Log.d(TAG, "create: ");
                    ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                    purchaseUnits.add(
                            new PurchaseUnit.Builder()
                                    .amount(
                                            new Amount.Builder()
                                                    .currencyCode(CurrencyCode.USD)
                                                    .value("10.00")
                                                    .build()
                                    )
                                    .build()
                    );
                    OrderRequest order = new OrderRequest(
                            OrderIntent.CAPTURE,
                            new AppContext.Builder()
                                    .userAction(UserAction.PAY_NOW)
                                    .build(),
                            purchaseUnits
                    );
                    createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                },
                approval -> approval.getOrderActions().capture(result -> {
                    Log.d(TAG, String.format("CaptureOrderResult: %s", result));
                    Toast.makeText(getApplication().getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                })
        );

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_settle_up_details;
    }

}