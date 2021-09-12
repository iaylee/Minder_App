package ui;


import ui.games.Hangman;
import ui.sound.Alerts;
import ui.games.Mastermind;
import ui.note.StickyNote;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javafx.application.Platform.exit;

// Action listener for main menu in sticky note
public class MenuActionListener implements ActionListener {
    private final Alerts alert = new Alerts();
    private final RunReminder startReminding = new RunReminder();
    private final StickyNote gui = new StickyNote();

    // REQUIRES: a valid action event, in this case, the user clicking the option they want
    // MODIFIES: this
    // EFFECTS: allows the user to roam into whichever function they wish to use from the sticky notes
    //           - reminders
    //           - games
    //           - Sticky note
    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();
        if (input.equals("add")) {
            startReminding.add();
        } else if (input.equals("edit")) {
            startReminding.edit();
        } else if (input.equals("delete")) {
            startReminding.delete();
        } else if (input.equals("refresh")) {
            startReminding.refresh();
        } else if (input.equals("save")) {
            alert.playSound("savereminder");
            startReminding.saveReminderList();
        } else if (input.equals("load")) {
            alert.playSound("loadedReminder");
            startReminding.loadReminderList();
        } else if (input.equals("today")) {
            startReminding.compareDate();
        } else if (input.equals("mastermind")) {
            new Mastermind();
        }  else if (input.equals("hangman")) {
            new Hangman();
        } else if (input.equals("newnote")) {
            gui.createNote();
        } else if (input.equals("close")) {
            exit();

        }
    }


}

