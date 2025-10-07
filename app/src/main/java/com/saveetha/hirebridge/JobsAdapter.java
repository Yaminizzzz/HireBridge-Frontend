package com.saveetha.hirebridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobViewHolder> {
    private Context context;
    private List<Job> jobList;

    public JobsAdapter(Context context, List<Job> jobList) {
        this.context = context;
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_jobs_item, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.tvTitle.setText(job.getTitle());
        holder.tvCompany.setText(job.getCompany());
        holder.tvLocation.setText(job.getLocation());
        holder.tvSalary.setText(job.getSalary());
        holder.tvExperience.setText(job.getExperience());
        holder.tvStatus.setText(job.getStatus());
        holder.tvViewDetails.setOnClickListener(v -> {
            Job clickedJob = jobList.get(position);
            Fragment fragment = JobDetailsFragment.newInstance(clickedJob);

            ((AppCompatActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCompany, tvLocation, tvSalary, tvExperience, tvStatus, tvViewDetails;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvSalary = itemView.findViewById(R.id.tvSalary);
            tvExperience = itemView.findViewById(R.id.tvExperience);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvViewDetails = itemView.findViewById(R.id.tvViewDetails);
        }
    }
}
