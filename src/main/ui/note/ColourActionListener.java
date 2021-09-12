package ui.note;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Action listener to change the colour of stickies based on user input
public class ColourActionListener implements ActionListener {
    private String input;
    private final StickyNote note;

    public ColourActionListener(StickyNote note) {
        this.note = note;
    }

    // REQUIRES: a valid action event, in this case the user clicking the wanted colour
    // MODIFIES: this
    // EFFECTS: changes the colour of the stiki to the colour the user wants
    public void actionPerformed(ActionEvent e) {
        input = e.getActionCommand();
        if (input.equals("change")) {
            note.setColour();
        } else if (input.equals("reset")) {
            note.setColour(new Color(255, 255, 153));
        }

    }
}
