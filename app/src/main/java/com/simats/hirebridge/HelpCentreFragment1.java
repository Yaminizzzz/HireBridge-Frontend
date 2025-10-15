package com.simats.hirebridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HelpCentreFragment1 extends Fragment {

    private RecyclerView recyclerView;
    private FaqAdapter adapter;
    private List<FaqItem> faqList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help_centre1, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFaq);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample FAQ Data
        faqList = new ArrayList<>();
        faqList.add(new FaqItem("I am not receiving the password reset email",
                "1. Check the spam/junk mail folder\n2. Search for 'Reset Your Account Password'\n3. Wait 5 minutes for the reset email"));
        faqList.add(new FaqItem("I forgot my password, how can I change it?",
                "Click on 'Forgot Password' and enter your registered email. You will get a reset link."));
        faqList.add(new FaqItem("How can I apply for jobs?",
                "Go to Jobs section, select a job and click 'Apply'. Fill details and submit."));
        faqList.add(new FaqItem("Can I edit my application after submitting?",
                "No. Once submitted, applications cannot be edited or cancelled."));
        faqList.add(new FaqItem("Can I apply again to a job I was rejected for?",
                "No. You cannot reapply to the same job once rejected."));

        adapter = new FaqAdapter(getContext(), faqList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
