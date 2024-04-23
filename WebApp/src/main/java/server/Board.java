package main.java.server;

class Board {
	protected int BOARD_SIZE;
	private char[][] board;

	
	public Board(int BOARD_SIZE) {
		this.BOARD_SIZE = BOARD_SIZE;
		this.board = new char[BOARD_SIZE][BOARD_SIZE];
		initializeBoard();
	}
	

    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.board[i][j] = '-';
            }
        }
    }
	
	public char[][] getBoard() {
		return this.board;
	}
	
	public void registerPlay(int row, int col, char piece) {
		this.board[row][col] = piece;
		System.out.println("jogada registada: row="+row+", col ="+col+ "piece=" + piece + "board[row][col]="+piece);
	}
	

    public void printBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j]);
                if (j < BOARD_SIZE - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < BOARD_SIZE - 1) {
                System.out.println("---------");
            }
        }
    }
}
