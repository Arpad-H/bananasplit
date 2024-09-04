package com.example.bananasplit.dataModel;

import androidx.room.Entity;

/**
 * CrossReference Class for the Many-to-many Relation of Expense and Person
 * @author Dennis Brockmeyer
 */
@Entity(primaryKeys = {"expenseID", "personID"})
public class ExpensePersonCrossRef {
    private int personID;
    private int expenseID;
    private float amount;

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public ExpensePersonCrossRef(int personID, int expenseID, float amount) {
        this.personID = personID;
        this.expenseID = expenseID;
        this.amount = amount;
    }
}