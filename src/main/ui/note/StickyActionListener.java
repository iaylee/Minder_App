package ui.note;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Changes the format of the text on the sticky note
public class StickyActionListener implements ActionListener {
    private String input;
    private final StickyNote note;

    public StickyActionListener(StickyNote note) {
        this.note = note;
    }

    // REQUIRES: a valid action event, in this case the user clicking the wanted font/size
    // MODIFIES: this
    // EFFECTS: changes the font or the size of the text on the stiki note
    public void actionPerformed(ActionEvent e) {
        input = e.getActionCommand();

        if (input.equals("bold")) {
            note.setFont(note.getFontSize(), "bold");
        } else if (input.equals("italic")) {
            note.setFont(note.getFontSize(), "italic");
        } else if (input.equals("both")) {
            note.setFont(note.getFontSize(), "both");
        } else if (input.equals("plain")) {
            note.setFont(note.getFontSize(), "plain");
        } else if (input.equals("size")) {
            String fontSize = JOptionPane.showInputDialog(note.getNewNote(), "Enter new font size (default = 15): ");
            try {
                int size = Integer.parseInt(fontSize);
                note.setFont(size, note.getFontType());
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(note.getNewNote(), "Invalid font size");
            }
        }
    }
}
