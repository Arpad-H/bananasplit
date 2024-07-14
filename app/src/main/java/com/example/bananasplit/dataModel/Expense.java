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
    public int id = 0;
    public int spenderID;
    public int groupID;
    public int amount;
    public Currency currency;

    public Expense(int spenderID, int groupID, int amount, Currency currency) {
        this.spenderID = spenderID;
        this.groupID = groupID;
        this.amount = amount;
        this.currency = currency;
    }
}
