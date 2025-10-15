package com.simats.hirebridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EducationFragment extends Fragment {

    TextView tvGraduation, tvSeniorSecondary, tvSecondary, tvDiploma, tvPhd;

    public EducationFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education, container, false);

        tvGraduation = view.findViewById(R.id.tvGraduation);
        tvSeniorSecondary = view.findViewById(R.id.tvSeniorSecondary);
        tvSecondary = view.findViewById(R.id.tvSecondary);
        tvDiploma = view.findViewById(R.id.tvDiploma);
        tvPhd = view.findViewById(R.id.tvPhd);

// Click listeners
        tvGraduation.setOnClickListener(v -> {
            // Open GraduationFragment
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new GraduationFragment())
                    .addToBackStack(null)
                    .commit();
        });

        tvSeniorSecondary.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SeniorSecondaryFragment())
                    .addToBackStack(null)
                    .commit();
        });

        tvSecondary.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SecondaryFragment())
                    .addToBackStack(null)
                    .commit();
        });

        tvDiploma.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new DiplomaFragment())
                    .addToBackStack(null)
                    .commit();
        });

        tvPhd.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new PhdFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;

    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
