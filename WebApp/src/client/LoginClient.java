package client;

import java.util.Scanner;

public class LoginClient {
	
public static String[] inputLogin() {
	String username = "";
	String password = "";
	
	// Scanner for user input
    Scanner scanner = new Scanner(System.in);
    
    
    while (username.equals("")) { 
        System.out.println("Username:");
        username = scanner.nextLine().trim(); // Read the next line of input
        
        if (username.equals("")) {
            System.out.println("Introduza o seu username.");
        }
    } 
    
    while (password.equals("")) { 
        System.out.println("Password:");
        password = scanner.nextLine().trim(); // Read the next line of input
        
        if (password.equals("")) {
            System.out.println("Introduza a sua password.");
        }
    }
    
    String[] credenciais = new String[2];
    credenciais[0] = username;
    credenciais[1] = password;
    return credenciais;
}

}
