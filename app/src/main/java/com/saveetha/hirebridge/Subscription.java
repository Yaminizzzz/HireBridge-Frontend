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
    private static final String SUBSCRIPTION_SKU = "univault_premium_subscription";
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

                    if (BillingClient.ProductType.SUBS.equals(productType)) {
                        List<ProductDetails.SubscriptionOfferDetails> offers = productDetails.getSubscriptionOfferDetails();
                        if (offers != null && !offers.isEmpty()) {
                            Log.d(TAG, "Available subscription offers: " + offers.size());
                            for (int i = 0; i < offers.size(); i++) {
                                ProductDetails.SubscriptionOfferDetails offer = offers.get(i);
                                Log.d(TAG, "Offer " + i + ": basePlanId=" + offer.getBasePlanId() +
                                        ", offerToken=" + offer.getOfferToken());
                            }
                        } else {
                            Log.w(TAG, "No subscription offers found");
                        }
                    }
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
        btnSkipForNow.setOnClickListener(v -> {
            startActivity(new Intent(Subscription.this, MainActivity.class));
            finish();
        });

        btnSubscribe.setOnClickListener(v -> launchSubscriptionFlow());
    }

    private void launchSubscriptionFlow() {
        if (!billingClient.isReady()) {
            Toast.makeText(this, "Billing service not ready. Please try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (productDetails != null) {
            List<BillingFlowParams.ProductDetailsParams> productDetailsParamsList = new ArrayList<>();

            if (BillingClient.ProductType.SUBS.equals(productDetails.getProductType())) {
                List<ProductDetails.SubscriptionOfferDetails> offers = productDetails.getSubscriptionOfferDetails();
                if (offers == null || offers.isEmpty()) {
                    Toast.makeText(this, "No subscription offers available", Toast.LENGTH_SHORT).show();
                    return;
                }

                ProductDetails.SubscriptionOfferDetails selectedOffer = offers.get(0);
                Log.d(TAG, "Using subscription offer: basePlanId=" + selectedOffer.getBasePlanId() +
                        ", offerToken=" + selectedOffer.getOfferToken());

                productDetailsParamsList.add(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails)
                                .setOfferToken(selectedOffer.getOfferToken())
                                .build());
            } else {
                productDetailsParamsList.add(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails)
                                .build());
            }

            BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(productDetailsParamsList)
                    .build();

            BillingResult result = billingClient.launchBillingFlow(this, billingFlowParams);

            if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                Log.d(TAG, "Billing flow launched successfully");
            } else {
                Toast.makeText(this, "Failed to start subscription process: " + result.getDebugMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Subscription not available. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, List<Purchase> purchases) {
        Log.d(TAG, "onPurchasesUpdated called - Response Code: " + billingResult.getResponseCode());
        Log.d(TAG, "Debug Message: " + billingResult.getDebugMessage());

        switch (billingResult.getResponseCode()) {
            case BillingClient.BillingResponseCode.OK:
                if (purchases != null) {
                    for (Purchase purchase : purchases) handlePurchase(purchase);
                }
                break;
            case BillingClient.BillingResponseCode.USER_CANCELED:
                Toast.makeText(this, "Purchase canceled", Toast.LENGTH_SHORT).show();
                break;
            case BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED:
                Toast.makeText(this, "You already have an active subscription", Toast.LENGTH_SHORT).show();
                navigateToMain();
                break;
            case BillingClient.BillingResponseCode.ITEM_UNAVAILABLE:
                Toast.makeText(this, "Subscription unavailable. Please download app from Play Store for testing.", Toast.LENGTH_LONG).show();
                break;
            case BillingClient.BillingResponseCode.DEVELOPER_ERROR:
                Toast.makeText(this, "Configuration error. Check Play Console setup.", Toast.LENGTH_LONG).show();
                break;
            case BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE:
                Toast.makeText(this, "Google Play services unavailable. Try again later.", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Purchase failed: " + getResponseCodeMessage(billingResult.getResponseCode()), Toast.LENGTH_LONG).show();
        }
    }

    private String getResponseCodeMessage(int responseCode) {
        switch (responseCode) {
            case BillingClient.BillingResponseCode.SERVICE_TIMEOUT:
                return "Service timeout";
            case BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED:
                return "Feature not supported";
            case BillingClient.BillingResponseCode.SERVICE_DISCONNECTED:
                return "Service disconnected";
            case BillingClient.BillingResponseCode.BILLING_UNAVAILABLE:
                return "Billing unavailable";
            case BillingClient.BillingResponseCode.NETWORK_ERROR:
                return "Network error";
            default:
                return "Unknown error (Code: " + responseCode + ")";
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
                        Log.d(TAG, "Purchase acknowledged successfully");
                        onSubscriptionSuccess();
                    } else {
                        Log.e(TAG, "Failed to acknowledge purchase: " + result.getDebugMessage());
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
                .putLong("subscription_time", System.currentTimeMillis())
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
