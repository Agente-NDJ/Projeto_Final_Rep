package main.java.server;

public class JogoGalo {

	protected Board board;
	
	private final int BOARD_SIZE = 3;

    protected char winner;
    
    protected static final char player1 = 'O', player2 = 'X', playableCell = '-';
    
    private int empty;

    public JogoGalo() {
    	this.board = new Board(BOARD_SIZE);
    	this.empty = BOARD_SIZE * BOARD_SIZE;
    	
    	board.printBoard();

        this.winner = '-';
    }
    
    public int getBoardSize() {
    	return this.BOARD_SIZE;
    }
    
    // Método que verifica se a jogada introduzida é válida 
    protected boolean checkValidPlay(int row, int col) {
    	
    	System.out.println("celula da jogada: " + this.board.getBoard()[row][col]);

    	if(this.board.getBoard()[row][col] == playableCell){
    		return true;
    	}

    	return false;
    }
    
    // Método que efetua uma jogada de um dado jogador e retorna true após uma jogada com 
    //sucesso. Caso não seja possível jogar na coluna selecionada pelo utilizador, retorna false
    
    protected boolean play(int row, int col, char player) {
    	
    	if(player != 'X' && player != 'O') {
    		return false;
    	}
    	
    	if(checkValidPlay(row, col)) {
    	
	        System.out.println("row :" + row);
	        System.out.println("col :" + col);
	        
	        board.registerPlay(row, col, player);
	        empty--;
	        return true;
    		
    	}

    	return false;
    }
    
    // Verificar se a última jogada deu vitória
    public boolean checkWin(int row, int col, char piece) {
        return checkVerticalWin(row, col, piece) ||
               checkHorizontalWin(row, col, piece) ||
               checkDiagonalWin(row, col, piece);
    }

    // Verifica por uma vitória vertical
    private boolean checkVerticalWin(int row, int col, char piece) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board.getBoard()[i][col] != piece) {
                return false;
            }
        }
        return true;
    }

    // Verifica por uma vitória horizontal
    private boolean checkHorizontalWin(int row, int col, char piece) {
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (board.getBoard()[row][j] != piece) {
                return false;
            }
        }
        return true;
    }

    // Verifica por uma vitória diagonal
    private boolean checkDiagonalWin(int row, int col, char piece) {
    	
        // verifica a diagonal principal
        if (row == col) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (board.getBoard()[i][i] != piece) {
                    return false;
                }
            }
            return true;
        }
        
        // Verifica a diagonal secundária
        if (row + col == BOARD_SIZE - 1) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (board.getBoard()[i][BOARD_SIZE - 1 - i] != piece) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    protected boolean checkEnd(int row, int col, char piece) {
    	
    	// Verificar se a última jogada deu vitória
    	if(checkWin(row, col, piece)) {
    		this.winner = piece;
    		return true;
    	}
    	
    	// Não há mais jogadas possíveis
    	if(empty == 0) {
    		this.winner = '-';
    		return true;
    	}
    	
    	return false;
    }
    

    protected String boardToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
        	
        	if(i == 0 ) {
        		
        		sb.append("  ");
        		
                for (int k = 0; k < BOARD_SIZE; k++) {
                    sb.append(k + " ");
                }
        		sb.append("\n");
        	}
        	
            for (int j = 0; j < BOARD_SIZE; j++) {
            	if(j == 0) {
            		sb.append(i + " ");
            	}
            	
                sb.append(board.getBoard()[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    protected void printBoard() {
    	System.out.println(boardToString());
    	//board.printBoard();
    }
}
