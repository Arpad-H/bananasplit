package com.example.bananasplit.scanner;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.R;

import java.util.List;

/**
 * Adapter class for displaying a list of ScanEntry objects in a RecyclerView.
 * @author Arpad Horvath
 */
public class ScanEntryAdapter extends RecyclerView.Adapter<ScanEntryAdapter.ScanEntryViewHolder> {

    private List<ScanEntry> scanEntries;

    /**
     * Constructs a ScanEntryAdapter with the provided list of ScanEntry objects.
     *
     * @param scanEntries List of ScanEntry objects to be displayed.
     */
    public ScanEntryAdapter(List<ScanEntry> scanEntries) {
        this.scanEntries = scanEntries;
    }

    @NonNull
    @Override
    public ScanEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_entry_list_item, parent, false);
        return new ScanEntryViewHolder(itemView);
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

    /**
     * ViewHolder class for displaying ScanEntry items.
     */
    static class ScanEntryViewHolder extends RecyclerView.ViewHolder {

        private final TextView entryNameTextView;
        private final TextView entryPriceTextView;

        /**
         * Constructs a ScanEntryViewHolder and initializes the views.
         *
         * @param itemView The view for the item in the RecyclerView.
         */
        public ScanEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            entryNameTextView = itemView.findViewById(R.id.entryName);
            entryPriceTextView = itemView.findViewById(R.id.entryPrice);
        }

        /**
         * Binds the data from a ScanEntry object to the views.
         *
         * @param scanEntry The ScanEntry object containing the data to bind.
         */
        @SuppressLint("DefaultLocale")
        public void bind(ScanEntry scanEntry) {
            entryNameTextView.setText(scanEntry.getName());
            entryPriceTextView.setText(String.format("$%.2f", scanEntry.getTotalPrice())); //Format not affected by locale
        }
    }
}
