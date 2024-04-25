package testes;

import database.BCrypt;

public class TesteHash {

	
	public static void main(String[] args) {
		String password = "afagwqsdf";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hashed); 
        
        String hashed2 = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hashed2);
        
        
        System.out.println(BCrypt.checkpw(password, hashed));
        System.out.println(BCrypt.checkpw(password, hashed2));
        
	}
}
