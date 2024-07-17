package com.example.bananasplit.dataModel;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys =   {
        @ForeignKey(entity = Person.class,
            parentColumns = "personID",
            childColumns = "spenderID",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Group.class,
            parentColumns = "groupID",
            childColumns = "groupID",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE)})
public class Expense implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private int spenderID;
    private int groupID;
    private float amount;
    private Currency currency;

    public Expense(int spenderID, int groupID, float amount, Currency currency) {
        this.spenderID = spenderID;
        this.groupID = groupID;
        this.amount = amount;
        this.currency = currency;
    }
    public float getAmount() {
        return this.amount;
    }


    public int getId() {
        return id;
    }

    public int getSpenderID() {
        return spenderID;
    }

    public Currency getCurrency() {
        return currency;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setSpenderID(int spenderID) {
        this.spenderID = spenderID;
    }
}
