package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.Random;

public class TicTacToeServer {
    private static final int PORT = 4096;
    private static final int MAX_PLAYERS = 2;
    private static ExecutorService executor = Executors.newFixedThreadPool(MAX_PLAYERS);
    private static ArrayList<PlayerHandler> players = new ArrayList<>();
    private static Random random = new Random();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Tic Tac Toe Server running on port " + PORT);

            while (players.size() < MAX_PLAYERS) {
                Socket playerSocket = serverSocket.accept();
                System.out.println("Player connected: " + playerSocket);

                PlayerHandler playerHandler = new PlayerHandler(playerSocket);
                players.add(playerHandler);

                executor.execute(playerHandler);

                if (players.size() == MAX_PLAYERS) {
                    // Assign opponents to each other
                    players.get(0).setOpponentHandler(players.get(1));
                    players.get(1).setOpponentHandler(players.get(0));
                    
                    // Randomly choose which player goes first
                    int firstPlayerIndex = random.nextInt(MAX_PLAYERS);
                    players.get(firstPlayerIndex).setTurnsAndSymbols(true, 'X', 'O');
                    
                    if(firstPlayerIndex == 0) {
                    	players.get(1).setTurnsAndSymbols(false, 'O', 'X');
                    }
                    
                    else {
                    	players.get(0).setTurnsAndSymbols(false, 'O', 'X');
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}

class PlayerHandler implements Runnable {
    private Socket playerSocket;
    private BufferedReader in;
    private PrintWriter out;
    private JogoGalo ticTacToe;
    private PlayerHandler opponentHandler; // Reference to opponent's handler
    private char playerSymbol; // 'X' or 'O'
    private char opponentSymbol;
    private GameState gameState; // Game state instance

    public PlayerHandler(Socket socket) {
        this.playerSocket = socket;
        this.ticTacToe = new JogoGalo(); // Initialize TicTacToe instance
        this.playerSymbol = 'Z';
        this.opponentSymbol = 'Z';
        this.gameState = new GameState(ticTacToe.getBoard(), 'X', false, '-'); // Initialize game state
    }

    public void setOpponentHandler(PlayerHandler opponentHandler) {
        this.opponentHandler = opponentHandler;
    }
    
    protected void setTurnsAndSymbols(boolean turn, char playerSymbol, char opponentSymbol) {
        this.playerSymbol = playerSymbol;
        this.opponentSymbol = opponentSymbol;
        
        if(turn) {
        	gameState.setCurrentPlayer(playerSymbol); // Update current player in game state
        }
        
        else {
        	gameState.setCurrentPlayer(opponentSymbol);
        }
    }

    
    @Override
    public void run() {
        try {
            // Initialize input and output streams for communication
            in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
            out = new PrintWriter(playerSocket.getOutputStream(), true);

            // Send initial game state to the player
            sendGameState();

            // Game loop
            while (!gameState.isGameOver()) {
                if (gameState.getCurrentPlayer() == playerSymbol) {
                	
                    // Read player's move from input stream
                    String move = in.readLine();
                    
                    if (move != null) {
                    	
                    	// Process player's move and update game state
                    	int[] moveCoords = processMove(move);
                        
                        boolean validMove = ticTacToe.play(moveCoords[0], moveCoords[1], this.playerSymbol);
                        
                        if (validMove) {

                            // Update game state with the new board state
                            gameState.setBoard(ticTacToe.getBoard());
                            
                            if (ticTacToe.checkEnd(moveCoords[0], moveCoords[1], playerSymbol)) {
                            	// Set the game over to true and set the winner
                                gameState.setGameOver();
                                gameState.setWinner(ticTacToe.getWinner());
                            }
                            
                            // Update the current player turn to the opponent
                            gameState.setCurrentPlayer(opponentSymbol);
                            
                            // updates opponent's game state
                            sendGameStateToOpponent(this.gameState);
                            
                            // Send updated game state to both players
                            sendGameState();
                            opponentHandler.sendGameState(); // Send to opponent as well
                            
                        }
                        else {
                            // Inform the player that the move is invalid and wait for a valid move
                            out.println("Invalid move. Try again.");
                        }
                        

                    }
                } else {
                    // Wait for the opponent's move without reading input
                    // This loop will effectively pause until it's the player's turn
                    Thread.sleep(100); // Add a small delay to reduce CPU usage
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                playerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void sendGameStateToOpponent(GameState gameState) {
        opponentHandler.receiveGameState(gameState);
    }
    
    public void receiveGameState(GameState gameState) {
        // Update the local game state with the received game state
        this.gameState = gameState;

        // Optionally, notify the player or perform any necessary actions
    }

    public void sendGameState() {
        // Construct the game state message in a formatted string
        String gameStateMessage = String.format("%s-%c-%b-%c",
                gameState.getBoard(), // Board
                gameState.getCurrentPlayer(), // Current player symbol
                gameState.isGameOver(), // Game over status
                gameState.getWinner()); // Winner symbol

        // Send the game state message to the player
        out.println(gameStateMessage);
    }

    private int[] processMove(String move) {
        // Parse player's move (e.g., "row,column") and update the TicTacToe board
        String[] coordinates = move.split("-");
        int row = Integer.parseInt(coordinates[0]);
        int col = Integer.parseInt(coordinates[1]);
        
        int[] moveArray = new int[2];
        moveArray[0] = row;
        moveArray[1] = col;
        return moveArray;
    }
}