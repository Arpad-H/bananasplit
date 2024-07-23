package com.example.bananasplit.util;

import com.example.bananasplit.dataModel.Person;
/**
 * Adapter Interface for logging activities
 * @author Arpad Horvath
 */
public interface ActivityLogger {

     /**
      * Logs an activity for example to a log file or database
      * @param initiator the person who initiated the activity
      * @param activityDetails the details of the activity
      * @param activityLocation the location of the activity
      */
     void logActivity(String initiator, String activityDetails, String activityLocation);
}
