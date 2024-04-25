package testes;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.BCrypt;
import database.Configura;
import database.Manipula;

public class TesteBD {
	
	public static void main(String[] args) {
		Manipula dados = new Manipula(new Configura());
		
		String username = "d";

		ResultSet rs = dados.getResultado("SELECT hash_password FROM projetoFinal.jogador"
				+ " WHERE username = '" + username + "';");
		
		String hash = "";

		try {
			while(rs!=null && rs.next()) {
				hash = rs.getString("hash_password");
	        }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dados.desligar();
		
		System.out.println("hash: " + hash);
		
		
		if(BCrypt.checkpw("aaa", hash)) {
			System.out.println("Entrou");
		}
	}
	


}
