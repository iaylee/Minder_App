package ui;

import javax.swing.*;
import java.io.OutputStream;

// CITATION: based on code by
// Author: Ranganath Kini
// Source: https://stackoverflow.com/questions/19834155/jtextarea-as-console

//Changes output from console to the Stiki note window
public class TextOutputSticky extends OutputStream {
    private JTextArea text;

    // EFFECTS: sets up the area where to move console output
    public TextOutputSticky(JTextArea area) {
        text = area;
    }

    // MODIFIES: this
    // EFFECTS: writes everything from console onto the window where this is outputted
    public void write(int b) {
        text.append(String.valueOf((char) b));
    }
}
