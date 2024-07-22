package com.example.bananasplit.dataModel;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Entity class representing an activity tracker.
 * Utilizes the Builder pattern for easy and flexible object creation.
 *
 * @Author YourName
 */
@Entity
public class AppActivityTracker {
    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    @PrimaryKey(autoGenerate = true)
    private int activityID;

    private String initiator;
    private String activityDetails;
    private Date activityDate;
    private String activityLocation;

    private AppActivityTracker(Builder builder) {
        this.initiator = builder.initiator;
        this.activityDetails = builder.activityDetails;
        this.activityDate = builder.activityDate != null ? builder.activityDate : new Date();
        this.activityLocation = builder.activityLocation;
    }

    public AppActivityTracker(String initiator, String activityDetails, Date activityDate, String activityLocation) {
        this.initiator = initiator;
        this.activityDetails = activityDetails;
        this.activityDate = activityDate;
        this.activityLocation = activityLocation;
    }

    public String getInitiator() {
        return initiator;
    }

    public String getActivityDetails() {
        return activityDetails;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public String getActivityLocation() {
        return activityLocation;
    }

    public static class Builder {
        private String initiator;
        private String activityDetails;
        private Date activityDate;
        private String activityLocation;

        public Builder() {}

        public Builder setInitiator(String initiator) {
            this.initiator = initiator;
            return this;
        }

        public Builder setActivityDetails(String activityDetails) {
            this.activityDetails = activityDetails;
            return this;
        }

        public Builder setActivityDate(Date activityDate) {
            this.activityDate = activityDate;
            return this;
        }

        public Builder setActivityLocation(String activityLocation) {
            this.activityLocation = activityLocation;
            return this;
        }

        public AppActivityTracker build() {
            return new AppActivityTracker(this);
        }
    }
}
