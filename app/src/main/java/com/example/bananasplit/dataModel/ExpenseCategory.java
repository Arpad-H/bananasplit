package com.example.bananasplit.dataModel;

import com.example.bananasplit.R;

public enum ExpenseCategory {
    FOOD("Food", R.drawable.fork_knife),
    TRANSPORT("Transport", R.drawable.bus),
    ACCOMMODATION("Accommodation", R.drawable.rockect),
    ACTIVITY("Activity" , R.drawable.bed),
    OTHER("Other", R.drawable.receipt_item);

    private final String category;
    private final int rescourceId;
    ExpenseCategory(String category, int rescourceId) {
        this.category = category;
        this.rescourceId = rescourceId;
    }

    public String getCategory() {
        return category;
    }
    public static ExpenseCategory fromString(String text) {
        for (ExpenseCategory ec : ExpenseCategory.values()) {
            if (ec.category.equalsIgnoreCase(text)) {
                return ec;
            }
        }
        return null;
    }
    public int getId() {
        return rescourceId;
    }
}
