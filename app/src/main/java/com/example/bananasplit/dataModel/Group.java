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
    public int groupID = 0;
    public String name;
    public String date; //TODO: eventuell als Date Object mit Converter?
    public int duration;

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

    public Group(String name, String date, int duration) {
        this.name = name;
        this.date = date;
        this.duration = duration;
    }
    protected Group(Parcel in) {
        groupID = in.readInt();
        name = in.readString();
        date = in.readString();
        duration = in.readInt();
    }

    public void setId(int id) {
        this.groupID = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(groupID);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeInt(duration);
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
