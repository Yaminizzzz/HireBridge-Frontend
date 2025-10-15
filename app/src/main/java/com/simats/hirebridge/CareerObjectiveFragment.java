package com.simats.hirebridge;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CareerObjectiveFragment extends Fragment {

    EditText etCareerObjective;
    TextView tvCharCount;
    Button btnSave;

    public CareerObjectiveFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_career_objective, container, false);

        etCareerObjective = view.findViewById(R.id.etCareerObjective);
        tvCharCount = view.findViewById(R.id.tvCharCount);
        btnSave = view.findViewById(R.id.btnSaveObjective);

        // Update character count
        etCareerObjective.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCharCount.setText(s.length() + "/300");
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Save button click
        btnSave.setOnClickListener(v -> {
            String careerObjective = etCareerObjective.getText().toString().trim();
            if (careerObjective.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter your career objective", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Career Objective Saved", Toast.LENGTH_SHORT).show();
                Fragment fragment = new EducationFragment();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
                // TODO: Save to backend or pass to next fragment
            }
        });

        return view;
    }
}
