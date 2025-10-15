package com.simats.hirebridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyApplicationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView textNoApplications;
    private MyApplicationsAdapter adapter; // Adapter class
    private List<String> appliedCompanies;

    public MyApplicationsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_applications, container, false);

        recyclerView = view.findViewById(R.id.recycler_applications);
        textNoApplications = view.findViewById(R.id.text_no_applications);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Example data — replace with your actual database or API data
        appliedCompanies = new ArrayList<>();
        // appliedCompanies.add("Google");
        // appliedCompanies.add("Microsoft");

        if (appliedCompanies.isEmpty()) {
            textNoApplications.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            textNoApplications.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            adapter = new MyApplicationsAdapter(appliedCompanies);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    // ---------------------------
    // RecyclerView Adapter Class
    // ---------------------------
    private static class MyApplicationsAdapter extends RecyclerView.Adapter<MyApplicationsAdapter.ViewHolder> {

        private final List<String> companies;

        public MyApplicationsAdapter(List<String> companies) {
            this.companies = companies;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(companies.get(position));
        }

        @Override
        public int getItemCount() {
            return companies.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
