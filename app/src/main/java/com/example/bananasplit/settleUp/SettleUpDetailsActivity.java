package com.example.bananasplit.settleUp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;

import com.example.bananasplit.BaseActivity;
import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Currency;
import com.example.bananasplit.databinding.ActivitySettleUpDetailsBinding;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.OrderRequest;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;

import java.util.ArrayList;

/**
 * Activity to handle settling up details.
 * Provides options for different payment methods including PayPal.
 *
 * @author Arpad Horvath, Dennis Brockmeyer(where specified)
 */
public class SettleUpDetailsActivity extends BaseActivity {
    SettleUpAdapter adapter;
    SettleUpViewModel settleUpViewModel;
    PaymentButtonContainer paymentButtonContainer;
    private ActivityResultLauncher<Intent> payPalLauncher;
    private ActivitySettleUpDetailsBinding binding;

    /**
     * Called when the activity is first created.
     * Initializes the activity and sets up the view components.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySettleUpDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Spinner currency = binding.spinnerChangeCurrency2;

        setupSpinner(currency, Currency.getCurrencySymbols());

        setupBackButton();
        setupPayPalPayment();
        setupTransferMoneyButton();
    }

    /**
     * Extracts data from the intent that started this activity.
     * @author Dennis Brockmeyer
     */
    private void extractDataFromIntent() {
        //TODO: extract data from intent
    }

    /**
     * Sets up the back button and its click listener.
     */
    private void setupBackButton() {
        binding.backButton.setOnClickListener(v -> finish());
    }

    /**
     * Sets up the transfer money button click listener.
     *
     * @author Dennis Brockmeyer
     */
    private void setupTransferMoneyButton() {
        binding.btnTransfer.setOnClickListener(v -> {
            Toast.makeText(this, "Cash Payment recorded", Toast.LENGTH_SHORT).show();
            //TODO: handle Database query
            finish();
        });

    }

    /**
     * Sets up the PayPal payment button container and its actions.
     * capture is deprecated but no alternative is provided in the documentation.
     * <a href="https://stackoverflow.com/questions/77989686/android-paypal-checkout-options">...</a>
     */
    private void setupPayPalPayment() {
        binding.paypalPaymentButton.setup(
                createOrderActions -> {
                    Log.d(TAG, "create: ");
                    ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                    purchaseUnits.add(
                            new PurchaseUnit.Builder()
                                    .amount(
                                            new Amount.Builder()
                                                    .currencyCode(CurrencyCode.USD)
                                                    .value("10.00")//TODO: get amount from intent and convert to usd
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
                    //TODO launch success activity
                })
        );

    }

    /**
     * Sets up the spinner with the options in the StringArray
     * @author Dennis Brockmeyer
     */
    private void setupSpinner(Spinner spinner, String[] stringArray) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Gets the layout resource ID for this activity.
     *
     * @return The layout resource ID.
     */
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_settle_up_details;
    }

}