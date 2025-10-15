package com.simats.hirebridge;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class ExperienceFragment extends Fragment {

    EditText etDesignation, etProfile, etLocation, etDescription;
    CheckBox cbWorkFromHome, cbCurrentWork;
    Button btnStartDate, btnEndDate, btnSaveResume;

    String startDate = "", endDate = "";

    public ExperienceFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_experience, container, false);

        etDesignation = view.findViewById(R.id.etDesignation);
        etProfile = view.findViewById(R.id.etProfile);
        etLocation = view.findViewById(R.id.etLocation);
        etDescription = view.findViewById(R.id.etDescription);
        cbWorkFromHome = view.findViewById(R.id.cbWorkFromHome);
        cbCurrentWork = view.findViewById(R.id.cbCurrentWork);
        btnStartDate = view.findViewById(R.id.btnStartDate);
        btnEndDate = view.findViewById(R.id.btnEndDate);
        btnSaveResume = view.findViewById(R.id.btnSaveResume);

        // Start Date Picker
        btnStartDate.setOnClickListener(v -> showDatePicker(true));

        // End Date Picker
        btnEndDate.setOnClickListener(v -> showDatePicker(false));

        // Save Button Click
        btnSaveResume.setOnClickListener(v -> {
            String designation = etDesignation.getText().toString().trim();
            String profile = etProfile.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            boolean workFromHome = cbWorkFromHome.isChecked();
            boolean currentWork = cbCurrentWork.isChecked();

            Toast.makeText(getActivity(),
                    "Saved Resume:\n" +
                            "Designation: " + designation + "\n" +
                            "Profile: " + profile + "\n" +
                            "Location: " + location + "\n" +
                            "Work from home: " + workFromHome + "\n" +
                            "Currently working: " + currentWork + "\n" +
                            "Start Date: " + startDate + "\n" +
                            "End Date: " + endDate + "\n" +
                            "Description: " + description,
                    Toast.LENGTH_LONG).show();
            // Navigate to another fragment or activity
             requireActivity().getSupportFragmentManager().beginTransaction()
                     .replace(R.id.fragment_container, new WorkSamplesFragment())
                     .commit();
        });

        return view;
    }

    private void showDatePicker(boolean isStart) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (DatePicker view, int year1, int month1, int dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    if (isStart) {
                        startDate = date;
                        btnStartDate.setText(date);
                    } else {
                        endDate = date;
                        btnEndDate.setText(date);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}
