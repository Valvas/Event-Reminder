package fr.hexus.aprivate.mondayreminder.Contracts;

import java.io.Serializable;

/**
 * Created by nicolas on 19/01/18.
 */

public class EventCycle implements Serializable{

    private int minutes;
    private int hour;
    private int day;
    private int month;
    private int year;

    public EventCycle(int minutes, int hour, int day, int month, int year) {
        this.minutes = minutes;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return year + " year(s) " + month + " month(s) " + day + " day(s) " + hour + " hour(s) " + minutes + " minute(s)";
    }
}
