package ui;

import model.exceptions.IncorrectDate;
import model.Reminder;
import model.ReminderList;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.awt.Checkbox;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

// Runs the reminder program - takes in user input for reminders
public class RunReminder {
    private static final String JSON_STORE = "./data/reminderlist.json";
    private ReminderList reminderList; // list of Reminders entered by the user so far
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame displayFrame = new JFrame("Reminders");
    private String whatToRemind;
    private int hour;
    private int minute;
    private String date;
    private int posX;
    private int posY;
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();


    // EFFECTS: sets up new window and functionality for reminders
    public RunReminder() {
        displayFrame.setSize(550, 300);
        displayFrame.setLocation(500, 400);
        displayFrame.setUndecorated(true);
        displayFrame.setLayout(new BoxLayout(displayFrame.getContentPane(), BoxLayout.Y_AXIS));
        setUpPanelOne();
        displayFrame.add(panel1);
        displayFrame.add(panel2);
        this.reminderList = new ReminderList(new ArrayList<>());
        this.whatToRemind = null;
        this.jsonReader = new JsonReader(JSON_STORE);
        this.jsonWriter = new JsonWriter(JSON_STORE);
    }

    public void setUpPanelOne() {
        BoxLayout boxlayout3 = new BoxLayout(panel1, BoxLayout.X_AXIS);
        panel1.setLayout(boxlayout3);
        JButton b = new JButton("Close");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Reminder> ar = reminderList.getListOfReminder();
                ar.clear();
                displayFrame.dispose();
            }
        });
        JLabel label = new JLabel("   REMINDERS    ");
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 20));
        panel1.add(b);
        panel1.add(label);
        setUpActions();
    }

    public void setUpActions() {
        displayFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                posX = e.getX();
                posY = e.getY();
            }
        });
        displayFrame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                displayFrame.setLocation(e.getXOnScreen() - posX, e.getYOnScreen() - posY);
            }
        });
    }


    // MODIFIES: this
    // EFFECTS: processes user input to add their Reminder to the list of Reminders
    public void add() {
        whatToRemind = JOptionPane.showInputDialog("Enter your reminder: ");
        isValidDate();
        if (date != null) {
            try {
                reminderList.addRemind(whatToRemind, hour, minute, date);
            } catch (IncorrectDate d) {
                JOptionPane.showMessageDialog(null, "Incorrect date format");
            }
            displayReminders();
        }
    }

    // MODIFIES: this
    // EFFECTS: verifies that the date entered is in a valid date format
    public void isValidDate() {
        date = JOptionPane.showInputDialog("Enter date (yyyy/mm/dd): ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            checkValidTime();
        } catch (ParseException e) {
            JOptionPane.showConfirmDialog(null, "Invalid date format", "Error", JOptionPane.DEFAULT_OPTION);
            isValidDate();
        } catch (NullPointerException n) {
            JOptionPane.showMessageDialog(null, "Sorry, something went wrong. Try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: shows the reminders in the reminder list so far
    public void displayReminders() {
        ArrayList<Reminder> myReminders = reminderList.getListOfReminder();
        Comparator byDate = new Comparator<Reminder>() {
            public int compare(Reminder r1, Reminder r2) {
                return Integer.valueOf(r1.getDate().compareTo(r2.getDate()));
            }
        };
        Collections.sort(myReminders, byDate);
        panel2.removeAll();
        BoxLayout boxlayout = new BoxLayout(panel2, BoxLayout.Y_AXIS);
        panel2.setLayout(boxlayout);
        for (Reminder r : myReminders) {
            int index = myReminders.indexOf(r) + 1;
            Checkbox thisReminder = new Checkbox(index + ".) " + r.viewReminder());
            thisReminder.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        thisReminder.setVisible(false);
                        myReminders.remove(r);
                        displayReminders();
                    }
                }
            });
            thisReminder.setVisible(true);
            LocalDate now = LocalDate.now();
            LocalDate reminderDate = LocalDate.of(r.getYear(), r.getMonth(), r.getDay());
            long noOfDaysBetween = ChronoUnit.DAYS.between(now, reminderDate);
            checkBoxBg(noOfDaysBetween, thisReminder);
            panel2.add(thisReminder);
        }
        displayFrame.add(panel2);
        panel1.setBackground(new Color(255, 255, 153));
        displayFrame.setVisible(true);
    }

    public void checkBoxBg(long n, Checkbox r){
        if (n >= 0 && n <= 5) {
            r.setBackground(new Color(255, 102, 102));
        } else if (n > 5 && n <= 14) {
            r.setBackground(new Color(255, 153, 0));
        } else if (n > 14 && n <= 30) {
            r.setBackground(new Color(255, 255, 100));
        } else if (n > 30) {
            r.setBackground(new Color(155, 255, 102));
        }
    }



    // EFFECTS: refreshes the list of reminders so that old ones are removed
    public void refresh() {
        ArrayList<Reminder> myReminders = reminderList.getListOfReminder();
        ArrayList<Reminder> newReminders = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (Reminder r : myReminders) {
            LocalDate reminderDate = LocalDate.of(r.getYear(), r.getMonth(), r.getDay());
            if (now.isBefore(reminderDate) || now.isEqual(reminderDate)) {
                newReminders.add(r);
            }
        }
        reminderList.setListOfReminder(newReminders);
        displayReminders();
    }

    // REQUIRES: index >= 1 -> is listed position of the reminder (see viewAllReminders() in RunReminder class)
    //              that user wants to edit
    //           editedReminder must be a non-empty string
    //           hour must be an int from 0-23
    //           minute must be an int from 0-59
    // MODIFIES: this
    // EFFECTS: processes user input to edit an already existing Reminder in the list of Reminders
    public void edit() {
        if (reminderList.getListOfReminder().size() == 0) {
            JOptionPane.showMessageDialog(displayFrame, "No editable reminders...");
        } else {
            try {
                String optionDialog = JOptionPane.showInputDialog("Which # reminder would you like to edit? ");
                int index = Integer.parseInt(optionDialog);
                reminderList.getListOfReminder().get(index - 1);
                whatToRemind = JOptionPane.showInputDialog("Edit reminder: ");
                isValidDate();
                reminderList.editRemind(index, whatToRemind, hour, minute, date);
                displayReminders();
            } catch (IncorrectDate d) {
                JOptionPane.showMessageDialog(null, "Incorrect date format");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(displayFrame, "That reminder doesn't exist");
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: checks if the hour and minute are valid on the clock
    public void checkValidTime() {
        try {
            hour = Integer.parseInt(JOptionPane.showInputDialog("Enter hour: "));
            if (hour < 0 || hour > 23) {
                JOptionPane.showMessageDialog(null, "Invalid hour");
                checkValidTime();
            }
            minute = Integer.parseInt(JOptionPane.showInputDialog("Enter minute: "));
            if (minute < 0 || minute > 59) {
                JOptionPane.showMessageDialog(null, "Invalid minute");
                checkValidTime();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showConfirmDialog(null, "Invalid time", "Error", JOptionPane.DEFAULT_OPTION);
            checkValidTime();
        }
    }

    // REQUIRES: index >= 1 -> is listed position of the reminder (see viewAllReminders() in RunReminder class)
    //              that user wants to delete
    // MODIFIES: this
    // EFFECTS: deletes an existing Remainder in the list of Reminders
    public void delete() {
        if (reminderList.getListOfReminder().size() == 0) {
            JOptionPane.showMessageDialog(displayFrame, "No deletable reminders...");
        } else {
            String optionDialog = JOptionPane.showInputDialog("Which # reminder would you like to delete? ");
            int index = Integer.parseInt(optionDialog);
            reminderList.deleteRemind(index);
            displayReminders();
        }
    }

    // EFFECTS: compares the date on reminders to today's date to remind the user of their duties today
    public void compareDate() {
        String dueToday = "";
        ArrayList<Reminder> myReminders = reminderList.getListOfReminder();
        LocalDate now = LocalDate.now();
        for (Reminder r : myReminders) {
            LocalDate reminderDate = LocalDate.of(r.getYear(), r.getMonth(), r.getDay());
            if (now.isEqual(reminderDate)) {
                dueToday = dueToday + (myReminders.indexOf(r) + 1) + ", ";
            }
        }
        if (dueToday.length() >= 1) {
            String byeComma = dueToday.substring(0, dueToday.length() - 2);
            JOptionPane.showMessageDialog(displayFrame, "TODO TODAY: \n Reminder #" + byeComma);

        } else {
            JOptionPane.showMessageDialog(displayFrame, "No reminders to do today!");

        }
    }


    // MODIFIES: this
    // EFFECTS: saves reminder list to file
    public void saveReminderList() {
        if (reminderList.getListOfReminder().size() == 0) {
            JOptionPane.showMessageDialog(displayFrame, "Let's add reminders before saving!");
        } else {
            try {
                displayReminders();
                jsonWriter.open();
                jsonWriter.write(reminderList);
                jsonWriter.close();
                JOptionPane.showMessageDialog(displayFrame, "Saved all reminders to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(displayFrame, "Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads reminder list from file
    public void loadReminderList() {
        try {
            reminderList = jsonReader.readReminder();
            if (reminderList.getListOfReminder().size() == 0) {
                JOptionPane.showMessageDialog(displayFrame, "No reminders here! Save or add them now!");
            } else {
                JOptionPane.showMessageDialog(displayFrame, "Loaded all reminders from " + JSON_STORE);
                displayReminders();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(displayFrame, "Unable to read from file: " + JSON_STORE);
        }
    }

}