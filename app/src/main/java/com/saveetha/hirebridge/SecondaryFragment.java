package com.saveetha.hirebridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SecondaryFragment extends Fragment {

    private RadioGroup radioGroupStatus;
    private EditText editYear, editBoard, editScore, editSchool;
    private Button btnSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_secondary, container, false);

        // Initialize views
        radioGroupStatus = view.findViewById(R.id.radioGroupStatus);
        editYear = view.findViewById(R.id.editYear);
        editBoard = view.findViewById(R.id.editBoard);
        editScore = view.findViewById(R.id.editScore);
        editSchool = view.findViewById(R.id.editSchool);
        btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            // Get radio button selection
            int selectedId = radioGroupStatus.getCheckedRadioButtonId();
            String status = "";
            if (selectedId != -1) {
                RadioButton selectedRadio = view.findViewById(selectedId);
                status = selectedRadio.getText().toString();
            } else {
                Toast.makeText(getActivity(), "Please select a status", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get input values
            String year = editYear.getText().toString().trim();
            String board = editBoard.getText().toString().trim();
            String score = editScore.getText().toString().trim();
            String school = editSchool.getText().toString().trim();

            // Simple validation (optional)
            if (year.isEmpty() || board.isEmpty() || score.isEmpty() || school.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show the collected info
            String message = "Saved Matriculation:\n" +
                    "Status: " + status + "\n" +
                    "Year: " + year + "\n" +
                    "Board: " + board + "\n" +
                    "Score: " + score + "%\n" +
                    "School: " + school;

            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

            // Optional: Navigate to another fragment after saving
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DiplomaFragment()) // Replace with your target fragment
                     .commit();
        });

        return view;
    }
}
