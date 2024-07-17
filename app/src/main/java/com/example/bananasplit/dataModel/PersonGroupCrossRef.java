package com.example.bananasplit.dataModel;

import androidx.room.Entity;

@Entity(primaryKeys = {"personID", "groupID"})
public class PersonGroupCrossRef {
    private int personID;

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    private int groupID;

    public PersonGroupCrossRef(int personID, int groupID) {
        this.personID = personID;
        this.groupID = groupID;
    }
}

// Wird benötigt, um die Many-to-Many-Relation von Personen und Reisen darzustellen