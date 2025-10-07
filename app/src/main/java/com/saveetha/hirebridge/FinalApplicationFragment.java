package com.saveetha.hirebridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FinalApplicationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_final_application, container, false);

        // Initialize views
        TextView tvJobTitle = view.findViewById(R.id.tvJobTitle);
        TextView tvCompany = view.findViewById(R.id.tvCompany);
        TextView tvDate = view.findViewById(R.id.tvDate);
        TextView tvExperience = view.findViewById(R.id.tvExperience);
        TextView tvSalary = view.findViewById(R.id.tvSalary);
        TextView tvApplied = view.findViewById(R.id.tvApplied);
        TextView tvVacancies = view.findViewById(R.id.tvVacancies);
        TextView tvEarlyApplicant = view.findViewById(R.id.tvEarlyApplicant);
        TextView tvAppliedStatus = view.findViewById(R.id.tvAppliedStatus);
        ImageView ivBack = view.findViewById(R.id.ivBack);

        // Set back navigation
        ivBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new JobsFragment())
                .addToBackStack(null)
                .commit()
        );

        // Populate views (can be dynamic from bundle/args if needed)
        tvJobTitle.setText("UI/UX Design");
        tvCompany.setText("Growedin Group\nYeshwanthpur, Bangalore");
        tvDate.setText("8th August 2025");
        tvExperience.setText("1 year(s) experience");
        tvSalary.setText("₹ 40,000/month");
        tvApplied.setText("700 applied");
        tvVacancies.setText("15 vacancies");
        tvEarlyApplicant.setText("Be an early applicant");
        tvAppliedStatus.setText("Applied");

        return view;
    }
}
