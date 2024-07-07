package com.example.bananasplit.dataModel;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Journey implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int journeyID = 0;
    public String name;
    public String date; //TODO: eventuell als Date Object mit Converter?
    public int duration;

    public Journey(String name, String date, int duration) {
        this.name = name;
        this.date = date;
        this.duration = duration;
    }
}
