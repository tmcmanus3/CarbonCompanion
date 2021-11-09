package com.example.carboncompanion;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
/**
 * Helper class to store data in the database
 */
public class User {

    public String email;
    public int numActivities;   // total number of activities completed
    public int carbonSaved;     // total amount of carbon saved
    public int goal;            // carbon goal

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumActivities() {
        return numActivities;
    }

    public void setNumActivities(int numActivities) {
        this.numActivities = numActivities;
    }

    public int getCarbonSaved() {
        return carbonSaved;
    }

    public void setCarbonSaved(int carbonSaved) {
        this.carbonSaved = carbonSaved;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(com.example.carboncompanion.User.class)
    }

    public User(String email, int numActivities, int carbonSaved) {
        this.email = email;
        this.numActivities = numActivities;
        this.carbonSaved = carbonSaved;
        // goal defaults to 10
        this.goal = 10;
    }


}
