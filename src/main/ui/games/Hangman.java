package ui.games;

import ui.TextOutputSticky;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

public class Hangman extends Game {
    private ArrayList<String> words = new ArrayList<>();
    private ArrayList<String> alreadyGuessed = new ArrayList<>();
    private String secretWord;
    private String userInput = "";
    private String curDash;
    private int guessesLeft;
    private int state;
    private TextOutputSticky outputStream;
    private String[] hangmanBody = {"|        / \\ ",
            "|        /",
            "|        /|\\ ",
            "|        /|",
            "|         0",
            "|         |",};
    private String[] gallows;

    public Hangman() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 404");
        }
        gameFrame.setSize(600, 350);
        gameFrame.setLocation(500, 400);
        gameFrame.setUndecorated(true);
        gameFrame.setVisible(true);
        console.setEditable(false);
        input1.setCaretColor(Color.RED);
        setUpHangman();
        welcome();
    }

    // MODIFIES: this
    // EFFECTS: sets up the new frame for mastermind
    public void setUpHangman() {
        setUpInputAction();
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(input1);
        scroll.setVisible(true);
        BoxLayout gameLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(gameLayout);
        gameFrame.add(panel);
        changeOutputStream();
    }


    public void welcome() {
        System.out.println("Welcome to Hangman!");
        System.out.println();
        System.out.println("HOW TO PLAY ");
        System.out.println(" ---------------------------------------------------------");
        System.out.println("The computer will generate a random word and you, as the");
        System.out.println("user, will have to guess the word within a certain number");
        System.out.println("of guesses before the body of Hangman is completed.");
        System.out.println(" ---------------------------------------------------------");
//        System.out.println("LEVELS:");
//        System.out.println("There are 3 different types of difficulty that you are");
//        System.out.println("able to choose from.");
//        System.out.println("1.) EASY - gives you 13 guesses");
//        System.out.println("2.) INTERMEDIATE - gives you 10 guesses");
//        System.out.println("3.) DIFFICULT - gives you 7 guesses");
//        System.out.println("*Hangman's body will also differ with which the difficulty");
//        System.out.println(" that you select.");
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("LET'S GET STARTED!");
        System.out.println();
        setUp();
    }

    public void setUp() {
        guessesLeft = 8;
        state = 0;
        setWords();
        setSecretWord();
        setGallows();
        printGallows();
        setDashes();
        System.out.println(curDash);
        setUpInputAction();
    }

    private void setWords() {
        words.add("pillow");
        words.add("university");
        words.add("strawberry");
        words.add("astronaut");
        words.add("calendar");
        words.add("lotion");

    }

    public void setSecretWord() {
        Random rand = new Random();
        int index = rand.nextInt(words.size());
        secretWord = words.get(index);
    }

    public void setGallows() {
        gallows = new String[]{"__________", "|         |", "|         |", "|", "|", "|", "|", "|", "~~~~~~~~~~~~"};
    }

    public void printGallows() {
        for (int i = 0; i < gallows.length; i++) {
            System.out.println(gallows[i]);
        }
    }

    public void setDashes() {
        curDash = "";
        for (int i = 0; i < secretWord.length(); i++) {
            curDash += "_";
        }
    }


    // EFFECTS: changes output stream from console to the window where the game is being played
    public void changeOutputStream() {
        outputStream = new TextOutputSticky(console);
        //The three lines below are based on code from:
        // Author: Ranganath Kini
        // Source: https://stackoverflow.com/questions/19834155/jtextarea-as-console
        PrintStream out = new PrintStream(outputStream);
        System.setOut(out);
        System.setErr(out);
    }


    public void setUpInputAction() {
        input1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInput = input1.getText();
                input1.setText("");
                if (state == 0) {
                    if (!userInput.equals("")) {
                        checkGuess();
                    }
                } else if (state == 1) {
                    if (userInput.equals("y")) {
                        System.out.println("Loading new game...");
                        setUp();
                    } else if (userInput.equals("q")) {
                        System.out.println("See you next time!");
                        gameFrame.dispose();
                    }
                }
            }
        });
    }

    public void checkGuess() {
        System.out.println();
        System.out.println();
        System.out.println("Your guess >>> " + userInput);
        if (userInput.equals(secretWord)) {
            System.out.println("Correct!");
            alert.playSound("correct");
            win();
            endGame();
        } else if(alreadyGuessed.contains(userInput)){
            System.out.println("You already guessed that letter.");
            alert.playSound("wrong");
            printGallows();
            System.out.println(curDash);
        } else if (secretWord.contains(userInput)) {
            System.out.println("That letter IS in the word!");
            alert.playSound("correct");
            alreadyGuessed.add(userInput);
            printGallows();
            updateDash(secretWord, curDash, userInput);
            System.out.println(curDash);
            if (curDash.equals(secretWord)) {
                win();
                endGame();
            }
        } else {
            alert.playSound("wrong");
            System.out.println("Not in word!");
            alreadyGuessed.add(userInput);
            guessesLeft--;
            editGallows();
        }
    }

    public void win() {
        System.out.println("YOU WIN!!!!");
    }

    public void editGallows() {
        if (gallows[6].equals(hangmanBody[0])) {
            lose();
            endGame();
        } else {
            switch (guessesLeft) {
                case 1:
                    gallows[6] = hangmanBody[0];
                    break;
                case 2:
                    gallows[6] = hangmanBody[1];
                    break;
                case 3:
                    gallows[5] = hangmanBody[5];
                    break;
                case 4:
                    gallows[4] = hangmanBody[2];
                    break;
                case 5:
                    gallows[4] = hangmanBody[3];
                    break;
                case 6:
                    gallows[4] = hangmanBody[5];
                    break;
                case 7:
                    gallows[3] = hangmanBody[4];
                    break;
            }
            printGallows();
            System.out.println(curDash);
        }
    }

    public void lose() {
        System.out.println("YOU LOSE ;-;");
    }

    public void endGame() {
        System.out.println("The word was: " + secretWord);
        setGallows();
        guessesLeft = 8;
        state = 1;
        System.out.println("Press Y to restart, or Q to quit");


    }

    public void updateDash(String answer, String dash, String guess) {
        String result = "";
        for (int i = 0; i < answer.length(); i++) {
            if (guess.equals(answer.substring(i, i + 1))) {
                result += guess;
            } else {
                result += dash.substring(i, i + 1);
            }
        }
        curDash = result;
    }

}
