package com.simats.hirebridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class PhdFragment extends Fragment {

    private EditText editCollege, editStartYear, editEndYear, editStream, editScore;
    private Button btnSave;

    public PhdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phd, container, false);

        // Initialize Views
        editCollege = view.findViewById(R.id.editCollege);
        editStartYear = view.findViewById(R.id.editStartYear);
        editEndYear = view.findViewById(R.id.editEndYear);
        editStream = view.findViewById(R.id.editStream);
        editScore = view.findViewById(R.id.editScore);
        btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String college = editCollege.getText().toString();
            String startYear = editStartYear.getText().toString();
            String endYear = editEndYear.getText().toString();
            String stream = editStream.getText().toString();
            String score = editScore.getText().toString();

            // Save logic
            Toast.makeText(getContext(), "PhD details saved!", Toast.LENGTH_SHORT).show();
            // Optional: Navigate to another fragment after saving
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ExperienceFragment())
                    .commit();
        });

        return view;
    }
}
