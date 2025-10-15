package com.simats.hirebridge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences = getSharedPreferences("HireBridgePrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
            boolean isSubscribed = getSharedPreferences("subscription_prefs", MODE_PRIVATE)
                    .getBoolean("is_premium_user", false);

            if (!isSubscribed) {
                // Not subscribed → go to subscription screen first
                startActivity(new Intent(SplashActivity.this, Subscription.class));
            } else if (isLoggedIn) {
                boolean isDetailsFilled = sharedPreferences.getBoolean("isDetailsFilled", false);
                if (isDetailsFilled) {
                    startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, PersonalDetailsActivity.class));
                }
            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }

            finish();
        }, SPLASH_SCREEN_TIME);
    }
}
