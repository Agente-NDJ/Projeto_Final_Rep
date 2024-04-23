package main.java.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

public class Client {

	private final static String DEFAULT_HOST = "localhost"; // Máquina onde reside o servidor 194.210.196.40 192.168.162.80
	private final static int DEFAULT_PORT = 5025; // porto onde o servidor está à espera


	public static void main(String[] args) {

		Client player = new Client();
		player.run();
	}

	public void run() {

		Socket socket = null;
		ObjectInputStream is = null;
		ObjectOutputStream os = null;
		boolean playerTurn = false;
		boolean endGame = false;

		try {
			// inicialização da socket do jogador
			socket = new Socket(DEFAULT_HOST, DEFAULT_PORT);

			// Mostrar os parametros da ligação
			System.out.println("Ligação: " + socket);

			// Stream para escrita no socket
			os = new ObjectOutputStream(socket.getOutputStream());

			// Stream para leitura do socket
			is = new ObjectInputStream(socket.getInputStream());
			
			// Scanner for user input
            Scanner scanner = new Scanner(System.in);
            
			// Mensagem com informação sobre a vez de jogar, tabuleiro ou fim de jogo
			String status = (String) is.readObject();
			System.out.println(status);
			
			if(status.startsWith("Escolha:")) {
			
				int choice = 0;
				
				while(choice != 1 && choice != 2) {
					choice = scanner.nextInt();
	    			
	    			if(choice != 1 && choice != 2) {
	    				System.out.println("Valor inválido. Introduza 1 para jogar contra outro jogador, 2 para jogar contra um bot");
	    			}
	    			
	    			else {
	    				os.writeObject(choice);
	    			}
				}
			}
			
			String username = "";
			String password = "";
			boolean success = false;
			
			while(username.equals("") && password.equals("") && !success) {
				
				// Pede os credenciais de login
				String[] credenciais = LoginClient.inputLogin();
				
				username = credenciais[0];
				password = credenciais[1];
				
                // Envia as credenciais para o servidor
                os.writeObject(username);
                os.writeObject(password);

                // Recebe a resposta do servidor
                String statusLogin = (String) is.readObject();
                System.out.println("STATUS LOGIN: " + statusLogin);
                
    			if(statusLogin.startsWith("Login efetuado com sucesso")) {
    				success = true;
    			}
			}

            int maxInput = (int) is.readObject();
            
            // Receive and display the current board from the server
            String board = (String) is.readObject();
            System.out.println();
            System.out.println(board);
            
	         // Game loop
            while (!endGame) {
            	
            	// Mensagem com informação sobre a vez de jogar, tabuleiro ou fim de jogo
    			status = (String) is.readObject();
    			System.out.println(status);
    			
    			
    			// Se for a sua vez de jogar
    			if(status.startsWith("É a sua vez")) {
    				playerTurn = true;
    			}
    			
                // Se o jogo tiver acabado
                else if (status.startsWith("Fim")) {
                    endGame = true;
                    playerTurn = false;
                }
    			
    			// Se não for a sua vez de jogar
    			else {
    				playerTurn = false;
    			}
    			
    			if(playerTurn) {
    				
    				// Jogador introduz linha e coluna da jogada
    				int row = -1;
    				int col = -1;
                    
                    
                    while (row < 0 || row > maxInput) { 
                        System.out.println("Introduza a linha (0-" + maxInput + "):");
                        if (scanner.hasNextInt()) {
                            row = scanner.nextInt();
                            if (row < 0 || row > maxInput) {
                                System.out.println("Linha inválida. Introduza um número entre 0 e " + maxInput);
                            }
                        } else {
                            System.out.println("Input inválido. Introduza o número da linha onde quer jogar.");
                            scanner.nextLine(); // Clear the buffer
                        }
                    }
                    
                    while (col < 0 || col > maxInput) { 
                        System.out.println("Introduza a coluna (0-" + maxInput + "):");
                        if (scanner.hasNextInt()) {
                        	col = scanner.nextInt();
                            if (col < 0 || col > maxInput) {
                                System.out.println("Coluna inválida. Introduza um número entre 0 e " + maxInput);
                            }
                        } else {
                            System.out.println("Input inválido. Introduza o número da coluna onde quer jogar.");
                            scanner.nextLine(); // Clear the buffer
                        }
                    }

                    // Send the move to the server
                    os.writeObject(row);
                    os.writeObject(col);

	                // Mensagem com informação da última jogada
	                String statusPlay = (String) is.readObject();
	                System.out.println("STATUS PLAY: " + statusPlay);
	                
	
	                if(statusPlay.startsWith("Já existe")) {
	                	playerTurn = true;
	                }
	                                
	                else {
	                	playerTurn = !playerTurn;
	                    // Receive and display the current board from the server
	                    board = (String) is.readObject();
	                    System.out.println();
	                    System.out.println(board);
	                }
    			}
            }
            
            //String endStatus = (String) is.readObject();
            //System.out.println(endStatus);

		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Erro na ligação " + e.getMessage());
			e.printStackTrace();
		} finally {
			// No fim de tudo, fechar os streams e o socket
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
			}
		}
	}
}