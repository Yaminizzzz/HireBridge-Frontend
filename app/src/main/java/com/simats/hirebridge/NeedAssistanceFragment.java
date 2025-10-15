package com.simats.hirebridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NeedAssistanceFragment extends Fragment {

    private EditText etSubject, etQuery;
    private Button btnSubmit;
    private TextView tvAttachment;

    public NeedAssistanceFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_need_assistance, container, false);

        etSubject = view.findViewById(R.id.etSubject);
        etQuery = view.findViewById(R.id.etQuery);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        tvAttachment = view.findViewById(R.id.tvAttachment);

        // Handle submit button
        btnSubmit.setOnClickListener(v -> {
            String subject = etSubject.getText().toString().trim();
            String query = etQuery.getText().toString().trim();

            if (subject.isEmpty() || query.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Query Submitted Successfully!", Toast.LENGTH_LONG).show();
                etSubject.setText("");
                etQuery.setText("");
                etQuery.clearFocus();
                etSubject.clearFocus();
                requireActivity().onBackPressed();
                requireActivity().getSupportFragmentManager().popBackStack();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HelpCentreFragment())
                        .commit();
            }
        });

        // Handle attachment click
        tvAttachment.setOnClickListener(v ->
                Toast.makeText(getContext(), "Attachment feature coming soon!", Toast.LENGTH_SHORT).show()
        );

        return view;
    }
}
