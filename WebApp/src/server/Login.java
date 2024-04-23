package server;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Configura;
import database.Manipula;

public class Login {
	
	public static boolean login(String username, String password) {
		
		Manipula dados = new Manipula(new Configura());
		
		ResultSet rs = dados.getResultado("SELECT id_jogador FROM jogador"
				+ " WHERE username = '" + username + "' AND hash_password = '" + password + "';");
		
		boolean resultado = false;
		
		try {
			if(rs!= null && rs.next()) {
				System.out.println(rs.getString("id_jogador"));
				resultado = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dados.desligar();
		
		
		return resultado;
	}

}
