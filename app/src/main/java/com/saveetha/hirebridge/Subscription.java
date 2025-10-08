package com.saveetha.hirebridge;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.*;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class Subscription extends AppCompatActivity implements PurchasesUpdatedListener {

    private MaterialButton btnSubscribe;
    private MaterialButton btnSkipForNow;
    private BillingClient billingClient;
    private ProductDetails productDetails;

    private static final String TAG = "SubscriptionActivity";
    private static final String SUBSCRIPTION_SKU = "hirebridge_premium_subscription";
    private static final String TEST_SUBSCRIPTION_SKU = "android.test.purchased"; // For testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        addDebugInformation();
        initializeViews();
        setupBillingClient();
        setupClickListeners();
    }

    private void addDebugInformation() {
        Log.d(TAG, "=== DEBUG INFORMATION ===");
        Log.d(TAG, "Package name: " + getPackageName());
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            long versionCode;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                versionCode = packageInfo.getLongVersionCode();
            } else {
                versionCode = packageInfo.versionCode;
            }
            Log.d(TAG, "Version code: " + versionCode);
            Log.d(TAG, "Version name: " + packageInfo.versionName);
        } catch (Exception e) {
            Log.w(TAG, "Unable to get package info: " + e.getMessage());
        }
        Log.d(TAG, "Product ID: " + SUBSCRIPTION_SKU);
        Log.d(TAG, "Test Product ID: " + TEST_SUBSCRIPTION_SKU);
        Log.d(TAG, "=========================");
    }

    private void initializeViews() {
        btnSubscribe = findViewById(R.id.btnSubscribe);
        btnSkipForNow = findViewById(R.id.btnSkipForNow);
    }

    private void setupBillingClient() {
        billingClient = BillingClient.newBuilder(this)
                .setListener(this)
                .enablePendingPurchases()
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "Billing setup finished successfully");
                    querySubscriptionDetails();
                } else {
                    Log.e(TAG, "Billing setup failed: " + billingResult.getDebugMessage());
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Log.d(TAG, "Billing service disconnected");
            }
        });
    }

    private void querySubscriptionDetails() {
        querySpecificProduct(SUBSCRIPTION_SKU, BillingClient.ProductType.SUBS, success -> {
            if (!success) {
                Log.w(TAG, "Real subscription product not found, trying test product...");
                querySpecificProduct(TEST_SUBSCRIPTION_SKU, BillingClient.ProductType.INAPP, testSuccess -> {
                    if (!testSuccess) {
                        Log.e(TAG, "Both real and test products failed");
                        showNoProductsAvailable();
                    }
                });
            }
        });
    }

    private void querySpecificProduct(String productId, String productType, ProductQueryCallback callback) {
        List<QueryProductDetailsParams.Product> productList = new ArrayList<>();
        productList.add(QueryProductDetailsParams.Product.newBuilder()
                .setProductId(productId)
                .setProductType(productType)
                .build());

        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                .setProductList(productList)
                .build();

        billingClient.queryProductDetailsAsync(params, (billingResult, productDetailsList) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                if (!productDetailsList.isEmpty()) {
                    productDetails = productDetailsList.get(0);
                    Log.d(TAG, "Product details retrieved successfully for: " + productId);
                    callback.onResult(true);
                } else {
                    Log.e(TAG, "No product details found for: " + productId);
                    callback.onResult(false);
                }
            } else {
                Log.e(TAG, "Failed to query product details for " + productId + ": " + billingResult.getDebugMessage());
                callback.onResult(false);
            }
        });
    }

    private void showNoProductsAvailable() {
        runOnUiThread(() -> Toast.makeText(this,
                "No subscription products available. Check your setup in Play Console.",
                Toast.LENGTH_LONG).show());
    }

    private void setupClickListeners() {
        // Skip button goes to MainActivity
        btnSkipForNow.setOnClickListener(v -> {
            startActivity(new Intent(Subscription.this, MainActivity.class));
            finish();
        });

        // ✅ Updated: Start Premium button also navigates to MainActivity directly
        btnSubscribe.setOnClickListener(v -> {
            // Direct navigation for now (you can later re-enable billing if needed)
            Toast.makeText(this, "Welcome to Premium!", Toast.LENGTH_SHORT).show();
            navigateToMain();

            // Uncomment the next line to use the actual subscription purchase flow instead
            // launchSubscriptionFlow();
        });
    }

    private void launchSubscriptionFlow() {
        if (!billingClient.isReady()) {
            Toast.makeText(this, "Billing service not ready. Please try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (productDetails != null) {
            List<BillingFlowParams.ProductDetailsParams> productDetailsParamsList = new ArrayList<>();
            productDetailsParamsList.add(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                            .setProductDetails(productDetails)
                            .build());

            BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(productDetailsParamsList)
                    .build();

            BillingResult result = billingClient.launchBillingFlow(this, billingFlowParams);

            if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                Log.d(TAG, "Billing flow launched successfully");
            } else {
                Toast.makeText(this, "Failed to start subscription: " + result.getDebugMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Subscription not available. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (Purchase purchase : purchases) handlePurchase(purchase);
        }
    }

    private void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams params = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

                billingClient.acknowledgePurchase(params, result -> {
                    if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        onSubscriptionSuccess();
                    }
                });
            } else {
                onSubscriptionSuccess();
            }
        }
    }

    private void onSubscriptionSuccess() {
        Toast.makeText(this, "Subscription successful! Welcome to Premium!", Toast.LENGTH_LONG).show();
        getSharedPreferences("subscription_prefs", MODE_PRIVATE)
                .edit()
                .putBoolean("is_premium_user", true)
                .apply();
        navigateToMain();
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (billingClient != null) billingClient.endConnection();
    }

    interface ProductQueryCallback {
        void onResult(boolean success);
    }
}
