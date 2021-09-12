package model;


import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

// Represents a reminder with its reminding time
public class Reminder implements Writable {
    private String whatToRemind;  // what to remind the user
    private int hour; // the hour of the time to remind the user
    private int minute; // the minute of the time to remind the user
    private int year;
    private int month;
    private int day;
    private LocalDate date;


    // REQUIRES: remind must be a non-empty string
    //           hour must be an int between 0 - 23
    //           minute must be an int between 0 - 59
    //           date must be a string in the format "yyyy/mm/dd"
    // EFFECTS: whatToRemind for this Reminder is set to remind
    //          hour is set to hour, minute to minute and date to date
    //          from date, the string is split up into year, month and day, then stored separately
    public Reminder(String remind, int hour, int minute, String date) {
        this.whatToRemind = remind;
        this.hour = hour;
        this.minute = minute;// for tests
        this.year = Integer.parseInt(date.substring(0, 4));
        this.month = Integer.parseInt(date.substring(5, 7));
        this.day = Integer.parseInt(date.substring(8));


    }


    // all getters and setters are for tests
    public LocalDate getDate() {
        return LocalDate.of(year, month, day);
    }

    public void setWhatToRemind(String whatToRemind) {
        this.whatToRemind = whatToRemind;
    }

    public String getWhatToRemind() {
        return whatToRemind;
    }

    public int getHour() {
        return hour;
    }


    public int getMinute() {
        return minute;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int newMonth) {
        this.month = newMonth;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int newDay) {
        this.day = newDay;
    }


    // REQUIRES: newRemind must be a non-empty string
    //           newHour must be an int between 0 - 23
    //           newMinute must be an int between 0 - 59
    //           newDate must be a string in the format "yyyy/mm/dd"
    // EFFECTS: whatToRemind for this Reminder is set to newRemind
    //          hour is set to newHour, minute to newMinute and date to newDate
    //          from newDate, the string is split up into year, month and day, then stored separately
    public void setReminder(String newRemind, int newHour, int newMinute, String newDate) {
        whatToRemind = newRemind;
        hour = newHour;
        minute = newMinute;
        this.year = Integer.parseInt(newDate.substring(0, 4));
        this.month = Integer.parseInt(newDate.substring(5, 7));
        this.day = Integer.parseInt(newDate.substring(8));
        this.date = LocalDate.of(year, month, day);

    }


    // EFFECTS: returns a String representation of a reminder
    public String viewReminder() {
        String date = year + "/" + month + "/" + day;
        return "Date: " + date + "   Time: " + hour + "h " + minute + "m    Reminder: " + whatToRemind;
    }



    //EFFECTS: take a reminder and changes it to a JSON object to be able to store it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        String strMonth = String.valueOf(month);
        String strDay = String.valueOf(day);
        int checkMonth = strMonth.length();
        int checkDay = strDay.length();

        if (checkMonth == 1) {
            strMonth = String.format("%02d", month);
        }

        if (checkDay == 1) {
            strDay = String.format("%02d", day);
        }
        String date = year + "/" + strMonth + "/" + strDay;
        json.put("reminder", whatToRemind);
        json.put("hour", hour);
        json.put("minute", minute);
        json.put("date", date);

        return json;

    }


}
