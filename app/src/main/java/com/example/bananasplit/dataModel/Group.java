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

    public Group(String name, String date, int duration, String imageUri) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.imageUri = imageUri;
    }

    protected Group(Parcel in) {
        groupID = in.readInt();
        name = in.readString();
        date = in.readString();
        duration = in.readInt();
        imageUri = in.readString();
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
        dest.writeString(date);
        dest.writeInt(duration);
        dest.writeString(imageUri);
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
}
