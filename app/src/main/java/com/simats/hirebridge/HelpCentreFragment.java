package com.simats.hirebridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HelpCentreFragment extends Fragment {

    LinearLayout optionAccount, optionFindJobs, optionApplications,
            optionFacingIssue, optionTechnical, optionAssistance;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help_centre, container, false);

        optionAccount = view.findViewById(R.id.optionAccount);
        optionFindJobs = view.findViewById(R.id.optionFindJobs);
        optionApplications = view.findViewById(R.id.optionApplications);
        optionFacingIssue = view.findViewById(R.id.optionFacingIssue);
        optionTechnical = view.findViewById(R.id.optionTechnical);
        optionAssistance = view.findViewById(R.id.optionAssistance);

        // Click listeners for each option
        optionAccount.setOnClickListener(v -> openFragment(new HelpCentreFragment1()));
        optionFindJobs.setOnClickListener(v -> openFragment(new HelpFindJobsFragment()));
        optionApplications.setOnClickListener(v -> openFragment(new HelpApplicationsFragment()));
        optionFacingIssue.setOnClickListener(v -> openFragment(new FacingIssueFragment()));
        optionTechnical.setOnClickListener(v -> openFragment(new TechnicalIssuesFragment()));
        optionAssistance.setOnClickListener(v -> openFragment(new NeedAssistanceFragment()));

        return view;
    }

    private void openFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment) // Ensure you have this container in activity_dashboard.xml
                .addToBackStack(null) // allows user to go back
                .commit();
    }
}
