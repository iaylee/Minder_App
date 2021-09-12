package ui.games;

import ui.sound.Alerts;

import javax.swing.*;

// Very basic set up for a game
public abstract class Game {
    JFrame gameFrame = new JFrame("Games");

    Alerts alert = new Alerts();

    JTextArea console = new JTextArea(60, 50);

    JTextField input1 = new JTextField();


    JScrollPane scroll = new JScrollPane(console);

    JPanel panel = new JPanel();

    public abstract void welcome();




}
