package tictactoe_game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToe implements ActionListener{

    final Random RANDOM = new Random();
    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel restartPanel = new JPanel();
    JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[9];
    JButton restartButton = new JButton();
    Color originalColor;
    public boolean player1_turn;


    TicTacToe() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.getContentPane().setBackground(new Color(35,35,35));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textfield.setFont(new Font("Ink Free",Font.BOLD,75));
        textfield.setText("Tic-Tac-Toe");
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setOpaque(true);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 100);

        buttonPanel.setLayout(new GridLayout(3,3));
        buttonPanel.setBackground(new Color(150,150,150));
        for (int i=0;i<9;i++) {
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli",Font.BOLD,120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttons[i].setEnabled(false);
        }
        originalColor = buttons[0].getBackground();
        titlePanel.add(textfield);
        frame.add(titlePanel,BorderLayout.NORTH);
        frame.add(buttonPanel);

        restartPanel.setLayout(new BorderLayout());
        restartPanel.setBounds(0, 0, 800, 100);
        frame.add(restartPanel,BorderLayout.SOUTH);

        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton button : buttons) {
            if (e.getSource() == button) {
                if(player1_turn) {
                    if (button.getText() == "") {
                        button.setForeground(new Color(255,0,0));
                        button.setText("X");
                        player1_turn = false;
                        textfield.setText("O turn");
                    }
                } else {
                    if (button.getText() == "") {
                        button.setForeground(new Color(0,0,255));
                        button.setText("O");
                        player1_turn = true;
                        textfield.setText("X turn");
                    }
                }
                checkIfPlayersWin();
            }


        }
        if(e.getSource() == restartButton) {

            for (int i=0;i<9;i++) {
                buttons[i].setText("");
                buttons[i].setBackground(originalColor);
            }

            restartPanel.remove(restartButton);
            firstTurn();
        }
    }

    public void firstTurn() {

        textfield.setBackground(new Color(25,25,25));
        textfield.setForeground(new Color(25,255,0));

        for (int i=0;i<9;i++) {
            buttons[i].setEnabled(true);
        }

        if(RANDOM.nextInt(2)==0) {
            player1_turn = true;
            textfield.setText("X turn");
        } else {
            player1_turn = false;
            textfield.setText("O turn");
        }


    }

    public boolean checkIfPlayersWin() {

        if(Arrays.stream(getHorizontalCombo("X")).anyMatch(x -> x == 3)) {
            onePlayerWins("X",locateHorizontalStrip("X",3).get(0),locateHorizontalStrip("X",3).get(1),locateHorizontalStrip("X",3).get(2));
            return true;
        }

        if(Arrays.stream(getVerticalCombo("X")).anyMatch(x -> x == 3)) {
            onePlayerWins("X",locateVerticalStrip("X",3).get(0),locateVerticalStrip("X",3).get(1),locateVerticalStrip("X",3).get(2));
            return true;
        }

        if(Arrays.stream(getDiagonalCombo("X")).anyMatch(x -> x == 3)) {
            onePlayerWins("X",locateDiagonalStrip("X",3).get(0),locateDiagonalStrip("X",3).get(1),locateDiagonalStrip("X",3).get(2));
            return true;
        }


        if(Arrays.stream(getHorizontalCombo("O")).anyMatch(x -> x == 3)) {
            onePlayerWins("O",locateHorizontalStrip("O",3).get(0),locateHorizontalStrip("O",3).get(1),locateHorizontalStrip("O",3).get(2));
            return true;
        }

        if(Arrays.stream(getVerticalCombo("O")).anyMatch(x -> x == 3)) {
            onePlayerWins("O",locateVerticalStrip("O",3).get(0),locateVerticalStrip("O",3).get(1),locateVerticalStrip("O",3).get(2));
            return true;
        }

        if(Arrays.stream(getDiagonalCombo("O")).anyMatch(x -> x == 3)) {
            onePlayerWins("O",locateDiagonalStrip("O",3).get(0),locateDiagonalStrip("O",3).get(1),locateDiagonalStrip("O",3).get(2));
            return true;
        }


        if (checkIfDraw()) {
            endGame();
            return true;
        }

        return false;

    }

    public boolean checkIfDraw() {
        int turn = 0;
        for (JButton button : buttons) {
            if (button.getText() == "X" || button.getText() == "O") {
                turn++;
            }
        }
        if (turn == 9) {
            return true;
        }
        return false;
    }

    public int[] getHorizontalCombo(String player) {
        int[] victoryPoint = new int[3];
        for (int row=0;row<7;row+=3) {
            if(buttons[row].getText() == player) {
                victoryPoint[row/3]++;
            }
            if(buttons[row+1].getText() == player) {
                victoryPoint[row/3]++;
            }
            if(buttons[row+2].getText() == player) {
                victoryPoint[row/3]++;
            }
        }
        return victoryPoint;
    }

    public int[] getVerticalCombo(String player) {
        int[] victoryPoint = new int[3];
        for (int column=0;column<3;column++) {
            if(buttons[column].getText() == player) {
                victoryPoint[column]++;
            }
            if(buttons[column+3].getText() == player) {
                victoryPoint[column]++;
            }
            if(buttons[column+6].getText() == player) {
                victoryPoint[column]++;
            }
        }
        return victoryPoint;
    }

    public int[] getDiagonalCombo(String player) {
        int[] victoryPoint = new int[2];
        for (int box=0;box<9;box+=4) {
            if (buttons[box].getText() == player) {
                victoryPoint[0]++;
            }

        }

        for (int box=2;box<7;box+=2) {
            if (buttons[box].getText() == player) {
                victoryPoint[1]++;
            }
        }

        return victoryPoint;
    }

    public List<Integer> locateHorizontalStrip(String player, int victoryPoint) {
        List<Integer> blocks = new ArrayList<>();

        for (int i=0;i<3;i++) {
            if (getHorizontalCombo(player)[i] == victoryPoint) {
                blocks.add(i*3);
                blocks.add(i*3+1);
                blocks.add(i*3+2);
            }
        }
        return blocks;
    }

    public List<Integer> locateVerticalStrip(String player, int victoryPoint) {
        List<Integer> blocks = new ArrayList<>();

        for (int i=0;i<3;i++) {
            if (getVerticalCombo(player)[i] == victoryPoint) {
                blocks.add(i);
                blocks.add(i+3);
                blocks.add(i+6);
            }
        }
        return blocks;
    }

    public List<Integer> locateDiagonalStrip(String player, int victoryPoint) {
        List<Integer> blocks = new ArrayList<>();

        if (getDiagonalCombo(player)[0] == victoryPoint) {
            blocks.add(0);
            blocks.add(4);
            blocks.add(8);
        }

        if (getDiagonalCombo(player)[1] == victoryPoint) {
            blocks.add(2);
            blocks.add(4);
            blocks.add(6);
        }

        return blocks;
    }

    public void onePlayerWins(String player,int a, int b, int c) {
        if (player == "X") {
            buttons[a].setBackground(new Color(255,0,0));
            buttons[b].setBackground(new Color(255,0,0));
            buttons[c].setBackground(new Color(255,0,0));

            textfield.setForeground(new Color(255,0,0));
            textfield.setText("X wins");
        }
        if (player == "O") {
            buttons[a].setBackground(new Color(0,0,255));
            buttons[b].setBackground(new Color(0,0,255));
            buttons[c].setBackground(new Color(0,0,255));

            textfield.setForeground(new Color(0,0,255));
            textfield.setText("O wins");
        }

        endGame();
    }

    public void endGame() {

        for (JButton button : buttons) {
            button.setEnabled(false);
        }

        restartPanel.add(restartButton);
        restartButton.setFont(new Font("MV Boli",Font.BOLD,50));
        restartButton.setForeground(new Color(25,25,25));
        restartButton.setText("Click to restart the game");
        restartButton.setFocusable(false);
        restartButton.addActionListener(this);

    }


    public JButton[] getButtons() {
        return buttons;
    }

    public boolean getTurn() {
        return player1_turn;
    }

}
