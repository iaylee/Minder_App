package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.lang.System.exit;


//Runs the MinderApp
public class MinderApp extends JFrame {
    private JFrame frame = new JFrame("Sticky Note");
    private JMenu reminder;
    private JMenu games;
    private JMenu stiki;
    private JMenuBar mb;
    private JTextArea textArea;
    private int posX;
    private int posY;
    private JScrollPane scroll;
    private MenuActionListener menuActionListener = new MenuActionListener();


    // MODIFIES: this
    // EFFECTS: sets up the main structure of the main sticky note window
    public void load() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 350);
        frame.setUndecorated(true);
        addAction();
        mb = new JMenuBar();
        setUpMenus();
        setUpTextPane();
        setUpExit();
        mb.add(stiki);
        mb.add(reminder);
        mb.add(games);
        frame.setJMenuBar(mb);
        frame.add(textArea);
        frame.setVisible(true);
    }

    public void addAction() {
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                posX = e.getX();
                posY = e.getY();
            }
        });
        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                frame.setLocation(e.getXOnScreen() - posX, e.getYOnScreen() - posY);
            }
        });
    }


    // MODIFIES: this
    // EFFECTS: sets up the text area that is editable by the user
    public void setUpTextPane() {
        textArea = new JTextArea(5, 20);
        textArea.setEditable(true);
        textArea.setBackground((new Color(255, 255, 153)));
        scroll = new JScrollPane(textArea);
    }

    // MODIFIES: this
    // EFFECTS: sets up the menus for the main window of the sticky notes
    public void setUpMenus() {
        setUpMenusNote();
        setUpMenusReminder();
        setUpMenusGames();
    }

    public void setUpExit() {
        JButton close = new JButton("Exit");
        close.setActionCommand("close");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit(0);
            }
        });
        mb.add(close);


    }


    // MODIFIES: this
    // EFFECTS: sets up menu for new Stiki note
    public void setUpMenusNote() {
        stiki = new JMenu("  Stiki   ");
        JMenuItem stikiMenu = new JMenuItem("New note");
        stikiMenu.setActionCommand("newnote");
        stikiMenu.addActionListener(menuActionListener);
        stiki.add(stikiMenu);
    }

    // MODIFIES: this
    // EFFECTS: sets up menu for games
    public void setUpMenusGames() {
        games = new JMenu("  Games   ");
        JMenuItem mastermind = new JMenuItem("Mastermind");
        mastermind.setActionCommand("mastermind");
        mastermind.addActionListener(menuActionListener);
        JMenuItem hangman = new JMenuItem("Hangman");
        hangman.setActionCommand("hangman");
        hangman.addActionListener(menuActionListener);
        games.add(mastermind);
        games.add(hangman);
    }

    // MODIFIES: this
    // EFFECTS: set up menu for reminders
    public void setUpMenusReminder() {
        reminder = new JMenu("  Reminders  ");
        JMenuItem add = new JMenuItem("Add ");
        add.setActionCommand("add");
        add.addActionListener(menuActionListener);
        JMenuItem edit = new JMenuItem("Edit ");
        edit.setActionCommand("edit");
        edit.addActionListener(menuActionListener);
        JMenuItem delete = new JMenuItem("Delete ");
        delete.setActionCommand("delete");
        delete.addActionListener(menuActionListener);
        JMenuItem refresh = new JMenuItem("Refresh ");
        refresh.setActionCommand("refresh");
        refresh.addActionListener(menuActionListener);
        JMenuItem today = new JMenuItem("To do today ");
        today.setActionCommand("today");
        today.addActionListener(menuActionListener);
        reminder.add(add);
        reminder.add(edit);
        reminder.add(delete);
        reminder.add(refresh);
        reminder.add(today);
        setUpJson();
    }

    // MODIFIES: this
    // EFFECTS: sets up menu to be able to save and load reminders from file
    public void setUpJson() {
        JMenuItem save = new JMenuItem("Save ");
        save.setActionCommand("save");
        save.addActionListener(menuActionListener);
        JMenuItem load = new JMenuItem("Load ");
        load.setActionCommand("load");
        load.addActionListener(menuActionListener);
        reminder.add(save);
        reminder.add(load);
    }

}
