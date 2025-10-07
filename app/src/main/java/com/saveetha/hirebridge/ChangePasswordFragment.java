package com.saveetha.hirebridge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {

    EditText etPassword, etConfirmPassword;
    Button btnSavePassword;

    int userId; // will be fetched from SharedPreferences

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        btnSavePassword = view.findViewById(R.id.btnSavePassword);

        // 🔹 Fetch userId from SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("HireBridgePrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        btnSavePassword.setOnClickListener(v -> changePassword());

        return view;
    }

    private void changePassword() {
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userId == -1) {
            Toast.makeText(getActivity(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<PasswordResponse> call = apiService.changePassword(userId, password);

        call.enqueue(new Callback<PasswordResponse>() {
            @Override
            public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    // 🔹 Clear password fields
                    etPassword.setText("");
                    etConfirmPassword.setText("");
                    // 🔹 Navigate back to ProfileFragment
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new ProfileFragment())
                            .commit();
                } else {
                    Toast.makeText(getActivity(), "Failed to update password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PasswordResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
