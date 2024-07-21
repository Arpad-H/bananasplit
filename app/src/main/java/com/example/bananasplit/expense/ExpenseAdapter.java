package com.example.bananasplit.expense;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Expense;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private final List<Expense> expenses;

    public ExpenseAdapter(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list_item, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.descriptionTextView.setText(expense.getDescription());

        String amount = expense.getAmount() + expense.getCurrency().getCurrencySymbol();
        holder.amountTextView.setText(amount);
        holder.byPersonTextView.setText(expense.getPersonWhoPaid().getName());
        holder.categoryImageView.setImageResource(expense.getCategory().getId());
        holder.dateTextView.setText(expense.getDateString());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public void updateExpenses(List<Expense> newExpenses) {
        expenses.clear();
        expenses.addAll(newExpenses);
        notifyDataSetChanged();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTextView;
        TextView amountTextView;
        TextView dateTextView;
        TextView byPersonTextView;
        ImageView categoryImageView;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.txt_expense);
            amountTextView = itemView.findViewById(R.id.txt_expense_amount);
            byPersonTextView = itemView.findViewById(R.id.txt_by_person);
            categoryImageView = itemView.findViewById(R.id.imageView_category);
            dateTextView = itemView.findViewById(R.id.txt_date);
        }
    }
}
