// Nicole Wang
// TA: Jake Page
// CSE 123 Section BL
// Due Date: 12 April 2023
// Program Name: PaperTennis
// Class Comment: This program represents the PaperTennis game, implementing the
// AbstractStrategyGame interface.

import java.util.*;
import java.io.*;
public class PaperTennis implements AbstractStrategyGame {
    private char[] court;
    private boolean turn;
    private int startOne;
    private int startTwo;
    private int tennisIndex;
    private int inputOne;
    private int inputTwo;
    private int roundsPlayed;
    private int playerOnePoints;
    private int playerTwoPoints;

    // This method creates an instance of the PaperTennis game.

    public PaperTennis() {
        this.court = new char[]{' ', '-', '-', 'X', '-', '-', ' '};
        this.turn = true;
        this.startOne = 50;
        this.startTwo = 50;
        this.tennisIndex = 0;
        this.inputOne = 0;
        this.inputTwo = 0;
        this.roundsPlayed = 0;
        this.playerOnePoints = 0;
        this.playerTwoPoints = 0;
    }

    // This method gives instructions for the game.
    // Returns:
    //  - String result: the instructions of the game

    public String instructions() {
        String result = "";
        result += "The players start with 50 points each. At each move both players write down ";
        result += "how many of their points to spend on the next move. The player who wrote the ";
        result += "highest number moves the ball towards the other player. Both players then ";
        result += "subtract their number from their remaining points. ";
        result += "Play continues in this way until either the ball is knocked out of the court,";
        result += " or neither player has any points left. As in tennis, ";
        result += "the player who has the ball on their side at the end of the game loses.";
        return result;
    }

    // This method gives the game board in its current state.
    // Returns:
    //  - String: the representation of the game board

    public String toString() {
        return Arrays.toString(this.court);
    }

    // This method checks if a winner has been declared (or if the game is over).
    // Returns:
    //  - boolean: whether a winner has been declared
    // Post-Conditions:
    //  - If the players have not played three rounds, this method will return false.
    //  - If there is a result after three rounds, this method will return true.

    public boolean isGameOver() {
        return this.getWinner() >= 0;
    }

    // This method tallies points at the end of each round and resets the game for the next round.

    private void scorePoints() {
        if (this.tennisIndex > 2) {
            this.playerOnePoints += 2;
            reset();
        } else if (this.tennisIndex < -2) {
            this.playerTwoPoints += 2;
            reset();
        }
        if (this.startOne == 0 && this.startTwo == 0) {
            if (this.tennisIndex == 1 || this.tennisIndex == 2) {
                this.playerOnePoints++;
            } else if (this.tennisIndex == -1 || this.tennisIndex == -2) {
                this.playerTwoPoints++;
            }
            reset();
        }
    }

    // This method determines if the game is still in session, or if there is a winner/tie. No
    // winner will be declared until the end of all three rounds.
    // Post-Conditions:
    //  - If the rounds are still in session, the method will return -1.
    //  - If there is a tie, the method will return 0.
    //  - If player one wins (has more points total), the method will return 1.
    //  - If player two wins (has more points total), the method will return 2.

    public int getWinner() {
        if (this.roundsPlayed <= 3) {
            if (!this.turn) {
                return -1;
            }
            moveTennis();
            scorePoints();
        }
        if (this.playerOnePoints == this.playerTwoPoints && this.roundsPlayed == 3) {
            return 0;
        } else if (this.playerOnePoints > this.playerTwoPoints && this.roundsPlayed == 3) {
            return 1;
        } else if (this.playerOnePoints < this.playerTwoPoints && this.roundsPlayed == 3) {
            return 2;
        }
        return -1;
    }

    // This method alternates the player number each turn.
    // Returns:
    //  - int: the player number (1 or 2)

    public int getNextPlayer() {
        return turn ? 1 : 2;
    }

    // This method prompts and stores the player's chosen input number, as well as decrementing
    // their starting points with their input number. If the inputted number is invalid, the
    // method will continue to prompt the player to put in a valid number.
    // Parameter:
    //  - Scanner input: the user's input data
    // Pre-Conditions:
    //  - The inputted number must be a positive integer.
    //  - The inputted number must be in the range between 0 and the current score they are at.
    // Post-Conditions:
    //  - If pre-conditions are not met, the method will throw an Illegal Argument Exception.
    //  - If pre-conditions are met, the method will decrement the starting points and take turns
    //      between players.

    public void makeMove(Scanner input) {
        System.out.print("Input a number: ");
        int inputNumber = input.nextInt();
        if (inputNumber < 0) {
            throw new IllegalArgumentException();
        }
        if (this.turn) {
            this.startOne -= inputNumber;
            if (this.startOne < 0) {
                this.startOne += inputNumber;
                throw new IllegalArgumentException();
            }
            this.inputOne = inputNumber;
        } else {
            this.startTwo -= inputNumber;
            if (this.startTwo < 0) {
                this.startTwo += inputNumber;
                throw new IllegalArgumentException();
            }
            this.inputTwo = inputNumber;
        }
        this.turn = !this.turn;
    }

    // This method moves the tennis ball depending on the two input numbers. If player one inputs
    // a larger number, then the tennis ball is moved towards the right. If player two inputs a
    // larger number, then the tennis ball is moved towards the left. No changes will be made if
    // both input numbers are the same.

    private void moveTennis() {
        if (this.inputOne > this.inputTwo) {
            this.court[this.tennisIndex + 3] = '-';
            if (this.tennisIndex == -1) {
                this.tennisIndex += 2;
            } else {
                this.tennisIndex++;
            }
            if (this.tennisIndex <= 2) {
                this.court[this.tennisIndex + 3] = 'X';
            }
        } else if (this.inputTwo > this.inputOne) {
            this.court[this.tennisIndex + 3] = '-';
            if (this.tennisIndex == 1) {
                this.tennisIndex -= 2;
            } else {
                this.tennisIndex--;
            }
            if (this.tennisIndex >= -2) {
                this.court[this.tennisIndex + 3] = 'X';
            }
        }
    }

    // This method resets the game to its original state, preparing it for new rounds.

    private void reset() {
        this.court = new char[]{' ', '-', '-', 'X', '-', '-', ' '};
        this.turn = true;
        this.startOne = 50;
        this.startTwo = 50;
        this.tennisIndex = 0;
        this.inputOne = 0;
        this.inputTwo = 0;
        this.roundsPlayed++;
    }
}
