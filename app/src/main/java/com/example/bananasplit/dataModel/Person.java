package com.example.bananasplit.dataModel;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

/**
 * represents a Person
 * @author Dennis Brockmeyer
 */
@Entity
public class Person implements Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int personID = 0;
    private String name;
    private String email;
    private String imageURI;
    List<Person> friends;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Person(PersonBuilder builder) {
        this.personID = builder.personID;
        this.name = builder.name;
        this.email = builder.email;
        this.imageURI = builder.imageURI;
        this.friends = builder.friends;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Constructor for the Parcelable interface
     *
     * @param in the parcel to read from
     */
    protected Person(Parcel in) {
        personID = in.readInt();
        name = in.readString();
        email = in.readString();
        friends = in.createTypedArrayList(Person.CREATOR);
    }

    /**
     * Parcelable Creator
     */
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


    /**
     * Writes the object to a parcel
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(personID);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeTypedList(friends);
    }

    /**
     * Inner Class to be used as a Builder Pattern
     */
    public static class PersonBuilder {
        private int personID;
        private String name;
        private String email;
        private String imageURI = "";
        private List<Person> friends;

        public PersonBuilder personID(int personID) {
            this.personID = personID;
            return this;
        }

        public PersonBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PersonBuilder imageURI(String imageURI) {
            this.imageURI = imageURI;
            return this;
        }

        public PersonBuilder friends(List<Person> friends) {
            this.friends = friends;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
