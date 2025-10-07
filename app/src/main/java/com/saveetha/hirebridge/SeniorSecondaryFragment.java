package com.saveetha.hirebridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SeniorSecondaryFragment extends Fragment {

    RadioGroup rgStatus;
    RadioButton rbPursuing, rbCompleted;
    Spinner spinnerYear, spinnerPerformanceType, spinnerStream;
    EditText etBoard, etPerformanceScore, etSchool;
    Button btnSaveSeniorSecondary;

    public SeniorSecondaryFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_senior_secondary, container, false);

        rgStatus = view.findViewById(R.id.rgStatus);
        rbPursuing = view.findViewById(R.id.rbPursuing);
        rbCompleted = view.findViewById(R.id.rbCompleted);
        spinnerYear = view.findViewById(R.id.spinnerYear);
        etBoard = view.findViewById(R.id.etBoard);
        spinnerPerformanceType = view.findViewById(R.id.spinnerPerformanceType);
        etPerformanceScore = view.findViewById(R.id.etPerformanceScore);
        spinnerStream = view.findViewById(R.id.spinnerStream);
        etSchool = view.findViewById(R.id.etSchool);
        btnSaveSeniorSecondary = view.findViewById(R.id.btnSaveSeniorSecondary);

        // Populate Year Spinner dynamically (last 50 years)
        List<String> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        years.add("Choose Year");
        for (int i = thisYear; i >= thisYear - 50; i--) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        // Performance Type
        ArrayAdapter<String> perfAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Percentage", "CGPA"});
        perfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPerformanceType.setAdapter(perfAdapter);

        // Streams
        ArrayAdapter<String> streamAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Choose stream", "Science", "Commerce", "Arts", "Vocational"});
        streamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStream.setAdapter(streamAdapter);

        // Save button click
        btnSaveSeniorSecondary.setOnClickListener(v -> {
            String status = rbPursuing.isChecked() ? "Pursuing" : "Completed";
            String year = spinnerYear.getSelectedItem().toString();
            String board = etBoard.getText().toString().trim();
            String perfType = spinnerPerformanceType.getSelectedItem().toString();
            String perfScore = etPerformanceScore.getText().toString().trim();
            String stream = spinnerStream.getSelectedItem().toString();
            String school = etSchool.getText().toString().trim();

            Toast.makeText(getActivity(),
                    "Saved Senior Secondary:\nStatus: " + status +
                            "\nYear: " + year +
                            "\nBoard: " + board +
                            "\nPerformance: " + perfScore + " " + perfType +
                            "\nStream: " + stream +
                            "\nSchool: " + school,
                    Toast.LENGTH_LONG).show();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SecondaryFragment())
                    .commit();
        });

        return view;
    }
}
