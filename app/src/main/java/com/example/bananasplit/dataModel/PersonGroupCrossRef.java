package com.example.bananasplit.dataModel;

import androidx.room.Entity;

@Entity(primaryKeys = {"personID", "groupID"})
public class PersonGroupCrossRef {
    public int personID;
    public int groupID;
}

// Wird benötigt, um die Many-to-Many-Relation von Personen und Reisen darzustellen