package com.example.bananasplit.dataModel;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Group implements Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int groupID = 0;
    private String name;
    private String date; //TODO: eventuell als Date Object mit Converter?
    private int duration;
    private Currency currency;
    private String imageUri;

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }


    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getDuration() {
        return duration;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    //    public Group(String name, String date, int duration, String imageUri) {
//        this.name = name;
////        this.date = date;
////        this.duration = duration;
//        this.imageUri = imageUri;
//    }
    public Group(String name, String imageUri) {
        this.name = name;
        this.imageUri = imageUri;
    }

    public Group(GroupBuilder builder) {
        this.name = builder.name;
        this.date = builder.date;
        this.duration = builder.duration;
        this.imageUri = builder.imageURI;
        this.currency = builder.currency;
    }


    protected Group(Parcel in) {
        groupID = in.readInt();
        name = in.readString();
//        date = in.readString();
//        duration = in.readInt();
        imageUri = in.readString();
        currency = Currency.from(in.readDouble());
    }

    public void setId(int id) {
        this.groupID = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getImageUri() {
        return imageUri;
    }


    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(groupID);
        dest.writeString(name);
//        dest.writeString(date);
//        dest.writeInt(duration);
        dest.writeString(imageUri);
        dest.writeDouble(currency.getValueInEur());
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public static class GroupBuilder {
        private String name;
        private String date;
        private int duration;
        private String imageURI;
        private Currency currency = Currency.EUR;

        public GroupBuilder name(String name) {
            this.name = name;
            return this;
        }

        public GroupBuilder date(String date) {
            this.date = date;
            return this;
        }

        public GroupBuilder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public GroupBuilder imageURI(String imageURI) {
            this.imageURI = imageURI;
            return this;
        }

        public GroupBuilder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Group build() {
            return new Group(this);
        }
    }
}
