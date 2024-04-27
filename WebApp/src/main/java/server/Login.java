package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import database.Configura;
import database.Manipula;
import database.BCrypt;

public class Login {
	
	public static boolean login(String username, String password) {
		
		Manipula dados = new Manipula(new Configura());

		ResultSet rs = dados.getResultado("SELECT hash_password FROM jogador"
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
		
		
		if(!hash.isEmpty() && BCrypt.checkpw(password, hash)) {
			return true;
		}
		
		return false;
	}
}
