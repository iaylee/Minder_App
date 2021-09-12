package ui.note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Sets up the GUI for new stiki notes
public class StickyNote {
    private final JFrame newNote = new JFrame("Note");
    private int fontSize;
    private int posX;
    private int posY;
    private String typeOfFont;
    private Font fontType;
    private JTextArea textArea = new JTextArea("\n", 100, 100);
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();


    private JMenu font;
    private JMenu colour;
    private JButton changeFontSize;
    private JMenuBar menuBar;


    // EFFECTS: sets up the basic text size and type of font
    public StickyNote() {
        fontSize = 15;
        typeOfFont = "plain";
    }


    // MODIFIES: this
    // EFFECTS: creates the window for the new stiki note
    public void createNote() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        newNote.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newNote.setSize(350, 350);
        newNote.setLocation(500, 400);
        addActions();
        fontType = new Font("Comic Sans MS", Font.PLAIN, fontSize);
        newNote.setUndecorated(true);
        textArea.setFont(fontType);
        menuBar = new JMenuBar();
        setMenu();
        BoxLayout boxlayoutXAxis = new BoxLayout(panel1, BoxLayout.X_AXIS);
        panel1.setLayout(boxlayoutXAxis);
        panel1.add(menuBar);
        //Border border = new LineBorder(Color.WHITE, 5, false);
        //panel1.setBorder(border);

        BoxLayout boxlayoutYAxis = new BoxLayout(panel2, BoxLayout.Y_AXIS);
        panel2.setLayout(boxlayoutYAxis);
        panel2.add(panel1);
        panel2.add(textArea);


        newNote.add(panel2);
        textArea.setEditable(true);
        newNote.setVisible(true);
    }

    public void addActions() {
        newNote.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                posX = e.getX();
                posY = e.getY();
            }
        });
        newNote.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                newNote.setLocation(e.getXOnScreen() - posX, e.getYOnScreen() - posY);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: sets up the menu for the new stiki note for the user
    public void setMenu() {
        //setUpColoursOne();
        //setUpColoursTwo();
        setUpColours();
        setUpFontType();
        setUpFontSize();
        JButton b = new JButton("Close");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newNote.dispose();
            }
        });
        menuBar.add(b);
        menuBar.add(colour);
        menuBar.add(font);
        menuBar.add(changeFontSize);
    }


    public JFrame getNewNote() {
        return newNote;
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getFontType() {
        return typeOfFont;
    }

    public void setColour(Color c) {
        newNote.setBackground(c);
        textArea.setBackground(c);
    }

    public void setColour() {
        JColorChooser cc = new JColorChooser();
        JDialog dialog = JColorChooser.createDialog(newNote, "Colours", true, cc, null, null);
        dialog.setVisible(true);
        Color newColor = cc.getColor();
        if (newColor != null) {
            textArea.setBackground(newColor);
            newNote.setBackground(newColor);
        }
//        jcc = new JColorChooser(textArea.getForeground());
//        JFrame colorFrame = new JFrame();
//        colorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        colorFrame.setSize(350, 350);
//        colorFrame.setLocation(50, 70);
//        colorFrame.add(jcc);
//        colorFrame.setVisible(true);
//        Color stikiColor = jcc.getSelectionModel().getSelectedColor();
//        textArea.setBackground(stikiColor);
//        newNote.setBackground(stikiColor);
    }


    // MODIFIES: this
    // EFFECTS: sets the text font for this stiki note
    public void setFont(int size, String type) {
        if (type.toLowerCase().equals("bold")) {
            fontType = new Font("Comic Sans MS", Font.BOLD, size);
            typeOfFont = "bold";
            textArea.setFont(fontType);
        } else if (type.toLowerCase().equals("italic")) {
            fontType = new Font("Comic Sans MS", Font.ITALIC, size);
            typeOfFont = "italic";
            textArea.setFont(fontType);
        } else if (type.toLowerCase().equals("both")) {
            fontType = new Font("Comic Sans MS", Font.BOLD + Font.ITALIC, size);
            typeOfFont = "both";
            textArea.setFont(fontType);
        } else {
            fontType = new Font("Comic Sans MS", Font.PLAIN, size);
            typeOfFont = "plain";
            textArea.setFont(fontType);
        }
        textArea.setVisible(true);

    }

    public void setUpColours() {
        colour = new JMenu(" Colour ");
        JMenuItem reset = new JMenuItem("Reset");
        reset.setActionCommand("reset");
        reset.addActionListener(new ColourActionListener(this));
        JMenuItem change = new JMenuItem("Change Colours");
        change.setActionCommand("change");
        change.addActionListener(new ColourActionListener(this));
        colour.add(reset);
        colour.add(change);
    }

//    // MODIFIES: this
//    // EFFECTS: sets up the valid colours the user can change the stiki to
//    public void setUpColoursOne() {
//        colour = new JMenu("Colour");
//        JMenuItem red = new JMenuItem("Red");
//        red.setActionCommand("red");
//        red.addActionListener(new ColourActionListener(this));
//        JMenuItem yellow = new JMenuItem("Yellow");
//        yellow.setActionCommand("yellow");
//        yellow.addActionListener(new ColourActionListener(this));
//        JMenuItem green = new JMenuItem("Green");
//        green.setActionCommand("green");
//        green.addActionListener(new ColourActionListener(this));
//        colour.add(red);
//        colour.add(yellow);
//        colour.add(green);
//
//    }
//
//    // MODIFIES: this
//    // EFFECTS: one method to set up the colours didn't meet checkstyle requirements so created a second method
//    public void setUpColoursTwo() {
//        JMenuItem blue = new JMenuItem("Blue");
//        blue.setActionCommand("blue");
//        blue.addActionListener(new ColourActionListener(this));
//        JMenuItem purple = new JMenuItem("Purple");
//        purple.setActionCommand("purple");
//        purple.addActionListener(new ColourActionListener(this));
//        colour.add(purple);
//        JMenuItem white = new JMenuItem("White");
//        white.setActionCommand("white");
//        white.addActionListener(new ColourActionListener(this));
//        JMenuItem pink = new JMenuItem("Pink");
//        pink.setActionCommand("pink");
//        pink.addActionListener(new ColourActionListener(this));
//        JMenuItem gray = new JMenuItem("Gray");
//        gray.setActionCommand("gray");
//        gray.addActionListener(new ColourActionListener(this));
//        colour.add(blue);
//        colour.add(purple);
//        colour.add(white);
//        colour.add(pink);
//        colour.add(gray);
//    }

    // MODIFIES: this
    // EFFECTS: sets up the font menu for the stiki note
    public void setUpFontType() {
        font = new JMenu(" Font ");
        JMenuItem bold = new JMenuItem("Bold");
        bold.setActionCommand("bold");
        bold.addActionListener(new StickyActionListener(this));
        JMenuItem italic = new JMenuItem("Italic");
        italic.setActionCommand("italic");
        italic.addActionListener(new StickyActionListener(this));
        JMenuItem both = new JMenuItem("Bold + Italic");
        both.setActionCommand("both");
        both.addActionListener(new StickyActionListener(this));
        JMenuItem plain = new JMenuItem("Regular");
        plain.setActionCommand("plain");
        plain.addActionListener(new StickyActionListener(this));
        font.add(bold);
        font.add(italic);
        font.add(both);
        font.add(plain);
    }

    // MODIFIES: this
    // EFFECTS: sets up the basic font size for the text on the stiki note
    public void setUpFontSize() {
        changeFontSize = new JButton("Change font size");
        changeFontSize.setActionCommand("size");
        changeFontSize.addActionListener(new StickyActionListener(this));
    }

}

