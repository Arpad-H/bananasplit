package com.example.bananasplit.dataModel;

import androidx.room.Entity;

import java.io.Serializable;


public class Currency implements Serializable{
    public float valueInEur;

    public Currency(float valueInEur) {
        this.valueInEur = valueInEur;
    }
}
