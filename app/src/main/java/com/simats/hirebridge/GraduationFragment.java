package com.simats.hirebridge;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class GraduationFragment extends Fragment {

    EditText etCollege, etDegree, etStream, etPerformanceScore;
    Spinner spinnerPerformanceType;
    Button btnStartYear, btnEndYear, btnSaveGraduation;

    String startYear = "", endYear = "";

    public GraduationFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graduation, container, false);

        etCollege = view.findViewById(R.id.etCollege);
        etDegree = view.findViewById(R.id.etDegree);
        etStream = view.findViewById(R.id.etStream);
        etPerformanceScore = view.findViewById(R.id.etPerformanceScore);
        spinnerPerformanceType = view.findViewById(R.id.spinnerPerformanceType);
        btnStartYear = view.findViewById(R.id.btnStartYear);
        btnEndYear = view.findViewById(R.id.btnEndYear);
        btnSaveGraduation = view.findViewById(R.id.btnSaveGraduation);

        // Spinner values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.performance_type,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPerformanceType.setAdapter(adapter);

        // Year Pickers
        btnStartYear.setOnClickListener(v -> showYearPicker(true));
        btnEndYear.setOnClickListener(v -> showYearPicker(false));

        // Save Button
        btnSaveGraduation.setOnClickListener(v -> {
            String college = etCollege.getText().toString().trim();
            String degree = etDegree.getText().toString().trim();
            String stream = etStream.getText().toString().trim();
            String score = etPerformanceScore.getText().toString().trim();
            String performanceType = spinnerPerformanceType.getSelectedItem().toString();


            Toast.makeText(getActivity(),
                    "Saved Graduation:\n" +
                            "College: " + college + "\n" +
                            "Start Year: " + startYear + "\n" +
                            "End Year: " + endYear + "\n" +
                            "Degree: " + degree + "\n" +
                            "Stream: " + stream + "\n" +
                            "Performance: " + score + " " + performanceType,
                    Toast.LENGTH_LONG).show();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SeniorSecondaryFragment())
                    .commit();
        });

        return view;
    }

    private void showYearPicker(boolean isStart) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year1, month, day) -> {
                    if (isStart) {
                        startYear = String.valueOf(year1);
                        btnStartYear.setText(startYear);
                    } else {
                        endYear = String.valueOf(year1);
                        btnEndYear.setText(endYear);
                    }
                }, year, c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        // Safely hide day and month if they exist
        try {
            int dayId = getResources().getIdentifier("day", "id", "android");
            int monthId = getResources().getIdentifier("month", "id", "android");

            View dayView = datePickerDialog.getDatePicker().findViewById(dayId);
            View monthView = datePickerDialog.getDatePicker().findViewById(monthId);

            if (dayView != null) dayView.setVisibility(View.GONE);
            if (monthView != null) monthView.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace(); // Log any unexpected errors
        }

        datePickerDialog.show();
    }

}
