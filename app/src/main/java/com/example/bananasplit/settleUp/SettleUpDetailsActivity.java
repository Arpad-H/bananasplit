package com.example.bananasplit.settleUp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

//import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.util.SecurePreferencesManager;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class SettleUpDetailsActivity extends BaseActivity {
    SettleUpAdapter adapter;
    SettleUpViewModel settleUpViewModel;
    private static final int PAYPAL_REQUEST_CODE = 123;
    private PayPalConfiguration payPalConfiguration;
    private ActivityResultLauncher<Intent> payPalLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(v-> finish());


        RadioGroup radioGroup = findViewById(R.id.paymentRadios);
        Button transferMoneyButton = findViewById(R.id.btnTransfer);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> transferMoneyButton.setEnabled(group.getCheckedRadioButtonId() != -1));
    setupPayPal();
        setupPaypalLauncher();
        transferMoneyButton.setOnClickListener(v -> {

            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

            if (selectedRadioButtonId != -1) { // Check if a RadioButton is selected

                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

                // Get the text or ID of the selected RadioButton
                String selectedOption = selectedRadioButton.getText().toString();

                // Show a Toast message or perform other actions
                Toast.makeText(this, "Selected option: " + selectedOption, Toast.LENGTH_SHORT).show();

                switch (selectedOption) {
                    case "PayPal":
                        processPayPalPayment();
                        break;

                    case "Cash":
                        // Perform action for Cash
                        break;
                    default:

                        break;
                }
            } else {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupPaypalLauncher() {
        payPalLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                            if (confirmation != null) {
                                try {
                                    String paymentDetails = confirmation.toJSONObject().toString(4);
                                    // Handle the payment confirmation
                                    Toast.makeText(this, "Payment Success!", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                        Toast.makeText(this, "Payment Cancelled", Toast.LENGTH_SHORT).show();
                    } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID) {
                        Toast.makeText(this, "Payment Invalid", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void setupPayPal() {
        SecurePreferencesManager securePreferencesManager = new SecurePreferencesManager(this);
        String PAYPAL_CLIENT_ID = securePreferencesManager.getApiKey();
        payPalConfiguration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(PAYPAL_CLIENT_ID);
    }
    private void processPayPalPayment() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal("10.00"), "USD", "Banana Split Settlement",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);


        payPalLauncher.launch(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, PayPalService.class));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_settle_up_details;
    }

}