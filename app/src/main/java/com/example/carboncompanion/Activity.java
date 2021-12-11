package com.example.carboncompanion;

public class Activity {
    private int ActivityType;
    private String input;

    public Activity(int ActivityType, String input) {
        this.ActivityType = ActivityType;
        this.input = input;
    }

    public String toString() {
        switch (ActivityType) {
            case 0:
                return "biked " + input + " miles.";
            case 1:
                return "walked " + input + " miles.";
            case 2:
                return "bussed " + input + " miles.";
            case 3:
                return "planted a tree.";
            case 4:
                return "ate " + input + ".";
            case 5:
                return "recycled " + input + " items.";
            case 6:
                return "turned their lights off.";
            case 7:
                return "volunteered " + input +" times.";
            case 8:
                return "bought " + input + " locally.";
        }
        return "";
    }
    public int getActivityType(){
        return ActivityType;
    }

}
