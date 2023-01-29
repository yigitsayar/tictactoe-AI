package tictactoe_game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

public class AI extends TicTacToe{

    boolean turnAI;// = true;
    String playAs;
    String opponent;

    public AI(String playAs){
        super();
        this.playAs = playAs;

        textfield.setText("tic-tac-toe");

        if (playAs == "X") {
            opponent = "O";
        } else if (playAs == "O") {
            opponent = "X";
        } else {
            //		error check whether playAs is not equal to "X" or "O".
        }

    }

    private void markBox(String player,JButton button) {
        if (player == "O") {
            button.setForeground(new Color(0,0,255));
        }
        if (player == "X") {
            button.setForeground(new Color(255,0,0));
        }

        button.setText(player);
    }

    private void executeAI() {

        if (!checkForAnyFinishingMoves(playAs)) {
            if (!checkForAnyFinishingMoves(opponent)) {
                if (!followStrategy()) {
                    useYourTurnRandomly();
                }
            }
        }
    }

    public void firstTurn() {

        textfield.setBackground(new Color(25,25,25));
        textfield.setForeground(new Color(25,255,0));

        for (int i=0;i<9;i++) {
            buttons[i].setEnabled(true);
        }

//        if(RANDOM.nextInt(2)==0) {
//            executeAI();
//        }
    }


    private boolean checkForAnyFinishingMoves(String player) {
        if(Arrays.stream(getHorizontalCombo(player)).anyMatch(x -> x == 2)) {
            for (int i=0;i<3;i++) {
                if (buttons[locateHorizontalStrip(player,2).get(i)].getText() == "") {
                    markBox(playAs,buttons[locateHorizontalStrip(player,2).get(i)]);
                    return true;
                }
            }
        }

        if(Arrays.stream(getVerticalCombo(player)).anyMatch(x -> x == 2)) {
            for (int i=0;i<3;i++) {
                if (buttons[locateVerticalStrip(player,2).get(i)].getText() == "") {
                    markBox(playAs,buttons[locateVerticalStrip(player,2).get(i)]);
                    return true;
                }
            }
        }

        if(Arrays.stream(getDiagonalCombo(player)).anyMatch(x -> x == 2)) {
            for (int i = 0; i < 3; i++) {
                if (buttons[locateDiagonalStrip(player, 2).get(i)].getText() == "") {
                    markBox(playAs, buttons[locateDiagonalStrip(player, 2).get(i)]);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean followStrategy() {
        //System.out.println("execution went through follow strategy");



        return false;
    }

    private void useYourTurnRandomly() {
        List<JButton> availableButtons = new ArrayList<>();

        for (int i=0;i<9;i++) {
            if (buttons[i].getText() == "" && buttons[i].isEnabled()) {
                availableButtons.add(buttons[i]);
            }
        }
        // have a look in this
        markBox(playAs,availableButtons.get(RANDOM.nextInt(availableButtons.size())));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton button : buttons) {
            if (e.getSource() == button) {
                // Player's turn
                if (button.getText() == "") {
                    markBox(opponent,button);

                    if (checkIfPlayersWin()) {
                        return;
                    }

                    // AI's turn
                    executeAI();
                    if (checkIfPlayersWin()) {
                        return;
                    }


                }


                // do not forget checkForAnyFinishingMoves(opponent) and checkForAnyFinishingMoves(playAs) will be swapped in the if statements hierarchy above




            }


        }
        if(e.getSource() == restartButton) {

            for (int i=0;i<9;i++) {
                buttons[i].setText("");
                buttons[i].setBackground(originalColor);
            }

            restartPanel.remove(restartButton);
            firstTurn();

            if (turnAI) {
                executeAI();
                System.out.println("AI first");
            }
            System.out.print(turnAI);
            turnAI = !turnAI;
            System.out.println(turnAI);
            System.out.println("----");
        }
    }

//    public void endGame() {
//
//        for (JButton button : buttons) {
//            button.setEnabled(false);
//        }
//
//        restartPanel.add(restartButton);
//        restartButton.setFont(new Font("MV Boli",Font.BOLD,50));
//        restartButton.setForeground(new Color(25,25,25));
//        restartButton.setText("Click to restart the game");
//        restartButton.setFocusable(false);
//        restartButton.addActionListener(this);
//
//
//
//    }

    public static void main(String[] args) {
        //TicTacToe game = new TicTacToe();
        AI playerAI = new AI("O");
    }


}
