package com.example.bananasplit.dataModel;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class JourneyWithPersons {
    @Embedded public Journey journey;
    @Relation(
            parentColumn = "journeyID",
            entityColumn = "personID",
            associateBy = @Junction(PersonJourneyCrossRef.class)
    )
    public List<Person> persons;
}

// Unused for now