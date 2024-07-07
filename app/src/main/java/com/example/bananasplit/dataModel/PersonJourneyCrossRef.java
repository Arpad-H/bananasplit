package com.example.bananasplit.dataModel;

import androidx.room.Entity;

@Entity(primaryKeys = {"personID", "journeyID"})
public class PersonJourneyCrossRef {
    public int personID;
    public int journeyID;
}

// Wird benötigt, um die Many-to-Many-Relation von Personen und Reisen darzustellen