package main.java.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.database.BCrypt;
import main.java.database.Configura;
import main.java.database.Manipula;

public class Login {

    public static boolean login(String username, String password) {

        Manipula dados = new Manipula(new Configura());

        ResultSet rs = dados.getResultado("SELECT hash_password FROM ProjetoFinal.jogador"
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


        if(BCrypt.checkpw(password, hash)) {
            return true;
        }

        return false;
    }
}