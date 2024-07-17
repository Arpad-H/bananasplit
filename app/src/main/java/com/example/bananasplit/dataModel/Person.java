package com.example.bananasplit.dataModel;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity
public class Person implements Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int personID = 0;
    private String name;
    List<Person> friends;

    public Person(String name) {
        this.name = name;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Person(Parcel in) {
        personID = in.readInt();
        name = in.readString();
        friends = in.createTypedArrayList(Person.CREATOR);
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(personID);
        dest.writeString(name);
        dest.writeTypedList(friends);
    }
}
