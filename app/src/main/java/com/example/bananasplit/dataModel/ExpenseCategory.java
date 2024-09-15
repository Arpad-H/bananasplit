package com.example.bananasplit.dataModel;

import com.example.bananasplit.R;

/**
 * Enum class for ExpenseCategory
 * Categorizes expenses into 5 categories: Food, Transport, Accommodation, Activity, Other. Settlement is used when Users settle up
 * @author Arpad Horvath
 * */
public enum ExpenseCategory {
    FOOD("Food", R.drawable.fork_knife),
    TRANSPORT("Transport", R.drawable.bus),
    ACCOMMODATION("Accommodation", R.drawable.rockect),
    ACTIVITY("Activity" , R.drawable.bed),
    SETTLEMENT("Settlement",R.drawable.wallet),
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
    /**
     * Returns the ExpenseCategory object that matches the given string
     * @param text the string to match
     * @return the ExpenseCategory object that matches the given string
     * */
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
