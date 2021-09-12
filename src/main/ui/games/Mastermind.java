package ui.games;

import ui.TextOutputSticky;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//A console-based game of Mastermind

public class Mastermind extends Game {
    private List<String> playerGuess; // holds the guess of the user
    private int numGuesses; // number of guesses left for the user
    private String[] choiceOfNumbers; //stores the digits that can be used to create the answer depending on difficulty
    private String input; // gets user input
    private List<String> answer; // saves the answer for this Mastermind round
    private int difficulty; // saves the difficulty choice of the user
    private int state;
    private JButton yes;
    private JButton no;
    private TextOutputSticky outputStream;


    // EFFECTS: runs the Mastermind program
    public Mastermind() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 404");
        }
        gameFrame.setSize(600, 350);
        gameFrame.setLocation(500, 400);
        gameFrame.setBackground(Color.CYAN);
        gameFrame.setUndecorated(true);
        gameFrame.setVisible(true);
        console.setEditable(false);
        setUpMastermind();
    }

    // MODIFIES: this
    // EFFECTS: sets up the new frame for mastermind
    public void setUpMastermind() {
        input1.setCaretColor(Color.RED);
        setUpInputAction();
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(input1);
        scroll.setVisible(true);
        BoxLayout gameLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(gameLayout);
        gameFrame.add(panel);
        changeOutputStream();
    }

    // REQUIRES: a valid action from the user
    // MODIFIES: this
    // EFFECTS: responds appropriately to the user's input to be able to play the game, with the given state
    //          state = 0 -- just started game
    //          state = 1 -- playing game
    //          state = 2 -- end of game
    public void setUpInputAction() {
        input1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input = input1.getText();
                input1.setText("");
                if (state == 0) {
                    startRound();
                } else if (state == 1) {
                    playGame();
                } else if (state == 2) {
                    restart();
                }
            }
        });
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
        startMastermind();
    }

    // MODIFIES: this
    // EFFECTS: starts the game
    public void startMastermind() {
        numGuesses = 15;
        displayInstructions();
    }


    // MODIFIES: this
    // EFFECTS: sets up the number of guesses for the user and sets the state to 1
    public void setUpGuesses() {
        state = 1;
        System.out.println();
        System.out.println("Number of guesses left: " + numGuesses);
    }

    // MODIFIES: this
    // EFFECTS: runs the game in order
    public void playGame() {
        userGuess(); //sets player guess to guess in arraylist
        if (playerGuess.equals(answer)) { //true if matching
            System.out.println("YOU WIN! CONGRATS :)");
            afterGame();
        } else {
            provideFeedbackOfGuess(); //give colours
            numGuesses -= 1;
            if (numGuesses == 0) {
                System.out.println("Better luck next time :(");
                afterGame();
            } else {
                setUpGuesses();
            }
        }
    }

    // REQUIRES: numGuesses = 0
    // MODIFIES: this
    // EFFECTS: tells user the answer and asks if they want to play again
    //          sets state to 2
    public void afterGame() {
        System.out.println("The answer was " + answer);
        System.out.println("Enter any key to exit or Y to play again");
        state = 2;
    }

    // REQUIRES: user typed "y" to play again
    // MODIFIES: this
    // EFFECTS: reruns mastermind user wants to play again, or if not, closes the window
    public void restart() {
        String playAgain = input.toLowerCase();
        if (playAgain.equals("y")) {
            startMastermind();
        } else {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error 404");
            }
            gameFrame.dispose();

        }
    }


    // MODIFIES: this
    // EFFECTS: gives choice of viewing instructions and lets user choose difficulty of the game
    public void displayInstructions() {

        System.out.println("WELCOME TO MASTERMIND!");
        System.out.println("Would you like to see the instructions?");
        yes = new JButton("Yes");
        no = new JButton("No");

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                welcome();
                chooseDifficulty();
            }
        });
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseDifficulty();
            }
        });
        panel.add(yes);
        panel.add(no);
        gameFrame.add(panel);
        gameFrame.setVisible(true);


    }

    // MODIFIES: this
    // EFFECTS: gets user to choose the difficulty level
    public void chooseDifficulty() {
        System.out.println("Choose a difficulty level:");
        System.out.println("\te -> LEVEL: EASY (answer contains only digits from 1-5)");
        System.out.println("\ti -> LEVEL: INTERMEDIATE (digits 1-7)");
        System.out.println("\th -> LEVEL: HARD (digits 1-8)");
        System.out.println("To quit the game, press q.");
        state = 0;
        panel.remove(no);
        panel.remove(yes);
        panel.setVisible(true);
    }

    // REQUIRES: a valid user input
    // MODIFIES: this
    // EFFECTS: decides what to do with user input from difficulty choosing
    public void startRound() {
        setUp();
        if (state == 3) {
            gameFrame.dispose();
        } else {
            try {
                choiceOfNumbers[0].equals("1");
                createSecretNum();
                setUpGuesses();
            } catch (NullPointerException e) {
                System.out.println("How... how... how hard can it be to enter a letter correctly??");
                System.out.println("Only letters e/i/h/q are valid inputs.");
            }
        }
    }

    // EFFECTS: prints out instructions of Mastermind
    public void welcome() {
        System.out.println("THE GOAL:");
        System.out.println("The goal of this game is for you to successfully guess four");
        System.out.println("numbers ranging from 1-7 in the correct order.");
        System.out.println("The secret number will be a set of four numbers (all different)");
        System.out.println("----------------------------------------------------------------");
        System.out.println("HOW THE GAME WORKS:");
        System.out.println("Every time you enter a guess, I will respond with one of three colours.");
        System.out.println("BLACK: one of the numbers guessed is not AT ALL in the secret number.");
        System.out.println("WHITE: one of the numbers guessed IS in the secret number, but not in correct place.");
        System.out.println("RED: one of the numbers you guessed IS placed correctly AND is situated correctly.");
        System.out.println("However, the order that this is given to you is random. So even if you see BLACK in the");
        System.out.println("FIRST place, that doesn't mean that the FIRST number isn't in the secret number.");
        System.out.println("----------------------------------------------------------------");
        System.out.println("EXAMPLE:");
        System.out.println("The secret number is 1234. You guess 1256.");
        System.out.println("The 1 and 2 are in the secret number and in the right spot: RED.");
        System.out.println("The 5 and 6 are not in the secret number: BLACK.");
        System.out.println("The hint generated will be any order of 2 REDs and 2 BLACKs.");
        System.out.println();
        System.out.println("But if you had guessed 5612, my hint would have been any order of 2 WHITEs and 2 BLACKs.");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("LET'S PLAY!");
        System.out.println();
    }

    // REQUIRES: user input needs to be a non-empty string
    // MODIFIES: this
    // EFFECTS: sets choiceOfNumbers and appropriate difficulty
    //          based on the choice of the user, or allows them to quit the game, setting the state appropriately
    public void setUp() {
        String lowercase = input.toLowerCase();
        System.out.println();
        if (lowercase.equals("e")) {
            System.out.println("Loading level EASY...");
            choiceOfNumbers = new String[]{"1", "2", "3", "4", "5"};
            difficulty = 1;
        } else if (lowercase.equals("i")) {
            System.out.println("Loading level INTERMEDIATE...");
            choiceOfNumbers = new String[]{"1", "2", "3", "4", "5", "6", "7"};
            difficulty = 2;
        } else if (lowercase.equals("h")) {
            System.out.println("Loading level HARD...");
            choiceOfNumbers = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};
            difficulty = 3;
        } else if (lowercase.equals("q")) {
            System.out.println("You've quitted the game. Until next time!");
            state = 3;
        }
    }


    // MODIFIES: this
    // EFFECTS: creates the answer using random numbers based on the difficulty level chosen by user
    //          (choiceOfNumbers differs by difficulty)
    public void createSecretNum() {
        List<String> secretNum = new ArrayList<>();

        while (secretNum.size() <= 3) {
            int randomNumber = chooseRandomNum();
            if (!secretNum.contains(choiceOfNumbers[randomNumber])) {
                secretNum.add(choiceOfNumbers[randomNumber]);
            }
        }
        answer = secretNum;


    }

    // REQUIRES: difficulty needs to be an integer from 1-3 inclusive.
    // MODIFIES: this
    // EFFECTS: chooses random numbers to be used in determining the answer
    //          in the range based on the difficulty level chosen by user
    public int chooseRandomNum() {
        Random rand = new Random();
        if (difficulty == 1) {
            return rand.nextInt(5);
        } else if (difficulty == 2) {
            return rand.nextInt(7);
        } else {
            return rand.nextInt(8);
        }

    }

    // MODIFIES: this
    // EFFECTS: processes the user input and checks it through conditions to verify it's in correct format
    public void userGuess() {
        //resets playerGuess for every time user guesses the answer.
        playerGuess = new ArrayList<>();
        String user = input;
        System.out.println("Your guess: ");

        System.out.println(">>> " + user);
        if (user.equals("show answer")){
            System.out.println(answer);
        } else if (user.length() != 4) {
            alert.playSound("wrong");
            System.out.println("Your guess must consist of 4 numbers!");
            numGuesses++;
        } else {
            makeListForPlayer(user);
        }

    }


    // MODIFIES: this
    // EFFECTS: separates user input and processes it, verifying it meets the needed conditions
    public void makeListForPlayer(String s) {
        for (int i = 0; i < 4; i++) {
            String str = s.substring(i, i + 1);
            if (playerGuess.contains(str)) {
                System.out.println("You can only use each number once!");
                alert.playSound("wrong");
                numGuesses++;
                break;
            } else if (checkCorrectNum(str)) {
                break;
            }
            playerGuess.add(str);
        }
    }


    // MODIFIES: this
    // EFFECTS: checks that only numbers within the correct range for the difficulty have been entered by the user
    public boolean checkCorrectNum(String s) {
        if (difficulty == 1 && (s.equals("6") || s.equals("7") || s.equals("8") || s.equals("9") || s.equals("0"))) {
            System.out.println("You can only use the numbers 1-5 as guesses!");
            numGuesses++;
            alert.playSound("wrong");
            return true;
        } else if (difficulty == 2 && (s.equals("8") || s.equals("9") || s.equals("0"))) {
            System.out.println("You can only use the numbers 1-7 as guesses!");
            numGuesses++;
            alert.playSound("wrong");
            return true;
        } else if (difficulty == 3 && (s.equals("9") || s.equals("0"))) {
            System.out.println("You can only use the numbers 1-8 as guesses!");
            numGuesses++;
            alert.playSound("wrong");
            return true;
        } else {
            return checkForLetters(s);
        }
    }

    // REQUIRES: a single character (letter, number doesn't matter)
    // MODIFIES: this
    // EFFECTS: warns user if they try entering letters instead of numbers
    private Boolean checkForLetters(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("nUmBErs noT leTTeRs!!!");
            numGuesses++;
            alert.playSound("wrong");
            return true;
        }
        return false;
    }

    // REQUIRES: answer must be a list comprised of four 1 digit numbers (ex. ["1", "2", "3", "4"])
    //           playerGuess must be of same condition as answer
    // EFFECTS: compares the playerGuess to the answer and gives shuffled hints (so that the answer isn't obvious)
    //          to the user on whether they have the correct numbers or not
    //            - red: correct number is in the correct place
    //            - white: correct number is in wrong place
    //            - black: number not in answer
    public void provideFeedbackOfGuess() {
        List<String> feedback = new ArrayList<>();
        if (playerGuess.size() != 4) {
            System.out.println("");
        } else {
            alert.playSound("correct");
            for (String guess : playerGuess) {
                if (answer.contains(guess) && answer.indexOf(guess) == playerGuess.indexOf(guess)) {
                    feedback.add("RED");
                } else if (answer.contains(guess) && answer.indexOf(guess) != playerGuess.indexOf(guess)) {
                    feedback.add("WHITE");

                } else {
                    feedback.add("BLACK");
                }
            }
            Collections.shuffle(feedback);
            System.out.println(feedback);
        }
    }


}
