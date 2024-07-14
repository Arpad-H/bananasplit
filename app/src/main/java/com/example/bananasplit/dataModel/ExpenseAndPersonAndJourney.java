package com.example.bananasplit.dataModel;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ExpenseAndPersonAndJourney {
    @Embedded public Expense expense;
    @Relation(
            parentColumn = "personID",
            entityColumn = "spenderID"
    )
    public Person person;

    @Relation(
            parentColumn = "journeyID",
            entityColumn = "journeyID"
    )
    public Group group;
}
