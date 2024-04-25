package server;

import java.io.Serializable;

public class GameState implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char[][] board;
    private char currentPlayer;
    private boolean gameOver;
    private char winner;

    // Constructor, getters, setters, etc.
    // Constructor
    public GameState(char[][] board, char currentPlayer, boolean gameOver, char winner) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.gameOver = gameOver;
        this.winner = winner;
    }

    public char[][] getBoard() {
        return board;
    }
    
    public void setBoard(char[][] board) {
    	this.board = board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }
    
    public void setCurrentPlayer(char currentPlayer) {
    	this.currentPlayer = currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }
    
    public void setGameOver() {
    	this.gameOver = true;
    }

    public char getWinner() {
        return winner;
    }
    
    public void setWinner(char winner) {
    	this.winner = winner;
    }
}

