package com.simats.hirebridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;

public class JobDetailsFragment extends Fragment {

    public static final String ARG_JOB = "job";

    private Job job;


    public JobDetailsFragment() {}


    public static JobDetailsFragment newInstance(Job job) {
        JobDetailsFragment fragment = new JobDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_JOB, (Serializable) job);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_job_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            job = (Job) getArguments().getSerializable(ARG_JOB);
        }

        if (job != null) {
            ((TextView) view.findViewById(R.id.tvTitle)).setText(job.getTitle());
            ((TextView) view.findViewById(R.id.tvCompany)).setText(job.getCompany());
            ((TextView) view.findViewById(R.id.tvLocation)).setText(job.getLocation());
            ((TextView) view.findViewById(R.id.tvSalary)).setText(job.getSalary());
            ((TextView) view.findViewById(R.id.tvExperience)).setText(job.getExperience());
            ((TextView) view.findViewById(R.id.tvStatus)).setText(job.getStatus());
            // Add other fields as needed
        }
        view.findViewById(R.id.btnApply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Optional: show confirmation
                // Toast.makeText(getContext(), "Applied Successfully!", Toast.LENGTH_SHORT).show();

                // Navigate to ResumeFragment
                Fragment resumeFragment = new ResumeFragment();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, resumeFragment) // replace with your container ID
                        .addToBackStack(null) // so you can go back with the back button
                        .commit();
            }
        });


    }
}
