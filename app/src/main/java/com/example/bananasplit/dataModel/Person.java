package com.example.bananasplit.dataModel;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity
public class Person implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int personID = 0;
    public String name;
    List<Person> friends;

    public Person(String name) {
        this.name = name;
    }
}
