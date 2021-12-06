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
                return "Biked " + input + " miles.";
            case 1:
                return "Walked " + input + " miles.";
            case 2:
                return "Bussed " + input + " miles.";
            case 3:
                return "Planted a tree.";
            case 4:
                return "Ate " + input;
            case 5:
                return "Recycled " + input;
            case 6:
                return "Turned his lights off.";
            case 7:
                return "Volunteered doing " + input;
            case 8:
                return "Bought " + input + " locally";
        }
        return "";
    }

}
