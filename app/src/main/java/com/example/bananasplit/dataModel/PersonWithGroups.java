package com.example.bananasplit.dataModel;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class PersonWithGroups {
    @Embedded public Person person;
    @Relation(
            parentColumn = "personID",
            entityColumn = "groupID",
            associateBy = @Junction(PersonGroupCrossRef.class)
    )
    public List<Group> groups;
}
