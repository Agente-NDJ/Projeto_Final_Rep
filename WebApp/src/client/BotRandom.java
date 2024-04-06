package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class BotRandom {

	private final static String DEFAULT_HOST = "localhost"; // Máquina onde reside o servidor 194.210.196.40 192.168.162.80
	private final static int DEFAULT_PORT = 5025; // porto onde o servidor está à espera


	public static void main(String[] args) {
		BotRandom bot = new BotRandom();
		bot.run();
	}

	public void run() {

		Socket socket = null;
		ObjectInputStream is = null;
		ObjectOutputStream os = null;
		boolean playerTurn = false;
		boolean endGame = false;

		try {
			// inicialização da socket do bot
			socket = new Socket(DEFAULT_HOST, DEFAULT_PORT);

			// Mostrar os parametros da ligação
			System.out.println("Ligação: " + socket);

			// Stream para escrita no socket
			os = new ObjectOutputStream(socket.getOutputStream());

			// Stream para leitura do socket
			is = new ObjectInputStream(socket.getInputStream());
			
			String username = "bot";
			String password = "bot";
			
            // Envia as credenciais para o servidor
            os.writeObject(username);
            os.writeObject(password);

            // Recebe a resposta do servidor
            String statusLogin = (String) is.readObject();
            System.out.println("STATUS LOGIN: " + statusLogin);
			
            
            int maxInput = (int) is.readObject();
            
            // Receive and display the current board from the server
            String board = (String) is.readObject();
            System.out.println();
            System.out.println(board);
            
	         // Game loop
            while (!endGame) {
            	                
            	// Mensagem com informação sobre a vez de jogar, tabuleiro ou fim de jogo
    			String status = (String) is.readObject();
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
                    Random random = new Random();
                    int row = random.nextInt(maxInput + 1);
                    int col = random.nextInt(maxInput + 1);

                    // Send the move to the server
                    os.writeObject(row);
                    os.writeObject(col);

	                // Receive game status message from the server
	                String statusPlay = (String) is.readObject();
	                System.out.println(statusPlay);
	                
	
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
