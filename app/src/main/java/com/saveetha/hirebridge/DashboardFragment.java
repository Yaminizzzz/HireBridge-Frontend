package com.saveetha.hirebridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private JobsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = view.findViewById(R.id.recyclerJobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadJobs();
        return view;
    }

    private void loadJobs() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getJobs().enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new JobsAdapter(getContext(), response.body());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "No jobs found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                if (isAdded()) {
                    Toast.makeText(requireContext(), "Failed to load", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
