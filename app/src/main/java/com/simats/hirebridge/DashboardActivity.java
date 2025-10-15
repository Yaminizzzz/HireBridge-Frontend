package com.simats.hirebridge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer setup
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Bottom Navigation setup
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int id = item.getItemId();
            if (id == R.id.nav_home) {
                selectedFragment = new DashboardFragment();
            } else if (id == R.id.nav_jobs) {
                selectedFragment = new JobsFragment();
            } else if (id == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });

        // Load default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DashboardFragment())
                    .commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }

    // Drawer item clicks

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_jobs) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new JobsFragment())
                    .commit();

        } else if (id == R.id.nav_notifications) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new NotificationsFragment())
                    .commit();
            Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_help) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HelpCentreFragment())
                    .commit();

        } else if (id == R.id.nav_complaint) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MyApplicationsFragment())
                    .commit();
            Toast.makeText(this, "My Applications clicked", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_safety) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SafetyTipsFragment())
                    .commit();

        } else if (id == R.id.nav_terms) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new TermsFragment())
                    .commit();
        }
        else if (id == R.id.nav_privacy) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PrivacyPolicyFragment())
                    .commit();
        }
        else if (id == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AboutFragment())
                    .commit();
        } else if (id == R.id.nav_update_password) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ChangePasswordFragment())
                    .commit();

        } else if (id == R.id.nav_manage_accounts) {
            Toast.makeText(this, "Manage Accounts clicked", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_logout) {
            // Clear SharedPreferences
            SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear(); // remove all stored data
            editor.apply();

            // Navigate back to login screen
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            finish(); // close DashboardActivity
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
