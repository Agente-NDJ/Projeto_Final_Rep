package main.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.database.BCrypt;
import main.java.database.Configura;
import main.java.database.Manipula;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;



@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Manipula dados = new Manipula(new Configura());
		
		String nome = request.getParameter("nome");
		
		String username = request.getParameter("username");
		
		String password = request.getParameter("password");
			
		String erro = "";
		request.setAttribute("erro", erro);
		
		try {
			if(usernameExists(dados, username)) {
				erro = "Já existe um utilizador com o nome de utilizador introduzido";
				request.setAttribute("erro", erro);
				getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);

			}
			
			else if(nome.trim().equals("") || username.trim().equals("") || password.trim().equals("")) {
				erro = "Os dados introduzidos devem conter caractéres diferentes de espaços.";
				request.setAttribute("erro", erro);
				getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
			}
				

			else {
				
				String hash_password = BCrypt.hashpw(password, BCrypt.gensalt());
					
				dados.xDirectiva("insert into projetoFinal.jogador(nome, username, hash_password) values" +
						"('"+nome+"', '"+username+"', '"+hash_password+"');");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dados.desligar();
		response.setContentType("text/html; charset=ISO-8859-1");
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
    private boolean usernameExists(Manipula dados, String username) throws SQLException {
  
        ResultSet rs = dados.getResultado("SELECT id FROM projetoFinal.jogador WHERE username = '"+username+"';");
        
        return rs.next();
    }
}