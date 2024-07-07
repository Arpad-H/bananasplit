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
        @ForeignKey(entity = Journey.class,
            parentColumns = "journeyID",
            childColumns = "journeyID",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE)})
public class Expense implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;
    public int spenderID;
    public int journeyID;

    public Expense(int spenderID, int journeyID) {
        this.spenderID = spenderID;
        this.journeyID = journeyID;
    }
}
