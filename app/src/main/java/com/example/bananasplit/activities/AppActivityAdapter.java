package com.example.bananasplit.activities;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.dataModel.AppActivityTrackerFootprint;
import com.example.bananasplit.databinding.AppActivityListItemBinding;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AppActivityAdapter extends RecyclerView.Adapter<AppActivityAdapter.ViewHolder> {

    private List<AppActivityTrackerFootprint> activityList;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public AppActivityAdapter(List<AppActivityTrackerFootprint> activityList) {
        this.activityList = activityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AppActivityListItemBinding binding = AppActivityListItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppActivityTrackerFootprint activity = activityList.get(position);
        holder.bind(activity);
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public void updateEntries(List<AppActivityTrackerFootprint> newEntries) {
        activityList = newEntries;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final AppActivityListItemBinding binding;

        ViewHolder(AppActivityListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AppActivityTrackerFootprint activity) {
            binding.textViewInitiator.setText(activity.getInitiator());
            binding.textViewDetails.setText(activity.getActivityDetails());
            binding.textViewLocation.setText(activity.getActivityLocation());
            binding.textViewDate.setText(dateFormat.format(activity.getActivityDate()));
        }
    }
}
