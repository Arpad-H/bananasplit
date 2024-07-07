package com.example.bananasplit.dataModel;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class PersonWithJourneys {
    @Embedded public Person person;
    @Relation(
            parentColumn = "personID",
            entityColumn = "journeyID",
            associateBy = @Junction(PersonJourneyCrossRef.class)
    )
    public List<Journey> journeys;
}
