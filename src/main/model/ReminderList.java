package model;

import model.exceptions.IncorrectDate;
import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.util.ArrayList;


// Represents the list of Reminders entered by the user and operations (add/edit/delete) that can be done on the list.
public class ReminderList implements Writable {
    private ArrayList<Reminder> listOfReminder; //all reminders user entered since program started
    private String name;


    // EFFECTS: receives an empty list of reminders and set up the reminder list
    public ReminderList(ArrayList<Reminder> al) {
        this.listOfReminder = al;
        this.name = "reminder list";
    }


    //for tests
    public ArrayList<Reminder> getListOfReminder() {
        return listOfReminder;
    }

    public void setListOfReminder(ArrayList<Reminder> arr) {
        listOfReminder = arr;
    }

    public int getNumElementsInListOfReminder() {
        return listOfReminder.size();
    }

    public String getName() {
        return name;
    }


    // REQUIRES: thingToRemind must be a non-empty string
    //           hour must be an int between 0-23
    //           minute must be an int between 0-59
    //           date must be a string in the form "yyyy/mm/dd"
    // MODIFIES: this
    // EFFECTS: adds new Reminder to the end of listOfReminder
    public void addRemind(String thingToRemind, int hour, int minute, String date) throws IncorrectDate {
        if (date.length() != 10) {
            throw new IncorrectDate();//JOptionPane.showMessageDialog(null, "Incorrect date format");
        } else {
            Reminder r = new Reminder(thingToRemind, hour, minute, date);
            listOfReminder.add(r);
        }
    }


    // REQUIRES: thingToRemind must be a valid Reminder
    // MODIFIES: this
    // EFFECTS: adds new Reminder to the end of listOfReminder
    public void addRemind(Reminder r) {
        listOfReminder.add(r);

    }


    // REQUIRES: index >= 1 -> is listed position of the reminder (see viewAllReminders() in RunReminder class)
    //               that user wants to edit
    //           editedReminder must be a non-empty String
    //           hour must be an int between 0-23
    //           minute must be an int between 0-59
    //           date must be a string in the form "yyyy/mm/dd"
    //           listOfReminder must contain at least one Reminder
    // MODIFIES: this
    // EFFECTS: edits Reminder at the index given by the user
    //          by replacing the original whatToRemind and time with editedReminder and newTime
    public void editRemind(int index, String editedReminder, int hour, int minute, String date) throws IncorrectDate {
        if (date.length() != 10) {
            throw new IncorrectDate();//JOptionPane.showMessageDialog(null, "Incorrect date format");
        } else {
            listOfReminder.get(index - 1).setReminder(editedReminder, hour, minute, date);
        }
    }


    // REQUIRES: index >= 1 -> is listed position of the reminder (see viewAllReminders() in RunReminder class)
    //               that user wants to delete
    //           listOfReminder must contain at least one Reminder
    // MODIFIES: this
    // EFFECTS: deletes the Reminder at the index given by the user
    public void deleteRemind(int index) {
        listOfReminder.remove(index - 1);

    }


    // EFFECTS: changes Reminders in ReminderList to a JSON object so it can be saved
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put(name, listOfReminderToJson());
        return json;
    }


    // EFFECTS: changes each Reminder to a JSON object
    private JSONArray listOfReminderToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Reminder r : listOfReminder) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }
}