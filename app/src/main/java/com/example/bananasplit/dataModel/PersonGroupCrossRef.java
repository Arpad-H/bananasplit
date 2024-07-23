package com.example.bananasplit.dataModel;

import androidx.room.Entity;

/**
 * This class represents the cross-reference table between persons and groups.
 * @author Arpad Horvath, Dennis Brockmeyer
 */
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
