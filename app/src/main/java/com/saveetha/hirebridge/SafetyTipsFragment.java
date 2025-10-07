package com.saveetha.hirebridge;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SafetyTipsFragment extends Fragment {

    public SafetyTipsFragment() {
        // Required empty public constructor
    }

    public static SafetyTipsFragment newInstance() {
        return new SafetyTipsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_safety_tips, container, false);
    }
}
