package com.example.bananasplit.scanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.ListItemHolder;
import com.example.bananasplit.R;

import java.util.List;

public class ScanEntryAdapter extends RecyclerView.Adapter<ScanEntryAdapter.ScanEntryViewHolder> {
    private List<ScanEntry> scanEntries;


    public ScanEntryAdapter(List<ScanEntry> scanEntries){
        this.scanEntries = scanEntries;
    }

    @NonNull
    @Override
    public ScanEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_entry_list_item, parent, false);
        return new ScanEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScanEntryViewHolder holder, int position) {
        ScanEntry scanEntry = scanEntries.get(position);
        holder.bind(scanEntry);
    }

    @Override
    public int getItemCount() {
        return scanEntries.size();
    }

    static class ScanEntryViewHolder extends RecyclerView.ViewHolder {
        private TextView entryNameTextView;
        private TextView entryPriceTextView;

        public ScanEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            entryNameTextView = itemView.findViewById(R.id.entryName);
            entryPriceTextView = itemView.findViewById(R.id.entryPrice);


        }

        public void bind(ScanEntry scanEntry) {
            entryNameTextView.setText(scanEntry.getName());
            entryPriceTextView.setText(String.valueOf(scanEntry.getTotalPrice()));
        }
    }
}
