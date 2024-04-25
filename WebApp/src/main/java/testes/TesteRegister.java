package testes;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.BCrypt;
import database.Configura;
import database.Manipula;

public class TesteRegister {
	
	public static void main(String[] args) {
Manipula dados = new Manipula(new Configura());
		
		String nome = "Miguelas";
		
		String username = "ReiDelas";
		
		String password = "Domgula";
		
		System.out.println("ENTROU");
		
		try {
			if(usernameExists(dados, username)) {
				System.out.println("Já existe um utilizador com o nome de utilizador introduzido");


			}
			
			else if(nome.trim().equals("") || username.trim().equals("") || password.trim().equals("")) {
				System.out.println("Os dados introduzidos devem conter caractéres diferentes de espaços.");;
			}
				

			else {
				
				String hash_password = BCrypt.hashpw(password, BCrypt.gensalt());
				
				System.out.println("nome: " + nome);
				System.out.println("username: " + username);
				System.out.println("password: " + password);
				System.out.println("hash: " + hash_password);
					
				dados.xDirectiva("insert into projetoFinal.jogador(nome, username, hash_password) values" +
						"('"+nome+"', '"+username+"', '"+hash_password+"');");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dados.desligar();
	}
	
	
    private static boolean usernameExists(Manipula dados, String username) throws SQLException {
  
        ResultSet rs = dados.getResultado("SELECT id FROM projetoFinal.jogador WHERE username = '"+username+"';");
        
        return rs.next();
    }

}
