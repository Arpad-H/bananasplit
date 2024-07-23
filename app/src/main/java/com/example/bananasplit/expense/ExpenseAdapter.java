package com.example.bananasplit.expense;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Expense;
import com.example.bananasplit.databinding.ExpenseListItemBinding;

import java.util.List;

/**
 * Adapter for the RecyclerView for Expenses.
 * @author Arpad Horvath
 */
public class ExpenseAdapter extends ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder> {

    /**
     * Constructor for the ExpenseAdapter.
     */
    public ExpenseAdapter() {
        super(DIFF_CALLBACK);
    }

    /**
     * DiffUtil.ItemCallback for the ExpenseAdapter.
     */
    private static final DiffUtil.ItemCallback<Expense> DIFF_CALLBACK = new DiffUtil.ItemCallback<Expense>() {
        @Override
        public boolean areItemsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
            return oldItem.equals(newItem);
        }
    };


    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ExpenseListItemBinding binding = ExpenseListItemBinding.inflate(inflater, parent, false);
        return new ExpenseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = getItem(position);
        holder.bind(expense);
    }
    /**
     * Updates the list of expenses in the adapter.
     * @param newExpenses The new list of expenses.
     */
    public void updateExpenses(List<Expense> newExpenses) {
        submitList(newExpenses);
    }

    /**
     * ViewHolder for the ExpenseAdapter.
     */
    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private final ExpenseListItemBinding binding;

        /**
         * Constructor for the ExpenseViewHolder.
         * @param binding The binding for the ViewHolder.
         */
        public ExpenseViewHolder(@NonNull ExpenseListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Binds the expense to the ViewHolder.
         * @param expense The expense to bind.
         */
        public void bind(Expense expense) {
            binding.txtExpense.setText(expense.getDescription());

            String amount = expense.getAmount() + expense.getCurrency().getCurrencySymbol();
            binding.txtExpenseAmount.setText(amount);
            binding.txtByPerson.setText(expense.getPersonWhoPaid().getName());
            binding.imageViewCategory.setImageResource(expense.getCategory().getId());
            binding.txtDate.setText(expense.getDateString());
        }
    }
}
