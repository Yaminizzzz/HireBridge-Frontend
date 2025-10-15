package com.simats.hirebridge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ResumeFragment extends Fragment {

    private static final int REQUEST_CODE_PICK_RESUME = 1001;

    Button btnUploadResume, btnProceed;
    TextView tvObjective, tvEducation, tvExperience, tvSkills, tvPortfolio;

    public ResumeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resume, container, false);

        btnUploadResume = view.findViewById(R.id.btnUploadResume);
        btnProceed = view.findViewById(R.id.btnProceed);
        tvObjective = view.findViewById(R.id.tvAddObjective);
        tvEducation = view.findViewById(R.id.tvAddEducation);
        tvExperience = view.findViewById(R.id.tvAddJob);
        tvSkills = view.findViewById(R.id.tvAddSkill);
        tvPortfolio = view.findViewById(R.id.tvAddPortfolio);

        // Career Objective click
        tvObjective.setOnClickListener(v -> {
            Fragment fragment = new CareerObjectiveFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Education click
        tvEducation.setOnClickListener(v -> {
            Fragment fragment = new EducationFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Experience click
        tvExperience.setOnClickListener(v -> {
            Fragment fragment = new ExperienceFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Skills click
        tvSkills.setOnClickListener(v -> {
            Fragment fragment = new WorkSamplesFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Portfolio click
        tvPortfolio.setOnClickListener(v -> {
            Fragment fragment = new AddLinkFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Upload Resume Button
        btnUploadResume.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            String[] mimeTypes = {
                    "application/pdf",
                    "application/msword",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            };
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Select Resume"), REQUEST_CODE_PICK_RESUME);
        });

        // Proceed Button
        btnProceed.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Proceeding to Application", Toast.LENGTH_SHORT).show();
            Fragment fragment = new CareerObjectiveFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();

            // TODO: Navigate to next screen
        });

        return view;
    }

    // Handle selected file from file picker
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_RESUME && resultCode == getActivity().RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri fileUri = data.getData();
                Toast.makeText(getActivity(), "Selected file: " + fileUri.getPath(), Toast.LENGTH_LONG).show();

                // TODO: Upload the fileUri or save it as needed
            }
        }
    }
}
