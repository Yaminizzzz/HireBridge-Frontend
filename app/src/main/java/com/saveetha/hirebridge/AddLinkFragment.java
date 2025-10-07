package com.saveetha.hirebridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class AddLinkFragment extends Fragment {

    private static final String ARG_PLATFORM = "platform";
    private String platformName;

    private EditText editLink;
    private TextView txtTitle, btnGoBack;
    private Button btnSave;

    public AddLinkFragment() {}

    public static AddLinkFragment newInstance(String platformName) {
        AddLinkFragment fragment = new AddLinkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLATFORM, platformName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            platformName = getArguments().getString(ARG_PLATFORM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_link, container, false);

        editLink = view.findViewById(R.id.editLink);
        txtTitle = view.findViewById(R.id.txtTitle);
        btnGoBack = view.findViewById(R.id.btnGoBack);
        btnSave = view.findViewById(R.id.btnSave);

        txtTitle.setText("Add your " + platformName + " link");

        btnGoBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        btnSave.setOnClickListener(v -> {
            String link = editLink.getText().toString().trim();
            if (!link.isEmpty()) {
                // Save logic here
                Toast.makeText(getContext(), platformName + " link saved!", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().popBackStack(); // Go back after saving
                // Optional: Navigate to another fragment after saving
                 requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FinalApplicationFragment())
                        .commit();
            } else {
                Toast.makeText(getContext(), "Please enter a valid link", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
