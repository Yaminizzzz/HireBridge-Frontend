package com.simats.hirebridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.fragment.app.Fragment;

public class WorkSamplesFragment extends Fragment {

    private GridLayout gridPlatforms;

    private String[] platforms = {
            "Analytics Vidhya", "Behance", "BitBucket", "Blog", "Blogspot",
            "CodeChef", "Dribbble", "HackerRank", "Figma", "Github",
            "Hubpage", "Kaggle", "Leetcode", "Medium", "Notion",
            "Play Store", "Quora", "Substack", "Tumblr", "Wix",
            "Wordpress", "+ Others"
    };

    public WorkSamplesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_samples, container, false);
        gridPlatforms = view.findViewById(R.id.gridPlatforms);

        for (String platform : platforms) {
            Button btn = new Button(getContext());
            btn.setText(platform);
            btn.setAllCaps(false);
            btn.setBackgroundResource(android.R.drawable.btn_default);
            btn.setPadding(16, 8, 16, 8);
            btn.setOnClickListener(v -> {
                // Navigate to AddLinkFragment with platform name
                Bundle bundle = new Bundle();
                bundle.putString("platform", platform);

                AddLinkFragment addLinkFragment = new AddLinkFragment();
                addLinkFragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, addLinkFragment)
                        .addToBackStack(null)
                        .commit();
            });

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setMargins(8, 8, 8, 8);
            btn.setLayoutParams(params);
            gridPlatforms.addView(btn);
        }

        return view;
    }
}
