package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class HandleConnectionThreadWithBot extends Thread {
    private Socket connection;
	private int gameID = Server.gameID;
	private ObjectInputStream is;
	private ObjectOutputStream os;
	
	// Variáveis do jogo
	char player1piece = 'X';
	char player2piece = 'O';
	char winner = '-'; // variável atualizada para X ou O consoante o vencedor


    public HandleConnectionThreadWithBot(Socket connection, ObjectInputStream is, ObjectOutputStream os) {
        this.connection = connection;
        this.is = is;
        this.os = os;
        
    }
    
	public void run() {
		
		BotRandom bot = new BotRandom();

		try {
			// circuito virtual estabelecido: socket cliente na variavel newSock
			System.out.println("\nThread do Jogo: " + this.getId() + ", " + connection.getRemoteSocketAddress() + ", "
					+ connection.getRemoteSocketAddress());
			System.out.println("Game ID: " + gameID);

			// inicio do jogo
			Boolean success = false;
			Boolean successLogin1 = false;

			
			//Verificar login dos utilizadores:
			while(!successLogin1) {
			    String username = (String) is.readObject();
			    String password = (String) is.readObject();
			        
		        System.out.println("username: " + username);
		        System.out.println("password: " + password);
		            
		        successLogin1 = Login.login(username, password);
		            
		        if(successLogin1) {
		        	os.writeObject("Login efetuado com sucesso");
		        }
		            
		        else {
		            os.writeObject("Dados incorretos. Tente outra vez.");
		        }
			}


			// variáveis do jogo
			JogoGalo game = new JogoGalo();
			Boolean endGame = false;
			
			// Envia o tamanho do tabuleiro
			os.writeObject(game.getBoardSize()-1);
			
		    // Envia o tabuleiro para o jogador
		    os.writeObject(game.boardToString());


			while (!endGame) {
				
				// receber jogada player1
				while (!success) {
					
					os.writeObject("É a sua vez de jogar");

					
			        // Receive row and column from player 1
			        int row = (int) is.readObject();
			        int col = (int) is.readObject();
			        
		            System.out.println("row 1: " + row);
		            System.out.println("col 1: " + col);

			        // Check if the play is valid
			        if (game.checkValidPlay(row, col)) {
			            // If valid, make the play and set success to true
			            game.play(row, col, player1piece);
			            success = true;
			            os.writeObject("Jogou na linha " + row + " e na coluna "+  col);
			            
			            System.out.println("valid play 1");
			            

			        } else {
			            // If invalid, ask for another input
			            os.writeObject("Já existe uma peça na casa introduzida. Introduza uma jogada numa casa vazia");
			        }
					
					// Verificar fim do jogo
					if(game.checkEnd(row, col, player1piece)) {
						endGame = true;
						winner = game.winner;
					}
				}
				
				success = false;
				
				System.out.println("Fim da jogada1");
				System.out.println(game.boardToString());
				
			    // Envia o tabuleiro para o jogador
			    os.writeObject(game.boardToString());
			   
				if (!endGame) {
					
					os.writeObject("Aguarde pela jogada do oponente");
					
					// receber jogada jogador2
					while (!success) {

						int[] play = bot.makeMove(game.getBoardSize() - 1);
						
						int row = play[0];
						int col = play[1];
				        
			            System.out.println("row 2: " + row);
			            System.out.println("col 2: " + col);

				        // Check if the play is valid
				        if (game.checkValidPlay(row, col)) {
				            // If valid, make the play and set success to true
				            game.play(row, col, player2piece);
				            success = true;
				            
				            os.writeObject("Oponente jogou na linha " + row + " e na coluna "+  col);
				            
							// Verificar fim do jogo
							if(game.checkEnd(row, col, player2piece)) {
								endGame = true;
								winner = game.winner;
							}
				        }
					}
				success = false;
				
				System.out.println("Fim da jogada2 \n");
				System.out.println(game.boardToString());
				
			    // Send the current board to each player
			    os.writeObject(game.boardToString());
				}
			}
			
			String winnerStr = "";
			
			if(winner == 'X') {
				winnerStr = "Vitória do jogador 1!";
			}
			
			else if(winner == 'O') {
				winnerStr = "Vitória do jogador 2!";
			}
			
			else{
				winnerStr = "Empate!";
			}
			
			// enviou mensagem de fim de jogo ao Jogador1
			os.writeObject("Fim do jogo. " + winnerStr);
			os.flush();

		} catch (IOException e) {
			System.err.println("Erro na ligaçao " + connection + ": " + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Terminou a Thread " + this.getId() + ", " + connection.getRemoteSocketAddress());

			try {
				if (is != null)
					is.close();
				if (os != null)
					os.close();

				if (connection != null)
					connection.close();

			} catch (IOException e) {
			}
		}
	} // end run
}