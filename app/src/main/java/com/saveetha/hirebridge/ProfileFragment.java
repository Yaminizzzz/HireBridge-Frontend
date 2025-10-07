package com.saveetha.hirebridge;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ImageView imgProfile;
    private EditText etUsername, etCollege, etEmail, etPhone, etLanguages;
    private Button btnEditProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        imgProfile = view.findViewById(R.id.imgProfile);
        etUsername = view.findViewById(R.id.etFirstName);
        etCollege = view.findViewById(R.id.etLastName);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        etLanguages = view.findViewById(R.id.etLanguages);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        loadProfile();

        btnEditProfile.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Edit Profile Clicked", Toast.LENGTH_SHORT).show();
            // Here you can enable fields for editing or navigate to Edit Profile screen
        });

        return view;
    }

    private void loadProfile() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        int userId = requireActivity()
                .getSharedPreferences("HireBridgePrefs", Context.MODE_PRIVATE)
                .getInt("userId", -1);

        if (userId == -1) {
            Toast.makeText(getContext(), "User ID not found, please log in again", Toast.LENGTH_SHORT).show();
            return;
        }

        // replace "USER_ID" with actual logged-in user id

        Call<ProfileResponse> call = apiService.getProfile(userId);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProfileResponse profile = response.body();
                    etUsername.setText(profile.getUsername());
                    etCollege.setText(profile.getCollege());
                    etEmail.setText(profile.getEmail());
                    etPhone.setText(profile.getPhone());
                    etLanguages.setText(profile.getLanguages());
                } else {
                    Toast.makeText(getContext(), "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
