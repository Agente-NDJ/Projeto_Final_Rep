package server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;

public class Server {

	public final static int DEFAULT_PORT = 5025;
	public static int gameID = 0;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(DEFAULT_PORT);

			Socket newSock1 = null;
			Socket newSock2 = null;
			while (true) {
				System.out.println("Servidor TCP concorrente aguarda ligacao no porto " + DEFAULT_PORT + "...");

				// Espera do Jogador1 entrar
				System.out.println("Espero pelo Jogador 1");
				newSock1 = serverSocket.accept();

				// Espera do Jogador2 entrar
				System.out.println("Espero pelo Jogador 2");
				newSock2 = serverSocket.accept();

				Thread t = new HandleConnectionThread(newSock1, newSock2);
				t.start();
				System.out.println("Começou o jogo: " + gameID);
				gameID++;
			}
		} catch (IOException e) {
			System.err.println("Excepção no servidor: " + e);
		}
	}
}

class HandleConnectionThread extends Thread {
	// registo dos utilizadores
	static Hashtable<String, Socket> active = new Hashtable<String, Socket>();

	private Socket connection1;
	private Socket connection2;
	private int gameID = Server.gameID;
	// Variáveis do jogo

	char player1piece = 'X';
	char player2piece = 'O';
	char winner = '-'; // variável atualizada para X ou O consoante o vencedor

	public HandleConnectionThread(Socket connection1, Socket connection2) {
		this.connection1 = connection1;
		this.connection2 = connection2;
	}
	
	public void run() {

		ObjectInputStream is1 = null;
		ObjectOutputStream os1 = null;

		ObjectInputStream is2 = null;
		ObjectOutputStream os2 = null;

		try {
			// circuito virtual estabelecido: socket cliente na variavel newSock
			System.out.println("\nThread do Jogo: " + this.getId() + ", " + connection1.getRemoteSocketAddress() + ", "
					+ connection2.getRemoteSocketAddress());
			System.out.println("Game ID: " + gameID);
			is1 = new ObjectInputStream(connection1.getInputStream());
			os1 = new ObjectOutputStream(connection1.getOutputStream());

			is2 = new ObjectInputStream(connection2.getInputStream());
			os2 = new ObjectOutputStream(connection2.getOutputStream());

			// inicio do jogo
			Boolean success = false;
			Boolean successLogin1 = false;
			Boolean successLogin2 = false;
			
			//Verificar login dos utilizadores:
			while(!successLogin1 || !successLogin2) {
				
				if(!successLogin1) {
			        // Receive row and column from player 1
			        String username = (String) is1.readObject();
			        String password = (String) is1.readObject();
			        
		            System.out.println("username: " + username);
		            System.out.println("password: " + password);
		            
		            successLogin1 = Login.login(username, password);
		            
		            if(successLogin1) {
		            	os1.writeObject("Login efetuado com sucesso");
		            }
		            
		            else {
		            	os1.writeObject("Dados incorretos. Tente outra vez.");
		            }
				}
				
				if(!successLogin2) {
			        // Receive row and column from player 1
			        String username = (String) is2.readObject();
			        String password = (String) is2.readObject();
			        
		            System.out.println("username: " + username);
		            System.out.println("password: " + password);
		            
		            successLogin2 = Login.login(username, password);
		            
		            if(successLogin2) {
		            	os2.writeObject("Login efetuado com sucesso");
		            }
		            
		            else {
		            	os2.writeObject("Dados incorretos. Tente outra vez.");
		            }
				}
			}

			// variáveis do jogo
			JogoGalo game = new JogoGalo();
			Boolean endGame = false;
			
			// Envia o tamanho do tabuleiro
			os1.writeObject(game.getBoardSize()-1);
			os2.writeObject(game.getBoardSize()-1);
			
		    // Send the current board to each player
		    os1.writeObject(game.boardToString());
		    os2.writeObject(game.boardToString());

			while (!endGame) {
				
				// receber jogada player1
				while (!success) {
					
					os1.writeObject("É a sua vez de jogar");
					os2.writeObject("Aguarde pela jogada do oponente");
					
			        // Receive row and column from player 1
			        int row = (int) is1.readObject();
			        int col = (int) is1.readObject();
			        
		            System.out.println("row 1: " + row);
		            System.out.println("col 1: " + col);

			        // Check if the play is valid
			        if (game.checkValidPlay(row, col)) {
			            // If valid, make the play and set success to true
			            game.play(row, col, player1piece);
			            success = true;
			            os1.writeObject("Jogou na linha " + row + " e na coluna "+  col);
			            os2.writeObject("Oponente jogou na linha " + row + " e na coluna "+  col);
			            
			            System.out.println("valid play 1");
			            

			        } else {
			            // If invalid, ask for another input
			            os1.writeObject("Já existe uma peça na casa introduzida. Introduza uma jogada numa casa vazia");
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
				
			    // Send the current board to each player
			    os1.writeObject(game.boardToString());
			    os2.writeObject(game.boardToString());
			    


				if (!endGame) {
					
					// receber jogada jogador2
					while (!success) {
						
						os2.writeObject("É a sua vez de jogar");
						os1.writeObject("Aguarde pela jogada do oponente");
						

						// Receive row and column from player 2
				        int row = (int) is2.readObject();
				        int col = (int) is2.readObject();
				        
			            System.out.println("row 2: " + row);
			            System.out.println("col 2: " + col);

				        // Check if the play is valid
				        if (game.checkValidPlay(row, col)) {
				            // If valid, make the play and set success to true
				            game.play(row, col, player2piece);
				            success = true;
				            
				            os2.writeObject("Jogou na linha " + row + " e na coluna "+  col);
				            os1.writeObject("Oponente jogou na linha " + row + " e na coluna "+  col);
				            System.out.println("valid play 2");

				        } else {
				            // If invalid, ask for another input
				            os2.writeObject("Já existe uma peça na casa introduzida. Introduza uma jogada numa casa vazia");
				        }
						
						// Verificar fim do jogo
						if(game.checkEnd(row, col, player2piece)) {
							endGame = true;
							winner = game.winner;
						}

					}
				success = false;
				
				System.out.println("Fim da jogada2 \n");
				System.out.println(game.boardToString());
				
			    // Send the current board to each player
			    os1.writeObject(game.boardToString());
			    os2.writeObject(game.boardToString());
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
			os1.writeObject("Fim do jogo. " + winnerStr);
			os1.flush();

			// enviou mensagem de fim de jogo ao Jogador2
			os2.writeObject("Fim do jogo. " + winnerStr);
			os2.flush();


		} catch (IOException e) {
			System.err.println("Erro na ligaçao " + connection1 + ": " + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Terminou a Thread " + this.getId() + ", " + connection1.getRemoteSocketAddress());

			try {
				if (is1 != null)
					is1.close();
				if (os1 != null)
					os1.close();
				if (is2 != null)
					is2.close();
				if (os2 != null)
					os2.close();

				if (connection1 != null)
					connection1.close();
				if (connection2 != null)
					connection2.close();
			} catch (IOException e) {
			}
		}
	} // end run
}
