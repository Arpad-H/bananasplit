package com.example.bananasplit.dataModel;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class GroupWithPersons {
    @Embedded public Group group;
    @Relation(
            parentColumn = "groupID",
            entityColumn = "personID",
            associateBy = @Junction(PersonGroupCrossRef.class)
    )
    public List<Person> persons;
}

