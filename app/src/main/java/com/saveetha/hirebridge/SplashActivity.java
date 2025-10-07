package com.saveetha.hirebridge;

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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("HireBridgePrefs", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                if (isLoggedIn) {
                    boolean isDetailsFilled = sharedPreferences.getBoolean("isDetailsFilled", false);
                    if (isDetailsFilled) {
                        // User logged in & details filled → Dashboard
                        startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                    } else {
                        // User logged in but details not filled → PersonalDetailsActivity
                        startActivity(new Intent(SplashActivity.this, PersonalDetailsActivity.class));
                    }
                } else {
                    // Not logged in → Login screen
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        }, SPLASH_SCREEN_TIME);
    }
}
